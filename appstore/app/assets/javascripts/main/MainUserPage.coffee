require([
    'jquery',
    'jquery.ui',
    'bootstrap',
    'knockback',
    'backbone',
    'knockout',
    'components/model/productModel',
    'components/modelView/productView',
    'knockoutHandlers'
    ], 
	($, ui,bs,kb, bb,ko, model, view) ->
        carouselCollection = new model.ProductModelCollection()
        carouselCollection.getTopProducts(10)
        carouselView = new view.ProductViewModelCollection(carouselCollection)
        ko.applyBindings(carouselView, document.getElementById("myCarousel"))
       
        pageCollection = new model.ProductModelCollection()
        # pageCollection.getNextPage()
        pageView = new view.ProductViewModelCollectionGrid(pageCollection)
        ko.applyBindings(pageView, document.getElementById("product_list"))
);