package br.com.luisfernandez.recyclerview.std.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.luisfernandez.recyclerview.std.AndroidUtil;
import br.com.luisfernandez.recyclerview.std.R;
import br.com.luisfernandez.recyclerview.std.pojo.Country;

public class MockService
{
    private static final String TAG = "MockService";

    private Context context;

    public MockService(Context context)
    {
        this.context = context;
    }

    public void loadCountryList(final DataCallback<List<Country>> dataCallback)
    {
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, List<Country>> asyncTask = new AsyncTask<Void, Void, List<Country>>()
        {
            @Override
            protected List<Country> doInBackground(Void... params)
            {
                List<Country> list = null;
                String result = "";

                try
                {
                    URL url = new URL("https://restcountries.eu/rest/v2/all");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try
                    {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        if (in != null)
                        {
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                            String line = "";

                            while ((line = bufferedReader.readLine()) != null)
                            {
                                result += line;
                            }
                        }
                        in.close();
                    }
                    finally
                    {
                        urlConnection.disconnect();
                    }

                    String json = result;
                    list = getStadiumListFrom(json);
                }
                catch (IOException e)
                {
                    Log.e(TAG, "Error loading mock json", e);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                return list;
            }

            private List<Country> getStadiumListFrom(String json) throws JSONException
            {
                List<Country> list = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(json);

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    list.add(new Country(jsonArray.optJSONObject(i)));
                }

                return list;
            }

            @Override
            protected void onPostExecute(List<Country> stadiumList)
            {
                super.onPostExecute(stadiumList);
                dataCallback.onLoadSuccess(stadiumList);
            }
        };

        asyncTask.execute();
    }

    public interface DataCallback<TYPE>
    {
        void onLoadSuccess(TYPE data);
    }
}
