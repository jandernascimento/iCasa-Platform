package fr.liglab.adele.icasa.device.util;

import fr.liglab.adele.icasa.device.DeviceListener;
import fr.liglab.adele.icasa.device.GenericDevice;
import fr.liglab.adele.icasa.location.LocatedDevice;
import fr.liglab.adele.icasa.location.LocatedDeviceListener;
import fr.liglab.adele.icasa.location.Position;

/**
 * Empty implementation of a located device listener.
 *
 * @author Thomas Leveque
 */
public class AbstractLocatedDeviceListener implements LocatedDeviceListener {


    @Override
    public void deviceAdded(LocatedDevice device) {
        //do nothing
    }

    @Override
    public void deviceRemoved(LocatedDevice device) {
        //do nothing
    }

    @Override
    public void deviceMoved(LocatedDevice device, Position oldPosition) {
        //do nothing
    }

    @Override
    public void devicePropertyModified(LocatedDevice device, String propertyName, Object oldValue) {
        //do nothing
    }

    @Override
    public void devicePropertyAdded(LocatedDevice device, String propertyName) {
        //do nothing
    }

    @Override
    public void devicePropertyRemoved(LocatedDevice device, String propertyName) {
        //do nothing
    }

    @Override
    public void deviceAttached(LocatedDevice container, LocatedDevice child) {
        //do nothing
    }

    @Override
    public void deviceDetached(LocatedDevice container, LocatedDevice child) {
        //do nothing
    }
}
