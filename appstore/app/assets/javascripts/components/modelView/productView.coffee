define ['underscore','knockback','knockout'], (_,kb,ko) ->

#	ko.bindingHandlers.bsPopover = {
#		init: (element, valueAccessor) ->
#			@val = ko.utils.unwrapObservable(valueAccessor())
#			@options = { title: val.title, animation: val.animation, placement: val.placement, trigger: val.trigger, delay: val.delay,content: if $(val.target).html()? then $(val.target).html() else val.content }
#			$(element).popover(@options)
#			return { controlsDescendantBindings: false}
#	}

	
	class ServiceViewModel extends kb.ViewModel
		constructor: (model) ->
			@id = model.id
			@name = ko.observable(model.name)
			@description = ko.observable(model.name)
			@version = ko.observable(model.version)
			
	class ApplicationViewModel extends kb.ViewModel
		constructor: (model) ->
			@id = model.id
			@name = ko.observable(model.name)
			@description = ko.observable(model.name)
			@version = ko.observable(model.version)
			
	class ProductViewModel extends kb.ViewModel
		constructor: (@model) ->
			@id = kb.observable(@model,'id')
			@name = kb.observable(@model,'name')
			@classItem = "item"
			@description = kb.observable(@model,'description')
#			@applications = ko.observableArray(@model.applications)
#			@services = ko.observableArray(@model.services)
		saveModel: () ->
# 			alert @model.get('id')
			console.log(@model.toJSON())
			@model.save()
			
	class ProductViewModelCollection
		constructor: (model) ->
			@products = kb.collectionObservable(model)
			@products.subscribe(@.test)
		test:(models)->
			_.each models, (imodel) -> 
				imodel.classItem= 'item'
			if models.length > 0
				firstModel = models[0]
				firstModel.classItem = 'active item'
			
	return {ProductViewModelCollection,ProductViewModel}