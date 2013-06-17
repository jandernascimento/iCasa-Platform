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
package fr.liglab.adele.icasa.service.preferences.impl.commands;

import java.io.InputStream;
import java.io.PrintStream;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.json.JSONObject;

import fr.liglab.adele.icasa.Signature;
import fr.liglab.adele.icasa.commands.impl.AbstractCommand;
import fr.liglab.adele.icasa.commands.impl.ScriptLanguage;
import fr.liglab.adele.icasa.service.preferences.Preferences;

@Component(name = "GetUserPreferenceCommand")
@Provides
@Instantiate(name = "get-user-preference-command")
public class GetUserPreferenceCommand extends AbstractCommand {

	@Requires
	private Preferences preferenceService;

	public GetUserPreferenceCommand() {
		setSignature(new Signature(new String[] {ScriptLanguage.USER, ScriptLanguage.PROPERTY}));
	}

	@Override
	public String getName() {
		return "get-user-preference";
	}


	@Override
	public String getDescription() {
		return "Gets a user preference property.\n\t" + super.getDescription();
	}

	@Override
   public Object execute(InputStream in, PrintStream out, JSONObject param, Signature signature) throws Exception {
		String property = param.getString(ScriptLanguage.NAME);
		String user = param.getString(ScriptLanguage.USER);
		Object value  = preferenceService.getUserPropertyValue(user, property);
		out.println("Property " + property + " - value: " + value);
	   return value;
   }

}