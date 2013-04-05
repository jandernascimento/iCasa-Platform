define(['backbone', 'underscore'], 
	(bb, _) ->

#define (['backbone'], (bb) ->
		DataModel =
		 	Models : {}
		 	Collections: {}
		 	collections: {}
		 	models : {}

		class DataModel.Models.Service extends bb.Model
	# 		urlRoot: '/service'
			defaults:
				name: 'msn'
				description: 'msd'
				version: '0.0.0'
		
		class DataModel.Collections.Services extends bb.Collection
	# 		url: '/services'
			model: DataModel.Models.Service

		class DataModel.Models.Application extends bb.Model
	# 		urlRoot: '/application'
			defaults:
				name: 'man'
				description: 'mad'
				version: '0.0.0'
		# ApplicationModel.setup()
		
		class DataModel.Collections.Applications extends bb.Collection
	# 		url: '/applications'
			model: DataModel.Models.Application

		class DataModel.Models.Category extends bb.Model
			defaults:
				name: 'generic'
				description: 'Generic product'

		class DataModel.Collections.Categories extends bb.Collection
			model: DataModel.Models.Category
			url: "catalog/categories"

		class DataModel.Models.Version extends bb.Model
			defaults:
				version: '1.0.0'

		class DataModel.Collections.Versions extends bb.Collection
			model: DataModel.Models.Version

		class DataModel.Models.Product extends bb.Model

			constructor: ()->
				super;
			urlRoot: 'catalog/products'
			
			defaults:
				name: 'my product name'
				description: 'my description'
				imageURL: 'assets/images/products/default.jpg'

			buyProduct:()->
				#only send product ID as part of URL, user association handled by secure social
				$.ajax(
					type: "PUT",
					url: 'user/products/' + @.id,
				);




		class DataModel.Collections.OwnedProducts extends bb.Collection
			model: DataModel.Models.Product
			url: 'user/products'
			constructor:()->
				super;

			updateOwnedModel:()=>
				@.fetch();

		class DataModel.Models.Device extends bb.Model
				constructor: ()->
					super;
					@.on("sync", @.getInstalledApplication());
					
					
				getInstalledApplication:()=>
					@appurl = @.get("url")+"/appstore/applications"
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
					return;
				getAppsSuccess:(data,  textStatus, jqXHR)=>
					json_data = JSON.stringify(data);
					@.set(data);
				getAppsError:(jqXHR, textStatus, errorThrown)=>
					console.log "error when getting installed applications: " + errorThrown;


				installApplication: (application)->
					$.ajax(
		   				type: "POST",
		   				url: @.appurl,
		   				data: { 
	        				'location':application.get("url")
	    				},
					);
				urlRoot: 'user/devices'
				defaults:
					name: 'generic'
					# installedPackages: new ApplicationModelCollection()
				

		class DataModel.Collections.OwnedDevices extends bb.Collection
			model: DataModel.Models.Device
			url: '/user/devices'
			constructor:()->
				super;

		class DataModel.Collections.Products extends bb.Collection

			url: 'catalog/products'
			model: DataModel.Models.Product
			constructor:()->
				@totalPages = 0;
				super;
			getTopProducts: (topProducts) ->
				@.fetch({ data: $.param({ topNumber: topProducts})});

			getNextPage:(currentPage, productsPerPage) ->
				@.fetch({ data: $.param({ page: currentPage, productsPerPage: productsPerPage}), add: true});

			parse: (response) ->
				#test if the list of products is located in an internal list called products
				if response.totalPages
					@.totalPages = response.totalPages;
				if response.products
	   		 		return response.products;
	   		 	else
	   		 		return response;

		class DataModel.Collections.FilteredProducts extends DataModel.Collections.Products
			url: 'catalog/products';
			constructor:()->
				@urlBase = 'catalog/products';
				@totalPages = 0;
				super;

			getProducsByCategory:(categoryId) ->
				@.url = @urlBase + '/category/'+categoryId;
				@.fetch();
				@.url = @urlBase;

		class DataModel.Models.DPSoftware extends bb.Model
		 	constructor: () ->
		 		super
		 	defaults:
		 		name: 'Application name'
		 		description: 'Application description'


		class DataModel.Collections.DPSoftwares extends bb.Collection
			constructor:()->
				super;

		return DataModel;
);