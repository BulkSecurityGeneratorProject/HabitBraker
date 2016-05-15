'use strict';

describe('Controller Tests', function() {

    describe('ExerciseRegularly Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockExerciseRegularly, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockExerciseRegularly = jasmine.createSpy('MockExerciseRegularly');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ExerciseRegularly': MockExerciseRegularly,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("ExerciseRegularlyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'habitBrakerApp:exerciseRegularlyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
