package fr.liglab.adele.icasa.device.manager.impl;

import fr.liglab.adele.icasa.device.manager.DependAccessRights;
import fr.liglab.adele.icasa.device.manager.DeviceAccessRight;
import fr.liglab.adele.icasa.device.manager.DeviceDependency;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO
 */
public class DependAccessRightsImpl implements DependAccessRights {

    private final DeviceDependency _deviceDep;

    private final Set<DeviceAccessRight> _devAccessRights = new HashSet<DeviceAccessRight>();

    public DependAccessRightsImpl(DeviceDependency deviceDep) {
        _deviceDep = deviceDep;
    }

    public void addAccessRight(DeviceAccessRight deviceAR) {
       _devAccessRights.add(deviceAR);
    }

    @Override
    public DeviceDependency getDependency() {
        return _deviceDep;
    }

    @Override
    public Set<DeviceAccessRight> getAccessRights() {
        return _devAccessRights;
    }

    @Override
    public boolean isAllowed(Method method) {
        //TODO

        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isExclusive(Method method) {
        //TODO

        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
