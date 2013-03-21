package main.java.org.teleal.cling.android.browser;

import java.beans.PropertyChangeSupport;

import org.teleal.cling.binding.annotations.UpnpAction;
import org.teleal.cling.binding.annotations.UpnpOutputArgument;
import org.teleal.cling.binding.annotations.UpnpService;
import org.teleal.cling.binding.annotations.UpnpServiceId;
import org.teleal.cling.binding.annotations.UpnpServiceType;
import org.teleal.cling.binding.annotations.UpnpStateVariable;

@UpnpService(
        serviceId = @UpnpServiceId("PowerSlider"),
        serviceType = @UpnpServiceType(value = "PowerSlider", version = 1)
)
public class PowerSlider {

    private final PropertyChangeSupport propertyChangeSupport;

    public PowerSlider() {
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    @UpnpStateVariable(name = "PowerLevel", defaultValue = "0")
    private float powerLevel = 0;

    @UpnpAction(out = @UpnpOutputArgument(name = "RetPowerLevelValue"))
    public float getPowerLevel() {
        powerLevel = DemoActivity._progressValue;
    	return powerLevel;
    }

}