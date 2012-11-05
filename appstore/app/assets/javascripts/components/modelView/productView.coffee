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
			@id = @model.get('id')
			@name = @model.get('name')
			@classItem = "item"
			@firstElement = "firstElement"+ @id
			@secondElement = "secondElement"+ @id
			@imageURL = @model.get('imageURL')
			@description = @model.get('description')
			@isRow = true
		saveModel: () ->
			@model.save()
			
	class ProductViewModelCollection
		constructor: (model) ->
			@products = kb.collectionObservable(model, {view_model: ProductViewModel})
			@products.subscribe(@.addItemClass)
		addItemClass:(models)->
			if models.length > 0
				firstModel = models[0]
				firstModel.classItem = 'active item'
	
	class ProductViewModelCollectionGrid
		constructor: (@model) ->
			@pageSize= ko.observable(12);
			@pageIndex= ko.observable(0);
			@products = kb.collectionObservable(model, {view_model: ProductViewModel})
			ko.computed(()=>
				model.getNextPage(@.pageIndex, @.pageSize)
			)



	return {ProductViewModelCollection,ProductViewModel, ProductViewModelCollectionGrid}