define ['backbone'], (bb) ->
 
	class ServiceModel extends bb.Model
		defaults:
			id: 's-id'
			name: 'msn'
			description: 'msd'
			version: '0.0.0'
	
	class ServiceModelCollection extends bb.Collection
		model: ServiceModel
	
	class ApplicationModelCollection extends bb.Collection
		model: ApplicationModel

	class ApplicationModel extends bb.Model
		defaults:
			id: 'a-id'
			name: 'man'
			description: 'mad'
			version: '0.0.0'
	
	class ProductModel extends bb.Model
		defaults:
			id: 'my product id'
			name: 'my product name'
			description: 'my description'
		
		@services = new ServiceModelCollection()
		@applications = new ApplicationModelCollection()

	return {ProductModel,ApplicationModel,ServiceModel}