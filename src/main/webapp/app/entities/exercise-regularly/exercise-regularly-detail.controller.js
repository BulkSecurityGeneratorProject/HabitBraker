(function() {
    'use strict';

    angular
        .module('habitBrakerApp')
        .controller('ExerciseRegularlyDetailController', ExerciseRegularlyDetailController);

    ExerciseRegularlyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ExerciseRegularly', 'User'];

    function ExerciseRegularlyDetailController($scope, $rootScope, $stateParams, entity, ExerciseRegularly, User) {
        var vm = this;
        vm.exerciseRegularly = entity;
        
        var unsubscribe = $rootScope.$on('habitBrakerApp:exerciseRegularlyUpdate', function(event, result) {
            vm.exerciseRegularly = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
