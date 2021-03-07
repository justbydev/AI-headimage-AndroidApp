package com.DupiTTam.aidupi;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Salon_Adapter extends RecyclerView.Adapter {
    public static Context context;
    public static ArrayList<Salon_address> data;
    public static ArrayList<Integer> image;
    public Salon_Adapter(Context context, ArrayList<Salon_address> data, ArrayList<Integer> image){
        this.data=data;
        this.context=context;
        this.image=image;
    }
    static final class Salon_ViewHolder extends RecyclerView.ViewHolder{
        ImageView salon_img;
        TextView salon_name, salon_address, salon_distance;

        public Salon_ViewHolder(@NonNull View itemView) {
            super(itemView);
            salon_img=itemView.findViewById(R.id.salon_img);
            salon_name=itemView.findViewById(R.id.salon_name);
            salon_address=itemView.findViewById(R.id.salon_address);
            salon_distance=itemView.findViewById(R.id.salon_distance);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    Intent intent=new Intent(context, Salon_MapActivity.class);
                    intent.putExtra("name", data.get(position).getName());
                    intent.putExtra("address", data.get(position).getAddress());
                    intent.putExtra("lat", data.get(position).getLat());
                    intent.putExtra("lon", data.get(position).getLon());
                    intent.putExtra("image", image.get(position));
                    context.startActivity(intent);
                }
            });
        }
        public void setItem(Salon_address item, int img){
            Glide.with(context).load(img).into(salon_img);
            salon_name.setText(item.getName());
            salon_address.setText(item.getAddress());
            salon_distance.setText(item.getDistance()+"km");
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.salon_item, parent, false);
        return new Salon_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Salon_ViewHolder vh=(Salon_ViewHolder)holder;
        vh.setItem(data.get(position), image.get(position));

    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    public void changeitem(ArrayList<Salon_address> data, ArrayList<Integer> img){
        if(this.data!=null){
            this.data=null;
        }
        if(this.image!=null){
            this.image=null;
        }
        this.data=data;
        this.image=img;
        notifyDataSetChanged();
    }
}
