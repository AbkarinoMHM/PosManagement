angular.module('posApp')
    .config(['$locationProvider', '$routeProvider', '$provide', '$mdIconProvider',
        function config($locationProvider, $routeProvider) {


            $locationProvider.hashPrefix('!');

            $routeProvider.when('/', {
                    template: '<p>Home Page</p>'
                }).when('/login', {
                template: '<login-form></login-form>'
            }).when('/regions', {
                template: '<regions-list></regions-list>'
            }).when('/projects', {
                template: '<projects-list></projects-list>'
            }).when('/users', {
                template: '<users-list></users-list>'
            }).when('/areas', {
                template: '<areas-list></areas-list>'
            }).when('/merchantBranch', {
                template: '<merchant-branch-list></merchant-branch-list>'
            }).when('/vendorBranch', {
                template: '<vendor-branch-list></vendor-branch-list>'
            }).when('/serviceCenter', {
                template: '<service-centers-list></service-centers-list>'
            }).when('/technician', {
                template: '<technicians-list></technicians-list>'
            }).when('/posType', {
                template: '<pos-types-list></pos-types-list>'
            }).when('/posVendor', {
                template: '<pos-vendors-list></pos-vendors-list>'
            }).when('/pos', {
                template: '<pos-list></pos-list>'
            }).when('/underconstruction', {
                template: '<h3>This feature is under construction!</h3>'
            }).otherwise('underconstruction');

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
