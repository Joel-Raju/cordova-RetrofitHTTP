(function() {

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
			if (!params) {
          		params = {};
        	}
        	if (!headers) {
	  			headers = {};
			}

			headers = mergeHeaders(this.headers, headers);

			return exec(success, failure, "CordovaRetrofitHttpPlugin", "get", [url, params, headers]);
		},
		post: function(url, params, headers, success, failure) {
			headers = mergeHeaders(this.headers, headers);
			return exec(success, failure, "CordovaRetrofitHttpPlugin", "post", [url, params, headers]);
		},
		downloadFile: function() {

		}
	};

	module.exports = retrofitHTTP;

	if (typeof angular !== 'undefined') {
		angular.module('cordovaRetrofitHTTP', [])
			.factory('cordovaRetrofitHTTP', function($timeout, $q) {
				function generatePromise(fn, args, async) {
					var deferred = $q.defer();

					var success = function(response) {
						if (async) {
							$timeout(function() {
								deferred.resolve(response);
							});
						} else {
							deferred.resolve(response);
						}
					};

					var failure = function(response) {
						if(async) {
							$timeout(function() {
								deferred.resolve(response);
							});
						} else {
							deferred.reject(response);
						}
					};

					args.push(success);
					args.push(failure);

					fn.apply(retrofitHTTP, args);
	            	
	            	return deferred.promise;
				}

				var cordovaRetrofitHTTP = {
					get: function(url, params, headers) {
						return generatePromise(retrofitHTTP.get, [url, params, headers], true);
					},
					post: function(url, params, headers) {
						return generatePromise(retrofitHTTP.post, [url, params, headers], true);
					},
					downloadFile: function() {
						
					}
				};

				return cordovaRetrofitHTTP;
			});
	} else {
		window.cordovaRetrofitHTTP = retrofitHTTP;
	}

	window.cordovaRetrofitHTTP = retrofitHTTP;
})();
