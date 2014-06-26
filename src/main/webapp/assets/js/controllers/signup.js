angular.module('customLoginApp')
    .controller('signupController', ['$scope', '$http', function($scope) {

        $scope.submitForm = function(){

            console.log("going to my dashboard");

            $window.alert("Signed up!");
        }
    }]);
}