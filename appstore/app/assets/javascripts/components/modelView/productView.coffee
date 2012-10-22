define ['knockback','knockout'], (kb,ko) ->

	class  ServiceViewModel extends kb.ViewModel
		constructor: (model) ->
			@ide = model.ide
			@name = ko.observable(model.name)
			@description = ko.observable(model.name)
			@version = ko.observable(model.version)
			
	class  ApplicationViewModel extends kb.ViewModel
		constructor: (model) ->
			@ide = model.ide
			@name = ko.observable(model.name)
			@description = ko.observable(model.name)
			@version = ko.observable(model.version)
			
	class ProductViewModel extends kb.ViewModel
		constructor: (model) ->
			@ide = model.ide
			@name = ko.observable(model.name)
			@description = ko.observable(model.description)
			@applications = ko.observableArray(model.applications)
			@services = ko.observableArray(model.services)
	return ProductViewModel