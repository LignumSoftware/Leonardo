package com.MaNoS.DarcyRibeiro.Tech.Leonardo;

import android.text.Html;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class WebClient {
    private final String url;

    public WebClient(String url) {
        this.url = url;
    }

    public String post(String json) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        Log.i("WebClient-Post", url);
        try {
            post.setEntity(new StringEntity(json));
            post.setHeader("Content-type", "application/json");
            post.setHeader("Accept", "application/json");
            HttpResponse response = httpClient.execute(post);
            String jsonResposta = EntityUtils.toString(response.getEntity(), "UTF-8");
            return jsonResposta;
        } catch (Exception e) {
            Log.i("WebClient-Post", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String get() {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        try {
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String json = toString(instream);
                instream.close();
                return json;
            }
        } catch (Exception e) {
            Log.e("WebClient - GET", "Falha ao acessar Web service " + e.getMessage());
        }
        return null;
    }

    private String toString(InputStream is) throws IOException {
        String retorno;
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int lidos;
        int tamanho = 0;
        while ((lidos = is.read(bytes)) > 0) {
            baos.write(bytes, 0, lidos);
            tamanho = tamanho + lidos;
        }
        Log.i("lendo", String.valueOf(tamanho));
        retorno = Html.escapeHtml(new String(baos.toByteArray()));
        Log.i("convertendo 1", retorno);
        retorno = new String(baos.toByteArray(), "UTF-8");
        Log.i("convertendo 2", retorno);
        return retorno;
    }

}
