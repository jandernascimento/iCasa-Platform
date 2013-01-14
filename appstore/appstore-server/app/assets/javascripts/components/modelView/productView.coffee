define ['jquery','jquery.ui','bootstrap','underscore','knockback','knockout','components/model/productModel','backbone'], ($,ui,bs,_,kb,ko, model,bb) ->
	
	# The accordion first configuration used in buyed products
	$(()->
  		$(".accordion-products .hidden-table:not(maccordion)").hide()
  		# $(".accordion-products table:first-child").show()
  	)

	#Register the knockout custom handler to handle clicks in accordion table
	ko.bindingHandlers.bsAccordionTable = {
		init: (element) ->
			$(element).click(()->
				$(element).nextAll(".hidden-table").fadeToggle("fast")
			)
			return { controlsDescendantBindings: false}
	}

	#Register the knockout custom handler to the carousel bootstrap effect
	ko.bindingHandlers.bsPopover = {
		init: (element, valueAccessor) ->
			@val = ko.utils.unwrapObservable(valueAccessor())
			@options = { title: val.title, animation: val.animation, placement: val.placement, trigger: val.trigger, delay: val.delay,content: if $(val.target).html()? then $(val.target).html() else val.content }
			$(element).popover(@options)
			return { controlsDescendantBindings: false}
	}

	#Register the knockout custom handler to the jQuery.ui toggle effect
	ko.bindingHandlers.bsToggle = {
		init: (element, valueAccessor) ->
			# console.log element
			val = ko.utils.unwrapObservable(valueAccessor())
			listElement = element.children

			firstElement = listElement[0]
			secondElement = listElement[1]
			# firstElement = "#"+val.firstElement
			# secondElement = "#"+val.secondElement
			effect = if val.effect? then val.effect else "blind"
			handler = new ToggleHandler(element, firstElement, secondElement, effect)
			return { controlsDescendantBindings: false}
	}
	#Class to handle the toggle effect for each product.
	class ToggleHandler
		constructor:(element, @firstElement, @secondElement, @effect)->
			$(@secondElement).hide();
			$(@firstElement).show();
			$(element).click(@.handleEventIn, @.handleEventOut)
		handleEventIn:()=>
			$(@firstElement).toggle(@effect)
			$(@secondElement).toggle(@effect)
		handleEventOut:()=>
			$(@secondElement).toggle(@effect)
			$(@firstElement).toggle(@effect)


	class ServiceViewModel extends kb.ViewModel
		constructor: (smodel) ->
			@id = smodel.get('id')
			@name = ko.observable(smodel.get('name'))
			@description = ko.observable(smodel.get('name'))
			@version = ko.observable(smodel.get('version'))
			
	class ApplicationViewModel extends kb.ViewModel
		constructor: (amodel) ->
			@id = amodel.get('id')
			@name = ko.observable(amodel.get('name'))
			@description = ko.observable(amodel.get('description'))
			@version = ko.observable(amodel.get('version'))
			console.log "Creating application view model" + @id
		
	class ProductViewModelBase extends kb.ViewModel
		constructor:(@pmodel) ->
			@id = pmodel.get('id')
			@name = pmodel.get('name')
			@imageURL = pmodel.get('imageURL')
			@description = pmodel.get('description')
			
			@lastVersion = pmodel.get('lastVersion')
			#The collection of  services
			@services = pmodel.get('services')
			#The collection of applications
			#@applications = pmodel.get('applications')
			@applications = kb.collectionObservable(new bb.Collection(pmodel.get('applications')), {view_model: ApplicationViewModel})
			#The collection of categories of the product
			@categories = pmodel.get('categories')

			#These fields are used only to the template.
			@shortDescription = ko.computed(()=>
			 	if @description.length > 100
			 		shortD = @description.substring(0,100) + "..."
			 	else
			 		shortD = @description
			 	shortD
			 )

	class ProductViewModelOwned extends ProductViewModelBase
		constructor: (@pmodel) ->
			super
			@modalId = "modal-application-owned-" + @id
			@modalIdRef = "#" + @modalId

	class ProductViewModel extends ProductViewModelBase
		constructor: (@pmodel) ->
			super
			@classItem = "item"
		saveModel: () ->
			@pmodel.save()
		
	class ProductViewModelGrid extends ProductViewModelBase
		constructor: (pmodel) ->
			super
			@modalId = "modal-product-grid" + @id
			@modalIdRef = "#" + @modalId

			@modalTabDescriptionId = @modalId + "_desc"
			@modalTabDescriptionIdRef = "#" + @modalTabDescriptionId

			@modalTabServicesId = @modalId + "_serv"
			@modalTabServicesIdRef = "#" + @modalTabServicesId

			@modalTabAppsId = @modalId + "_apps"
			@modalTabAppsIdRef = "#" + @modalTabAppsId

				
	class OwnedProductViewModelCollection
		constructor:(pmodel) ->
			@products = kb.collectionObservable(pmodel, {view_model: ProductViewModelBase})
			console.log pmodel

	class ProductViewModelCollection
		constructor: (pmodel) ->
			@products = kb.collectionObservable(pmodel, {view_model: ProductViewModel})
			@products.subscribe(@.addItemClass)
		addItemClass:(pmodels)->
			if pmodels.length > 0
				firstModel = pmodels[0]
				firstModel.classItem = 'active item'
	
	class ProductViewModelCollectionGrid
		constructor: (@pmodel) ->
			@pageSize= 8 #ko.observable(5);
			@pageIndex= 0 #ko.observable(0);
			@products = kb.collectionObservable(pmodel, {view_model: ProductViewModelGrid})
			#Get the first page
			pmodel.getNextPage(@.pageIndex, @.pageSize)
			#Souscribe to get newer pages on scroll down
			$(window).scroll(@.fetchNewPage)

		fetchNewPage:()=>
			if $(window).scrollTop() == $(document).height() - $(window).height()
				@.pageIndex++
				@.pmodel.getNextPage(@.pageIndex, @.pageSize)

	return {ProductViewModelCollection,ProductViewModel, ProductViewModelBase, ProductViewModelCollectionGrid, OwnedProductViewModelCollection}