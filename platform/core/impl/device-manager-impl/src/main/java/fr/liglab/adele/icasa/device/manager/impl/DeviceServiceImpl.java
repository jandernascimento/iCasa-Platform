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

import fr.liglab.adele.icasa.common.StateVariable;
import fr.liglab.adele.icasa.device.manager.Device;
import fr.liglab.adele.icasa.device.manager.Operation;
import fr.liglab.adele.icasa.device.manager.util.AbstractService;


public class DeviceServiceImpl extends AbstractService {
	
	public DeviceServiceImpl(String serviceId, Device device) {
		super(serviceId, device);
	}

	public void addVar(StateVariable var) {
		addStateVariable(var);
	}
	
	public void removeVar(StateVariable var) {
		removeStateVariable(var);
	}
	
	public void removeVar(String varName) {
		removeStateVariable(varName);
	}
	
	public void addOp(Operation op) {
		addOperation(op);
	}
}
