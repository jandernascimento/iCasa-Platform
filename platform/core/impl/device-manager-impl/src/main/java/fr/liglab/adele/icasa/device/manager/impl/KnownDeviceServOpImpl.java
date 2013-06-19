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

import java.util.List;


import fr.liglab.adele.icasa.device.manager.Device;
import fr.liglab.adele.icasa.device.manager.Operation;
import fr.liglab.adele.icasa.device.manager.OperationParameter;
import fr.liglab.adele.icasa.device.manager.ParameterType;
import fr.liglab.adele.icasa.device.manager.Service;
import fr.liglab.adele.icasa.device.manager.util.AbstractOperation;
import fr.liglab.adele.icasa.device.manager.util.OperationParameterImpl;

public class KnownDeviceServOpImpl extends AbstractOperation implements DelegateToDevice {
	
	private Device _device;
	
	protected Object _lockDelegate = new Object();

	public KnownDeviceServOpImpl(Operation delegateOp, Service service, Device delegateDevice) {
		super(delegateOp.getName(), service);
		setDelegateDevice(delegateDevice);
		
		for (OperationParameter delegateParam : delegateOp.getParameters()) {
			OperationParameter param = new OperationParameterImpl(delegateParam);
			addParameter(param);
		}
	}

	@Override
	public Object invoke(Object... args) {
		synchronized (_lockDelegate) {
			if (getDelegateDevice() == null)
				throw new IllegalStateException(
						"Cannot call delegate from this operation.");

			Service delegateServ = getDelegateDevice().getService(getService()
					.getTypeId());

			List<OperationParameter> delegateParams = getParameters();
			Class[] paramTypes = new Class[delegateParams.size()];
			for (int i = 0; i < delegateParams.size(); i++) {
				OperationParameter delegateParam = delegateParams.get(i);
				paramTypes[i] = delegateParam.getValueType();
			}
			Operation delegateOp = delegateServ.getOperation(getName(),
					paramTypes);

			return delegateOp.invoke(args);
		}
	}

	@Override
	public void invoke(OperationParameter... params) {
		final List<OperationParameter> opParams = getParametersInternal();
		boolean hasParams = opParams.size() > 0;
		boolean hasReturn = hasParams && opParams.get(opParams.size() - 1).getParameterType().equals(ParameterType.OUT);
		Object[] args = hasReturn ? new Object[opParams.size() - 1] : new Object[opParams.size()];
		
		for (int i = 0; i < params.length; i++) {
			if ((i == (params.length - 1)) && hasReturn)
				break;
			
			OperationParameter arg = params[i];
			args[i] = arg.getValue();
		}
		
		Object returnObj = invoke(args);
		if (hasReturn)
			params[params.length - 1].setValue(returnObj);
	}

	public void setDelegateDevice(Device delegateDevice) {
		synchronized (_lockDelegate) {
			_device = delegateDevice;
		}
	}

	@Override
	public Device getDelegateDevice() {
		return _device;
	}

}
