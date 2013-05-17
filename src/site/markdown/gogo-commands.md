# iCasa GOGO commands

iCasa provides a set of functional <a href="http://felix.apache.org/site/apache-felix-gogo.html">gogo</a> commands helping the manipulation of iCasa elements, such as devices, zones and persons.


- <a href="#Device">Manipulating devices</a>
- <a href="#Zone">Manipulating zones</a>

<a name="Device"></a>
## Manipulating Devices

### Create device
Creates a new simulated device.

Scope -> __icasa__

Name-> __create-device__

Parameters:

- __${deviceId}__ The device Id of the new device.
- __${deviceType}__ The device type of the new device.


#### Example:
    > g! icasa:create-device iCASA.Thermometer therm-1
   
### List of devices

Gets the list of devices in the iCasa execution platform, this command shows the devices identifiers, and their position.

Scope -> __icasa__

Name -> __show-devices__

Parameters -> __NONE__

#### Example:

    > g! icasa:show-devices
    Devices:
    Device Id: Pres-B1255D-D - Position: (413:185)
    Device Id: BiLi-C7496W-S - Position: (58:427)
    Device Id: Pres-A1255D-D - Position: (504:374)
    Device Id: Toogle-B1286X-Y - Position: (599:38)
    Device Id: Toogle-D1286X-Y - Position: (219:349)
    Device Id: Pres-D1255D-D - Position: (166:24)
    Device Id: Pres-C1255D-D - Position: (240:399)
    Device Id: BiLi-D7496W-S - Position: (202:59)
    Device Id: Toogle-C1286X-Y - Position: (97:384)
    Device Id: Toogle-A1286X-Y - Position: (509:451)
    Device Id: BiLi-A7496W-S - Position: (492:440)
    Device Id: BiLi-B7496W-S - Position: (505:270)

### Activate device
Active a device.

Scope -> __icasa__

Name-> __activate-device__

Parameters-> 

- __${deviceId}__  The device ID.


#### Example:
    > g! icasa:activate-device Pres-B1255D-D
    
### Deactive device
Deactive a device.

Scope -> __icasa__

Name-> __deactivate-device__

Parameters-> 

- __${deviceId}__ The device ID.


#### Example:
    > g! icasa:deactivate-device Pres-B1255D-D
    
### List device properties
Shows the list of properties of a device.

Scope -> __icasa__

Name-> __show-device__

Parameters-> 

- __${deviceId}__ The device ID.


#### Example:
    > g! icasa:show-device Pres-B1255D-D
    Properties: 
    Property: presenceSensor.sensedPresence - Value: false
    Property: state - Value: activated
    Property: Location - Value: livingroom
    Property: fault - Value: no
    
### Simulate a fail in device
Simulate a fail in a device.

Scope -> __icasa__

Name-> __fault-device__

Parameters-> 

- __${deviceId}__ the device ID.


#### Example:
    > g! icasa:fault-device Pres-B1255D-D

### Simulate a reparation in device
Repair a device.

Scope -> __icasa__

Name-> __repair-device__

Parameters-> 

- __${deviceId}__ the device ID to repair.


#### Example:
    > g! icasa:repair-device Pres-B1255D-D
   
### Move device
Move a device into new X,Y coordinates

Scope -> __icasa__

Name-> __move-device__

Parameters-

- __${deviceId}__ the device ID to move.
- __${X}__  The new X-coordinate.
- __${Y}__  The new Y-coordinate


#### Example:
    > g! icasa:move-device Pres-B1255D-D 60 80
    
### Move device into zone
Move a device into new X,Y coordinates

Scope -> __icasa__

Name-> __move-device-zone__

Parameters-

- __${deviceId}__ the device ID to move.
- __${zoneId}__  The new zone.



#### Example:
    > g! icasa:move-device-zone Pres-B1255D-D kitchen
    
   
### Set device property
Set a new device property

Scope -> __icasa__

Name-> __set-device-property__

Parameters-

- __${deviceId}__ the device ID to modify.
- __${property}__  The property name.
- __${value}__  The property value.


#### Example:
    > g! icasa:set-device-property Pres-B1255D-D newProp newValue
    
### Remove a device
Remove a simulated device

Scope -> __icasa__

Name-> __remove-device__

Parameters-

- __${deviceId}__ the device ID to remove.



#### Example:
    > g! icasa:remove-device Pres-A1255D-D

  
<a name="Zone"></a>
## Manipulating Zones
### Create zone
Creates a new zone.

Scope -> __icasa__

Name-> __create-zone__

Parameters-

- __${zoneId}__ the new zone ID.
- __${leftX}__ The topX value.
- __${topY}__ The topY value.
- __${width}__ The width of the new zone.
- __${height}__ The height of the new zone.


#### Example:
    > g! icasa:create-zone kitchen  50 50 100 100

### List zones
Show the existant zones.

Scope -> __icasa__

Name-> __show-zones__

Parameters-

- __NONE__ 


#### Example:
    > g! icasa:show-zones
    Zones: 
    Zone livingroom des Zone: livingroom X: 410 Y: 28 -- Width: 245 Height: 350 - Parent: Unset - Use parent: false
    Zone kitchen des Zone: kitchen X: 410 Y: 370 -- Width: 245 Height: 210 - Parent: Unset - Use parent: false
    Zone bathroom des Zone: bathroom X: 55 Y: 20 -- Width: 260 Height: 350 - Parent: Unset - Use parent: false
    Zone bedroom des Zone: bedroom X: 55 Y: 370 -- Width: 259 Height: 210 - Parent: Unset - Use parent: false


### Move zone
Move a zone to a new position

Scope -> __icasa__

Name-> __move-zone__

Parameters-

- __${zoneId}__ the new zone ID.
- __${leftX}__ The topX value.
- __${topY}__ The topY value.


#### Example:
    > g! icasa:move-zone livingroom 410 25
    
### Resize zone
Resize a zone

Scope -> __icasa__

Name-> __resize-zone__

Parameters-

- __${zoneId}__ the new zone ID.
- __${width}__ The new width value.
- __${height}__ The new height value.


#### Example:
    > g! icasa:resize-zone livingroom 245 300
    
### Add parent
Add a parent to a zone.

Scope -> __icasa__

Name-> __set-zone-parent__

Parameters-

- __${zoneId}__ the child zone ID.
- __${parentId}__ The parent zone ID.
- __${useParentVariables}__ true if zone will use the parent variables, false if not.

Throws-> Exception when zone does not fit in the parent.


#### Example:
    > g! icasa:set-zone-parent livingroom chair true
           
  
### Add variable
Add a variable to a zone.

Scope -> __icasa__

Name-> __add-zone-variable__

Parameters-

- __${zoneId}__ the zone ID.
- __${variableName}__ The variable name.


#### Example:
    > g! icasa:add-zone-variable livingroom comfortable

### Modify variable
Modifies a variable in a given zone.

Scope -> __icasa__

Name-> __modify-zone-variable__

Parameters-

- __${zoneId}__ the zone ID.
- __${variableName}__ The variable name.
- __${variableValue}__ The numeric value.


#### Example:
    > g! icasa:modify-zone-variable livingroom comfortable 100
     
### Attach a zone to a device
Attach a zone to a device .

Scope -> __icasa__

Name-> __attach-zone-device__

Parameters-

- __${deviceId}__ the zone ID.
- __${zoneId}__ The variable name.
- __${attach}__ True to attach, false to deatach.


#### Example:
    > g! icasa:attach-zone-device Pres-A1255D-D chair true
    > g! icasa:attach-zone-device Pres-A1255D-D chair false
       
### Show zone
Shows the information of a zone.

Scope -> __icasa__

Name-> __show-zone__

Parameters-> 

- __${zoneId}__ The zone ID.


#### Example:
    > g! icasa:show-zone livingroom
    Variables: 
    Variable: Volume - Value: 10.0
    Variable: Illuminance - Value: 5.0
    Variable: Temperature - Value: 295.15
