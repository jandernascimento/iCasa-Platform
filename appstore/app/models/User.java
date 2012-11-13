package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import securesocial.core.java.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

//import securesocial.core.java.PasswordInfo;

/**
 * Created with IntelliJ IDEA.
 * User: torito
 * Date: 11/6/12
 * Time: 4:56 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="users")
public class User extends Model {



    @Id
    @Column(name="id")
    public int id;

    @Column(name="userId")
    public String userId;

    @Constraints.Required
    @Column(name="username")
    public String username;

    @Column(name="fullname")
    public String fullname;

    @Column(name="email")
    public String email;

    @Column(name="password")
    public String password;

    @Column(name="provider")
    public String provider;

    @Column(name="authmethod")
    public String authMethod;


    /**
     * Add a user to the DB
     * @param user
     */
    public static void create(SocialUser user) {
        User newUser = User.getUserByUserId(user.id.id);
        if (newUser == null){
            newUser = new User();
        }
        System.out.println("Writing user on the DB");
        newUser.username = user.id.id;
        newUser.userId = user.id.id ;
        newUser.fullname = user.fullName;
        newUser.email = user.email;
        newUser.provider = user.id.provider;
        newUser.authMethod = AuthenticationMethod.toSala(user.authMethod).method();

        if (user.authMethod == AuthenticationMethod.USERNAME_PASSWORD) {
            newUser.password = user.passwordInfo.password;
        }
        else if (user.authMethod == AuthenticationMethod.OAUTH1){
            newUser.password = user.oAuth1Info.token;
        } else if (user.authMethod == AuthenticationMethod.OAUTH2){
            newUser.password = user.oAuth2Info.accessToken;
        }
        newUser.save();
    }

    public static securesocial.core.java.SocialUser getSocialUser(String userId){
        User user = User.getUserByUserId(userId);
        SocialUser socialUser = null;
        if(user != null){
            socialUser = new SocialUser ();
            socialUser.id = new UserId();
            socialUser.id.id = userId;
            socialUser.id.provider = user.provider;
            socialUser.fullName = user.username;
            socialUser.avatarUrl = null;
            socialUser.email = user.email;

            //socialUser.isEmailVerified = true;
            if (user.authMethod.compareToIgnoreCase(securesocial.core.AuthenticationMethod.UserPassword().method())==0){
                socialUser.authMethod = AuthenticationMethod.USERNAME_PASSWORD;
                socialUser.passwordInfo = new PasswordInfo();
                socialUser.passwordInfo.password = user.password;
                System.out.println("Is U_PWD");
            } else if (user.authMethod.compareToIgnoreCase(securesocial.core.AuthenticationMethod.OAuth1().method())==0){
                socialUser.authMethod = AuthenticationMethod.OAUTH1;
                socialUser.oAuth1Info = new OAuth1Info();
                socialUser.oAuth1Info.token = user.password;
                System.out.println("Is OAUTH1");
            } else if (user.authMethod.compareToIgnoreCase(securesocial.core.AuthenticationMethod.OAuth2().method()) == 0){
                socialUser.authMethod = AuthenticationMethod.OAUTH2;
                socialUser.oAuth2Info = new OAuth2Info();
                socialUser.oAuth2Info.accessToken = user.password;
                System.out.println("Is OAUTH2");
            } else {
                System.out.println("Is NOTHING!!!" + user.authMethod + "Oth! " +securesocial.core.AuthenticationMethod.OAuth2().method());
                return null;
            }

        }
        return socialUser;
    }

    public static Finder<String,User> find = new Finder<String, User>(
            String.class, User.class
    );

    public static User getUserByUserId(String userId){
        List<User> listUsers = find.where().eq("userId", userId).findList();
        if (listUsers.isEmpty()){
            System.out.println("Is Empty!!");
            return null;
        }
        return listUsers.get(0);//It must be only one user with the Id
    }
}
