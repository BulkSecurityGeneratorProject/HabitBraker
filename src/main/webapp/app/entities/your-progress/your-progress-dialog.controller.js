(function() {
    'use strict';

    angular
        .module('habitBrakerApp')
        .controller('YourProgressDialogController', YourProgressDialogController);

    YourProgressDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'YourProgress', 'ExerciseRegularly'];

    function YourProgressDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, YourProgress, ExerciseRegularly) {
        var vm = this;
        vm.yourProgress = entity;
        vm.exerciseregularlies = ExerciseRegularly.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('habitBrakerApp:yourProgressUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.yourProgress.id !== null) {
                YourProgress.update(vm.yourProgress, onSaveSuccess, onSaveError);
            } else {
                YourProgress.save(vm.yourProgress, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
