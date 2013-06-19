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
package fr.liglab.adele.icasa.device.manager.impl;

/**
 * Allows a component to participate to device tracking.
 * 
 * @author Thomas Leveque
 *
 */
public interface DevicetrackerCustomizer {

	/**
	 * Called when a new device is detected.
	 * 
	 * @param event a device event
	 */
	public void addDevice(DeviceEvent event);
	
	/**
	 * Called when an existing device is modified.
	 * 
	 * @param event a device event
	 */
	public void updateDevice(DeviceEvent event);
	
	/**
	 * Called when an existing device is removed.
	 * 
	 * @param event a device event
	 */
	public void removeDevice(DeviceEvent event);
 }
