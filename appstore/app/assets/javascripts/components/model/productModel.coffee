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

	class ApplicationModel extends bb.Model
# 		urlRoot: '/application'
		defaults:
			name: 'man'
			description: 'mad'
			version: '0.0.0'
	
	class ApplicationModelCollection extends bb.Collection
# 		url: '/applications'
		model: ApplicationModel

	class CategoryModel extends bb.Model
		defaults:
			name: 'generic'
			description: 'Generic product'

	class CategoryModelCollection extends bb.Collection
		model: CategoryModel


	
	class ProductModel extends bb.Model
		constructor: ()->
			super
		urlRoot: 'api/product'
		defaults:
			name: 'my product name'
			description: 'my description'
			imageURL: 'assets/images/products/default.jpg'
			versions: new bb.Collection()
			lastVersion: new bb.Model()
			applications: new ApplicationModelCollection()
			services: new ServiceModelCollection()
			categories: new CategoryModelCollection()

	
	class ProductModelCollection extends bb.Collection

		url: 'api/products'
		model: ProductModel
		constructor:()->
			@totalPages = 0
			super
		getTopProducts: (topProducts) ->
			@.fetch({ data: $.param({ topNumber: topProducts})})

		getNextPage:(currentPage, productsPerPage) ->
			@.fetch({ data: $.param({ page: currentPage, productsPerPage: productsPerPage}), add: true})
		parse: (response) ->
			#test if the list of products is located in an internal list called products
			console.log response
			if response.totalPages
				@.totalPages = response.totalPages
			if response.products
   		 		return response.products
   		 	else
   		 		return response

	return {ProductModelCollection, ProductModel,ApplicationModel,ServiceModel}