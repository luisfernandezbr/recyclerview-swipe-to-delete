package br.com.luisfernandez.recyclerview.std.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.com.luisfernandez.recyclerview.std.AndroidUtil;
import br.com.luisfernandez.recyclerview.std.R;
import br.com.luisfernandez.recyclerview.std.pojo.Stadium;

public class MockService
{
    private static final String TAG = "MockService";

    private Context context;

    public MockService(Context context) {
        this.context = context;
    }

    public void loadStadiumList(final DataCallback<List<Stadium>> dataCallback) {
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, List<Stadium>> asyncTask = new AsyncTask<Void, Void, List<Stadium>>() {
            @Override
            protected List<Stadium> doInBackground(Void... params) {
                List<Stadium> list = new ArrayList<>();

                try {
                    String json = AndroidUtil.getJsonFromRaw(context, R.raw.stadium_list);
                    list = getStadiumListFrom(json);
                } catch (IOException e) {
                    Log.e(TAG, "Error loading mock json", e);
                }

                return list;
            }

            private List<Stadium> getStadiumListFrom(String json) {
                Gson gson = new Gson();

                Type type = new TypeToken<List<Stadium>>() {
                }.getType();

                return gson.fromJson(json, type);
            }

            @Override
            protected void onPostExecute(List<Stadium> stadiumList) {
                super.onPostExecute(stadiumList);
                dataCallback.onLoadSuccess(stadiumList);
            }
        };

        asyncTask.execute();
    }

    public interface DataCallback<TYPE> {
        void onLoadSuccess(TYPE data);
    }
}
