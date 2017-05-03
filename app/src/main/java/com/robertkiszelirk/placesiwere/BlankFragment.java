package com.robertkiszelirk.placesiwere;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;

public class BlankFragment extends Fragment {

    JSONArray passPlace;

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

        //SET ADAPTER
        adapter = new MyAdapter(passPlace, this.getContext());
        rv.setAdapter(adapter);

        //SET LAYOUT MANAGER
        llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

}
