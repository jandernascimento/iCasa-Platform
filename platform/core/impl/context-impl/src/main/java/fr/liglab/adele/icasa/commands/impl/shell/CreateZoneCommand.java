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
import fr.liglab.adele.icasa.location.Zone;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.json.JSONObject;

/**
 * 
 * Command to Create a Zone
 * 
 * @author Gabriel
 * 
 */
@Component(name = "CreateZoneCommand")
@Provides
@Instantiate(name = "create-zone-command")
public class CreateZoneCommand extends AbstractCommand {

	@Requires
	private ContextManager simulationManager;

	@Override
	public Object execute(JSONObject param) throws Exception {
		String id = param.getString(ScriptLanguage.ID);
		int leftX = param.getInt(ScriptLanguage.LEFT_X);
		int topY = param.getInt(ScriptLanguage.TOP_Y);
		int height = param.getInt(ScriptLanguage.HEIGHT);
		int width = param.getInt(ScriptLanguage.WIDTH);
        System.err.println("Z-length and Z-Bottom will be set to the default values: " + Zone.DEFAULT_Z_LENGTH + " and " + Zone.DEFAULT_Z_BOTTOM);
		simulationManager.createZone(id, leftX, topY, Zone.DEFAULT_Z_BOTTOM, width, height, Zone.DEFAULT_Z_LENGTH);
		return null;
	}

	/**
	 * Get the name of the Script and command gogo.
	 * 
	 * @return The command name.
	 */
	@Override
	public String getName() {
		return "create-zone";
	}

	/**
	 * Get the list of parameters.
	 * 
	 * @return
	 */
	@Override
	public String[] getParameters() {
		return new String[] { ScriptLanguage.ID, ScriptLanguage.LEFT_X, ScriptLanguage.TOP_Y, ScriptLanguage.HEIGHT,
		      ScriptLanguage.WIDTH };
	}

	@Override
	public String getDescription() {
		return "Creates a new zone.\n\t" + super.getDescription();
	}
}