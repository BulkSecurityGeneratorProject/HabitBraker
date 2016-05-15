'use strict';

describe('Controller Tests', function() {

    describe('YourProgress Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockYourProgress, MockExerciseRegularly;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockYourProgress = jasmine.createSpy('MockYourProgress');
            MockExerciseRegularly = jasmine.createSpy('MockExerciseRegularly');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'YourProgress': MockYourProgress,
                'ExerciseRegularly': MockExerciseRegularly
            };
            createController = function() {
                $injector.get('$controller')("YourProgressDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'habitBrakerApp:yourProgressUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
