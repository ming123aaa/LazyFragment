package com.example.myapplication4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class LazyFragment2 extends Fragment {


    protected View view;
    private boolean isViewCreate = false;//View是否创建
    private boolean isCurrentVisibleState = false;//保存上一次页面的可见性状态
    private boolean isLoad=false;//保存是否已经加载的状态

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayoutId(), container, false);
        }
        initView(view);
        isViewCreate = true;
        if (getUserVisibleHint()) {//第一次没有调用问题
            setUserVisibleHint(true);
        }
        return view;
    }

    protected abstract void initView(View root);

    protected abstract int getLayoutId();


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isViewCreate) {
            if (isVisibleToUser && !isCurrentVisibleState) {
                dispatchUserVisibleHint(true);
            } else if (!isVisibleToUser && isCurrentVisibleState) {
                dispatchUserVisibleHint(false);
            }
        }
    }

    private void dispatchUserVisibleHint(boolean isVisible) {
        isCurrentVisibleState = isVisible;

        if (isVisible) {
            if (isLoad){return;}
            isLoad=true;
            startLoad();
        } else {
            stopLoad();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && !isCurrentVisibleState) {
            dispatchUserVisibleHint(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint() && isCurrentVisibleState) {
            dispatchUserVisibleHint(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isLoad=false;
    }

    public void stopLoad() {
    }



    public void startLoad() {
    }


}
