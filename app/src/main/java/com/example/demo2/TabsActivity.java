package com.example.demo2;

/*
 * 底部导航主页面：承载三个功能Tab——首页(HomeFragment)、信息(InfoFragment)、我的(MineFragment)。
 * 通过 BottomNavigationView 切换 Fragment，默认选中“首页”。
 */

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo2.fragment.HomeFragment;
import com.example.demo2.fragment.InfoFragment;
import com.example.demo2.fragment.MineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TabsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        // 绑定底部导航并监听选项切换
        BottomNavigationView nav = findViewById(R.id.bottomNav);
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                // 获取Fragment管理器，开启事务，替换Fragment容器（id:fragmentContainer）的内容为HomeFragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new HomeFragment())
                        .commit();
                return true;
            } else if (id == R.id.nav_info) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new InfoFragment())
                        .commit();
                return true;
            } else if (id == R.id.nav_mine) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new MineFragment())
                        .commit();
                return true;
            }
            return false;
        });
        // 设置默认选中“首页”
        nav.setSelectedItemId(R.id.nav_home);
    }
}

