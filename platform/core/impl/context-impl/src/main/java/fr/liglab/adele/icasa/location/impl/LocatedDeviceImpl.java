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
package fr.liglab.adele.icasa.location.impl;

import java.rmi.server.LogStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import fr.liglab.adele.icasa.ContextManager;
import fr.liglab.adele.icasa.device.DeviceListener;
import fr.liglab.adele.icasa.device.GenericDevice;
import fr.liglab.adele.icasa.location.*;

public class LocatedDeviceImpl extends LocatedObjectImpl implements LocatedDevice, DeviceListener{

	private String m_serialNumber;

	private final List<LocatedDeviceListener> listeners = new ArrayList<LocatedDeviceListener>();
		
	private GenericDevice deviceComponent;
	
	private ContextManager manager;

    private String _type;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public LocatedDeviceImpl(String serialNumber, Position position, GenericDevice deviceComponent, String type, ContextManager manager) {
		super(position);
		m_serialNumber = serialNumber;
		this.deviceComponent = deviceComponent;			
		this.manager = manager;
        this._type = type;
	}

	@Override
	public String getSerialNumber() {
		return m_serialNumber;
	}

    @Override
    public String getType() {
        return _type;
    }

    @Override
	public Set<String> getProperties() {
		return deviceComponent.getProperties();
	}

	@Override
	public Object getPropertyValue(String propertyName) {		
		if (deviceComponent!=null)
			return deviceComponent.getPropertyValue(propertyName);
		return null;
	}

	@Override
	public void setPropertyValue(String propertyName, Object value) {
		
		if (deviceComponent==null)
			return;
		//TODO: Test which properties are readonly
		deviceComponent.setPropertyValue(propertyName, value);
	}

	@Override
	public void addListener(final LocatedDeviceListener listener) {
		if (listener == null) {
			throw new NullPointerException("listener");
		}
        lock.writeLock().lock();
        try{
    	    listeners.add(listener);
        }finally {
            lock.writeLock().unlock();
        }

	}

	@Override
	public void removeListener(final LocatedDeviceListener listener) {
		if (listener == null) {
			throw new NullPointerException("listener");
		}
        lock.writeLock().lock();
		try {
			listeners.remove(listener);
		}  finally {
            lock.writeLock().unlock();
        }
	}



	@Override
   public void setCenterAbsolutePosition(Position position) {
		Position oldPosition = getCenterAbsolutePosition();
		super.setCenterAbsolutePosition(position);
        List<LocatedDeviceListener> snapshotListener = getListenerCopy();
        // Listeners notification
		for (LocatedDeviceListener listener : snapshotListener) {
            try{
			    listener.deviceMoved(this, oldPosition);
            }catch (Exception ex){
                System.err.println("Listener in deviceMoved event has throw an exception: " + listener);
                ex.printStackTrace();
            }
		}
		
		// Computes the new location
		if (deviceComponent!=null) {
			Zone zone = manager.getZoneFromPosition(getCenterAbsolutePosition());
			String location = GenericDevice.LOCATION_UNKNOWN;
			if (zone!=null)			
				location = zone.getId();			
			deviceComponent.setPropertyValue(GenericDevice.LOCATION_PROPERTY_NAME, location);
		}
			
   }

    @Override
    protected void notifyAttachedObject(LocatedObject attachedObject) {
        LocatedDevice childDevice = null;
        if (attachedObject instanceof  LocatedDevice){
            childDevice = (LocatedDevice)attachedObject;
        } else {
            return; // If attached object is not a locatedDevice we do nothing.
        }
        List<LocatedDeviceListener> snapshotListener = getListenerCopy();
        for (LocatedDeviceListener listener : snapshotListener) {
            try{
                listener.deviceAttached(this, childDevice);
            }catch(Exception ex){
                System.err.println("Listener has throw an exception: " + listener);
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void notifyDetachedObject(LocatedObject attachedObject) {
        LocatedDevice childDevice = null;
        if (attachedObject instanceof  LocatedDevice){
            childDevice = (LocatedDevice)attachedObject;
        } else {
            return; // If attached object is not a locatedDevice we do nothing.
        }
        List<LocatedDeviceListener> snapshotListener = getListenerCopy();
        for (LocatedDeviceListener listener : snapshotListener) {
            try{
                listener.deviceDetached(this, childDevice);
            }catch(Exception ex){
                System.err.println("Listener has throw an exception: " + listener);
                ex.printStackTrace();
            }
        }
    }

    @Override
	public String toString() {
		return "Id: " + getSerialNumber() + " - Position: " + getCenterAbsolutePosition();
	}

	@Override
   public void enterInZones(List<Zone> zones) {			
		deviceComponent.enterInZones(zones);			
   }

	@Override
   public void leavingZones(List<Zone> zones) {
		deviceComponent.leavingZones(zones);	   
   }

    @Override
    public GenericDevice getDeviceObject() {
        return deviceComponent;
    }

    // --- Listeners methods -- //
	
	@Override
   public void deviceAdded(GenericDevice device) {
	   // TODO Auto-generated method stub
	   
   }

	@Override
   public void deviceRemoved(GenericDevice device) {
	   // TODO Auto-generated method stub
	   
   }

	@Override
   public void devicePropertyModified(GenericDevice device, String propertyName, Object oldValue) {
        List<LocatedDeviceListener> snapshotListener = getListenerCopy();
        for (LocatedDeviceListener listener : snapshotListener) {
            try{
                listener.devicePropertyModified(this, propertyName, oldValue);
            }catch(Exception ex){
                System.err.println("Listener has throw an exception: " + listener);
                ex.printStackTrace();
            }
        }
   }

	@Override
   public void devicePropertyAdded(GenericDevice device, String propertyName) {
        List<LocatedDeviceListener> snapshotListener = getListenerCopy();
        for (LocatedDeviceListener listener : snapshotListener) {
            try{
                listener.devicePropertyAdded(this, propertyName);
            }catch(Exception ex){
                System.err.println("Listener has throw an exception: " + listener);
                ex.printStackTrace();
            }
        }
    }

	@Override
   public void devicePropertyRemoved(GenericDevice device, String propertyName) {
        List<LocatedDeviceListener> snapshotListener = getListenerCopy();
        for (LocatedDeviceListener listener : snapshotListener) {
            try{
                listener.devicePropertyRemoved(this, propertyName);
            }catch(Exception ex){
                System.err.println("Listener has throw an exception: " + listener);
                ex.printStackTrace();
            }
        }
   }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocatedDeviceImpl that = (LocatedDeviceImpl) o;

        if (!_type.equals(that._type)) return false;
        if (!m_serialNumber.equals(that.m_serialNumber)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = m_serialNumber.hashCode();
        result = 31 * result + _type.hashCode();
        return result;
    }

    /**
     * Get a copy of the listener to iterate in it.
     * @return a copy of the listeners
     */
    private List<LocatedDeviceListener> getListenerCopy(){
        lock.readLock().lock();
        try {
            return new ArrayList<LocatedDeviceListener>(listeners);
        }finally {
            lock.readLock().unlock();
        }
    }

}
