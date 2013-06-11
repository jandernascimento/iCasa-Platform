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

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.ow2.chameleon.sharedprefs.SharedPreferencesService;

import fr.liglab.adele.icasa.Signature;
import fr.liglab.adele.icasa.commands.impl.ScriptLanguage;

@Component(name = "GetConfigPropertyCommand")
@Provides
@Instantiate(name = "get-config-property-command")
public class GetConfigPropertyCommand extends SharedPreferencesCommand {

	@Requires
	private SharedPreferencesService preferenceService;

	public GetConfigPropertyCommand() {
		setSignature(new Signature(new String[] { ScriptLanguage.PROPERTY}));
	}

	@Override
	public String getName() {
		return "get-config-property";
	}


	@Override
	public String getDescription() {
		return "Gets a platform configuration property.\n\t" + super.getDescription();
	}

	@Override
   protected String getCommandType() {
	   return "get";
   }

	@Override
   protected String getPreferencesName() {
	   return "platform-configuration";
   }

	@Override
   protected SharedPreferencesService getPreferenceService() {
	   return preferenceService;
   }

}
