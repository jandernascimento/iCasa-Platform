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

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * @author <a href="mailto:cilia-devel@lists.ligforge.imag.fr">Cilia Project
 *         Team</a>
 *
 */
@Entity
@Table(name="products")
public class Product extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6533282470018272197L;

	@Id
	@Required
	@Column(name="id", length=25)
	public String id;
	
	@Required
	@Column(name="name", length=50)
	public String name;
	
	@Column(name="description", columnDefinition="TEXT")
	public String description;
	
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
	
	/**
	 * Add a product to the DB
	 * @param product
	 */
	public static void create(Product product) {
		product.save();
	}
	
	/**
	 * Remove an existent product
	 * @param identifier
	 */
	public static void remove(String identifier){
		find.byId(identifier).delete();
	}
	
}
