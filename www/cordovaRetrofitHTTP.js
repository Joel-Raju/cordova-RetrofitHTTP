var exec = require('cordova/exec');


function mergeHeaders(globalHeaders, localHeaders) {
    var globalKeys = Object.keys(globalHeaders);
    var key;
    for (var i = 0; i < globalKeys.length; i++) {
        key = globalKeys[i];
        if (!localHeaders.hasOwnProperty(key)) {
            localHeaders[key] = globalHeaders[key];
        }
    }
    return localHeaders;
}


var retrofitHTTP = {
	headers: {},
	get: function(url, params, headers, success, failure) {
		params  = params || {};
		headers = headers || {};
	},
	post: function(url, params, headers, success, failure) {
		headers = mergeHeaders(this.headers, headers);
		return exec(success, failure, )
	}
};

module.exports = retrofitHTTP;

if (typeof angular !== 'undefined') {
	angular.module('cordova', [])
		.factory('cordovaHTTP', function($timeout, $q) {
			function makePromise(fn, args, async) {
				var deferred = $q.defer();

				var success = function(response) {

				};

				var failure = function(response) {
					if(async) {
						$timeout(function() {

						});
					}
				};
			}
		});
} else {
	window.cordovaHTTP = http;
}

window.retrofitHTTP = retrofitHTTP;