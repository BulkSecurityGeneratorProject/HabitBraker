(function() {
    'use strict';
    angular
        .module('habitBrakerApp')
        .factory('ExerciseRegularly', ExerciseRegularly);

    ExerciseRegularly.$inject = ['$resource', 'DateUtils'];

    function ExerciseRegularly ($resource, DateUtils) {
        var resourceUrl =  'api/exercise-regularlies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startDate = DateUtils.convertDateTimeFromServer(data.startDate);
                        data.endDate = DateUtils.convertDateTimeFromServer(data.endDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
