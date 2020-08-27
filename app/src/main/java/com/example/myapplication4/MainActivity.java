package com.example.myapplication4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.Ohuang.ilivedata.HandlerLiveData;
import com.Ohuang.ilivedata.MyLiveData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   private ViewPager viewPager;
   private RadioGroup radioGroup;
   private List<Fragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager=findViewById(R.id.viewPager);
        radioGroup=findViewById(R.id.radioGroup);

        fragments=new ArrayList<>();
        fragments.add(new BlankFragment("fg1"));
        fragments.add(new BlankFragment("fg2"));
        fragments.add(new BlankFragment("fg3"));
        fragments.add(new BlankFragment("fg4"));
        fragments.add(new BlankFragment("fg5"));
        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(),fragments));
        viewPager.setOffscreenPageLimit(1);
       viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

           }

           @Override
           public void onPageSelected(int position) {
               RadioButton radioButton = (RadioButton) radioGroup.getChildAt(position);//获取该页面对应的radioButton
               radioButton.setChecked(true);//选中radioButton
           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });

       radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup radioGroup, int i) {
               for (int j = 0; j < radioGroup.getChildCount(); j++) {//遍历radioButton
                   if (radioGroup.getChildAt(j).getId() == i) {//获取选中的radioButton
                       viewPager.setCurrentItem(j);//设置对应的viewpage页面
                       return;
                   }
               }
           }
       });


    }


    private class MyFragmentAdapter extends FragmentPagerAdapter{
        private List<Fragment> list;

        public MyFragmentAdapter(@NonNull FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list=list;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return list==null?null:list.get(position);
        }

        @Override
        public int getCount() {
            return list==null?0:list.size();
        }
    }

}