package br.usjt.desmob.atlas;

import android.content.Context;
import android.net.ConnectivityManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Lucas Vasconcelos Molessani - 201508392
 * DEVMOBI
 * CCP3AN-MCA
 */

public class PaisNetwork {

    /**
     * @author Lucas Vasconcelos Molessani - 201508392
     * Retorna uma lista dos paises do continente
     * @param url API
     * @param regiao
     * @return Pais[]
     */
    public static Pais[] buscarPaises(String url, String regiao) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ArrayList<Pais> paises = new ArrayList<>();

        Request request = new Request.Builder()
                .url(url+regiao)
                .build();

        Response response = client.newCall(request).execute();

        String resultado = response.body().string();

        try {
            JSONArray vetor = new JSONArray(resultado);
            for(int i = 0; i < vetor.length(); i++){
                JSONObject item = (JSONObject) vetor.get(i);
                Pais pais = new Pais();
                try {
                    pais.setArea(item.getInt("area"));
                } catch (Exception e){
                    pais.setArea(0);
                }
                pais.setBandeira(item.getString("flag"));
                pais.setCapital(item.getString("capital"));
                pais.setNome(item.getString("name"));
                pais.setRegiao(item.getString("region"));
                pais.setCodigo3(item.getString("alpha3Code"));
                paises.add(pais);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        return paises.toArray(new Pais[0]);
    }

    /**
     * @author Lucas Vasconcelos Molessani - 201508392
     * Verifica se tem internet
     * @param context
     * @return boolean
     */
    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
