package controllers;

import models.Device;
import models.User;
import models.values.Product;
import org.codehaus.jackson.node.ArrayNode;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import securesocial.core.Identity;
import securesocial.core.java.SecureSocial;

/**
 * Created with IntelliJ IDEA.
 * User: torito
 * Date: 1/16/13
 * Time: 5:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class DeviceController extends Controller{

    static Form<Device> deviceForm = form(Device.class);


    @SecureSocial.SecuredAction
    public static Result getDevices(){
        Identity socialUser = (Identity) ctx().args.get(SecureSocial.USER_KEY);
        User user = User.getUserByUserId(socialUser.id().id());
        System.out.println("get Devices ");
        ArrayNode devices = Device.toJson(user.devices);
        return ok(devices);
    }
    @SecureSocial.SecuredAction
    public static Result addDevice(){
        Identity socialUser = (Identity) ctx().args.get(SecureSocial.USER_KEY);
        User user = User.getUserByUserId(socialUser.id().id());
        Form<Device> filledForm = deviceForm.bindFromRequest();
        Device device = filledForm.get();
        user.assignDevice(device);
        System.out.println("Add Device");
        return ok();
    }
}
