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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.liglab.adele.icasa.application.Application;
import fr.liglab.adele.icasa.common.Attributable;
import fr.liglab.adele.icasa.common.StateVariable;
import fr.liglab.adele.icasa.common.StateVariableListener;
import fr.liglab.adele.icasa.common.impl.StateVariableImpl;
import fr.liglab.adele.icasa.common.impl.StateVariableProxy;
import fr.liglab.adele.icasa.device.manager.ApplicationDevice;
import fr.liglab.adele.icasa.device.manager.AvailableDevice;
import fr.liglab.adele.icasa.device.manager.KnownDevice;
import fr.liglab.adele.icasa.device.manager.Operation;
import fr.liglab.adele.icasa.device.manager.OperationParameter;
import fr.liglab.adele.icasa.device.manager.Service;
import fr.liglab.adele.icasa.device.manager.VariableLifeCycle;
import fr.liglab.adele.icasa.device.manager.impl.util.AppendFaultIfPossibleStateVar;
import fr.liglab.adele.icasa.device.manager.util.AbstractDevice;


/**
 * Implementation of known device.
 * 
 * @author Thomas Leveque
 *
 */
public class KnownDeviceImpl extends SynchronizedDevice implements KnownDevice {
	
	private Application _ownerApp;

	private Map<Application, ApplicationDevice> _visibleDevices = new HashMap<Application, ApplicationDevice>();
	
	public KnownDeviceImpl(AvailableDeviceImpl device) {
		super(device.getId(), device.getName(), device.getVendor(), device.getTypeId());
		addAvailableDevice(device);
	}
	
	protected void customizeVariables() {
		replaceByDelegateVar(NAME_PROP_NAME);
		replaceByDelegateVar(VENDOR_PROP_NAME);
		replaceByDelegateAppendVar(FAULTS_PROP_NAME);
		replaceByDelegateVar(TYPE_PROP_NAME);
		
		// availability attribute
		StateVariable originalVar = getInternalVariable(AVAILABLE_PROP_NAME);
		changeVariableImplem(new AvailabilityStateVar(originalVar, getAvailableDevice()));
	}

	@Override
	public AvailableDevice getAvailableDevice() {
		return (AvailableDevice) getDelegateDevice();
	}

	@Override
	public List<ApplicationDevice> getApplicationDevices() {
		return new ArrayList<ApplicationDevice>(_visibleDevices.values());
	}

	@Override
	public Application getAppOwner() {
		return _ownerApp;
	}

	@Override
	public boolean hasExclusiveAccess() {
		AvailableDevice device = getAvailableDevice();
		if (device == null)
			return false;
		
		return  device.hasExclusiveAccess();//TODO
	}

	public void addApplicationDevice(ApplicationDevice appDev) {
		synchronized (_visibleDevices) {
			_visibleDevices.put(appDev.getApplication(), appDev);
		}
	}
	
	public ApplicationDevice removeApplicationDevice(Application app) {
		synchronized (_visibleDevices) {
			return _visibleDevices.remove(app);
		}
	}

	@Override
	public ApplicationDevice getApplicationDevice(Application app) {
		if (app == null)
			throw new IllegalArgumentException("application parameter cannot be null.");
		
		synchronized(_visibleDevices) {
			return _visibleDevices.get(app);
		}
	}

	public void removeAvailableDevice() {
		setDelegateDevice(null);
	}

	public void addAvailableDevice(AvailableDeviceImpl device) {
		synchronized (_lockStructChanges) {
			device.setKnownDevice(this);
			setDelegateDevice(device);
		}
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (! (obj instanceof KnownDevice))
			return false;
		
		KnownDevice other = (KnownDevice) obj;
		return other.getId().equals(getId());
	}
	
}
