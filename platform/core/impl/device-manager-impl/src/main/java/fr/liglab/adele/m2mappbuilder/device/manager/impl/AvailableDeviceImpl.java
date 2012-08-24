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
package fr.liglab.adele.m2mappbuilder.device.manager.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import fr.liglab.adele.m2mappbuilder.application.Application;
import fr.liglab.adele.m2mappbuilder.common.StateVariable;
import fr.liglab.adele.m2mappbuilder.common.StateVariableExtender;
import fr.liglab.adele.m2mappbuilder.common.StateVariableListener;
import fr.liglab.adele.m2mappbuilder.common.impl.EntityImpl;
import org.omg.CORBA._PolicyStub;

import fr.liglab.adele.m2mappbuilder.device.manager.ApplicationDevice;
import fr.liglab.adele.m2mappbuilder.device.manager.AvailableDevice;
import fr.liglab.adele.m2mappbuilder.device.manager.DetailedFault;
import fr.liglab.adele.m2mappbuilder.device.manager.Device;
import fr.liglab.adele.m2mappbuilder.device.manager.DeviceProxy;
import fr.liglab.adele.m2mappbuilder.device.manager.DiscoveryRecord;
import fr.liglab.adele.m2mappbuilder.device.manager.Fault;
import fr.liglab.adele.m2mappbuilder.device.manager.KnownDevice;
import fr.liglab.adele.m2mappbuilder.device.manager.ProvidedDevice;
import fr.liglab.adele.m2mappbuilder.device.manager.Service;

/**
 * 
 * 
 * @author Thomas Leveque
 *
 */
public class AvailableDeviceImpl extends SynchronizedDevice implements AvailableDevice {

	private KnownDevice _knownDevice;

	public AvailableDeviceImpl(ProvidedDevice device) {
		super(device);
	}
	
	protected void customizeVariables() {
		replaceByDelegateVar(NAME_PROP_NAME);
		replaceByDelegateVar(VENDOR_PROP_NAME);
		replaceByDelegateAppendVar(FAULTS_PROP_NAME);
		replaceByDelegateVar(TYPE_PROP_NAME);
	}

	@Override
	public List<DetailedFault> getDetailedFaults() {
		return  getDelegateDevice().getDetailedFaults();
	}

	@Override
	public boolean hasExclusiveAccess() {
		return  getDelegateDevice().hasExclusiveAccess();//TODO
	}

	@Override
	public List<DiscoveryRecord> getDiscoveryRecords() {
		return Collections.emptyList(); //TODO
	}

	@Override
	public synchronized List<ApplicationDevice> getVisibleDevices() {
		if (_knownDevice == null)
			return null;
		
		return _knownDevice.getApplicationDevices();
	}

	@Override
	public synchronized Application getApplicationOwner() {
		if (_knownDevice == null)
			return null;
		
		return _knownDevice.getAppOwner();
	}

	@Override
	public List<DeviceProxy> getDeviceProxies() {
		List<DeviceProxy> proxies = new ArrayList<DeviceProxy>();
		Device delegateDev = getDelegateDevice();
		if (delegateDev != null)
			proxies.add(new DeviceProxy(delegateDev, null)); //TODO manage discovery records
		return proxies;
	}

	public synchronized void setKnownDevice(KnownDevice knownDevice) {
		_knownDevice = knownDevice;
	}

	@Override
	public synchronized KnownDevice getKnownDevice() {
		return _knownDevice;
	}

	@Override
	public Object getDeviceProxy(Class... interfaces) {
		//TODO use getDeviceProxies
		Device delegateDev = getDelegateDevice();
		for (Class interf : interfaces) {
			if (!(interf.isInstance(delegateDev)))
				return null;
		}
		
		return delegateDev;
	}
}
