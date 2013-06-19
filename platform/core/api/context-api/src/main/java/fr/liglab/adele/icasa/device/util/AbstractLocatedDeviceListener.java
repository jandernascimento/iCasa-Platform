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
