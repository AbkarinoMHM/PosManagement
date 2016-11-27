angular.module('posApp')
    .config(['$locationProvider', '$routeProvider','$provide', '$mdIconProvider',
        function config($locationProvider, $routeProvider) {
           

            $locationProvider.hashPrefix('!');

            $routeProvider
                .when('/', {
                    template: '<p>Home Page</p>'
                }).when('/login', {
                template: '<login-form></login-form>'
            }).when('/regions', {
                template: '<regions-list></regions-list>'
            }).when('/projects', {
                template: '<projects-list></projects-list>'
            }).when('/users', {
                template: '<users-list></users-list>'
            }).otherwise('/');

        }


    ])
    .run(['$rootScope', '$location', '$cookieStore', '$http',
        function ($rootScope, $location, $cookieStore, $http) {
            // keep user logged in after page refresh
            $rootScope.globals = $cookieStore.get('globals') || {};
            if ($rootScope.globals.currentUser) {
                $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
            }

            $rootScope.$on('$locationChangeStart', function (event, next, current) {
                // redirect to login page if not logged in
                if ($location.path() !== '/login' && !$rootScope.globals.currentUser) {
                    $location.path('/login');
                }
            });
        }]);
