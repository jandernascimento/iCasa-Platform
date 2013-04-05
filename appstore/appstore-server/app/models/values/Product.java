/*
 * Copyright Adele Team LIG
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 
 */
package models.values;

import com.avaje.ebean.Ebean;
import org.codehaus.jackson.node.ObjectNode;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.libs.Json;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:cilia-devel@lists.ligforge.imag.fr">Cilia Project
 *         Team</a>
 *
 */
@Entity
@Table(name="Product")
public class Product extends Model  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6533282470018272197L;

	@Id
	@Column(name="id")
    @GeneratedValue
	public int id;
	
	@Required
	@Column(name="name", length=50)
	public String name;
	
	@Column(name="description", columnDefinition="TEXT")
	public String description;
	
	@Column(name="imageURL")
	public String imageURL;

    @OneToMany (cascade=CascadeType.ALL, mappedBy = "product")
    public List<ProductVersion> versions;

    @ManyToMany
    @JoinTable(name = "Product_has_Category")
    public List<Category> categories;

    @OneToOne
    @JoinColumn(name="productVersion_id", referencedColumnName = "id")
    public ProductVersion lastVersion;

    @OneToMany
    @JoinTable(name = "Product_Price")
    public List<ProductPrice> prices;

	/**
	 * To locate Products
	 */
	public static Finder<String,Product> find = new Finder<String, Product>(
		    String.class, Product.class
		  );
	
	/**
	 * get the list of all products
	 * @return the list of products
	 */
	public static List<Product> all() {
		return find.all();
	}

	
	public static List<Product> getTopProducts(int topNumber){
		//TODO change the search mechanism.
		return getPageProducts(topNumber, 0);
	}
	
	public static List<Product> getPageProducts(int pageSize, int pageNumber){
        return find.findPagingList(pageSize).getPage(pageNumber).getList();
	}
	
	public static int getPageCount(int pageSize){
		return find.findPagingList(pageSize).getTotalPageCount();
	}
	
	/**
	 * Add a product to the DB
	 * @param product
	 */
	public static void create(Product product) {
        //Add the given version as the current version
		product.save();
        if (product.versions != null && product.lastVersion == null) {
            product.lastVersion = product.versions.get(product.versions.size()-1); //get the last, usually there is only one.
            product.save();
        }
	}
	
	/**
	 * Remove an existent product
	 * @param identifier
	 */
	public static void remove(String identifier){
		find.byId(identifier).delete();
	}

	public static ObjectNode toJson(Product product){
		ObjectNode result = Json.newObject();
		result.put("id", product.id);
		result.put("name", product.name);
		result.put("description", product.description);
        result.put("imageURL", product.imageURL);
        result.put("versions", ProductVersion.toJson(product.versions));
        result.put("lastVersion", ProductVersion.toJson(product.lastVersion));
        result.put("categories", Category.toJson(product.categories));
        result.put("applications", ProductVersion.getApplications(product.lastVersion));
        result.put("services", ProductVersion.getServices(product.lastVersion));
		return result;
	}


    public ProductPrice getPrice(){
        ProductPrice price = null;
        if (prices == null){
            prices = new ArrayList();
        }
        if (prices.size()<1){
            price = new ProductPrice();
            price.price = 0.0f;
            price.product = this;
            price.unit = "€";
            prices.add(price);
            price.save();
            this.save();
            System.out.println("New Price");
        } else {
            price = prices.get(0);
            System.out.println("Existing Price");

        }
        return price;
    }

    public static List<Product> getProductByCategory(int categoryId){
        List <Product> products = Ebean.find(Product.class).fetch("categories").where().eq("category_id",categoryId).findList();
        return products;
    }
	
}
