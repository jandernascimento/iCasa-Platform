require([
    'jquery',
    'jquery.ui',
    'bootstrap',
    'knockback',
    'backbone',
    'knockout',
    'components/model/productModel',
    'components/modelView/productView',
    'MainMenu'
    ], 
	($, ui,bs,kb, bb,ko, model, view) ->
		pmodel = new model.ProductModel({id: 'product id', name: 'this is my product name', version: '1.0', description: 'my description'})
		pmodel2 = new model.ProductModel({id: 'product id 2', name: 'this is my product name 2', version: '1.0', description: 'This is my description'})
		
		appmodel = new model.ApplicationModel()
		appmodel.set(id: "app Id")
		appmodel.set(name: "app name")
		appmodel.set(description: "description")
	
		pmodel.set(applications: appmodel);
	    
		
# 		bb.sync = (method, model) ->
# 			alert(method + ": " + model.url)
	    
		collection = new model.ProductModelCollection()
		collection.fetch()
#		collection.add(pmodel)
#		collection.add(pmodel2)
		pview = new view.ProductViewModelCollection(collection)
		
			    
#		ko.applyBindings(pview, $('#kb_collection')[0]);
		ko.applyBindings(pview);
   
        
);