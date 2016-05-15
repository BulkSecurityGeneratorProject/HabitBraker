(function() {
    'use strict';

    angular
        .module('habitBrakerApp')
        .controller('YourProgressDeleteController',YourProgressDeleteController);

    YourProgressDeleteController.$inject = ['$uibModalInstance', 'entity', 'YourProgress'];

    function YourProgressDeleteController($uibModalInstance, entity, YourProgress) {
        var vm = this;
        vm.yourProgress = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            YourProgress.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
