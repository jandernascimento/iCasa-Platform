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
package fr.liglab.adele.icasa.commands.impl;

import java.io.InputStream;
import java.io.PrintStream;

import org.json.JSONObject;

import fr.liglab.adele.icasa.iCasaCommand;

public abstract class AbstractCommand implements iCasaCommand {

    /**
     * Default namespace for iCasa commands
     */
    private static String NAMESPACE = "icasa";

	@Override
	public Object execute(InputStream in, PrintStream out, JSONObject param) throws Exception {
		if (validate(param)){
            return execute(param);
        } else {
            out.println(getDescription());
            throw new Exception("Invalid parameters: " + param);
        }
	}

    /**
     * Get the command description.
     *
     * @return The description of the Script and command gogo.
     */
    @Override
    public String getDescription() {
        String[] params = getParameters();
        StringBuilder descr = new StringBuilder("Parameters (");
        for (String param: params){
            descr.append(" ");
            descr.append(param);
            descr.append(" ");
        }
        descr.append(")");
        return descr.toString();
    }

    /**
     *
     * @param param The parameters in JSON format
     * @return true if all parameters are in the JSON object, false if not. For optional
     * parameters, this method should be override..
     * @throws Exception
     */
    @Override
	public boolean validate(JSONObject param) throws Exception {
        String []params = getParameters();
        for (String name: params){
            if(!param.has(name)){
                return false;
            }
        }
        return true;// All params are in the Json object.
    }
	
	public abstract Object execute(JSONObject param) throws Exception;
}
