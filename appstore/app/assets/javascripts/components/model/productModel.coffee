define ['backbone'], (bb) ->
 
	class ServiceModel extends bb.Model
# 		urlRoot: '/service'
		defaults:
			name: 'msn'
			description: 'msd'
			version: '0.0.0'
	
	class ServiceModelCollection extends bb.Collection
# 		url: '/services'
		model: ServiceModel
	
	class ApplicationModelCollection extends bb.Collection
# 		url: '/applications'
		model: ApplicationModel

	class ApplicationModel extends bb.Model
# 		urlRoot: '/application'
		defaults:
			name: 'man'
			description: 'mad'
			version: '0.0.0'
	
	class ProductModel extends bb.Model
		constructor: ()->
		 	console.log "initializing Product Model"
		 	super
 		urlRoot: 'api/product'
		defaults:
			name: 'my product name'
			description: 'my description'
			imageURL: 'assets/images/products/default.jpg'
	
	class ProductModelCollection extends bb.Collection

		url: 'api/products'
		model: ProductModel
		@topProducts = 10
		@productsPerPage = 10
		getTopProducts: () ->
			@.fetch({ data: $.param({ topNumber: @topProducts})})

		getPage:(page) ->
			@.fetch({ data: $.param({ page: page, productsPerPage:@productsPerPage})})


	return {ProductModelCollection, ProductModel,ApplicationModel,ServiceModel}