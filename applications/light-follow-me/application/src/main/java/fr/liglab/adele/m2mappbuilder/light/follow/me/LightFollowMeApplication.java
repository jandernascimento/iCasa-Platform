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
import java.util.List;

import fr.liglab.adele.icasa.device.DeviceListener;
import fr.liglab.adele.icasa.device.GenericDevice;
import fr.liglab.adele.icasa.device.light.BinaryLight;
import fr.liglab.adele.icasa.device.presence.PresenceSensor;

public class LightFollowMeApplication {

    /**
     * Liste contenant l'ensemble des lampes de la maison :
     */
    List<BinaryLight> binaryLights = new ArrayList<BinaryLight>();

    /**
     * Liste contenant l'ensemble des detecteurs de présence de la maison :
     */
    List<PresenceSensor> presenceSensors = new ArrayList<PresenceSensor>();

    PresenceSensorListener presenceSensorListener = new PresenceSensorListener();


    /** Bind Method for binaryLights dependency */
    public void bindBinaryLight(BinaryLight binaryLight) {
        //ajoutte la nouvelle lampe à la liste des lampes
        binaryLights.add(binaryLight);
        System.out.println("Add lamp "+ binaryLight);
    }

    /** Unbind Method for binaryLights dependency */
    public void unbindBinaryLight(BinaryLight binaryLight) {
        //Retire la lampe de la liste des lampes
        binaryLights.remove(binaryLight);
        System.out.println("Remove lamp "+ binaryLight);
    }

    /** Bind Method for presenceSensors dependency */
    public void bindPresenceSensor(PresenceSensor presenceSensor) {
        //ajoutte le nouveau détecteur à la liste des detecteurs
        presenceSensors.add(presenceSensor);
        System.out.println("Add presence detector "+ presenceSensor);

        //enrigistrement du listener d'evennement :
        presenceSensor.addListener(presenceSensorListener);
    }

    /** Unbind Method for presenceSensors dependency */
    public void unbindPresenceSensor(PresenceSensor presenceSensor) {
        //Retire le detecteur de la liste des detecteurs
        presenceSensors.remove(presenceSensor);
        System.out.println("Remove presence detector "+ presenceSensor);

        presenceSensor.removeListener(presenceSensorListener);
    }

    /** Component Lifecycle Method */
    public void stop() {
        System.out.println("The follow me is stopping");
    }

    /** Component Lifecycle Method */
    public void start() {
        System.out.println("The follow me is starting");
    }

    /**
     * Cherche le PresenceSensor possédant le sn donné
     * @param deviceSerialNumber : le sn recherché
     * @return le sensor ou null si non trouvé.
     */
    public PresenceSensor getPresenceSensor(String deviceSerialNumber){
        for (PresenceSensor sensor : presenceSensors) {
            if (sensor.getSerialNumber().equals(deviceSerialNumber)){
                return sensor;
            }
        }
        return null;
    }

    /**
     * Retourne les lampes dans la piece donnée
     * @param location : la piece cherchée
     * @return les lampes de la piéce
     */
    public List<BinaryLight> getBinaryLightFromLocation(String location){
        List<BinaryLight> ligths = new ArrayList<BinaryLight>();
        for (BinaryLight binaryLight : binaryLights) {
            if(binaryLight.getPropertyValue("location").equals(location)){
                ligths.add(binaryLight);
            }
        }
        return ligths;
    }

    public class PresenceSensorListener implements DeviceListener {

        public void notifyDeviceEvent(String deviceSerialNumber) {
            System.out.println("The presence detector "+ deviceSerialNumber+ " has changed.");
            PresenceSensor sensor = getPresenceSensor(deviceSerialNumber);
            if(sensor!=null){
                String location = (String) sensor.getPropertyValue("location");
                System.out.println("this detector is into room " + location);
                List<BinaryLight> ligths = getBinaryLightFromLocation(location);
                System.out.println("There are "+ ligths.size()+ " lamps in this room.");

                //vérifie l'état du detecteur :
                System.out.println("Présence detectée ? " + sensor.getSensedPresence());
                if(sensor.getSensedPresence()){
                    //si presence detectée :
                    for (BinaryLight binaryLight : ligths) {
                        //allume toute les lampes
                        binaryLight.setPowerStatus(true);
                        binaryLight.setState(GenericDevice.STATE_ACTIVATED);
                        System.out.println("Light up "+ binaryLight.getSerialNumber());
                    }
                }else{
                    //si presence plus detectée :
                    for (BinaryLight binaryLight : ligths) {
                        //éteinds toute les lampes
                        binaryLight.setState(GenericDevice.STATE_DEACTIVATED);
                        System.out.println("Light down "+ binaryLight.getSerialNumber());
                    }
                }
            }
        }

        public void deviceAdded(GenericDevice device) {
            notifyDeviceEvent(device.getSerialNumber());
        }

        public void deviceRemoved(GenericDevice device) {
            notifyDeviceEvent(device.getSerialNumber());
        }

        public void devicePropertyModified(GenericDevice device, String property, Object oldValue) {
            notifyDeviceEvent(device.getSerialNumber());
        }

        public void devicePropertyAdded(GenericDevice device, String property) {
            notifyDeviceEvent(device.getSerialNumber());
        }

        public void devicePropertyRemoved(GenericDevice device, String property) {
            notifyDeviceEvent(device.getSerialNumber());
        }

    }

}
