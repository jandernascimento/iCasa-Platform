package main.java.org.teleal.cling.android.browser;

import org.teleal.cling.binding.annotations.*;

import java.beans.PropertyChangeSupport;

@UpnpService(
		serviceId = @UpnpServiceId("Gyroscope"),
		serviceType = @UpnpServiceType(value = "Gyroscope", version = 1)
		)
public class Gyroscope {

	private final PropertyChangeSupport propertyChangeSupport;

	public Gyroscope() {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}

	@UpnpStateVariable(defaultValue = "0")
	private float xGyroValue = 0;
	
	@UpnpStateVariable(defaultValue = "0")
	private float yGyroValue = 0;

	@UpnpStateVariable(defaultValue = "0")
	private float zGyroValue = 0;
	
	@UpnpAction(out = @UpnpOutputArgument(name = "RetXGyroValue"))
	public float getXGyroValue() {
		xGyroValue = DemoActivity.xGyroscope;
		return xGyroValue;
	}
	
	@UpnpAction(out = @UpnpOutputArgument(name = "RetYGyroValue"))
	public float getYGyroValue() {
		yGyroValue = DemoActivity.yGyroscope;
		return yGyroValue;
	}
	
	@UpnpAction(out = @UpnpOutputArgument(name = "RetZGyroValue"))
	public float getZGyroValue() {
		zGyroValue = DemoActivity.zGyroscope;
		return zGyroValue;
	}
}
