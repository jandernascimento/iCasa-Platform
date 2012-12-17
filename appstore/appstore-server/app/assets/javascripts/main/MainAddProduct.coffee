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
	($, ui,bs,kb, bb,ko, model,view,mm) ->
# 		pmodel = new model.ProductModel({id: 'product id', name: 'this is my product name', version: '1.0', description: 'my description'})
		pmodel = new model.ProductModel()
		pmodel.fetch()
		view = new view.ProductViewModel(pmodel)
		ko.applyBindings(view)
)