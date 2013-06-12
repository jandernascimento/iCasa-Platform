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
package fr.liglab.adele.icasa.service.preferences;

import java.util.Set;

/**
 * 
 * @author Gabriel Pedraza Ferreira
 *
 */
public interface Preferences {

	Object getGlobalPropertyValue(String name);
	
	Object getUserPropertyValue(String user, String name);
	
	Object getApplicationPropertyValue(String applicationId, String name);
	
	void setGlobalPropertyValue(String name, Object value);
	
	void setUserPropertyValue(String user, String name, Object value);
	
	void setApplicationPropertyValue(String applicationId, String name, Object value);
	
	Set<String> getGlobalProperties();
	
	Set<String> getUserProperties(String user);
	
	Set<String> getApplicationProperties(String applicationId);
}
