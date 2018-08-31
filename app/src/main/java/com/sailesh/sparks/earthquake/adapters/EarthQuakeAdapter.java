package com.sailesh.sparks.earthquake.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sailesh.sparks.earthquake.R;
import com.sailesh.sparks.earthquake.model.EarthQuake;
import com.sailesh.sparks.earthquake.model.EarthQuakeFeatures;
import java.util.List;

public class EarthQuakeAdapter extends RecyclerView.Adapter<EarthQuakeAdapter.MyViewHolder>{

    private Context mContext;
    private final List<EarthQuakeFeatures> earthQuakeFeatures;

    public EarthQuakeAdapter(Context context, List<EarthQuakeFeatures> earthQuakeFeatures){
        mContext = context;
        this.earthQuakeFeatures = earthQuakeFeatures;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.earthquake_list_items, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final EarthQuake properties = earthQuakeFeatures.get(position).getProperties();
        holder.magTextView.setText(properties.getMag()+"");
        holder.placeTextView.setText(properties.getPlace());

        int magnitudeColor = getMagnitudeColor(properties.getMag());

        holder.magTextView.setBackgroundColor(magnitudeColor);

        holder.detailsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri earthquakeUri = Uri.parse(properties.getUrl());
                Intent webIntent = new Intent(Intent.ACTION_VIEW,earthquakeUri);
                mContext.startActivity(webIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return earthQuakeFeatures.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView magTextView;
        private final TextView placeTextView;
        private final ImageView detailsImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            magTextView = itemView.findViewById(R.id.magnitude);
            placeTextView = itemView.findViewById(R.id.place);
            detailsImageView = itemView.findViewById(R.id.detailsImageView);
        }
    }

    public void updateQuakeData(List<EarthQuakeFeatures>  earthQuakeFeatures){
        this.earthQuakeFeatures.addAll(earthQuakeFeatures);
        notifyDataSetChanged();
    }


    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.colorPrimaryDark;
        }
        return ContextCompat.getColor(mContext, magnitudeColorResourceId);
    }
}
