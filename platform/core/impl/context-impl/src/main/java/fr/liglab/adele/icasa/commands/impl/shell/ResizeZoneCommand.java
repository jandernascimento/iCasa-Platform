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
@Component(name = "ResizeZoneCommand")
@Provides
@Instantiate(name="resize-zone-command")
public class ResizeZoneCommand extends AbstractCommand {
	@Requires
	private ContextManager simulationManager;

    private static final String[] PARAMS =  new String[]{ScriptLanguage.ZONE_ID, ScriptLanguage.WIDTH, ScriptLanguage.HEIGHT};

    private static final String NAME= "resize-zone";

    /**
     * Get the name of the  Script and command gogo.
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
        String zoneId = param.getString(PARAMS[0]);
        Zone zone = simulationManager.getZone(zoneId);
        if (zone == null){
            throw new IllegalArgumentException("Zone ("+ zoneId +") does not exist");
        }
        int width = param.getInt(PARAMS[1]);
        int height = param.getInt(PARAMS[2]);
        int depth = zone.getZLength();
        System.err.println("Z-length will not change");
		simulationManager.resizeZone(zoneId, width, height, depth);
		return null;
	}
    @Override
    public String getDescription(){
        return "Resize a zone.\n\t" + super.getDescription();
    }

}