require([
    'jquery',
    'jquery.ui',
    'bootstrap',
    'knockback',
    'backbone',
    'knockout',
    'components/model/productModel',
    'components/modelView/productView'
    ], 
	($, ui,bs,kb, bb,ko, model, view) ->
        # Get top products
        carouselCollection = new model.ProductModelCollection()
        carouselCollection.getTopProducts(10)
        # carouselView = new view.ProductViewModelCollection(carouselCollection)
        # ko.applyBindings(carouselView, document.getElementById("myCarousel"))
        # Get first page product list
        pageCollection = new model.ProductModelCollection()
        # pageView = new view.ProductViewModelCollectionGrid(pageCollection)
        # ko.applyBindings(pageView, document.getElementById("product_list"))
         #Get owned devices
        ownedDevicesCollection = new model.OwnedDeviceModelCollection()
        ownedDevicesCollection.fetch();
        # ownedDevicesViewModel = new view.OwnedDeviceViewModelCollection(ownedDevicesCollection)
        # ko.applyBindings(ownedDevicesViewModel, document.getElementById("userMenu"))

        # ownedDevicesViewModelTab = new view.OwnedDeviceViewModelCollection(ownedDevicesCollection)
        # ko.applyBindings(ownedDevicesViewModelTab, document.getElementById("installedSoftware"))
        # ko.applyBindings(ownedDevicesViewModelTab, document.getElementById("main-tab-content"))
        # Get purshased products
        ownedProducts = new model.OwnedProductModelCollection()
        #Get products only when clicking in tab
        $("#ownedProductLink").click(ownedProducts.updateOwnedModel)
        # purshasedView = new view.OwnedViewModelCollection(ownedProducts, ownedDevicesCollection)
        # ko.applyBindings(purshasedView, document.getElementById("ownedProducts"))

        mainView = new view.MainUserView(ownedProducts, ownedDevicesCollection, carouselCollection, pageCollection)
        ko.applyBindings(mainView)




       # window.validateForm = validateForm
        # ko.applyBindings(ownedDevicesViewModel, document.getElementById("ownedDeviceList"))

);