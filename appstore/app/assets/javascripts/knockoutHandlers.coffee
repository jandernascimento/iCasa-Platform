define ['jquery','jquery.ui','knockout','bootstrap'], ($,ui,ko,bs) ->
	#Register the knockout custom handler to the bootstrap popover
	ko.bindingHandlers.bsPopover = {
		init: (element, valueAccessor) ->
			@val = ko.utils.unwrapObservable(valueAccessor())
			@options = { title: val.title, animation: val.animation, placement: val.placement, trigger: val.trigger, delay: val.delay,content: if $(val.target).html()? then $(val.target).html() else val.content }
			$(element).popover(@options)
			return { controlsDescendantBindings: false}
	}
	#Register the knockout custom handler to the bootstrap carousel
	# ko.bindingHandlers.bsCarousel = {
	# 	init: (element, valueAccessor) ->
	# 		@val = ko.utils.unwrapObservable(valueAccessor())
	# 		@options = { title: val.title, animation: val.animation, placement: val.placement, trigger: val.trigger, delay: val.delay,content: if $(val.target).html()? then $(val.target).html() else val.content }
	# 		$('.carousel').carousel()
	# 		return { controlsDescendantBindings: false}
	# }