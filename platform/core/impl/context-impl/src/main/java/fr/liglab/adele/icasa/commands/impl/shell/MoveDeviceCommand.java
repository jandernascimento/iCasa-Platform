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
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.json.JSONObject;

import fr.liglab.adele.icasa.location.Position;


/**
 * 
 * Sets the fault state of device to "Yes"
 * 
 * @author Gabriel
 * 
 */
@Component(name = "MoveDeviceCommandNew")
@Provides
@Instantiate(name = "move-device-command-new")
public class MoveDeviceCommand extends AbstractCommand {

	@Requires
	private ContextManager contextManager;

	@Override
	public Object execute(JSONObject param) throws Exception {
        String deviceId = param.getString(ScriptLanguage.DEVICE_ID);
        int newX = param.getInt(ScriptLanguage.NEW_X);
        int newY = param.getInt(ScriptLanguage.NEW_X);
		contextManager.setDevicePosition(deviceId, new Position(newX, newY));
		return null;
	}

    /**
     * Get the name of the  Script and command gogo.
     *
     * @return The command name.
     */
    @Override
    public String getName() {
        return "move-device";
    }

    /**
     * Get the list of parameters.
     *
     * @return
     */
    @Override
    public String[] getParameters() {
        return new String[]{ScriptLanguage.DEVICE_ID, ScriptLanguage.NEW_X, ScriptLanguage.NEW_Y};
    }
    @Override
    public String getDescription(){
        return "Move a device to new X,Y positions.\n\t" + super.getDescription();
    }

}
