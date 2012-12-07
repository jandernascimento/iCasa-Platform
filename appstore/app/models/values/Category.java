package models.values;

import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.libs.Json;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: torito
 * Date: 12/5/12
 * Time: 4:59 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="Category")
public class Category extends Model{
    @Id
    @Column(name="id")
    public int id;

    @Constraints.Required
    @Column(name="name", length=50)
    public String name;

    @Column(name="description", columnDefinition="TEXT")
    public String description;

    @ManyToMany
    @JoinTable(name = "Product_has_Category")
    public List<Product> products;

    public static ArrayNode toJson(List<Category> categories){
        ArrayNode result = Json.newObject().arrayNode();
        for(Category category: categories){
            result.add(Category.toJson(category));
        }
        return result;
    }

    public static ObjectNode toJson(Category category){
        ObjectNode result = Json.newObject();
        result.put("id", category.id);
        result.put("name", category.name);
        result.put("description", category.description);
        return result;
    }
}

