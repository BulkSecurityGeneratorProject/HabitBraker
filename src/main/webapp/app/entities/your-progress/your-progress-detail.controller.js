(function() {
    'use strict';

    angular
        .module('habitBrakerApp')
        .controller('YourProgressDetailController', YourProgressDetailController);

    YourProgressDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'YourProgress', 'ExerciseRegularly'];

    function YourProgressDetailController($scope, $rootScope, $stateParams, entity, YourProgress, ExerciseRegularly) {
        var vm = this;
        vm.yourProgress = entity;
        
        var unsubscribe = $rootScope.$on('habitBrakerApp:yourProgressUpdate', function(event, result) {
            vm.yourProgress = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
