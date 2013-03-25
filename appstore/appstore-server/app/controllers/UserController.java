package controllers;

import models.Device;
import models.User;
import models.values.Application;
import org.codehaus.jackson.node.ArrayNode;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import securesocial.core.Identity;
import securesocial.core.java.SecureSocial;

/**
 * Created with IntelliJ IDEA.
 * User: torito
 * Date: 1/14/13
 * Time: 5:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserController extends Controller {
    static Form<User> userForm = form(User.class);
    public static Result login() {
        Form<User> filledForm = userForm.bindFromRequest();
        System.out.println("FORM " + filledForm);
        User user = filledForm.get();
        session("connected", user.userId);
        System.out.println("Login " + user.userId);
        return ok();
    }

    public static Result logout(){
        session().clear();
        System.out.println("Logout ");
        return ok();
    }


}
