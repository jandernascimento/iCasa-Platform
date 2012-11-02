define ['jquery','jquery.ui','knockout','bootstrap'], ($,ui,ko,bs) ->
	#Register the knockout custom handler to the bootstrap popover
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

	class ToggleHandler
		constructor:(element, @firstElement, @secondElement, @effect)->
			$(@secondElement).hide();
			$(@firstElement).show();
			
			@hidden = true
			$(element).click(@.handleEvent)
		handleEvent:()=>
			if @hidden
				$(@firstElement).toggle(@effect)
				$(@secondElement).toggle(@effect)
			else
				$(@secondElement).toggle(@effect)
				$(@firstElement).toggle(@effect)
			@hidden = !@hidden







