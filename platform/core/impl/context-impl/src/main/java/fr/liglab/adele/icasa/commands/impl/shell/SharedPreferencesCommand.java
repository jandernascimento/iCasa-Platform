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

import java.io.InputStream;
import java.io.PrintStream;

import org.json.JSONObject;
import org.ow2.chameleon.sharedprefs.SharedPreferences;
import org.ow2.chameleon.sharedprefs.SharedPreferencesService;

import fr.liglab.adele.icasa.Signature;
import fr.liglab.adele.icasa.commands.impl.AbstractCommand;
import fr.liglab.adele.icasa.commands.impl.ScriptLanguage;

public abstract class SharedPreferencesCommand extends AbstractCommand {

	
	private SharedPreferencesService preferenceService;
	
	@Override
	public Object execute(InputStream in, PrintStream out, JSONObject param, Signature signature) throws Exception {
		
		preferenceService = getPreferenceService();
		SharedPreferences preferences = preferenceService.getSharedPreferences(getPreferencesName());
		String property = param.getString(ScriptLanguage.PROPERTY);
		
		if (getCommandType().equals("set")) {

			String type = param.getString(ScriptLanguage.TYPE);
			
			SharedPreferences.Editor editor = preferences.edit();
			if (type.equals("String")) {
				String value = param.getString(ScriptLanguage.VALUE);
				editor.putString(property, value);
				editor.commit();
			} else if (type.equals("Boolean")) {
				Boolean value = param.getBoolean(ScriptLanguage.VALUE);
				editor.putBoolean(property, value);
				editor.commit();
			} else if (type.equals("Float")) {
				Float value = (float) param.getDouble(ScriptLanguage.VALUE);
				editor.putFloat(property, value);
				editor.commit();
			} else if (type.equals("Integer")) {
				Integer value = param.getInt(ScriptLanguage.VALUE);
				editor.putInt(property, value);
				editor.commit();
			} else if (type.equals("Long")) {
				Long value = param.getLong(ScriptLanguage.VALUE);
				editor.putLong(property, value);
				editor.commit();
			} 
		} else if (getCommandType().equals("get")) {
			if (preferences.contains(property)){
				return preferences.getAll().get(property);
			}
		}			
		return null;
	}

	protected abstract String getCommandType();
	
	protected abstract String getPreferencesName();

	protected abstract SharedPreferencesService getPreferenceService();
	
}
