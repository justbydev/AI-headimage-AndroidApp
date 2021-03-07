package com.DupiTTam.aidupi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

public class Salon_MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    MapView map;
    ImageView img;
    TextView name, address;
    double lat, lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_map);

        setting_view();
    }

    public void setting_view(){
        map=findViewById(R.id.map);
        img=findViewById(R.id.image);
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);

        Intent getintent=getIntent();
        lat=getintent.getDoubleExtra("lat", 0);
        lon=getintent.getDoubleExtra("lon", 0);
        name.setText(getintent.getStringExtra("name"));
        address.setText(getintent.getStringExtra("address"));
        Glide.with(this).load(getintent.getIntExtra("image", R.drawable.place1)).into(img);

        map.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        LatLng location=new LatLng(lat, lon);
        CameraPosition cameraPosition=new CameraPosition(location, 17);
        naverMap.setCameraPosition(cameraPosition);

        Marker marker=new Marker();
        marker.setPosition(location);
        marker.setWidth(Marker.SIZE_AUTO);
        marker.setHeight(Marker.SIZE_AUTO);
        marker.setMap(naverMap);
    }
}