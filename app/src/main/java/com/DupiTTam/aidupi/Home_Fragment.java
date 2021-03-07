package com.DupiTTam.aidupi;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class Home_Fragment extends Fragment {
    MainActivity mainActivity;
    ImageView main_imgup, shampoo2, shampoo3, story1, story2;
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
        View v=inflater.inflate(R.layout.home_fragment, container, false);

        setting_view(v);
        return v;
    }

    public void setting_view(View v){
        main_imgup=v.findViewById(R.id.main_imgup);
        shampoo2=v.findViewById(R.id.shampoo2);
        shampoo3=v.findViewById(R.id.shampoo3);
        story1=v.findViewById(R.id.salone_story1);
        story2=v.findViewById(R.id.solone_story2);

        Glide.with(mainActivity).load(R.drawable.main_imgup).into(main_imgup);
        Glide.with(mainActivity).load(R.drawable.shampoo2).into(shampoo2);
        Glide.with(mainActivity).load(R.drawable.shampoo3).into(shampoo3);
        Glide.with(mainActivity).load(R.drawable.salone_story1).into(story1);
        Glide.with(mainActivity).load(R.drawable.salone_story2).into(story2);
    }
}
