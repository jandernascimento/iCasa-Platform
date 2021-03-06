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
import org.apache.felix.ipojo.annotations.*;
import org.json.JSONObject;

/**
 * @author Thomas Leveque
 */
@Component(name = "AddZoneVariableCommand")
@Provides
@Instantiate(name="add-zone-variable-command")
public class AddZoneVariableCommand extends AbstractCommand {

    @Requires
    private ContextManager contextManager;

    @Override
    public Object execute(JSONObject param) throws Exception {
        contextManager.addZoneVariable(param.getString(ScriptLanguage.ZONE_ID), param.getString(ScriptLanguage.VARIABLE));
        return null;
    }

    @Override
    public boolean validate(JSONObject param) throws Exception {
        return param.has(ScriptLanguage.ZONE_ID)  && param.has(ScriptLanguage.VARIABLE);
    }

    /**
     * Get the name of the  Script and command gogo.
     *
     * @return The command name.
     */
    @Override
    public String getName() {
        return "add-zone-variable";
    }

    /**
     * Get the list of parameters.
     *
     * @return
     */
    @Override
    public String[] getParameters() {
        return new String[]{ScriptLanguage.ZONE_ID, ScriptLanguage.VARIABLE};
    }

    @Override
    public String getDescription(){
        return "Add a variable to a zone.\n\t" + super.getDescription();
    }

}