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
        # Get top products
        carouselCollection = new model.ProductModelCollection()
        carouselCollection.getTopProducts(10)
        # Get first page product list
        pageCollection = new model.ProductModelCollection()
         #Get owned devices
        ownedDevicesCollection = new model.OwnedDeviceModelCollection()
        ownedDevicesCollection.fetch();
        # Get purshased products
        ownedProducts = new model.OwnedProductModelCollection()
        #Get products only when clicking in tab
        $("#ownedProductLink").click(ownedProducts.updateOwnedModel)

        mainView = new view.MainUserView(ownedProducts, ownedDevicesCollection, carouselCollection, pageCollection)
        ko.applyBindings(mainView)

);