(function() {
    'use strict';

    angular
        .module('habitBrakerApp')
        .controller('ExerciseRegularlyDeleteController',ExerciseRegularlyDeleteController);

    ExerciseRegularlyDeleteController.$inject = ['$uibModalInstance', 'entity', 'ExerciseRegularly'];

    function ExerciseRegularlyDeleteController($uibModalInstance, entity, ExerciseRegularly) {
        var vm = this;
        vm.exerciseRegularly = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ExerciseRegularly.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
