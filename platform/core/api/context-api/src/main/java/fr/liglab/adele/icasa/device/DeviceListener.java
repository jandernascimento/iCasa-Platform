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
package fr.liglab.adele.icasa.device;

import fr.liglab.adele.icasa.listener.IcasaListener;

/**
 * A listner to the events related with device modifications.
 * 
 * Events may be notified more than once per event. The only waranty is that at least one event will be sent to
 * listeners.
 * 
 * @author Gabriel Pedraza Ferreira
 * 
 */
public interface DeviceListener extends IcasaListener {

	/**
	 * Callback notifying the addition of a device to the platform.
	 * 
	 * @param device The device added. 
	 */
	public void deviceAdded(GenericDevice device);

	/**
	 * Callback notifying the elimination of a device to the platform.
	 * 
	 * @param device The device removed. 
	 */
	public void deviceRemoved(GenericDevice device);

	/**
	 * Callback notifying the modification of a property on the device listened.
	 * 
	 * @param device The device
	 * @param propertyName The name of the modified property
	 * @param oldValue The previous value of the property
	 */
	public void devicePropertyModified(GenericDevice device, String propertyName, Object oldValue);

	/**
	 * Callback notifying the addition of a property on the device listened.
	 *  
	 * @param device The device
	 * @param propertyName The name of the added property
	 */
	public void devicePropertyAdded(GenericDevice device, String propertyName);

	/**
	 * Callback notifying the elimination of a property on the device listened.
	 *  
	 * @param device The device
	 * @param propertyName The name of the removed property
	 */
	public void devicePropertyRemoved(GenericDevice device, String propertyName);
}
