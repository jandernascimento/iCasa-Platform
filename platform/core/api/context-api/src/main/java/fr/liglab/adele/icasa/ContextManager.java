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

	/**
	 * Create a new zone
	 * @param id the identifier of the zone.
	 * @param leftX the left X value of the zone.
	 * @param topY the top X value of the zone.
	 * @param width the width of the zone.
	 * @param height the height of the zone.
	 * @return the new zone.
	 */
	public Zone createZone(String id, int leftX, int topY, int width, int height);
	/**
	 * Creates a new square zone with a center position.  
	 * @param id The identifier of the zone.
	 * @param center The center position of the new zone. 
	 * @param detectionScope used to calculate the width/height of the zone.
	 * 		width = detectionScope * 2
	 * 		height = detectionScope * 2
	 * @return
	 */
	public Zone createZone(String id, Position center, int detectionScope);
	
	/**
	 * Remove a zone given his identifier
	 * @param id The zone identifier.
	 */
	public void removeZone(String id);
	/**
	 * Move a zone to a new top left corner position. 
	 * @param id The identifier of the zone to move.
	 * @param leftX The new X corner value.
	 * @param topY The new Y corner value
	 * @throws Exception when new position does not fit in the parent zone.
	 */
	public void moveZone(String id, int leftX, int topY) throws Exception;
	/**
	 * Resize a zone to a new width and height.
	 * @param id The identifier of the zone to resize.
	 * @param width the new width of the zone.
	 * @param height The new height of the zone
	 * @throws Exception Throws an exception when the zone does not fit in the parent zone.
	 */
	public void resizeZone(String id, int width, int height) throws Exception; 
	
	/**
	 * Remove all zones in the context
	 */
	public void removeAllZones();
		
	/**
	 * Get the set of available variables in a given zone.
	 * @param zoneId the zone identifier.
	 * @return The set of the variables of the zone.
	 */
	public Set<String> getZoneVariables(String zoneId);
	/**
	 * Get the value of a given variable in a specified zone.
	 * @param zoneId The zone identifier to inspect the variables.
	 * @param variable The variable name to retrieve its value.
	 * @return The value of the variable in the given zone.
	 */
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
	
	public void resetContext();

}
