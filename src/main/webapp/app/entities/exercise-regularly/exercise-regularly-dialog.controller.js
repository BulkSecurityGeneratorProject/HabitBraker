(function() {
    'use strict';

    angular
        .module('habitBrakerApp')
        .controller('ExerciseRegularlyDialogController', ExerciseRegularlyDialogController);

    ExerciseRegularlyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ExerciseRegularly', 'User'];

    function ExerciseRegularlyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ExerciseRegularly, User) {
        var vm = this;
        vm.exerciseRegularly = entity;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('habitBrakerApp:exerciseRegularlyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.exerciseRegularly.id !== null) {
                ExerciseRegularly.update(vm.exerciseRegularly, onSaveSuccess, onSaveError);
            } else {
                ExerciseRegularly.save(vm.exerciseRegularly, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
