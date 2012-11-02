package controllers.api;

import java.util.List;

import models.values.Product;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import play.data.Form;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

public class ProductREST extends Controller {

	static Form<Product> productForm = form(Product.class);

	private static final String TOP_NUMBER = "topNumber";
	
	private static final String PAGE = "page";
	
	private static final String PRODUCTS_PER_PAGE = "productsPerPage";
	
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	public static Result products(){ 
		if (form().bindFromRequest().get(TOP_NUMBER) != null) {
			return topProducts(form().bindFromRequest().get(TOP_NUMBER));
		} else if (form().bindFromRequest().get(PAGE) != null){
			return productsPerPage(form().bindFromRequest().get(PAGE), form().bindFromRequest().get(PRODUCTS_PER_PAGE));
		}
		List<Product> allproducts = null;
		allproducts = Product.all();
		ArrayNode products = Json.newObject().arrayNode();
		for (Product product : allproducts) {
			products.add(Product.toJson(product));
		}
		return ok(products);
	}	
	
	public static Result topProducts(String topNumber){
		List<Product> allproducts = null;
		int top = 10;
		try{
			top = Integer.valueOf(topNumber);
		}catch(Exception ex){
			top = 10;
		}
		allproducts = Product.getTopProducts(top);
		ArrayNode products = Json.newObject().arrayNode();
		for (Product product : allproducts) {
			products.add(Product.toJson(product));
		}
		return ok(products);
	}
	
	public static Result productsPerPage(String page, String productsPerPage){
		List<Product> allproducts = null;
		System.out.println("calling products per page" );
		int _page;
		int _productsPerPage;
		try{
			_page = Integer.valueOf(page);
			_productsPerPage = Integer.valueOf(productsPerPage);
		}catch(Exception ex){
			ex.printStackTrace();
			_page = 1;
			_productsPerPage = 10;
		}
		allproducts = Product.getPageProducts(_productsPerPage, _page);
		int totalPages = Product.getPageCount(_productsPerPage);
		ObjectNode returningJSONObject = Json.newObject();
		returningJSONObject.put("totalPages", totalPages);
		ArrayNode products = Json.newObject().arrayNode();
		for (Product product : allproducts) {
			products.add(Product.toJson(product));
		}
		returningJSONObject.put("products", products);
		return ok(returningJSONObject);
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