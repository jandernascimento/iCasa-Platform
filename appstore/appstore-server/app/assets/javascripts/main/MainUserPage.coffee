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
        carouselView = new view.ProductViewModelCollection(carouselCollection)
        ko.applyBindings(carouselView, document.getElementById("myCarousel"))
        # Get first page product list
        pageCollection = new model.ProductModelCollection()
        pageView = new view.ProductViewModelCollectionGrid(pageCollection)
        ko.applyBindings(pageView, document.getElementById("product_list"))
        # Get purshased products
        purshased = new model.OwnedProductModelCollection()
        purshased.fetch()
        purshasedView = new view.OwnedProductViewModelCollection(purshased)
        ko.applyBindings(purshasedView, document.getElementById("ownedProducts"))
);