package models;

import models.values.Category;
import models.values.ProductVersion;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import play.db.ebean.Model;
import play.libs.Json;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: torito
 * Date: 1/16/13
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="OwnedDevice")
public class Device extends Model {
    @Id
    @Column(name="id")
    @GeneratedValue
    public int id;

    @Column(name="name")
    public String name;

    @Column(name="url")
    public String url;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    public User user;



    public static ObjectNode toJson(Device product){
        ObjectNode result = Json.newObject();
        result.put("id", product.id);
        result.put("name", product.name);
        result.put("url", product.url);
        return result;
    }

    public static ArrayNode toJson(List<Device> devices){
        ArrayNode result = Json.newObject().arrayNode();
        for(Device device: devices){
            result.add(Device.toJson(device));
        }
        return result;
    }

}
