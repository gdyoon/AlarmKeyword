package com.example.administrator.moamoa_v10.Config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.AccessToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-12-23.
 */

public class DataConfig {
    public static ArrayList<String> KeywordList = new ArrayList<String>();

    //public static AccessToken accessToken = null;

    public static void setKeywordArrayPref(Context _context, String _key, ArrayList<String> values )
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(_context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray jsonArr = new JSONArray();

        for (int i = 0; i < values.size(); i++) {
            jsonArr.put(values.get(i));
        }

        if (!values.isEmpty()) {
            editor.putString(_key, jsonArr.toString());
        }

        /*
        else {
            editor.putString(_key, null);
        }
        */
        editor.commit();
    }

    public static void getKeywordArrayPref(Context context, String key) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);

        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    KeywordList.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


}
