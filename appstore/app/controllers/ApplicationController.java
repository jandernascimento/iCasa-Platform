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

import models.values.Application;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author <a href="mailto:cilia-devel@lists.ligforge.imag.fr">Cilia Project
 *         Team</a>
 *
 */
public class ApplicationController extends Controller {
	static Form<Application> applicationForm = form(Application.class);


	public static Result index() {

		return applications();

	}

	public static Result applications(){
		return ok(views.html.applications.render(Application.all()));
	}

	public static Result addApplicationForm(){
		return ok(views.html.newApplication.render(form(Application.class)));
	}

	public static Result addApplication(){
		Form<Application> filledForm = applicationForm.bindFromRequest();
		if (filledForm.hasErrors()){
			return badRequest(views.html.newApplication.render(filledForm));
		} else {
			try{
				Application.create(filledForm.get());
			}catch (Exception ex){
				return badRequest(views.html.newApplication.render(filledForm));
			}
			return redirect(routes.ApplicationController.applications());
		}
	}
}
