package controllers.api;

import java.util.List;

import models.values.Product;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;

import play.data.Form;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http.RequestBody;
import play.mvc.Result;

public class ProductREST extends Controller {

	static Form<Product> productForm = form(Product.class);

	
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	public static Result products(){
		List<Product> allproducts = Product.all();
		ArrayNode products = Json.newObject().arrayNode();
		for (Product product : allproducts) {
			products.add(Product.toJson(product));
		}
		return ok(products);
	}
	
	public static Result getTopProducts(){
		
		return ok();
	}
	
	public static Result addProduct(){
		System.out.println("hello add product  ");
		System.out.println("aDD PRODUCT " + request().body());
		JsonNode body = request().body().asJson();
		System.out.println("aDD PRODUCT " + body.get(0));
		
		Form<Product> filledForm = productForm.bindFromRequest();
		System.out.println(filledForm);
		if (filledForm.hasErrors()){
			return badRequest(views.html.products.newProduct.render(filledForm));
		} else {
			try{
				Product.create(filledForm.get());
			}catch (Exception ex){
				return badRequest(views.html.products.newProduct.render(filledForm));
			}
			return ok();
		}
	}
}