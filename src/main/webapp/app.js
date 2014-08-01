var myApp = angular.module('loginForm', []);

myApp.service('sharedProperties', function() {
    var apiKey = "";
    var apiSecret = "";
    var encodedAuth = "";
    var oauthToken = "";
    var userName = "";

    return {
        getApiKey: function() {
            return apiKey;
        },
        setApiKey: function(value) {
            apiKey = value;
        },
        getApiSecret: function() {
            return apiSecret;
        },
        setApiSecret: function(value) {
            apiSecret = value;
        },
        getEncodedAuth: function() {
            return encodedAuth;
        },
        setEncodedAuth: function(value) {
            encodedAuth = value;
        },
        getOauthToken: function() {
            return oauthToken;
        },
        setOauthToken: function(value) {
            oauthToken = value;
        },
        getUsername: function() {
            return userName;
        },
        setUsername: function(value) {
            userName = value;
        }
    };
});

myApp.controller('loginController', ["$scope", "$window", "$http", 'sharedProperties', function($scope, $window, $http, sharedProperties) {

    $scope.submitFunction = function() {

        var username = $scope.userName;
        var password = $scope.password;

        //Try to log in to account
        $http({method: "POST", url: '/rest/login',
            data: {'username': username, 'password': password}
        }).success(function(data, status, headers, config) {
                $window.location.href = "/dashboard.html";

            }).
            error(function(data, status, headers, config) {
                $window.alert("Wrong username/password. Please try again");
            });
    }
}]);

myApp.controller('logoutController', ["$scope", "$window", "$http", function($scope, $window, $http) {

    $scope.makeLogoutCall = function() {

        $http({method: "GET", url: '/rest/logout',
        }).success(function(data, status, headers, config) {
                $window.location.href = "/login.html";

            }).
            error(function(data, status, headers, config) {
                $window.alert("Log out error");
            });
    }
}]);

myApp.controller('makeAccountController', ["$scope", "$http", "$window", function($scope, $http, $window) {

    $scope.signUp = function() {

        //Get data from Create New account form
        var firstName = $scope.firstName;
        var lastName = $scope.lastName;
        var userName = $scope.userName;
        var email = $scope.email;
        var password = $scope.password;

        //Try to create the account
        $http({method: "POST", url: '/rest/makeStormpathAccount',
               data: {'first_name': firstName, 'last_name': lastName, 'user_name': userName, 'email': email, 'password': password}
        }).success(function(data, status, headers, config) {

            //Clear Create Account fields
            $scope.firstName = "";
            $scope.lastName = "";
            $scope.userName = "";
            $scope.email = "";
            $scope.password = "";
            $window.alert("Account Created! Now, Sign In!");

        }).
            error(function(data, status, headers, config) {
                $window.alert("Error");
        });
    }

}]);

myApp.controller('ApiKeyController', ["$scope", "$http", 'sharedProperties', '$window', function($scope, $http, sharedProperties, $window) {

    //Get an API key and secret
    $http({method: "GET", url: '/rest/getApiKey'}).success(function(data, status, headers, config) {

        $scope.apiKey = data.api_key;
        $scope.apiSecret = data.api_secret;
        var username = data.username;
        $scope.userName = username;

        sharedProperties.setApiKey($scope.apiKey);
        sharedProperties.setApiSecret($scope.apiSecret);

        //Base64 encode the key and secret and save to shared properties
        var Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(e){var t="";var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length)
        {n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.
            charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9\+\/\=]/g,"");while(f<e.length){s=this._keyStr.
            indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.
            fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);return t},_utf8_encode:function(e){e=e.replace(/\r\n/g,"\n");var t="";for(var n=0;n<e.length;n++)
        {var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);
            t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);
            t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t}}

        var toEncode = sharedProperties.getApiKey().concat(':', sharedProperties.getApiSecret());
        var encodedAuth = Base64.encode(toEncode);
        sharedProperties.setEncodedAuth(encodedAuth);
        sharedProperties.setUsername(username);


    })
        .error(function(data, status, headers, config) {
            $window.alert("Please log in");
            $window.location.href = "/login.html";
        });

}]);


myApp.controller('RestBasicController', ["$scope", "$window", "$http", 'sharedProperties', function($scope, $window, $http, sharedProperties) {

    $scope.myCity = "______";

    $scope.makeRestCall = function() {

        //Get the temperature of specified city from REST endpoint, using Basic Auth
        $http({method: "GET", url: '/rest/api/weather/' + $scope.city, headers: {'Authorization': 'Basic ' + sharedProperties.getEncodedAuth()}}).success(function(data, status, headers, config) {
            $scope.temp = data;
            $scope.myCity = $scope.city;

        })
            .error(function(data, status, headers, config) {
                $window.alert("Error");
            });

    }
}]);

myApp.controller('OauthTokenController', ["$scope", "$http", "$window",'sharedProperties', function($scope, $http, $window, sharedProperties) {

    $scope.providers = [{provider:{Id:'London', ScopeName: 'London'}},{provider:{Id:'Berlin', ScopeName: 'Berlin'}},
        {provider:{Id:'San Mateo', ScopeName: 'SanMateo'}}, {provider:{Id:'San Francisco', ScopeName: 'SanFrancisco'}}];
    $scope.ids = {};


    $scope.getOauthToken = function() {

        var myData = $.param({grant_type: "client_credentials"});
        var scopeData = "";

        var first = 0;
        for(var i in $scope.ids) {
            console.log(i + "=" + $scope.ids[i]);
            if($scope.ids[i] == true){
                if(first == 0) {
                    scopeData = scopeData + i;
                    first = 1;
                }
                else{
                    scopeData = scopeData + " " + i;
                }
            }
        }

        myData = $.param({grant_type: "client_credentials", scope: scopeData});

        console.log(myData);

        //Try and get an Oauth Token
        $http({method: "POST", url: '/rest/oauthToken',
            headers: {'Authorization': 'Basic ' + sharedProperties.getEncodedAuth(), 'Content-Type': 'application/x-www-form-urlencoded'},
            data : myData})

            .success(function(data, status, headers, config) {
                console.log(data.access_token);
                var oauthToken = data.access_token;
                sharedProperties.setOauthToken(oauthToken);
                $scope.oauthToken = oauthToken;


            }).
            error(function(data, status, headers, config) {
                $window.alert("Error");
            });
        }


}]);


myApp.controller('RestOauthController', ["$scope", "$window", "$http", 'sharedProperties', function($scope, $window, $http, sharedProperties) {

    $scope.myCity = "______";

    $scope.makeRestCall = function() {

        //Get the temperature of specified city from REST endpoint, using Oauth
        $http({method: "GET", url: '/rest/api/weather/' + $scope.city, headers: {'Authorization': 'Bearer ' + sharedProperties.getOauthToken()}}).success(function(data, status, headers, config) {
            $scope.temp = data;
            $scope.myCity = $scope.city;

        })
            .error(function(data, status, headers, config) {
                $window.alert("Permission Denied!");
            });

    }
}]);