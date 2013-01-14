
require(['./commons',
  'jquery',
  'jquery.ui',
  'bootstrap',
  'knockout',
  'components/model/productModel',
  'components/modelView/productView'
  ],
  (common, $, ui, bs, ko, model, view) ->

    # Get top products
    carouselCollection = new model.ProductModelCollection();

    carouselView = new view.ProductViewModelCollection(carouselCollection);
    ko.applyBindings(carouselView, document.getElementById("myCarousel"));

    # Get first page product list
    pageCollection = new model.ProductModelCollection();
    pageView = new view.ProductViewModelCollectionGrid(pageCollection);
    ko.applyBindings(pageView, document.getElementById("product_list"));
);