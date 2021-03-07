package com.DupiTTam.aidupi;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Place_Fragment extends Fragment implements LocationListener {
    MainActivity mainActivity;
    CircleImageView find_mylocation;
    TextView text_mylocation;
    Button all, kangnam, mapo, seocho, seodaemoon, buchun, hwasung;
    ImageView imgup, imgdown, style1, style2, style3;
    RecyclerView place_recyclerview;

    Geocoder geocoder;
    private static final int GPS_ENABLE_REQUEST_CODE=1002;
    private static final int PERMISSIONS_REQUEST_CODE=1001;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    LocationManager locationManager;
    Location mylocation;
    double mylat, mylon;
    SharedPreferences localdb;
    String myaddress;

    List<String> total_name=Arrays.asList("로위 신촌점", "로위 합정점", "로위 홍대점",
            "쉐어스팟 강남점", "팔레트에이치 도산점", "팔레트에이치 강남역1호점",
            "세븐에비뉴 강남1호점", "세븐에비뉴 합정점", "세븐에비뉴 동탄점",
            "세븐에비뉴 부천점", "샬롱포레스트 역삼점", "포레스트 헤어");
    List<String> total=  Arrays.asList("서울특별시 서대문구 창천동 72-3",
            "서울특별시 마포구 서교동 394-44", "서울특별시 마포구 서교동 407-2", "서울특별시 강남구 역삼도 834-64",
            "서울특별시 강남구 신사동 651-21 CGV 청담 씨네시티 4층", "서울특별시 서초구 서초동 1327-1",
            "서울특별시 서초구 서초동 1330-9", "서울특별시 마포구 합정동 473", "경기도 화성시 반송동 93-3",
            "경기도 부천시 괴안동 113-1", "서울특별시 강남구 역삼동 736-1", "서울특별시 강남구 역삼동 616-2");
    List<String> locate_divide=Arrays.asList("서대문구", "마포구", "마포구", "강남구", "강남구",
            "서초구", "서초구", "마포구", "화성시", "부천시", "강남구", "강남구");
    ArrayList<Salon_address> salon;
    ArrayList<Integer> salon_img;
    ArrayList<Salon_address> recyclerview_item;
    ArrayList<Integer> recyclerview_img;
    Salon_Adapter salon_adapter;
    getLatLon_Thread latlon;
    Handler maphandler;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity=(MainActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity=null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.place_fragment, container, false);

        setting_view(v);
        getting_myaddress_from_sharedpreference();
        /***thread that getting lat&long from every salon address***/
        maphandler=new Handler();
        latlon=new getLatLon_Thread();
        Thread thread=new Thread(latlon);
        thread.start();
        /***thread getting lat&long***/

        setting_mylocation_btn();

        setting_salon_location_btn();

        return v;
    }

    public void setting_view(View v){
        all=v.findViewById(R.id.total);
        kangnam=v.findViewById(R.id.kangnam);
        mapo=v.findViewById(R.id.mapo);
        seocho=v.findViewById(R.id.seocho);
        seodaemoon=v.findViewById(R.id.seodaemoon);
        buchun=v.findViewById(R.id.buchun);
        hwasung=v.findViewById(R.id.hwasung);
        place_recyclerview=v.findViewById(R.id.salon_recyclerview);
        find_mylocation=v.findViewById(R.id.find_mylocation);
        text_mylocation=v.findViewById(R.id.text_mylocation);
        imgup=v.findViewById(R.id.place_imgup);
        imgdown=v.findViewById(R.id.place_imgdown);
        style1=v.findViewById(R.id.style1);
        style2=v.findViewById(R.id.style2);
        style3=v.findViewById(R.id.style3);

        Glide.with(mainActivity).load(R.drawable.place_imgup).into(imgup);
        Glide.with(mainActivity).load(R.drawable.place_imgdown).into(imgdown);
        Glide.with(mainActivity).load(R.drawable.style1).into(style1);
        Glide.with(mainActivity).load(R.drawable.style2).into(style2);
        Glide.with(mainActivity).load(R.drawable.style3).into(style3);

        all.setTextColor(Color.WHITE);all.setBackground(getResources().getDrawable(R.drawable.button_click));
        kangnam.setTextColor(Color.parseColor("#6200EE"));kangnam.setBackground(getResources().getDrawable(R.drawable.button_unclick));
        mapo.setTextColor(Color.parseColor("#6200EE"));mapo.setBackground(getResources().getDrawable(R.drawable.button_unclick));
        seocho.setTextColor(Color.parseColor("#6200EE"));seocho.setBackground(getResources().getDrawable(R.drawable.button_unclick));
        seodaemoon.setTextColor(Color.parseColor("#6200EE"));seodaemoon.setBackground(getResources().getDrawable(R.drawable.button_unclick));
        buchun.setTextColor(Color.parseColor("#6200EE"));buchun.setBackground(getResources().getDrawable(R.drawable.button_unclick));
        hwasung.setTextColor(Color.parseColor("#6200EE"));hwasung.setBackground(getResources().getDrawable(R.drawable.button_unclick));

        salon=new ArrayList<>();
        salon_img = new ArrayList<>();
    }

    public void getting_myaddress_from_sharedpreference(){
        localdb=mainActivity.getSharedPreferences("AIhead", Context.MODE_PRIVATE);
        myaddress=localdb.getString("myaddress", null);
        if(myaddress==null){
            text_mylocation.setText("내 위치를 찾아주세요");
        }
        else{
            text_mylocation.setText(myaddress);
            mylat=localdb.getFloat("lat", 0);
            mylon=localdb.getFloat("lon", 0);
        }
    }

    public void setting_mylocation_btn(){
        find_mylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /***1. setting location permission***/
                getlocationpermission();
            }
        });
    }

    public void getlocationpermission(){
        if(Build.VERSION.SDK_INT<23){
            getmylocation();
        }
        /***위치 권한을 하려면 우선 핸드폰 자체의 네트워크, GPS 상태를 체크하고 그 후, 핸드폰이 GPS 사용 가능하면 앱 자체의 위치 접근 권한 설정을 체크해줘야 합니다***/
        /***2. setting each phone location permission, not app but for phone***/
        if(!checkLocationServicesStatus()){
            showDialogForLocationServiceSetting();
        }else{/***3. if phone permit location service, then check for application location permission***/
            checkRunTimePermission();
        }
    }

    /***checking user's phone permission***/
    public boolean checkLocationServicesStatus(){
        locationManager= (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /***make phone enable to use GPS service***/
    public void showDialogForLocationServiceSetting(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하시겠습니까?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    /***4. after checking phone location permission by showDialogForLocationServiceSetting***/
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==mainActivity.RESULT_OK){//success intent communication
            switch(requestCode){
                case GPS_ENABLE_REQUEST_CODE:
                    if(checkLocationServicesStatus()){
                        checkRunTimePermission();
                        return;
                    }
                    break;
            }
        }

    }

    /***5. check if user permit app location permission before and if not, explain user why and how permit location permission of app***/
    public void checkRunTimePermission(){
        int permissioncoarse= ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_COARSE_LOCATION);
        int permissionfind=ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION);
        /***one of perission denied or not permitted yet***/
        if(permissioncoarse== PackageManager.PERMISSION_DENIED||permissionfind==PackageManager.PERMISSION_DENIED){
            /***if user denied permission before***/
            if(shouldShowRequestPermissionRationale(REQUIRED_PERMISSIONS[0])){
                Toast.makeText(mainActivity, "위치 권한 설정이 필요합니다.", Toast.LENGTH_SHORT).show();
                try {
                    Thread.sleep(1000);
                }catch(Exception e){e.printStackTrace();}
                requestPermissions(REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }else{/***if this is the first time user get permission message***/
                requestPermissions(REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        }else{
            getmylocation();
        }
    }

    /***6. after runtime checking permission***/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==PERMISSIONS_REQUEST_CODE&&grantResults.length==REQUIRED_PERMISSIONS.length){
            /***if location permission send successfully and cnt of permission is right***/
            boolean check_result=true;
            /***check if user permit all permisstion***/
            for(int result: grantResults){
                if(result!=PackageManager.PERMISSION_GRANTED){
                    check_result=false;
                    break;
                }
            }
            /***can get location information***/
            if(check_result){
                Toast.makeText(mainActivity, "위치 권한이 설정되었습니다", Toast.LENGTH_SHORT).show();
            }
            else{
                AlertDialog.Builder builder=new AlertDialog.Builder(mainActivity);
                builder.setTitle("알림");
                builder.setMessage("[설정]->[권한]에서\n권한을 허용해주세요.\n");
                builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }/***user denied permission***/
        }
    }

    public void getmylocation(){
        if(ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED&&
        ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            locationManager= (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            if(locationManager!=null){
                mylocation=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if(mylocation==null){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    mylocation=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if(mylocation!=null){
                        mylat=mylocation.getLatitude();
                        mylon=mylocation.getLongitude();
                        List<Address> myadd=null;
                        try{
                            myadd=geocoder.getFromLocation(mylat, mylon, 1);
                        }catch(IOException e){
                            Toast.makeText(mainActivity, "지오코더 서비스 사용불가", Toast.LENGTH_SHORT).show();
                        }
                        if(myadd==null||myadd.size()==0){
                            Toast.makeText(mainActivity, "주소 발견 실패", Toast.LENGTH_SHORT).show();
                        }else{
                            myaddress=myadd.get(0).getAddressLine(0);
                            text_mylocation.setText(myaddress);
                            SharedPreferences.Editor editor=localdb.edit();
                            editor.putString("myaddress", myaddress);
                            editor.putFloat("lat", (float) mylat);
                            editor.putFloat("lon", (float)mylon);
                            editor.commit();
                            return;
                        }
                    }
                }else{
                    mylat=mylocation.getLatitude();
                    mylon=mylocation.getLongitude();
                    List<Address> myadd=null;
                    try{
                        myadd=geocoder.getFromLocation(mylat, mylon, 1);
                    }catch(IOException e){
                        Toast.makeText(mainActivity, "지오코더 서비스 사용불가", Toast.LENGTH_SHORT).show();
                    }
                    if(myadd==null||myadd.size()==0){
                        Toast.makeText(mainActivity, "주소 발견 실패", Toast.LENGTH_SHORT).show();
                    }else{
                        myaddress=myadd.get(0).getAddressLine(0);
                        text_mylocation.setText(myaddress);
                        SharedPreferences.Editor editor=localdb.edit();
                        editor.putString("myaddress", myaddress);
                        editor.putFloat("lat", (float) mylat);
                        editor.putFloat("lon", (float)mylon);
                        editor.commit();
                        return;
                    }
                }
                Toast.makeText(mainActivity, "네트워크 통신이 불안정합니다.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }



    class getLatLon_Thread implements Runnable{
        @Override
        public void run() {
            getting_latlon();
            maphandler.post(new Runnable() {
                @Override
                public void run() {
                    setting_salon_recyclerview();
                }
            });
        }
    }

    /***getting lat & lon of salon address using Geocoder***/
    public void getting_latlon(){
        geocoder=new Geocoder(mainActivity, Locale.KOREAN);
        for(int i=0; i<total.size(); i++){
            try {
                List<Address> list=null;
                while(true){
                    list=geocoder.getFromLocationName(total.get(i), 1);
                    if(list!=null){
                        double lat2=list.get(0).getLatitude();
                        double lon2=list.get(0).getLongitude();
                        salon.add(new Salon_address(total_name.get(i), total.get(i),  list.get(0).getLatitude(), list.get(0).getLongitude(), locate_divide.get(i),distance(mylat, mylon, lat2, lon2)));
                        break;
                    }
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        salon_img.add(R.drawable.place1);salon_img.add(R.drawable.place2);salon_img.add(R.drawable.place3);
        salon_img.add(R.drawable.place4);salon_img.add(R.drawable.place5);salon_img.add(R.drawable.place6);
        salon_img.add(R.drawable.place7);salon_img.add(R.drawable.place8);salon_img.add(R.drawable.place9);
        salon_img.add(R.drawable.place10);salon_img.add(R.drawable.place11);salon_img.add(R.drawable.place12);

    }

    public double distance(double lat1, double lon1, double lat2, double lon2){
        Location startPos = new Location("PointA");
        Location endPos = new Location("PointB");

        startPos.setLatitude(lat1);
        startPos.setLongitude(lon1);
        endPos.setLatitude(lat2);
        endPos.setLongitude(lon2);

        double d = startPos.distanceTo(endPos);
        d=d/1000.0;
        d=Double.parseDouble(String.format("%.2f", d));
        return d;


    }
    public void setting_salon_recyclerview(){
        salon_adapter=new Salon_Adapter(mainActivity, salon, salon_img);
        place_recyclerview.setLayoutManager(new LinearLayoutManager(mainActivity));
        place_recyclerview.setAdapter(salon_adapter);
    }

    public void setting_salon_location_btn(){
        recyclerview_item=new ArrayList<>();
        recyclerview_img=new ArrayList<>();
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerview_item=salon;
                recyclerview_img=salon_img;
                salon_adapter.changeitem(recyclerview_item, recyclerview_img);
                all.setTextColor(Color.WHITE);all.setBackground(getResources().getDrawable(R.drawable.button_click));
                kangnam.setTextColor(Color.parseColor("#6200EE"));kangnam.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                mapo.setTextColor(Color.parseColor("#6200EE"));mapo.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                seocho.setTextColor(Color.parseColor("#6200EE"));seocho.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                seodaemoon.setTextColor(Color.parseColor("#6200EE"));seodaemoon.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                buchun.setTextColor(Color.parseColor("#6200EE"));buchun.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                hwasung.setTextColor(Color.parseColor("#6200EE"));hwasung.setBackground(getResources().getDrawable(R.drawable.button_unclick));
            }
        });
        kangnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting_recyclerview_item("강남구");
                salon_adapter.changeitem(recyclerview_item, recyclerview_img);
                all.setTextColor(Color.parseColor("#6200EE"));all.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                kangnam.setTextColor(Color.WHITE);kangnam.setBackground(getResources().getDrawable(R.drawable.button_click));
                mapo.setTextColor(Color.parseColor("#6200EE"));mapo.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                seocho.setTextColor(Color.parseColor("#6200EE"));seocho.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                seodaemoon.setTextColor(Color.parseColor("#6200EE"));seodaemoon.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                buchun.setTextColor(Color.parseColor("#6200EE"));buchun.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                hwasung.setTextColor(Color.parseColor("#6200EE"));hwasung.setBackground(getResources().getDrawable(R.drawable.button_unclick));
            }
        });
        mapo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting_recyclerview_item("마포구");
                salon_adapter.changeitem(recyclerview_item, recyclerview_img);
                all.setTextColor(Color.parseColor("#6200EE"));all.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                kangnam.setTextColor(Color.parseColor("#6200EE"));kangnam.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                mapo.setTextColor(Color.WHITE);mapo.setBackground(getResources().getDrawable(R.drawable.button_click));
                seocho.setTextColor(Color.parseColor("#6200EE"));seocho.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                seodaemoon.setTextColor(Color.parseColor("#6200EE"));seodaemoon.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                buchun.setTextColor(Color.parseColor("#6200EE"));buchun.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                hwasung.setTextColor(Color.parseColor("#6200EE"));hwasung.setBackground(getResources().getDrawable(R.drawable.button_unclick));
            }
        });
        seocho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting_recyclerview_item("서초구");
                salon_adapter.changeitem(recyclerview_item, recyclerview_img);
                all.setTextColor(Color.parseColor("#6200EE"));all.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                kangnam.setTextColor(Color.parseColor("#6200EE"));kangnam.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                mapo.setTextColor(Color.parseColor("#6200EE"));mapo.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                seocho.setTextColor(Color.WHITE);seocho.setBackground(getResources().getDrawable(R.drawable.button_click));
                seodaemoon.setTextColor(Color.parseColor("#6200EE"));seodaemoon.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                buchun.setTextColor(Color.parseColor("#6200EE"));buchun.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                hwasung.setTextColor(Color.parseColor("#6200EE"));hwasung.setBackground(getResources().getDrawable(R.drawable.button_unclick));
            }
        });
        seodaemoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting_recyclerview_item("서대문구");
                salon_adapter.changeitem(recyclerview_item, recyclerview_img);
                all.setTextColor(Color.parseColor("#6200EE"));all.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                kangnam.setTextColor(Color.parseColor("#6200EE"));kangnam.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                mapo.setTextColor(Color.parseColor("#6200EE"));mapo.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                seocho.setTextColor(Color.parseColor("#6200EE"));seocho.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                seodaemoon.setTextColor(Color.WHITE);seodaemoon.setBackground(getResources().getDrawable(R.drawable.button_click));
                buchun.setTextColor(Color.parseColor("#6200EE"));buchun.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                hwasung.setTextColor(Color.parseColor("#6200EE"));hwasung.setBackground(getResources().getDrawable(R.drawable.button_unclick));
            }
        });
        buchun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting_recyclerview_item("부천시");
                salon_adapter.changeitem(recyclerview_item, recyclerview_img);
                all.setTextColor(Color.parseColor("#6200EE"));all.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                kangnam.setTextColor(Color.parseColor("#6200EE"));kangnam.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                mapo.setTextColor(Color.parseColor("#6200EE"));mapo.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                seocho.setTextColor(Color.parseColor("#6200EE"));seocho.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                seodaemoon.setTextColor(Color.parseColor("#6200EE"));seodaemoon.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                buchun.setTextColor(Color.WHITE);buchun.setBackground(getResources().getDrawable(R.drawable.button_click));
                hwasung.setTextColor(Color.parseColor("#6200EE"));hwasung.setBackground(getResources().getDrawable(R.drawable.button_unclick));
            }
        });
        hwasung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting_recyclerview_item("화성시");
                salon_adapter.changeitem(recyclerview_item, recyclerview_img);
                all.setTextColor(Color.parseColor("#6200EE"));all.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                kangnam.setTextColor(Color.parseColor("#6200EE"));kangnam.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                mapo.setTextColor(Color.parseColor("#6200EE"));mapo.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                seocho.setTextColor(Color.parseColor("#6200EE"));seocho.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                seodaemoon.setTextColor(Color.parseColor("#6200EE"));seodaemoon.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                buchun.setTextColor(Color.parseColor("#6200EE"));buchun.setBackground(getResources().getDrawable(R.drawable.button_unclick));
                hwasung.setTextColor(Color.WHITE);hwasung.setBackground(getResources().getDrawable(R.drawable.button_click));
            }
        });
    }

    public void setting_recyclerview_item(String locate){
        recyclerview_img=null;
        recyclerview_item=null;
        recyclerview_img=new ArrayList<>();
        recyclerview_item=new ArrayList<>();
        for(int i=0; i<salon.size(); i++){
            if(locate_divide.get(i).equals(locate)){
                recyclerview_item.add(salon.get(i));
                recyclerview_img.add(salon_img.get(i));
            }
        }
    }
}
