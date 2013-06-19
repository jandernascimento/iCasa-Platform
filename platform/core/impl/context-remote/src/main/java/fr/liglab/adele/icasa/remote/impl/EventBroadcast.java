/**
 *
 *   Copyright 2011-2012 Universite Joseph Fourier, LIG, ADELE team
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package fr.liglab.adele.icasa.remote.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;
import org.atmosphere.cpr.AtmosphereInterceptor;
import org.atmosphere.cpr.AtmosphereResponse;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.handler.OnMessage;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;
import org.barjo.atmosgi.AtmosphereService;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import fr.liglab.adele.icasa.ContextManager;
import fr.liglab.adele.icasa.clock.Clock;
import fr.liglab.adele.icasa.clock.ClockListener;
import fr.liglab.adele.icasa.listener.MultiEventListener;
import fr.liglab.adele.icasa.location.LocatedDevice;
import fr.liglab.adele.icasa.location.Position;
import fr.liglab.adele.icasa.location.Zone;
import fr.liglab.adele.icasa.remote.RemoteEventBroadcast;
import fr.liglab.adele.icasa.remote.util.IcasaJSONUtil;

@Component(name = "iCasa-event-broadcast")
@Provides(specifications = RemoteEventBroadcast.class)
@Instantiate(name = "iCasa-event-broadcast-1")
public class EventBroadcast extends OnMessage<String> implements RemoteEventBroadcast{

	@Property(name = "mapping", value = "/event")
	private String mapping;

	private final List<AtmosphereInterceptor> _interceptors = new ArrayList<AtmosphereInterceptor>();

	@Requires
	private AtmosphereService _atmoService;

	@Requires
	private HttpService _httpService;

	@Requires
	private ContextManager _ctxMgr;
	
	

	@Requires
	private Clock _clock;

	private Broadcaster _eventBroadcaster;

	private ICasaEventListener _iCasaListener;

	private ClockEventListener _clockListener;
	

	// private final BundleContext _context;

	public EventBroadcast(BundleContext context) {
		// _context = context;
	}

	@Validate
	protected void start() {

		// Register iCasa listeners
		_iCasaListener = new ICasaEventListener();
		_ctxMgr.addListener(_iCasaListener);
		

		_clockListener = new ClockEventListener();
		_clock.addListener(_clockListener);
		

		_eventBroadcaster = _atmoService.getBroadcasterFactory().get();

		// Register the server (itself)
		_interceptors.add(new AtmosphereResourceLifecycleInterceptor());
		_atmoService.addAtmosphereHandler(mapping, this, _eventBroadcaster, _interceptors);

		// Register the web client
		try {
			_httpService.registerResources("/event", "/web", null);
		} catch (NamespaceException e) {
			e.printStackTrace();
		}

	}

	@Invalidate
	protected void stop() {

		// Unregister iCasa listeners

		if (_iCasaListener != null) {
			_ctxMgr.removeListener(_iCasaListener);
			_iCasaListener = null;
		}
		if (_clockListener != null) {
			_clock.removeListener(_clockListener);
			_clockListener = null;
		}
		
		_atmoService.removeAtmosphereHandler(mapping);
		_interceptors.clear();

		_httpService.unregister("/event");

	}

	@Override
	public void onMessage(AtmosphereResponse atmosphereResponse, String s) throws IOException {
		atmosphereResponse.getWriter().write(s);
	}

	private UUID _lastEventId = UUID.randomUUID();

	private String generateUUID() {
		_lastEventId = UUID.randomUUID();
		return _lastEventId.toString();
	}

	public void sendEvent(String eventType, JSONObject event) {
		try {
			event.put("eventType", eventType);
			event.put("id", generateUUID());
			event.put("time", new Date().getTime());
			_eventBroadcaster.broadcast(event.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private class ICasaEventListener implements MultiEventListener {

		@Override
		public void deviceTypeRemoved(String deviceType) {
			JSONObject json = new JSONObject();
			try {
				json.put("deviceTypeId", deviceType);
				sendEvent("device-type-removed",json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void deviceTypeAdded(String deviceType) {
			JSONObject json = new JSONObject();
			try {
				json.put("deviceTypeId", deviceType);
				sendEvent("device-type-added", json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void deviceMoved(LocatedDevice device, Position position) {
			JSONObject json = new JSONObject();
			try {
				json.put("deviceId", device.getSerialNumber());
				json.put("device", IcasaJSONUtil.getDeviceJSON(device, _ctxMgr));
				sendEvent("device-position-update", json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void deviceAdded(LocatedDevice device) {
			JSONObject json = new JSONObject();
			try {
				json.put("deviceId", device.getSerialNumber());
				json.put("device", IcasaJSONUtil.getDeviceJSON(device, _ctxMgr));
				sendEvent("device-added", json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void deviceRemoved(LocatedDevice device) {
			JSONObject json = new JSONObject();
			try {
				json.put("deviceId", device.getSerialNumber());
				sendEvent("device-removed", json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void devicePropertyModified(LocatedDevice device, String propertyName, Object oldValue) {
			JSONObject json = new JSONObject();
			try {
				json.put("deviceId", device.getSerialNumber());
				json.put("device", IcasaJSONUtil.getDeviceJSON(device, _ctxMgr));
				sendEvent("device-property-updated", json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void devicePropertyAdded(LocatedDevice device, String propertyName) {
			JSONObject json = new JSONObject();
			try {
				json.put("deviceId", device.getSerialNumber());
				json.put("device", IcasaJSONUtil.getDeviceJSON(device, _ctxMgr));
				sendEvent("device-property-added", json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void devicePropertyRemoved(LocatedDevice device, String propertyName) {
			JSONObject json = new JSONObject();
			try {
				json.put("deviceId", device.getSerialNumber());
				json.put("device", IcasaJSONUtil.getDeviceJSON(device, _ctxMgr));
				sendEvent("device-property-removed", json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

        /**
         * Invoked when a device has been attached to another device
         *
         * @param container
         * @param child
         */
        @Override
        public void deviceAttached(LocatedDevice container, LocatedDevice child) {
            JSONObject json = new JSONObject();
            try {
                json.put("deviceId", container.getSerialNumber());
                json.put("container", IcasaJSONUtil.getDeviceJSON(container, _ctxMgr));
                json.put("child", IcasaJSONUtil.getDeviceJSON(child, _ctxMgr));
                sendEvent("device-attached-device", json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /**
         * * Invoked when a device has been detached from another device
         *
         * @param container
         * @param child
         */
        @Override
        public void deviceDetached(LocatedDevice container, LocatedDevice child) {
            JSONObject json = new JSONObject();
            try {
                json.put("deviceId", container.getSerialNumber());
                json.put("container", IcasaJSONUtil.getDeviceJSON(container, _ctxMgr));
                json.put("child", IcasaJSONUtil.getDeviceJSON(child, _ctxMgr));
                sendEvent("device-detached-device", json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        @Override
		public void zoneVariableAdded(Zone zone, String variableName) {
			JSONObject json = new JSONObject();
			try {
				json.put("zoneId", zone.getId());
				json.put("zone", IcasaJSONUtil.getZoneJSON(zone));
				sendEvent("zone-variable-added", json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void zoneVariableRemoved(Zone zone, String variableName) {
			JSONObject json = new JSONObject();
			try {
				json.put("zoneId", zone.getId());
				json.put("zone", IcasaJSONUtil.getZoneJSON(zone));
				sendEvent("zone-variable-removed", json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void zoneVariableModified(Zone zone, String variableName, Object oldValue) {
			JSONObject json = new JSONObject();
			try {
				json.put("zoneId", zone.getId());
				json.put("zone", IcasaJSONUtil.getZoneJSON(zone));
				sendEvent("zone-variable-updated", json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void zoneMoved(Zone zone, Position oldPosition) {
			JSONObject json = new JSONObject();
			try {
				json.put("zoneId", zone.getId());
				json.put("zone", IcasaJSONUtil.getZoneJSON(zone));
				sendEvent("zone-moved", json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void zoneResized(Zone zone) {
			JSONObject json = new JSONObject();
			try {
				json.put("zoneId", zone.getId());
				json.put("zone", IcasaJSONUtil.getZoneJSON(zone));
				sendEvent("zone-resized", json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void zoneParentModified(Zone zone, Zone oldParent) {
			JSONObject json = new JSONObject();
			try {
				json.put("zoneId", zone.getId());
				json.put("zone", IcasaJSONUtil.getZoneJSON(zone));
				sendEvent("zone-parent-updated", json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

        /**
         * Invoked when a device has been attached a zone
         *
         * @param container
         * @param child
         */
        @Override
        public void deviceAttached(Zone container, LocatedDevice child) {
            JSONObject json = new JSONObject();
            try {
                json.put("zoneId", container.getId());
                json.put("zone", IcasaJSONUtil.getZoneJSON(container));
                json.put("device", IcasaJSONUtil.getDeviceJSON(child, _ctxMgr));
                sendEvent("device-attached-zone", json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /**
         * * Invoked when a device has been detached from a zone
         *
         * @param container
         * @param child
         */
        @Override
        public void deviceDetached(Zone container, LocatedDevice child) {
            JSONObject json = new JSONObject();
            try {
                json.put("zoneId", container.getId());
                json.put("zone", IcasaJSONUtil.getZoneJSON(container));
                json.put("device", IcasaJSONUtil.getDeviceJSON(child, _ctxMgr));
                sendEvent("device-detached-zone", json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
		public void zoneAdded(Zone zone) {
			JSONObject json = new JSONObject();
			try {
				json.put("zoneId", zone.getId());
				json.put("zone", IcasaJSONUtil.getZoneJSON(zone));
				sendEvent("zone-added", json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void zoneRemoved(Zone zone) {
			JSONObject json = new JSONObject();
			try {
				json.put("zoneId", zone.getId());
				sendEvent("zone-removed", json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

	private class ClockEventListener implements ClockListener {

		@Override
		public void factorModified(int oldFactor) {
			sendClockModifiedEvent();
		}

		@Override
		public void startDateModified(long oldStartDate) {
			sendClockModifiedEvent();
		}

		@Override
		public void clockPaused() {
			sendClockModifiedEvent();
		}

		@Override
		public void clockResumed() {
			sendClockModifiedEvent();
		}

		@Override
		public void clockReset() {
			sendClockModifiedEvent();
		}

		private void sendClockModifiedEvent() {
			JSONObject json = new JSONObject();
			try {
				json.put("clock", IcasaJSONUtil.getClockJSON(_clock));
				sendEvent("clock-modified", json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}
	

}
