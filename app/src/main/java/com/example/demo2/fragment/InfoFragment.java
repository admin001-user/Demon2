package com.example.demo2.fragment;

/*
 * 信息列表页：使用 RecyclerView 展示列表条目，数据为静态示例。
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo2.adapter.InfoAdapter;
import com.example.demo2.R;
import com.example.demo2.viewmodel.InfoViewModel;

public class InfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        RecyclerView rv = view.findViewById(R.id.recyclerInfo);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        InfoAdapter adapter = new InfoAdapter();
        rv.setAdapter(adapter);
        InfoViewModel vm = new ViewModelProvider(this).get(InfoViewModel.class);
        vm.items.observe(getViewLifecycleOwner(), list -> adapter.setData(list));
    }
}
