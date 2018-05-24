var Login = function() {

    var handleLogin = function() {
        $('.login-form input').keypress(function(e) {
            if (e.which == 13) {
                $('.login-form').submit(); //form validation success, call ajax form submit
                return false;
            }
        });

        $('.forget-form input').keypress(function(e) {
            if (e.which == 13) {
                $('.forget-form').submit();
                return false;
            }
        });

        $('#forget-password').click(function(){
            $('.login-form').hide();
            $('.forget-form').show();
        });

        $('#back-btn').click(function(){
            $('.login-form').show();
            $('.forget-form').hide();
        });
    }

 
  

    return {
        //main function to initiate the module
        init: function() {

            handleLogin();

            // init background slide images
            $('.login-bg').backstretch([
                "assets/pages/img/login/bg3.jpg",
                "assets/pages/img/login/bg2.jpg",
                "assets/pages/img/login/mangosteen.jpg"
                ], {
                  fade: 1000,
                  duration: 8000
                }
            );

            $('.forget-form').hide();

        }

    };

}();

jQuery(document).ready(function() {
    Login.init();
});