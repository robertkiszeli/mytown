package com.robertkiszelirk.placesiwere;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    // LIST OF PLACES
    private ArrayList<Place> places;
    private Context context;


    static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        ImageView mImageView;
        TextView mTextView;

        ImageButton placeButton;
        ImageButton webButton;
        ImageButton emailButton;
        ImageButton phoneButton;

        MyViewHolder(View v) {
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view);
            mImageView = (ImageView) v.findViewById(R.id.card_view_image);
            mTextView = (TextView) v.findViewById(R.id.card_view_text);

            placeButton = (ImageButton) v.findViewById(R.id.card_view_place_button);
            webButton = (ImageButton) v.findViewById(R.id.card_view_web_button);
            emailButton = (ImageButton) v.findViewById(R.id.card_view_mail_button);
            phoneButton = (ImageButton) v.findViewById(R.id.card_view_phone_button);

        }

    }

    MyAdapter(ArrayList<Place> places, Context context) {
        this.places = places;
        this.context = context;}

    //CREATE NEW VIEWS
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        //CREATE NEW VIEW
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_row, parent, false);
        return new MyViewHolder(v);
    }

    //SET VIEW ITEM
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        // SET BASE PLACE DATA
        final double lat = places.get(position).getLatitude();
        final double lon = places.get(position).getLongitude();
        final String web = places.get(position).getLink();
        final String mail = places.get(position).getEmail();
        final String phone = places.get(position).getPhoneNumber();

        holder.mTextView.setText(places.get(position).getPlaceName());

        String picName = places.get(position).getPictureName();

        //SET CARD VIEW IMAGE
        if(picName != null){
            int resID = context.getResources().getIdentifier(picName,"drawable",context.getPackageName());
            Glide.with(context).load(resID).into(holder.mImageView);
        }

        //SHOW LOCATION
        holder.placeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                    Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/search/loc:"+lat+","+lon);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    context.startActivity(mapIntent);
            }
        });

        //SET WEB BUTTON OR HIDE
        if(web.equals("null")){
            holder.webButton.setVisibility(View.GONE);
        }else {
            holder.webButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(web));
                        context.startActivity(intent);
                }
            });
        }

        //SET EMAIL BUTTON OR HIDE
        if(mail.equals("null")){
            holder.emailButton.setVisibility(View.GONE);
        }else {
            holder.emailButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("mailto:" + mail));
                        context.startActivity(intent);
                }
            });
        }

        //SET PHONE BUTTON OR HIDE
        if(phone.equals("null")){
            holder.phoneButton.setVisibility(View.GONE);
        }else {
            holder.phoneButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(view.getContext(),"Enable phone permission!",Toast.LENGTH_LONG).show();
                            return;
                        }else{
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:" + phone));
                            context.startActivity(intent);
                        }

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return places.size();//places.length();
    }

}