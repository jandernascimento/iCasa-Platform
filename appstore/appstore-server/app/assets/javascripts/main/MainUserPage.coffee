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
        # Get top products
        carouselCollection = new DataModel.Collections.Products()
        carouselCollection.getTopProducts(10)
        # Get first page product list
        pageCollection = new DataModel.Collections.Products()
         #Get owned devices
        ownedDevicesCollection = new DataModel.Collections.OwnedDevices()
        ownedDevicesCollection.fetch();
        # Get purshased products
        ownedProducts = new DataModel.Collections.OwnedProducts()
        #Get products only when clicking in tab
        $("#ownedProductLink").click(ownedProducts.updateOwnedModel)

        #get available categories
        categories = new DataModel.Collections.Categories()
        categories.fetch();

        #productsByCategories
        productsByCategories = new DataModel.Collections.FilteredProducts()

        mainView = new view.MainUserView(ownedProducts, ownedDevicesCollection, carouselCollection, pageCollection, categories, productsByCategories)
        ko.applyBindings(mainView)

);