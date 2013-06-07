package test.context.source;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Unbind;

import fr.liglab.adele.icasa.device.GenericDevice;

@Component(name = "ConsumerFakeDevice")
@Instantiate
public class ConsumerFakeDevice {

	@Bind(id = "dev", filter = "(location=${person.paul.location})")
	public void bindFakeDevice(GenericDevice device) {
		System.out.println("--------------> Device Binded ----- " + device.getSerialNumber());
	}

	@Unbind(id = "dev")
	public void unbindFakeDevice(GenericDevice device) {
		System.out.println("--------------> Device Unbinded ----- " + device.getSerialNumber());
	}

}
