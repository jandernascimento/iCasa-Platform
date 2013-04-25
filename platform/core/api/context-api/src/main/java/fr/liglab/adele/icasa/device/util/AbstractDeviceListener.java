package fr.liglab.adele.icasa.device.util;

import fr.liglab.adele.icasa.device.DeviceListener;
import fr.liglab.adele.icasa.device.GenericDevice;

/**
 * Empty implementation of a device listener.
 *
 * @author Thomas Leveque
 */
public class AbstractDeviceListener implements DeviceListener {

    @Override
    public void deviceAdded(GenericDevice device) {
        //do nothing
    }

    @Override
    public void deviceRemoved(GenericDevice device) {
        //do nothing
    }

    @Override
    public void devicePropertyModified(GenericDevice device, String propertyName, Object oldValue) {
        //do nothing
    }

    @Override
    public void devicePropertyAdded(GenericDevice device, String propertyName) {
        //do nothing
    }

    @Override
    public void devicePropertyRemoved(GenericDevice device, String propertyName) {
        //do nothing
    }
}
