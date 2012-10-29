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
		
	
		collection = new model.ProductModelCollection()
		collection.getTopProducts()

		pview = new view.ProductViewModelCollection(collection)
		ko.applyBindings(pview);
);