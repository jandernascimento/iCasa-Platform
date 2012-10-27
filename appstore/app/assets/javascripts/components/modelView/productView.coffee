define ['underscore','knockback','knockout'], (_,kb,ko) ->

#	ko.bindingHandlers.bsPopover = {
#		init: (element, valueAccessor) ->
#			@val = ko.utils.unwrapObservable(valueAccessor())
#			@options = { title: val.title, animation: val.animation, placement: val.placement, trigger: val.trigger, delay: val.delay,content: if $(val.target).html()? then $(val.target).html() else val.content }
#			$(element).popover(@options)
#			return { controlsDescendantBindings: false}
#	}

	
	class  ServiceViewModel extends kb.ViewModel
		constructor: (model) ->
			@id = model.id
			@name = ko.observable(model.name)
			@description = ko.observable(model.name)
			@version = ko.observable(model.version)
			
	class  ApplicationViewModel extends kb.ViewModel
		constructor: (model) ->
			@id = model.id
			@name = ko.observable(model.name)
			@description = ko.observable(model.name)
			@version = ko.observable(model.version)
			
	class ProductViewModel extends kb.ViewModel
		constructor: (@model) ->
			@id = kb.observable(@model,'id')
			@name = kb.observable(@model,'name')
			classItem = "item"
			@description = kb.observable(@model,'description')
#			@applications = ko.observableArray(@model.applications)
#			@services = ko.observableArray(@model.services)
		saveModel: () ->
# 			alert @model.get('id')
			console.log(@model.toJSON())
			@model.save()
			
	class ProductViewModelCollection 
		constructor:(model) ->
			@products = kb.collectionObservable(model)
			_.each(@products.collection(), (imodel) -> 
				console.log (imodel)
				#imodel.classItem = 'item'
			)
			console.log ("initializing the collection")
		setClassItem: (product) ->
			product.classItem = "item"
#  			if @products.lenght > 0
#  				firstModel = @products.at(0)
#  				firstModel.set(classItem: 'active item')
	return {ProductViewModelCollection,ProductViewModel}