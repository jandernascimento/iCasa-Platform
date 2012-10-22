define ['jquery','jquery.ui','knockout','bootstrap'], ($,ui,ko,bs) ->

	$( "#accordion" ).show()
	$( "#accordion" ).accordion()

	ko.bindingHandlers.bsPopover = {
		init: (element, valueAccessor) ->
			@val = ko.utils.unwrapObservable(valueAccessor())
			@options = { title: val.title, animation: val.animation, placement: val.placement, trigger: val.trigger, delay: val.delay,content: if $(val.target)? then $(val.target).html() else val.content }
			$(element).popover(@options)
		}
		

