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
package fr.liglab.adele.icasa;

import java.util.List;
import java.util.Set;

import fr.liglab.adele.icasa.listener.IcasaListener;
import fr.liglab.adele.icasa.location.LocatedDevice;
import fr.liglab.adele.icasa.location.Position;
import fr.liglab.adele.icasa.location.Zone;

/**
 * @author <a href="mailto:cilia-devel@lists.ligforge.imag.fr">Cilia Project
 *         Team</a>
 */
public interface ContextManager {


	// -- Zone related methods --//

	public Zone createZone(String id, int leftX, int topY, int width, int height);

	public Zone createZone(String id, Position center, int detectionScope);
	
	public void removeZone(String id);

	public void moveZone(String id, int leftX, int topY) throws Exception;

	public void resizeZone(String id, int width, int height) throws Exception; 
		
	public Set<String> getZoneVariables(String zoneId);

	public Object getZoneVariableValue(String zoneId, String variable);

	public void addZoneVariable(String zoneId, String variable);
	
	public void setZoneVariable(String zoneId, String variable, Object value);

	public Set<String> getZoneIds();

	public List<Zone> getZones();

	public Zone getZone(String zoneId);

	public Zone getZoneFromPosition(Position position);
	
	public void setParentZone(String zoneId, String parentId) throws Exception; 
	



	// -- Device related method --//

	public Set<String> getDeviceIds();


	public void setDeviceState(String deviceId, boolean value);


	public LocatedDevice getDevice(String deviceId);

	public List<LocatedDevice> getDevices();

	public Position getDevicePosition(String deviceId);

	public void setDevicePosition(String deviceId, Position position);

	public void moveDeviceIntoZone(String deviceId, String zoneId);

	public Set<String> getDeviceTypes();
	

	// -- Listener related methods --  //

	public void addListener(IcasaListener listener);

	public void removeListener(IcasaListener listener);

}