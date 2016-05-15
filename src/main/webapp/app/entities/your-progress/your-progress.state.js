(function() {
    'use strict';

    angular
        .module('habitBrakerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('your-progress', {
            parent: 'entity',
            url: '/your-progress?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'habitBrakerApp.yourProgress.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/your-progress/your-progresses.html',
                    controller: 'YourProgressController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('yourProgress');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('your-progress-detail', {
            parent: 'entity',
            url: '/your-progress/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'habitBrakerApp.yourProgress.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/your-progress/your-progress-detail.html',
                    controller: 'YourProgressDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('yourProgress');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'YourProgress', function($stateParams, YourProgress) {
                    return YourProgress.get({id : $stateParams.id});
                }]
            }
        })
        .state('your-progress.new', {
            parent: 'your-progress',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/your-progress/your-progress-dialog.html',
                    controller: 'YourProgressDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                metGoalToday: false,
                                date: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('your-progress', null, { reload: true });
                }, function() {
                    $state.go('your-progress');
                });
            }]
        })
        .state('your-progress.edit', {
            parent: 'your-progress',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/your-progress/your-progress-dialog.html',
                    controller: 'YourProgressDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['YourProgress', function(YourProgress) {
                            return YourProgress.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('your-progress', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('your-progress.delete', {
            parent: 'your-progress',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/your-progress/your-progress-delete-dialog.html',
                    controller: 'YourProgressDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['YourProgress', function(YourProgress) {
                            return YourProgress.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('your-progress', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
