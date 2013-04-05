require([
    'jquery',
    'jquery.ui',
    'bootstrap',
    'knockback',
    'backbone',
    'knockout',
    'components/model/productModel',
    'components/modelView/productView',
    ],
	($, ui,bs,kb, bb,ko, DataModel, view) ->
        # Get first page product list
        pageCollection = new DataModel.Collections.Products()
        
        mainView = new view.MainAdminView(pageCollection)
        ko.applyBindings(mainView)

);