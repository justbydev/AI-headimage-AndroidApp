package com.DupiTTam.aidupi;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Head_Fragment extends Fragment {
    MainActivity mainActivity;
    TextView today;
    ImageView hair;
    RadarChart head_chart;
    RadarDataSet dataSet;
    RadarData data;
    String[] chart_labels={"모량", "민감", "비듬", "모공", "수분", "피지"};
    String today_date;
    ArrayList<RadarEntry> dataVals;
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
        View v=inflater.inflate(R.layout.head_fragment, container, false);
        setting_view(v);
        get_today_date();
        setting_radar_chart();
        return v;
    }public void setting_view(View v){
        today=v.findViewById(R.id.today);
        head_chart=v.findViewById(R.id.head_chart);
        hair=v.findViewById(R.id.my_hair);

        Glide.with(mainActivity).load(R.drawable.my_hair).into(hair);
    }

    public void get_today_date(){
        long now=System.currentTimeMillis();
        Date mDate=new Date(now);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        today_date=simpleDateFormat.format(mDate);

        today.setText(today_date);
    }

    public void setting_radar_chart(){
        dataVals=new ArrayList<>();
        dataVals.add(new RadarEntry(7));
        dataVals.add(new RadarEntry(3));
        dataVals.add(new RadarEntry(5));
        dataVals.add(new RadarEntry(5));
        dataVals.add(new RadarEntry(8));
        dataVals.add(new RadarEntry(6));

        dataSet=new RadarDataSet(dataVals, "");
        dataSet.setFillDrawable(getResources().getDrawable(R.drawable.graph_gradient));
        dataSet.setDrawFilled(true);//내부 그래프 색깔 설정
        dataSet.setLineWidth(0f);
        dataSet.setDrawValues(false);//각각의 값 표시 제거

        data=new RadarData();
        data.addDataSet(dataSet);

        head_chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(chart_labels));
        head_chart.getYAxis().setAxisMinimum(0);//y축 숫자 최소값
        head_chart.getYAxis().setAxisMaximum(8);//y축 실제값+maximum 값
        head_chart.getYAxis().setDrawTopYLabelEntry(false);//가장 최댓값 숫자 표시 제거

        head_chart.setData(data);
        head_chart.setDescription(null);//description label 제거
        head_chart.setWebColorInner(Color.LTGRAY);//내부 web 색
        head_chart.setWebColor(Color.WHITE);//web의 주축 색
        head_chart.setWebLineWidthInner(0);
    }
}
