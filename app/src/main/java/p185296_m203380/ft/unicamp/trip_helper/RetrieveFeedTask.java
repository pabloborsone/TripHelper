package p185296_m203380.ft.unicamp.trip_helper;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

    private TextView textView;

    public RetrieveFeedTask(TextView textView) {
        this.textView = textView;
    }


    static final String API_KEY = "13e2e5ffd8mshbf6c290940128b5p1d1ceajsncf562ee2aa15"; //if this key doesn´t work try b6907d289e10d714a6e88b30761fae22
    static final String API_URL = "community-open-weather-map.p.rapidapi.com/weather?";

    private TextView cidadeNome; //this needs to receive the current city in the recyclerlist
    private Exception exception;

    protected void onPreExecute() {
        cidadeNome.setText("");
    }

    protected String doInBackground(Void... urls) {
        @SuppressLint("WrongThread") String cidade = cidadeNome.getText().toString();
        try {
            URL url = new URL(API_URL + "q=" + cidade + "&apiid=" + API_KEY);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String response) {
        textView.setText(response);
    }
}