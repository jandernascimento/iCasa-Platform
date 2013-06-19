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
