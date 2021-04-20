package com.thoughtwork.comment.otherfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.xbj.webview.CommonWebFragment;
import com.xbj.comment.R;
import com.xbj.comment.databinding.FragmentOthersBinding;


/**
 * Created by Vishal Patolia on 18-Feb-18.
 */

public class CategoryFragment extends Fragment {
    FragmentOthersBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_others, container, false);
        mBinding.homeTxtTitle.setText("统计");
       Fragment fragment =  CommonWebFragment.newInstance("http://192.168.207.180:81?token=2342342342342342342");
        return mBinding.getRoot();
    }
}
