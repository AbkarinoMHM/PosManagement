angular.module('posApp')
    .config(['$locationProvider', '$routeProvider',
    function config($locationProvider, $routeProvider) {
        $locationProvider.hashPrefix('!');

        $routeProvider.when('/', {
            template: '<p>Home Page</p>'
        }).when('/regions', {
            template: '<regions-list></regions-list>'
        }).when('/projects', {
            template: '<projects-list></projects-list>'
        }).otherwise('/');
    }
]);
