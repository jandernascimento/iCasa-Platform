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
package fr.liglab.adele.m2mappbuilder.light.follow.me;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.liglab.adele.icasa.device.DeviceListener;
import fr.liglab.adele.icasa.device.GenericDevice;
import fr.liglab.adele.icasa.device.light.BinaryLight;
import fr.liglab.adele.icasa.device.presence.PresenceSensor;

public class LightFollowMeApplication implements DeviceListener {

	public static String LOCATION_PROPERTY_NAME = "Location";
	public static String LOCATION_UNKNOWN = "unknown";	
	
	private Map<String, PresenceSensor> detectors;
	
	private List<BinaryLight> lights;
	
	
	public void bindDetector(PresenceSensor detector) {
		detector.addListener(this);
		detectors.put(detector.getSerialNumber(), detector);		
	}

	public void unbindDetector(PresenceSensor detector) {
		detector.removeListener(this);
		detectors.remove(detector.getSerialNumber());
	}
	
	public void bindLight(BinaryLight light) {
		lights.add(light);
	}

	public void unbindLight(BinaryLight light) {
		lights.remove(light);
	}
	
	
	public void start() {
		detectors = new HashMap<String, PresenceSensor>();
		lights = new ArrayList<BinaryLight>();
	}

	public void stop() {
		detectors = null;
		lights = null;
	}
	
	private List<BinaryLight> getLigthByLocation(String location) {
		List<BinaryLight> sameLocationLigths = new ArrayList<BinaryLight>();
		for (BinaryLight light : lights) {
			String lightLocation = (String) light.getPropertyValue(LOCATION_PROPERTY_NAME);
			if (lightLocation.equals(location))
				sameLocationLigths.add(light);
		}
		return sameLocationLigths;
	}
	
	
	@Override
	public void devicePropertyModified(GenericDevice device, String propertyName, Object oldValue) {
		PresenceSensor detector = detectors.get(device.getSerialNumber());
		if (detector!=null && propertyName.equals(PresenceSensor.PRESENCE_SENSOR_SENSED_PRESENCE)) {
			String detectorLocation = (String)detector.getPropertyValue(LOCATION_PROPERTY_NAME);
			if (!detectorLocation.equals(LOCATION_UNKNOWN)) {
				List<BinaryLight> sameLocationLigths = getLigthByLocation(detectorLocation);
				for (BinaryLight binaryLight : sameLocationLigths) {
					binaryLight.setPowerStatus(!(Boolean) oldValue);						
				}				
			}
		}				
	}
		
	
	@Override
	public void deviceAdded(GenericDevice arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void devicePropertyAdded(GenericDevice arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void devicePropertyRemoved(GenericDevice arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deviceRemoved(GenericDevice arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
