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

/**
 * Represents an event related to a device.
 * 
 * @author Thomas Leveque
 */
public class DeviceEvent {

	private String _propName;
	private String _description;
	private DeviceEventType _type;
	private GenericDevice _device;
	private Object _oldValue;

	/**
	 * Creates a DeviceEvent.
	 * 
	 * @param device Device associated to the event
	 * @param type Type of the event
	 * @param propName Property name
	 * @param oldValue Old property value
	 */
	public DeviceEvent(GenericDevice device, DeviceEventType type, String propName, Object oldValue) {
		_device = device;
		_propName = propName;
		_type = type;
		_oldValue = oldValue;
	}

	/**
	 * Creates a DeviceEvent
	 * 
	 * @param device Device associated to the event
	 * @param type Type of the event
	 * @param propName Property name
	 */
	public DeviceEvent(GenericDevice device, DeviceEventType type, String propName) {
		this(device, type, propName, null);
	}

	/**
	 * Creates a DeviceEvent
	 * 
	 * @param device Device associated to the event
	 * @param type Type of the event
	 */
	public DeviceEvent(GenericDevice device, DeviceEventType type) {
		this(device, type, null);
	}

	/**
	 * Sets the description of the event
	 * 
	 * @param description new event description
	 */
	public void setDescription(String description) {
		_description = description;
	}

	/**
	 * Gets the event description
	 * 
	 * @return The event description
	 */
	public String getDescription() {
		return _description;
	}

	/**
	 * Gets the event type
	 * 
	 * @return The event type
	 */
	public DeviceEventType getType() {
		return _type;
	}

	/**
	 * Gets the property name
	 * 
	 * @return The property name
	 */
	public String getPropertyName() {
		return _propName;
	}

	/**
	 * Gets the event associated device
	 * 
	 * @return The event associated device
	 */
	public GenericDevice getDevice() {
		return _device;
	}

	/**
	 * Gets the old value of the property modified
	 * 
	 * @return The old value of the property modified
	 */
	public Object getOldValue() {
		return _oldValue;
	}
}
