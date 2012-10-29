 var backendComponent = {

            hub: null,
            isUserLoggedIn: false,
            user: null,

            // First we are a component, so we need to implement the 4 methods required to be a valid component:

            /**
             * Method returning the component <b>unique</b>
             * name. Using a fully qualified name is encouraged.
             * @return the component unique name
             */
            getComponentName: function() {
                return 'backend-component';
            },

            /**
             * Configure method. This method is called when the
             * component is registered on the hub.
             * @param theHub the hub
             */
            configure: function(theHub) {
                this.hub = theHub;
                // We provide the UserContractService:
                this.hub.provideService({
                    component: this,
                    contract: UserServiceContract
                });
            },

            /**
             * The Start function
             * This method is called when the hub starts or just
             * after configure if the hub is already started.
             */
            start: function() {},

            /**
             * The Stop method is called when the hub stops or
             * just after the component removal if the hub is 
             * not stopped. No events can be send in this method.
             */
            stop: function() {},

            // Now the UserServiceContract implementation:

            /**
             * Checks if the user is logged in.
             */
            isLoggedIn: function() {
                return this.isUserLoggedIn;
            },

            /**
             * Get the logged user.
             */
            getUser: function() {
                return this.user;
            },

            /**
             * Ask to log in.
             * This method simulates an async call
             * and so returns immediately.
             * @param {String} name
             */
            login: function(name) {
                // Because setTimeout calls the method on the global object (window),
                // we use a closure.
                var self = this;
                setTimeout(function() { self.loggedIn(name); }, 2000);
                return;
            },

            /**
             * Ask to log out.
             * This method simulates an async call,
             * and so returns immediately.
             */
            logout: function() {
                var self = this;
                setTimeout(function() { self.loggedOut(); }, 1000);
                return;
            },


            // We have two internal/private methods sending events

            /**
            * This method is called 2 seconds after the login request.
            * It's used to inform the hub that the login was successful.
            */
            loggedIn: function(name) {
                this.isLoggedIn = true;
                this.user = name;
                // We send an event notifying other components that we're logged in.
                this.hub.publish(this, "/user/login", {
                    loggedIn: true
                });
            },

            loggedOut: function() {
                this.isLoggedIn = false;
                this.user = null;
                // We send an event notifying other components that we're logged out.
                this.hub.publish(this, "/user/login", {
                    loggedIn: false
                });
            },
        }