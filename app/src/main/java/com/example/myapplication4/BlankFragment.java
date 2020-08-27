package com.example.myapplication4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.Ohuang.ilivedata.HandlerLiveData;


public class BlankFragment extends LazyFragment{
    private final static String TAG="LazyFragment";
    private String name;
    private TextView textView;
    private ListView listView;
    public BlankFragment(String s) {
        name=s;
    }

    @Override
    protected void initView(View root) {
      textView=root.findViewById(R.id.textView);
      listView=root.findViewById(R.id.listView);

      final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1);
      listView.setAdapter(arrayAdapter);
      textView.setText("加载中");
      HandlerLiveData.getInstance().clear(name);
      HandlerLiveData.getInstance().addListener(name, this, new HandlerLiveData.HandlerLiveDataListener() {
            @Override
            public void HandlerMsg(Message message) {
                switch (message.what){
                    case 0:
                        textView.setText(name);
                        for (int i=0;i<100;i++){
                            arrayAdapter.add(name+":"+i);
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_blank;
    }

    @Override
    public void startLoad() {
        super.startLoad();
        Log.d(TAG, "startLoad: "+name);
        new Thread(runnable).start();

    }

    @Override
    public void stopLoad() {
        super.stopLoad();
        Log.d(TAG,"stopLoad:"+name);
        Toast.makeText(getContext(),name+":停止加载",Toast.LENGTH_SHORT).show();
    }

    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            HandlerLiveData.getInstance().sendEmptyMessage(name,0);
        }
    };

}