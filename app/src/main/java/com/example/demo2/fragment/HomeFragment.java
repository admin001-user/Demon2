package com.example.demo2.fragment;

/*
 * 首页：展示“今日推荐”与四个功能入口（资讯/知识/趋势/社区）。
 * 目前点击行为以Toast提示示例，后续可跳转到具体功能页面。
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demo2.R;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // “立即查看”按钮——示例提示
        View btn = view.findViewById(R.id.btnRecommend);
        if (btn != null) btn.setOnClickListener(v -> android.widget.Toast.makeText(getContext(), "立即查看", android.widget.Toast.LENGTH_SHORT).show());
        // 四个入口卡片
        int[] ids = new int[]{R.id.tileNews, R.id.tileKnowledge, R.id.tileTrend, R.id.tileCommunity};
        String[] labels = new String[]{"资讯", "知识", "趋势", "社区"};
        for (int i = 0; i < ids.length; i++) {
            View tile = view.findViewById(ids[i]);
            final String label = labels[i];
            if (tile != null) tile.setOnClickListener(v -> android.widget.Toast.makeText(getContext(), "点击了" + label, android.widget.Toast.LENGTH_SHORT).show());
        }
    }
}
