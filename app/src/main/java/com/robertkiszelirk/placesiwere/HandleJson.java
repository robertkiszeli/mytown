package com.robertkiszelirk.placesiwere;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

public class HandleJson {

    private JSONObject city;
    private JSONArray categories;
    private JSONObject category;
    private JSONArray places;

    private ArrayList<String> categoriesList;

    //SETTING BASE JSON DATA
    public void setJSON(Context context){

        String json = null;
        try {
            //GET CURRENT LANGUAGE
            String language = Locale.getDefault().getLanguage();
            String file;

            //SELECT LANGUAGE DATA
            if(language.equals("hu")){
                file = "datahu.json";
            }else{
                file = "data.json";
            }

            //OPEN AND READ JSON
            InputStream is = context.getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            //GET CITY NAME
            city = new JSONObject(json);

            //GET ALL TABS
            categories = city.getJSONArray("categories");
            categoriesList = new ArrayList<>();
            for(int i = 0; i < categories.length(); i++){
                category = categories.getJSONObject(i);
                categoriesList.add(category.get("categoryName").toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //RETURNING CITY NAME
    public String getCityName(){

        String returnS = "null";

        try{
            returnS =  city.get("cityName").toString();
        }catch (JSONException je){
            je.printStackTrace();
        }

        return returnS;
    }

    //RETURNING TABS LIST
    public ArrayList<String> getCategories(){

        return categoriesList;
    }

    //RETURNING PLACE OBJECT
    public JSONArray getPlaceObject(int position){

        try {

            category = categories.getJSONObject(position);
            places = category.getJSONArray("places");

        }catch(JSONException je){
            je.printStackTrace();
        }

        return places;
    }
}
