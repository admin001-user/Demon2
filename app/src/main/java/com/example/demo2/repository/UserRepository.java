package com.example.demo2.repository;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.demo2.auth.UserDatabaseHelper;

public class UserRepository {
    private final Context appCtx;
    private final SharedPreferences sp;
    private final UserDatabaseHelper db;

    public UserRepository(Context context) {
        this.appCtx = context.getApplicationContext();
        this.sp = appCtx.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        this.db = new UserDatabaseHelper(appCtx);
    }

    public boolean validateUser(String email, String password) {
        return db.validateUser(email, password);
    }

    public String getNameByEmail(String email) {
        return db.getNameByEmail(email);
    }

    public void saveLogin(String username) {
        SharedPreferences.Editor e = sp.edit();
        e.putBoolean("logged_in", true);
        e.putString("username", username);
        if (!sp.contains("signature")) {
            e.putString("signature", "欢迎来到信息App");
        }
        e.apply();
    }

    public void logout() {
        sp.edit().putBoolean("logged_in", false).apply();
    }

    public String getUsername() { return sp.getString("username", "用户名"); }
    public String getSignature() { return sp.getString("signature", "欢迎来到信息App"); }
    public String getAvatarPath() { return sp.getString("avatar_path", null); }
    public void setAvatarPath(String path) { sp.edit().putString("avatar_path", path).apply(); }
}

