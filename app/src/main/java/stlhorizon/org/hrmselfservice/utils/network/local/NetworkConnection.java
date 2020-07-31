package stlhorizon.org.hrmselfservice.utils.network.local;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class NetworkConnection {
    private static Application myapp;
    private  static ProxyConfig proxyConfig;
    private static MediaType mediaType = MediaType.parse("application/json");

    public  static  void useProxy(ProxyConfig proxyConfig){
        NetworkConnection.proxyConfig=proxyConfig;
    }
    private static Certificate certificate=Certificate.NO_OVERRIDE;

    public static void setCertificate(Certificate certificate) {
        NetworkConnection.certificate = certificate;
    }

    public static Certificate getCertificate() {
        return certificate;
    }

    public static boolean isConnectedToInternet(Application application) {
        myapp = application;
        myapp.getSystemService(Context.CONNECTIVITY_SERVICE);
        ConnectivityManager connectivityManager = (ConnectivityManager) myapp.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo == null ? false : true;
    }
    public static  void uploadImageToRemoteServer(final String url, final File imagefile, final JSONObject headers, final JSONObject body, final OnReceivingResult onReceivingResult){
        Runnable runnable= new Runnable() {
            @Override
            public void run() {


                OkHttpClient okHttpClient = new OkHttpClient();
                MultipartBody multipartBody;
                MultipartBody.Builder requestbuilder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file","file",RequestBody.create(MediaType.parse("image/png"),imagefile));
                if(body!=null){
                    while(body.keys().hasNext()){
                        try {
                            String key=body.keys().next();

                            String value=body.getString(key);
                            requestbuilder.addFormDataPart(key,value)  ;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
                multipartBody=requestbuilder.build();
                //.build();

                Request.Builder request= new  Request.Builder();
                request.url(url);
                if(proxyConfig!=null){
                    proxyConfig.bind(okHttpClient.newBuilder());
                }
                if(headers!=null) {
                    Iterator<String> keys = headers.keys();
                    while (keys.hasNext()) {
                        String ourkey = keys.next();
                        try {

                            request.addHeader(ourkey, headers.getString(ourkey));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
                request.post(multipartBody);

                okHttpClient.newCall(request.build()).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        seriesRouter(e,onReceivingResult);


                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        seriesRouter(response,onReceivingResult);



                    }
                });
            }
        };
        new Thread(runnable).start();
    }
    public static void makeAPutRequest(String url, JSONObject headers,final OnReceivingResult receivingResult) {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder request = new Request.Builder().url(url);
        if(proxyConfig!=null){
            proxyConfig.bind(okHttpClient.newBuilder());
        }
        if(headers!=null) {
            Iterator<String> keys = headers.keys();
            while (keys.hasNext()) {
                String ourkey = keys.next();
                try {
                    request.addHeader(ourkey, headers.getString(ourkey));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        request.put(new RequestBody() {
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {

            }
        });
        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                seriesRouter(e,receivingResult);


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                seriesRouter(response,receivingResult);


            }

        });

    }

    public static void makeAPutRequest(String url, final String body, JSONObject headers,final OnReceivingResult receivingResult) {

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(mediaType, body);
        Request.Builder request = new Request.Builder().url(url);
        if(proxyConfig!=null){
            proxyConfig.bind(okHttpClient.newBuilder());
        }
        if(headers!=null) {
            Iterator<String> keys = headers.keys();
            while (keys.hasNext()) {
                String ourkey = keys.next();
                try {
                    request.addHeader(ourkey, headers.getString(ourkey));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        request.put(requestBody);
        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                seriesRouter(e,receivingResult);

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                seriesRouter(response,receivingResult);


            }
        });

    }
    public  static  void downloadImage(String url, final OnReceivingResult receivingResult){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder request = new Request.Builder().url(url);
        if(proxyConfig!=null){
            proxyConfig.bind(okHttpClient.newBuilder());
        }
        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                seriesRouter(e,receivingResult);


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                seriesRouter(response,receivingResult);

            }
        });
    }
    public static void makeAPostRequest(final String url,   final String body, final JSONObject headers, final OnReceivingResult receivingResult) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient;
                OkHttpClient.Builder okHttpClientbuilder=new OkHttpClient.Builder();
                if(proxyConfig!=null){
                    proxyConfig.bind(okHttpClientbuilder);
                }
                if (certificate==Certificate.NO_OVERRIDE){
                    okHttpClient = new OkHttpClient();
                }else{
                    okHttpClient =getUnsafeOkHttpClient(okHttpClientbuilder);
                }


                RequestBody requestBody = RequestBody.create(mediaType, body);
                Request.Builder request = new Request.Builder().url(url);


                if(headers!=null) {
                    Iterator<String> keys = headers.keys();
                    while (keys.hasNext()) {
                        String ourkey = keys.next();
                        try {
                            request.addHeader(ourkey, headers.getString(ourkey));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
                request.post(requestBody);
                okHttpClient.newCall(request.build()).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        seriesRouter( e,receivingResult);


                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        seriesRouter(response,receivingResult);


                    }
                });
            }
        }).start();

    }
    public static void makeAGetRequest(String url, JSONObject headers, final OnReceivingResult receivingResult) {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder request = new Request.Builder().url(url);
        if(proxyConfig!=null){
            proxyConfig.bind(okHttpClient.newBuilder());
        }
        if (headers!=null){
            Iterator<String> keys = headers.keys();
            while (keys.hasNext()) {
                String ourkey = keys.next();
                try {
                    request.addHeader(ourkey, headers.getString(ourkey));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                seriesRouter( e,receivingResult);
            }

            @Override
            public void onResponse(Call call, final Response response) {

                seriesRouter( response,receivingResult);

            }
        });

    }
    public static void makeAGetRequest(URL url, JSONObject headers, final OnReceivingResult receivingResult) {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder request = new Request.Builder().url(url);
        if(proxyConfig!=null){
            proxyConfig.bind(okHttpClient.newBuilder());
        }
        Iterator<String> keys = headers.keys();
        while (keys.hasNext()) {
            String ourkey = keys.next();
            try {
                request.addHeader(ourkey, headers.getString(ourkey));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                seriesRouter(e ,receivingResult);

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                seriesRouter( response,receivingResult);


            }
        });

    }
    //check the http response code and route accordingly code in http series i.e 100 series,200 series, 300 series upto 500 series
    private static void seriesRouter(final Response response, final OnReceivingResult onReceivingResult){
        final int code=response.code();
        if (response.body() == null) {
            onReceivingResult.onErrorResult(new IOException("Response body was null"));
            onReceivingResult.onAnyEvent();
            return;
        }
        try {
            final byte[] responsebody=response.body().bytes();
            final String our_dear_response = new String(responsebody);

            if (code>=100&&code<200){

                new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        RemoteResponse remoteResponse= new RemoteResponse(code,our_dear_response);
                        remoteResponse.setResponseBody(responsebody);
                        onReceivingResult.onReceiving100SeriesResponse(remoteResponse);
                        onReceivingResult.onAnyEvent();

                    }
                });

                return;
            }
            if (code>=200&&code<300){
                new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        RemoteResponse remoteResponse= new RemoteResponse(code,our_dear_response);
                        remoteResponse.setResponseBody(responsebody);
                        onReceivingResult.onReceiving200SeriesResponse(remoteResponse);
                        onReceivingResult.onAnyEvent();
                    }
                });

                return;
            }
            if (code>=300&&code<400){
                new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        onReceivingResult.onReceiving300SeriesResponse(new RemoteResponse(code,our_dear_response));
                        onReceivingResult.onAnyEvent();

                    }
                });

                return;
            }
            if (code>=400&&code<500){
                final RemoteResponse remoteResponse=new RemoteResponse(code,our_dear_response);

                new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        onReceivingResult.onReceiving400SeriesResponse(remoteResponse);
                        onReceivingResult.onAnyEvent();


                    }
                });


                return;
            }
            if (code>=500&&code<600){
                new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

                        onReceivingResult.onReceiving500SeriesResponse(new RemoteResponse(code,our_dear_response));
                        onReceivingResult.onAnyEvent();

                    }
                });



                return;
            }
            onReceivingResult.onErrorResult( new IOException(our_dear_response));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static void seriesRouter(final IOException ioexception, final OnReceivingResult onReceivingResult){
        new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                onReceivingResult.onErrorResult( ioexception);
                onReceivingResult.onAnyEvent();
            }
        });



    }
    public  static  void remoteResponseLogger(RemoteResponse remoteResponse){
        Log.e("Remote response","Code is :"+remoteResponse.getCode()+" "+remoteResponse.getMessage());
    }
    public static void generalJsonLogger(String tag,JSONObject jsonObject){
        Log.e(tag,jsonObject.toString());
    }
    /* public static JSONObject getGlobalHeaders(){
         JSONObject header= new JSONObject();
         try {
             header.put("accept", "application/json");
             header.put("Authorization", "Bearer " + Authenticate.getToken().getTokenString());

         } catch (JSONException e) {
             e.printStackTrace();
         }
         return  header;
     }*/
    public  static  void makeAPostOfQueryParams(String url, JSONObject headers, Map<String,String> queryParams, final OnReceivingResult onReceivingResult){
        OkHttpClient okHttpClient = new OkHttpClient();
        if(proxyConfig!=null){
            proxyConfig.bind(okHttpClient.newBuilder());
        }
        Request.Builder request = new Request.Builder();
        HttpUrl httpUrl=HttpUrl.parse(url);
        Iterator<String> keys = headers.keys();
        if (queryParams!=null) {
            HttpUrl.Builder builder = new HttpUrl.Builder();
            for (Map.Entry<String, String> query : queryParams.entrySet()){
                Log.e("query is",query.toString());

                try {
                    URL url1= new URL(url);

                    httpUrl = builder.addQueryParameter(query.getKey(), query.getValue()).scheme("http").host(url1.getHost()).encodedPath(url1.getPath()).build();
                    Log.e("Search path",httpUrl.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            request.url(httpUrl);
        }
        while (keys.hasNext()) {
            String ourkey = keys.next();
            try {
                request.addHeader(ourkey, headers.getString(ourkey));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                seriesRouter(e ,onReceivingResult);

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                seriesRouter( response,onReceivingResult);


            }
        });
    }


    private static OkHttpClient getUnsafeOkHttpClient(OkHttpClient.Builder builder) {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();


            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Upload Image
     *
     * @param url
     * @param sourceImageFile
     * @return null
     */
    public static void uploadFile(String url,JSONObject headers,JSONObject body,String fileName, String sourceImageFile,final OnReceivingResult onReceivingResult) {


        File sourceFile = new File(sourceImageFile);


        Log.d("File Upload", "File...::::" + sourceFile + " : " + sourceFile.exists());

        final MediaType MEDIA_TYPE = sourceImageFile.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image/jpeg");
        MultipartBody.Builder requestBody = new MultipartBody.Builder();
        requestBody.setType(MultipartBody.FORM);


        if (body != null) {
            Iterator<String> keys = body.keys();
            while (keys.hasNext()) {
                String ourkey = keys.next();
                try {
                    requestBody.addFormDataPart(ourkey, body.getString(ourkey));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        if (sourceFile.exists()){
            requestBody.addFormDataPart("image", "image", RequestBody.create(MEDIA_TYPE, sourceFile));
        }else {
            Log.e("file",sourceFile.getAbsolutePath());

        }


        Request.Builder requestbuilder = new Request.Builder()
                .url(url)
                .post(requestBody.build()) ;


        Iterator<String> keys = headers.keys();
        requestbuilder.header("content-type","multipart/form-data");
        while (keys.hasNext()) {
            String ourkey = keys.next();
            try {

                requestbuilder.addHeader(ourkey, headers.getString(ourkey));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        Request  request=requestbuilder.build();

        OkHttpClient client = new OkHttpClient();
//            Response response = client.newCall(request).execute();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.e("Request",call.toString());
                seriesRouter(e ,onReceivingResult);

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                seriesRouter( response,onReceivingResult);


            }
        });






    }
    public static void uploadFiles(String url,JSONObject headers,JSONObject body,String fileName, JSONObject sourceImageFile,final OnReceivingResult onReceivingResult) {
        MultipartBody.Builder requestBody = new MultipartBody.Builder();
        for (Iterator<String> it = sourceImageFile.keys(); it.hasNext(); ) {
            String key=it.next();
            File sourceFile = null;
            String fileAbsolute;
            try {
                sourceFile = new File(fileAbsolute=sourceImageFile.getString(key));

                Log.d("File Upload", "File...::::" + sourceFile + " : " + sourceFile.exists());

                final MediaType MEDIA_TYPE = fileAbsolute.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image/jpeg");

                requestBody.setType(MultipartBody.FORM);
                if (sourceFile.exists()){
                    requestBody.addFormDataPart(key, key, RequestBody.create(MEDIA_TYPE, sourceFile));
                }else {
                    Log.e("file",sourceFile.getAbsolutePath());

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        if (body != null) {
            Iterator<String> keys = body.keys();
            while (keys.hasNext()) {
                String ourkey = keys.next();
                try {
                    requestBody.addFormDataPart(ourkey, body.getString(ourkey));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }




        Request.Builder requestbuilder = new Request.Builder()
                .url(url)
                .post(requestBody.build()) ;


        Iterator<String> keys = headers.keys();
        requestbuilder.header("content-type","multipart/form-data");
        while (keys.hasNext()) {
            String ourkey = keys.next();
            try {

                requestbuilder.addHeader(ourkey, headers.getString(ourkey));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        Request  request=requestbuilder.build();

        OkHttpClient client = new OkHttpClient();
//            Response response = client.newCall(request).execute();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.e("Request",call.toString());
                seriesRouter(e ,onReceivingResult);

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                seriesRouter( response,onReceivingResult);


            }
        });






    }


//    ADDED FOR FORM MULTIPART

    public static void makeAPostRequestFormData(String url, final JSONObject mbody, JSONObject headers, final OnReceivingResult receivingResult) {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);


        if(mbody!=null) {
            Iterator<String> keys = mbody.keys();
            while (keys.hasNext()) {
                String ourkey = keys.next();
                try {
                    bodyBuilder.addFormDataPart(ourkey, mbody.getString(ourkey));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        RequestBody body= bodyBuilder .build();
        Request.Builder builder = new Request.Builder()
                .url(url)
                .method("POST", body);


        if(headers!=null) {
            Iterator<String> keys = headers.keys();
            builder .addHeader("Content-Type", "multipart/form-data");
            while (keys.hasNext()) {
                String ourkey = keys.next();
                try {
                    builder.addHeader(ourkey, headers.getString(ourkey));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        client.newCall(builder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                receivingResult.onErrorResult(e);


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                seriesRouter(response,receivingResult);


            }
        });

    }


}
