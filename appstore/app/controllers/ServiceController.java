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
package controllers;

import models.values.Product;
import models.values.Service;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author <a href="mailto:cilia-devel@lists.ligforge.imag.fr">Cilia Project
 *         Team</a>
 *
 */
public class ServiceController extends Controller {
	static Form<Service> serviceForm = form(Service.class);


	public static Result services(){
		return ok(views.html.services.render(Service.all()));
	}

	public static Result addServiceForm(){
		return ok(views.html.newService.render(form(Service.class)));
	}

	public static Result addService(){
		Form<Service> filledForm = serviceForm.bindFromRequest();
		if (filledForm.hasErrors()){
			return badRequest(views.html.newService.render(filledForm));
		} else {
			try{
				Service.create(filledForm.get());
			}catch (Exception ex){
				return badRequest(views.html.newService.render(filledForm));
			}
			return redirect(routes.ServiceController.services());
		}
	}
}
