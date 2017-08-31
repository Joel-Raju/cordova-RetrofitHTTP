package com.github.joelraju;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import okio.Buffer;
import okio.BufferedSource;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.TlsVersion;
import okhttp3.OkHttpClient;
import okhttp3.ConnectionSpec;

import javax.net.ssl.SSLContext;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
 
public class RetrofitClient {
 
    private static Retrofit retrofit = null;
    
 	private static final String BASE_URL = "http://www.example.com/";

    private static Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());


    private static OkHttpClient getNewHttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .cache(null)
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS);

        return enableTls12OnPreLollipop(client).build();
    }


    private static OkHttpClient.Builder enableTls12OnPreLollipop(OkHttpClient.Builder client) {
        
        if (android.os.Build.VERSION.SDK_INT >= 16 && android.os.Build.VERSION.SDK_INT < 22) {
            try {
                SSLContext sc = SSLContext.getInstance("TLSv1.2");
                sc.init(null, null, null);
                client.sslSocketFactory(new Tls12SocketFactory(sc.getSocketFactory()));

                ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .build();

                List<ConnectionSpec> specs = new ArrayList<ConnectionSpec>();
                specs.add(cs);
                specs.add(ConnectionSpec.COMPATIBLE_TLS);
                specs.add(ConnectionSpec.CLEARTEXT);

                client.connectionSpecs(specs);
            } catch (Exception exc) {
                //Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc);
            }
        }

        return client;
    }                

    public static Retrofit getClient() {
        if (retrofit == null) {
            builder.client(getNewHttpClient());
            retrofit = builder.build();
        }

        return retrofit;
    }
}