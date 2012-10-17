$(document).ready(function(){
        // Component registration
        hub
            .registerComponent(backendComponent)
            .registerComponent(frontendComponent, {
                loginId : '#login',
                logoutId : '#logout',
                statusId : '#status',
            })
            .start();
    });