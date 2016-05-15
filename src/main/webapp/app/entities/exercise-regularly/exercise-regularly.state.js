(function() {
    'use strict';

    angular
        .module('habitBrakerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('exercise-regularly', {
            parent: 'entity',
            url: '/exercise-regularly?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'habitBrakerApp.exerciseRegularly.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/exercise-regularly/exercise-regularlies.html',
                    controller: 'ExerciseRegularlyController',
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
                    $translatePartialLoader.addPart('exerciseRegularly');
                    $translatePartialLoader.addPart('charities');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('exercise-regularly-detail', {
            parent: 'entity',
            url: '/exercise-regularly/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'habitBrakerApp.exerciseRegularly.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/exercise-regularly/exercise-regularly-detail.html',
                    controller: 'ExerciseRegularlyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('exerciseRegularly');
                    $translatePartialLoader.addPart('charities');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ExerciseRegularly', function($stateParams, ExerciseRegularly) {
                    return ExerciseRegularly.get({id : $stateParams.id});
                }]
            }
        })
        .state('exercise-regularly.new', {
            parent: 'exercise-regularly',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exercise-regularly/exercise-regularly-dialog.html',
                    controller: 'ExerciseRegularlyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                committedTo: null,
                                pledgedAmmount: null,
                                pledgedCharity: null,
                                startDate: null,
                                endDate: null,
                                notes: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('exercise-regularly', null, { reload: true });
                }, function() {
                    $state.go('exercise-regularly');
                });
            }]
        })
        .state('exercise-regularly.edit', {
            parent: 'exercise-regularly',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exercise-regularly/exercise-regularly-dialog.html',
                    controller: 'ExerciseRegularlyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExerciseRegularly', function(ExerciseRegularly) {
                            return ExerciseRegularly.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('exercise-regularly', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('exercise-regularly.delete', {
            parent: 'exercise-regularly',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/exercise-regularly/exercise-regularly-delete-dialog.html',
                    controller: 'ExerciseRegularlyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ExerciseRegularly', function(ExerciseRegularly) {
                            return ExerciseRegularly.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('exercise-regularly', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
