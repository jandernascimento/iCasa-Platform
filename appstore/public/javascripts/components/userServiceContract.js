/**
 * User Service Contract definition.
 * The implementations are empty,
 * like a Java interface.
 */
var UserServiceContract = {
    /**
     * Checks if the user is logged in.
     */
    isLoggedIn : function() { },

    /**
     * Get the logged-in user.
     */
    getUser : function() { },

    /**
     * Ask to log in.
     * This method simulates an async call
     * and so returns immediately.
     * @param {String} name
     */
    login: function(name) { },

    /**
     * Ask to log out.
     * This method simulates an async call.
     * and so returns immediately.
     */
    logout: function() { }
}