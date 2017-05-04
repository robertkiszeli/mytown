package com.robertkiszelirk.placesiwere;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BlankFragment extends Fragment {

    JSONArray passPlace;
    ArrayList<Place> places;

    MyAdapter adapter;
    RecyclerView rv;
    LinearLayoutManager llm;

    public BlankFragment() {
    }

    public void setJsonObject(JSONArray jArray){
        this.passPlace = jArray;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //INFLATE LAYOUT FOR CURRENT FRAGMENT
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);

        //SET RECYCLER VIEW
        rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);

        //Parse JSON
        places = new ArrayList<>();
        places = parseJson();

        //SET ADAPTER
        adapter = new MyAdapter(places, this.getContext());
        rv.setAdapter(adapter);

        //SET LAYOUT MANAGER
        llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

    private ArrayList<Place> parseJson() {
        // LIST FOR PLACES
        ArrayList<Place> placesList = new ArrayList<>();

        // PLACE DATA
        String placeName;
        String pictureName;
        double latitude;
        double longitude;
        String link;
        String email;
        String phoneNumber;

        // ITERATE THROUGH JSON ARRAY
        for(int i = 0; i < passPlace.length(); i++){

            try{
                // SET JSON OBJECT
                JSONObject place = passPlace.getJSONObject(i);

                // GET AND SET PLACE DATA
                placeName = place.getString("placeName");
                pictureName = place.getString("placePicture");
                latitude = place.getDouble("latitude");
                longitude = place.getDouble("longitude");
                link = place.getString("web");
                email = place.getString("email");
                phoneNumber = place.getString("phone");

                // CREATE CURRENT PLACE
                Place currentPlace = new Place(placeName,
                        pictureName,
                        latitude,
                        longitude,
                        link,
                        email,
                        phoneNumber);

                // ADD PLACE TO LIST
                placesList.add(currentPlace);

            }catch(JSONException je){
                je.printStackTrace();
            }
        }

        // RETURN LIST
        return placesList;
    }

}
