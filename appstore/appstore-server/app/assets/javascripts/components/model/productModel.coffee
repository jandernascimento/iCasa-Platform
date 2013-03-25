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
	# ApplicationModel.setup()
	
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
		urlRoot: 'user/product'
		
		defaults:
			name: 'my product name'
			description: 'my description'
			imageURL: 'assets/images/products/default.jpg'
			versions: new bb.Collection()
			lastVersion: new bb.Model()
			applications: new ApplicationModelCollection()
			services: new ServiceModelCollection()
			categories: new CategoryModelCollection()
		buyProduct:()->
			#only send product ID as part of URL, user association handled by secure social
			$.ajax(
				type: "PUT",
				url: 'user/buy/product/' + @.id,
			)




	class OwnedProductModelCollection extends bb.Collection
		model: ProductModel
		url: 'user/buy/products'
		constructor:()->
			super

		updateOwnedModel:()=>
			@.fetch()

	class DeviceModel extends bb.Model
			constructor: ()->
				console.log "in device constructor"
				console.log @
				super
				console.log "end device constructor"
				@.on("sync", @.getInstalledApplication())
				
				
			getInstalledApplication:()=>
				@appurl = @.get("url")+"/appstore/applications"
				console.log "will call " + @.appurl
				$.ajax({
					url: @.appurl,
					success: @.getAppsSuccess,
					error: @.getAppsError
					})
				# $.get(@.appurl, (data)=>
				# 	console.log "loaded apps" + data
				# 	# console.log data
				# 	# @.set(data)
				# 	# console.log @
				# )
				return
			getAppsSuccess:(data,  textStatus, jqXHR)=>
				console.log "get apps success on " + @.appurl
				console.log "status: " + textStatus
				json_data = JSON.stringify(data)
				console.log "set" + json_data
				console.log json_data
				@.set(data)
			getAppsError:(jqXHR, textStatus, errorThrown)=>
				console.log "error when getting installed applications: " + errorThrown


			installApplication: (application)->
				$.ajax(
	   				type: "POST",
	   				url: @.appurl,
	   				data: { 
        				'location':application.get("url")
    				},
				)
			urlRoot: 'user/device'
			defaults:
				name: 'generic'
				# installedPackages: new ApplicationModelCollection()
			

	class OwnedDeviceModelCollection extends bb.Collection
		model: DeviceModel
		url: '/usr/devices'
		constructor:()->
			console.log "creating device collection"
			super




	class ProductModelCollection extends bb.Collection

		url: 'user/products'
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

   		 class DPSoftware extends bb.Model
   		 	constructor: () ->
   		 		super
   		 	defaults:
   		 		name: 'Application name'
   		 		description: 'Application description'


   		 class DPSoftwareCollection extends bb.Collection
   		 	constructor:()->
   		 		super


	return {ProductModelCollection, ProductModel,ApplicationModel,ServiceModel, OwnedProductModelCollection, OwnedDeviceModelCollection, DeviceModel}