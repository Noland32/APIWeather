package fr.wildcodeschool.apiweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class fiveDaysActivity extends AppCompatActivity {

    private final static String API_KEY = "d891bdcf1e4cb3397e24af13751eea28";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_days);


        // Crée une file d'attente pour les requêtes vers l'API
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // TODO : URL de la requête vers l'API
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=Toulouse&appid=" + API_KEY;


        // Création de la requête vers l'API, ajout des écouteurs pour les réponses et erreurs possibles
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO : traiter la réponse
                        try {
                            JSONArray list = response.getJSONArray("list");
                            for (int i = 0; i < list.length(); i++) {
                                JSONObject jsonObj = (JSONObject) list.get(i);
                                // recup array
                                JSONArray weather = jsonObj.getJSONArray("weather");
                                int len = weather.length();

                                // affiche
                                //ArrayList<String> weatherList = new ArrayList<>();
                                for(int j=0; j<len; j++){
                                    JSONObject json = weather.getJSONObject(j);
                                    String description = json.getString("description");
                                    Toast.makeText(fiveDaysActivity.this, description, Toast.LENGTH_SHORT).show();

                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Afficher l'erreur
                        Log.d("VOLLEY_ERROR", "onErrorResponse: " + error.getMessage());
                    }
                }
        );

        // On ajoute la requête à la file d'attente
        requestQueue.add(jsonObjectRequest);

    }
}
