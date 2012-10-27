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
 		urlRoot: 'api/product'
		defaults:
			name: 'my product name'
			description: 'my description'

	
	class ProductModelCollection extends bb.Collection
		url: 'api/products'
		model: ProductModel

	return {ProductModelCollection, ProductModel,ApplicationModel,ServiceModel}