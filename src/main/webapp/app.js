var myApp = angular.module('loginForm', []);

myApp.controller('makeAccountController', ["$scope", "$http", "$window", function($scope, $http, $window) {
    $scope.signUp = function() {

        $http.defaults.headers.common.firstName = $scope.firstName;
        $http.defaults.headers.common.lastName = $scope.lastName;
        $http.defaults.headers.common.userName = $scope.userName;
        $http.defaults.headers.common.email = $scope.email;
        $http.defaults.headers.common.password = $scope.password;

        $http({method: "POST", url: '/rest/makeStormpathAccount'}).success(function(data, status, headers, config) {
            $window.alert("Account Created! Now, Sign In!");

        }).
            error(function(data, status, headers, config) {
                $window.alert("Error");
        });
    }

}]);

myApp.service('sharedProperties', function() {
    var apiKey = "";
    var apiSecret = "";

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
        }
    };
});

myApp.controller('loginController', ["$scope", "$window", "$http", function($scope, $window, $http) {
    $scope.submitFunction = function() {

          $http.defaults.headers.common.username = $scope.userName;
          $http.defaults.headers.common.password = $scope.password;

        $http({method: "POST", url: '/rest/login'}).success(function(data, status, headers, config) {
              if(headers('MyResult') == "Not Authorized") {
                $window.alert("Wrong username/password. Please try again");

              }
              else {
                  $window.location.href = "/dashboard.html";
              }

          }).
          error(function(data, status, headers, config) {
                $window.alert("Error");
          });


    }

}]);

myApp.controller('weatherController', ["$scope", "$window", "$http", function($scope, $window, $http) {
        $scope.$watch('city', function(){
            console.log($scope.city);

            $http({method: "GET", url: '/rest/weather/' + $scope.city}).success(function(data, status, headers, config) {
                console.log(data);
                $scope.temp = data;

            })
            .error(function(data, status, headers, config) {
                $window.alert("Error");
            });


        })

}]);

myApp.controller('RestController', ["$scope", "$window", "$http", 'sharedProperties', function($scope, $window, $http, sharedProperties) {
    $scope.makeRestCall = function() {
        console.log(sharedProperties.getApiKey());
        console.log(sharedProperties.getApiSecret());

        var Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(e){var t="";var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length)
        {n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.
            charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9\+\/\=]/g,"");while(f<e.length){s=this._keyStr.
            indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.
            fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);return t},_utf8_encode:function(e){e=e.replace(/\r\n/g,"\n");var t="";for(var n=0;n<e.length;n++)
        {var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);
            t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);
            t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t}}

        var apiKey = sharedProperties.getApiKey();
        var apiSecret = sharedProperties.getApiSecret();
        var toEncode = apiKey.concat(':', apiSecret);
        console.log(toEncode);
        var encodedAuth = Base64.encode(toEncode);
        console.log(encodedAuth);

        $http({method: "GET", url: '/rest/api/weather/' + $scope.city, headers: {'Authorization': 'Basic ' + encodedAuth}}).success(function(data, status, headers, config) {
            console.log(data);
            $scope.temp = data;
            $scope.myCity = $scope.city;

        })
        .error(function(data, status, headers, config) {
            $window.alert("Error");
        });

    }
}]);

myApp.controller('ApiKeyController', ["$scope", "$http", 'sharedProperties', function($scope, $http, sharedProperties) {

    $http({method: "GET", url: '/rest/getApiKey'}).success(function(data, status, headers, config) {
        $scope.apiKey = headers('APIKeyId');
        $scope.apiSecret = headers('APIKeySecret');
        //console.log($scope.apiKey);
        //console.log($scope.apiSecret);
        sharedProperties.setApiKey($scope.apiKey);
        sharedProperties.setApiSecret($scope.apiSecret);
        //console.log(sharedProperties.getApiKey());

    })
        .error(function(data, status, headers, config) {
            window.alert("Error");
        });

}]);