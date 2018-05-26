package br.com.luisfernandez.recyclerview.std.pojo;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by luisfernandez on 26/05/18.
 */

public class Country implements Serializable
{
    private String name;
    private String currency;
    private String language;

    public Country(JSONObject jsonObject) throws JSONException
    {
        if (jsonObject != null) {
            this.name = jsonObject.optString("name");

            JSONArray currencies = jsonObject.optJSONArray("currencies");
            if (currencies != null && currencies.length() > 0) {
                this.currency = currencies.optJSONObject(0).optString("name");
            }

            JSONArray languages = jsonObject.optJSONArray("languages");
            if (languages != null && languages.length() > 0) {
                this.language = languages.optJSONObject(0).optString("name");
            }
        }
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }
}
