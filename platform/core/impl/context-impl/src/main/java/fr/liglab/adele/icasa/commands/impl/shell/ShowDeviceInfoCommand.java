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

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Set;

/**
 * Show the properties of a specific device
 */
@Component(name = "ShowDeviceCommand")
@Provides
@Instantiate(name = "show-device-command")
public class ShowDeviceInfoCommand extends AbstractCommand {

    @Requires
    private ContextManager simulationManager;

    private static final String[] PARAMS =  new String[]{ScriptLanguage.DEVICE_ID};

    private static final String NAME= "show-device";

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
    public Object execute(InputStream in, PrintStream out, JSONObject param) throws Exception {
        String deviceId = param.getString(PARAMS[0]);
        out.println("Properties: ");
        LocatedDevice device = simulationManager.getDevice(deviceId);
        if (device==null)
            return null;

        Set<String> properties = device.getProperties();
        for (String property : properties) {
            out.println("Property: " + property + " - Value: " +device.getPropertyValue(property));
        }
        return null;
    }

    @Override
    public Object execute(JSONObject param) throws Exception {
        return execute(System.in, System.out, param);
    }

    @Override
    public String getDescription(){
        return "Shows the information of a device.\n\t" + super.getDescription();
    }

}
