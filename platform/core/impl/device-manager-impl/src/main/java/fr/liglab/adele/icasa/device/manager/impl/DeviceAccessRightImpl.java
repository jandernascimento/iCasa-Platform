package fr.liglab.adele.icasa.device.manager.impl;

import fr.liglab.adele.icasa.device.manager.AccessRight;
import fr.liglab.adele.icasa.device.manager.DeviceAccessRight;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: thomas
 * Date: 21/05/13
 * Time: 16:44
 * To change this template use File | Settings | File Templates.
 */
public class DeviceAccessRightImpl implements DeviceAccessRight {

    private final Method _deviceMethod;
    private final AccessRight _accessRight;

    public DeviceAccessRightImpl(Method deviceMethod, AccessRight accessRight) {
        _deviceMethod = deviceMethod;
        _accessRight = accessRight;
    }

    @Override
    public Method getMethod() {
        return _deviceMethod;
    }

    @Override
    public AccessRight getAccessRight() {
        return _accessRight;
    }
}
