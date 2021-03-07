package com.DupiTTam.aidupi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    ImageView btn1, btn2, btn3, btn4, btn5;
    Home_Fragment home_fragment;
    Head_Fragment head_fragment;
    Mypage_Fragment mypage_fragment;
    Place_Fragment place_fragment;
    Search_Fragment search_fragment;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setting_view();
        setting_fragment();
        setting_btn1();
        setting_btn2();
        setting_btn3();
        setting_btn4();
        setting_btn5();
    }

    public void setting_view(){
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);
        btn5=findViewById(R.id.btn5);

        context=this;
    }

    public void setting_fragment(){
        home_fragment=new Home_Fragment();
        head_fragment=new Head_Fragment();
        mypage_fragment=new Mypage_Fragment();
        place_fragment=new Place_Fragment();
        search_fragment=new Search_Fragment();

        getSupportFragmentManager().beginTransaction().add(R.id.container, home_fragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.container, head_fragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.container, mypage_fragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.container, place_fragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.container, search_fragment).commit();

        getSupportFragmentManager().beginTransaction().hide(head_fragment).commit();
        getSupportFragmentManager().beginTransaction().hide(mypage_fragment).commit();
        getSupportFragmentManager().beginTransaction().hide(place_fragment).commit();
        getSupportFragmentManager().beginTransaction().hide(search_fragment).commit();

        Glide.with(this).load(R.drawable.home_click).into(btn1);
        Glide.with(this).load(R.drawable.search_unclick).into(btn2);
        Glide.with(this).load(R.drawable.place_unclick).into(btn3);
        Glide.with(this).load(R.drawable.head_unclick).into(btn4);
        Glide.with(this).load(R.drawable.mypage_unclick).into(btn5);
    }
    public void setting_btn1(){
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().show(home_fragment)
                        .hide(head_fragment).hide(mypage_fragment).hide(place_fragment).hide(search_fragment).commit();
                Glide.with(context).load(R.drawable.home_click).into(btn1);
                Glide.with(context).load(R.drawable.search_unclick).into(btn2);
                Glide.with(context).load(R.drawable.place_unclick).into(btn3);
                Glide.with(context).load(R.drawable.head_unclick).into(btn4);
                Glide.with(context).load(R.drawable.mypage_unclick).into(btn5);
            }
        });
    }
    public void setting_btn2(){
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().show(search_fragment)
                        .hide(head_fragment).hide(mypage_fragment).hide(place_fragment).hide(home_fragment).commit();
                Glide.with(context).load(R.drawable.home_unclick).into(btn1);
                Glide.with(context).load(R.drawable.search_unclick).into(btn2);
                Glide.with(context).load(R.drawable.place_unclick).into(btn3);
                Glide.with(context).load(R.drawable.head_unclick).into(btn4);
                Glide.with(context).load(R.drawable.mypage_unclick).into(btn5);
            }
        });
    }
    public void setting_btn3(){
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().show(place_fragment)
                        .hide(head_fragment).hide(mypage_fragment).hide(home_fragment).hide(search_fragment).commit();
                Glide.with(context).load(R.drawable.home_unclick).into(btn1);
                Glide.with(context).load(R.drawable.search_unclick).into(btn2);
                Glide.with(context).load(R.drawable.place_click).into(btn3);
                Glide.with(context).load(R.drawable.head_unclick).into(btn4);
                Glide.with(context).load(R.drawable.mypage_unclick).into(btn5);
            }
        });
    }
    public void setting_btn4(){
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().show(head_fragment)
                        .hide(home_fragment).hide(mypage_fragment).hide(place_fragment).hide(search_fragment).commit();
                Glide.with(context).load(R.drawable.home_unclick).into(btn1);
                Glide.with(context).load(R.drawable.search_unclick).into(btn2);
                Glide.with(context).load(R.drawable.place_unclick).into(btn3);
                Glide.with(context).load(R.drawable.head_click).into(btn4);
                Glide.with(context).load(R.drawable.mypage_unclick).into(btn5);
            }
        });
    }
    public void setting_btn5(){
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().show(mypage_fragment)
                        .hide(head_fragment).hide(home_fragment).hide(place_fragment).hide(search_fragment).commit();
                Glide.with(context).load(R.drawable.home_unclick).into(btn1);
                Glide.with(context).load(R.drawable.search_unclick).into(btn2);
                Glide.with(context).load(R.drawable.place_unclick).into(btn3);
                Glide.with(context).load(R.drawable.head_unclick).into(btn4);
                Glide.with(context).load(R.drawable.mypage_unclick).into(btn5);
            }
        });
    }
}