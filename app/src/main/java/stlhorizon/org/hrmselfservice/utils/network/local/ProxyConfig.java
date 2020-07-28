package stlhorizon.org.hrmselfservice.utils.network.local;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class ProxyConfig {
    int proxyPort = 8080;
    String proxyHost = "proxyHost";
  String username = "username";
   String password = "password";

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public  void  setUsername(String username){
        this.username=username;
    }
 public void setPassword(String password){


 }
    /*
        OkHttpClient client = new OkHttpClient.Builder()

                .
                .build();*/
    public OkHttpClient.Builder  bind(OkHttpClient.Builder clientokHttpClient){
        clientokHttpClient .protocols(Arrays.asList(Protocol.HTTP_2, Protocol.HTTP_1_1, Protocol.SPDY_3));
        clientokHttpClient .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);
        Authenticator proxyAuthenticator = new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                String credential = Credentials.basic(username, password);
                return response.request().newBuilder()
                        .header("Proxy-Authorization", credential)
                       // .header("User-Agent", "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0")
                        .build();
            }
        };
Proxy proxy=new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
System.out.println(proxy.toString());
        clientokHttpClient.proxy(proxy);
        clientokHttpClient .proxyAuthenticator(proxyAuthenticator);
        clientokHttpClient.authenticator(proxyAuthenticator);
        return clientokHttpClient;
    }

}
