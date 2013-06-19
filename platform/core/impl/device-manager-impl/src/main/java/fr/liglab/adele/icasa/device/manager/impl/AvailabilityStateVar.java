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

import fr.liglab.adele.icasa.common.Attributable;
import fr.liglab.adele.icasa.common.StateVariable;

public class AvailabilityStateVar extends DeriveIfPossibleStateVar {

	public AvailabilityStateVar(StateVariable originalVar,
			Attributable delegateObj) {
		super(originalVar, delegateObj);
	}

	public void setDelegateObj(Attributable delegateObj) {
		synchronized (_lockDelegate) {
			super.setDelegateObj(delegateObj);
			setValue(delegateObj != null);
		}
	}
	
	@Override
	public Object getValue() {
		boolean value;
		synchronized (_lockDelegate) {
			value = (getDelegateObj() != null);
		}
		
		if (_originalVar != null)
			_originalVar.setValue(value);
		
		return value;
	}
}
