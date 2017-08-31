package com.github.joelraju;


public class ApiUtils {

	public static ApiService getService() {
		return RetrofitClient.getClient().create(ApiService.class);
	}
}