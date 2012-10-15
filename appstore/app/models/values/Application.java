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

import play.db.ebean.Model;



/**
 * @author <a href="mailto:cilia-devel@lists.ligforge.imag.fr">Cilia Project
 *         Team</a>
 *
 */
@Entity
@Table(name="applications")
public class Application extends Model  {

	private static final long serialVersionUID = 7658036868515739627L;
	@Id
	@Column(name="id", length=25)
	public String id;
	@Column(name="name", length=50)
	public String name;
	@Column(name="description", columnDefinition="TEXT")
	public String description;
	
	public static Finder<String,Application> find = new Finder<String, Application>(
		    String.class, Application.class
		  );
	
	public static List<Application> all() {
		return find.all();
	}
	
	public static void create(Application application) {
		application.save();
	}
	
	public static void remove(String identifier){
		find.byId(identifier).delete();
	}
	
}
