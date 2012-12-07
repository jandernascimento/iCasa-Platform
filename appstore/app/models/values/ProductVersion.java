package models.values;

import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.Json;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: torito
 * Date: 11/8/12
 * Time: 10:59 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="ProductVersion")
public class ProductVersion {
    @Id
    @Column(name="id")
    public int id;

    @Column(name="version")
    public String version;

    @Column(name="product_id")
    public int product_id;

    @ManyToOne
    @JoinColumn(table = "Product", name="product_id", referencedColumnName = "id")
    Product product;

//    @ManyToMany
//    @JoinTable(
//            name="product_has_services",
//            joinColumns={@JoinColumn(name="ProductVersion_id", referencedColumnName="ID")},
//            inverseJoinColumns={@JoinColumn(name="ServiceVersion_id", referencedColumnName="version_id")})
    List<Service> services;

    public static ObjectNode toJson(ProductVersion product){
        ObjectNode result = Json.newObject();
        if (product != null) {
            result.put("id", product.id);
            result.put("version", product.version);
        }
        return result;
    }

    public static ArrayNode toJson(List<ProductVersion> list){
        ArrayNode result = Json.newObject().arrayNode();
        if (list != null ){
            for (ProductVersion productVersion: list){
                  result.add(ProductVersion.toJson(productVersion));
            }
        }
        return result;
    }



}
