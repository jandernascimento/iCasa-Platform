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
package fr.liglab.adele.icasa.commands.impl.shell;

import fr.liglab.adele.icasa.ContextManager;
import fr.liglab.adele.icasa.commands.impl.AbstractCommand;
import fr.liglab.adele.icasa.commands.impl.ScriptLanguage;
import fr.liglab.adele.icasa.location.LocatedDevice;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.json.JSONObject;

@Component(name = "SetDevicePropertyCommand")
@Provides
@Instantiate(name = "property-device-command")
public class SetDevicePropertyCommand extends AbstractCommand {

	@Requires
	private ContextManager contextManager;

	private static final String[] PARAMS = new String[] { ScriptLanguage.DEVICE_ID, ScriptLanguage.PROPERTY,
	      ScriptLanguage.VALUE };

	private static final String NAME = "set-device-property";

	/**
	 * Get the name of the Script and command gogo.
	 * 
	 * @return The command name.
	 */
	@Override
	public String getName() {
		return NAME;
	}

	/**
	 * Get the list of parameters.
	 * 
	 * @return
	 */
	@Override
	public String[] getParameters() {
		return PARAMS;
	}

	@Override
	public Object execute(JSONObject param) throws Exception {
		String deviceId = param.getString(PARAMS[0]);
		String propertyId = param.getString(PARAMS[1]);
		Object value = param.get(PARAMS[2]);
		LocatedDevice device = contextManager.getDevice(deviceId);
		System.out.println("Trying to modifiy " + propertyId + " property ");
		if (device != null)
			device.setPropertyValue(propertyId, value);
		return null;
	}

	@Override
	public String getDescription() {
		return "Set the value of a device property.\n\t" + super.getDescription();
	}

}
