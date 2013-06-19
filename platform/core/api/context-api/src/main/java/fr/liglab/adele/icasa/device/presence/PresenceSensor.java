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
package fr.liglab.adele.icasa.device.presence;

import fr.liglab.adele.icasa.device.GenericDevice;

/**
 * Service definition of a simple presence sensor device.
 * 
 * @author bourretp
 */
public interface PresenceSensor extends GenericDevice {

    /**
     * Service property indicating the current sensed presence.
     * 
     * <ul>
     * <li>This property is <b>mandatory</b></li>
     * <li>Type of values : <b><code>java.lang.Boolean</code></b></li>
     * <li>Description : value is <code>true</code> when a presence is sensed,
     * <code>false</code> otherwise.</li>
     * </ul>
     * 
     * @see #getSensedPresence()
     */
    String PRESENCE_SENSOR_SENSED_PRESENCE = "presenceSensor.sensedPresence";
    
    /**
     * Return the current presence sensed by this presence sensor.
     * 
     * @return the current presence sensed by this presence sensor.
     */
    boolean getSensedPresence();
}
