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
		
		pmodel = new model.ProductModel({id: '1', name: 'this is my product name', version: '1.0', description: 'my description', imageURL: 'assets/images/products/1.jpg'})
		pmodel2 = new model.ProductModel({id: '2', name: 'this is my product name 2', version: '1.0', description: 'This is my description', imageURL: 'assets/images/products/2.jpg'})
	
		
		collection = new model.ProductModelCollection()
		#collection.fetch()
		collection.add(pmodel)
		collection.add(pmodel2)
		pview = new view.ProductViewModelCollection(collection)
		ko.applyBindings(pview);
);