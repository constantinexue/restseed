var MessageListCtrl = ['$scope', '$http', function($scope, $http) {
    $http({
        method: 'GET',
        url: 'http://localhost:8080/messages?skip=0&take=20'
    }).
    success(function(data, status, headers, config) {
        $scope.messages = data;
    }).
    error(function(data, status, headers, config) {
    });

    $scope.create = function(messageText) {
        $scope.messages.objects.push({
            text: $scope.messageText
        });
        $scope.messageText = "";
    }
}];