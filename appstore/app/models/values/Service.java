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
@Table(name="services")
public class Service extends Model implements Value {
	/**
	 * 
	 */
	private static final long serialVersionUID = 498903206959165812L;

	@Id
	@Column(name="id", length=25)
	public String id;

	@Column(name="name", length=50)
	public String name;

	@Column(name="description", columnDefinition="TEXT")
	public String description;

	public static Finder<String,Service> find = new Finder<String, Service>(
			String.class, Service.class
			);

	public static List<Service> all() {
		return find.all();

	}

	public static List<Value> getValues(){
		List<? extends Value> all = all();
		return (List<Value>) all;
	}

	public static void create(Service service) {
		service.save();
	}

	public static void remove(String identifier){
		find.byId(identifier).delete();
	}

	/* (non-Javadoc)
	 * @see models.values.Value#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see models.values.Value#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see models.values.Value#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

}
