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
package fr.liglab.adele.icasa.device.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.liglab.adele.icasa.device.DeviceEvent;
import fr.liglab.adele.icasa.device.DeviceEventType;
import fr.liglab.adele.icasa.device.DeviceListener;
import fr.liglab.adele.icasa.device.GenericDevice;
import fr.liglab.adele.icasa.location.Zone;

/**
 * Abstract implementation of the {@link GenericDevice} interface that manages
 * the listeners addition, removal and notifications.
 * 
 * @author Gabriel Pedraza Ferreira
 */
public abstract class AbstractDevice implements GenericDevice {

	
	private final List<DeviceListener> m_listeners = new LinkedList<DeviceListener>();

	private Map<String, Object> _properties = new HashMap<String, Object>();

	public AbstractDevice() {
		setPropertyValue(GenericDevice.STATE_PROPERTY_NAME, GenericDevice.STATE_ACTIVATED);
		setPropertyValue(GenericDevice.FAULT_PROPERTY_NAME, GenericDevice.FAULT_NO);
	}

	@Override
	public Set<String> getProperties() {
		synchronized (_properties) {
			return _properties.keySet();
		}
	}

	@Override
	public Object getPropertyValue(String propertyName) {
		if (propertyName == null) {
			throw new NullPointerException("Null property name");
		}
		Object value = null;
		synchronized (_properties) {
			value = _properties.get(propertyName);
		}
		return value;
	}

	@Override
	public void setPropertyValue(String propertyName, Object value) {
		if (propertyName == null) {
			throw new NullPointerException("Null property name");
		}
		boolean modified = false;
		Object oldValue = null;
		synchronized (_properties) {
			oldValue = _properties.get(propertyName);
			if (oldValue != null) {
				// if the new value is equal to the previous one the property is not  set
				if (!oldValue.equals(value)) {
					_properties.put(propertyName, value);
					modified = true;
				}
			} else {
				if (value != null) {
					_properties.put(propertyName, value);
					modified = true;
				}
			}
		}

		if (modified)
			notifyListeners(new DeviceEvent(this, DeviceEventType.PROP_MODIFIED, propertyName, oldValue));
	}


	@Override
	public String getState() {
		return (String) getPropertyValue(STATE_PROPERTY_NAME);
	}

	@Override
	public void setState(String state) {
		setPropertyValue(STATE_PROPERTY_NAME, state);
	}

	@Override
	public String getFault() {
		return (String) getPropertyValue(FAULT_PROPERTY_NAME);
	}

	@Override
	public void setFault(String fault) {
		setPropertyValue(FAULT_PROPERTY_NAME, fault);
	}

	@Override
	public void addListener(DeviceListener listener) {
		if (listener == null) {
			return;
		}
		synchronized (m_listeners) {
			if (!m_listeners.contains(listener)) {
				m_listeners.add(listener);
			}
		}
	}

	@Override
	public void removeListener(DeviceListener listener) {
		synchronized (m_listeners) {
			m_listeners.remove(listener);
		}
	}

	/**
	 * Notify all listeners. In case of exceptions, exceptions are dumped to the
	 * standard error stream.
	 */
	protected void notifyListeners(DeviceEvent event) {
		List<DeviceListener> listeners;
		// Make a snapshot of the listeners list
		synchronized (m_listeners) {
			listeners = Collections.unmodifiableList(new ArrayList<DeviceListener>(m_listeners));
		}
		// Call all listeners sequentially
		for (DeviceListener listener : listeners) {
			try {
				if (DeviceEventType.ADDED.equals(event.getType())) {
					listener.deviceAdded(event.getDevice());
					continue;
				} else if (DeviceEventType.REMOVED.equals(event.getType())) {
					listener.deviceRemoved(event.getDevice());
					continue;
				} else if (DeviceEventType.PROP_ADDED.equals(event.getType())) {
					listener.devicePropertyAdded(event.getDevice(), event.getPropertyName());
					continue;
				} else if (DeviceEventType.PROP_REMOVED.equals(event.getType())) {
					listener.devicePropertyRemoved(event.getDevice(), event.getPropertyName());
					continue;
				} else if (DeviceEventType.PROP_MODIFIED.equals(event.getType())) {
					listener.devicePropertyModified(event.getDevice(), event.getPropertyName(), event.getOldValue());
					continue;
				}
			} catch (Exception e) {

				Exception ee = new Exception("Exception in device listener '" + listener + "'", e);
				ee.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see fr.liglab.adele.icasa.device.GenericDevice#enterInZones(java.util.List)
	 */
	@Override
	public void enterInZones(List<Zone> zones) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see fr.liglab.adele.icasa.device.GenericDevice#leavingZones(java.util.List)
	 */
	@Override
	public void leavingZones(List<Zone> zones) {
		// TODO Auto-generated method stub
		
	}

}
