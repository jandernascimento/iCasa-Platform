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
	($, ui,bs,kb, bb,ko, model, view) ->
        # Get first page product list
        pageCollection = new model.ProductModelCollection()

        mainView = new view.MainAdminView(pageCollection)
        ko.applyBindings(mainView)

);