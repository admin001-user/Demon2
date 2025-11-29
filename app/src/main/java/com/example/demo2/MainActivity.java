package com.example.demo2;

/*
 * 登录页面：负责采集邮箱和密码，做基础格式校验，
 * 调用本地SQLite验证账号，保存登录态到SharedPreferences，
 * 然后跳转到底部导航主页面（TabsActivity）。
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.demo2.auth.UserDatabaseHelper;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.lifecycle.ViewModelProvider;
import com.example.demo2.viewmodel.LoginViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 开启沉浸式边距处理，避免状态栏遮挡内容
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        // 让根视图根据系统栏自动调整内边距
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextInputLayout tilEmail = findViewById(R.id.tilEmail);
        TextInputLayout tilPassword = findViewById(R.id.tilPassword);
        TextInputEditText emailEt = findViewById(R.id.etEmail);
        TextInputEditText pwdEt = findViewById(R.id.etPassword);
        Button loginBtn = findViewById(R.id.btnLogin);
        TextView tvForget = findViewById(R.id.tvForget);
        Button btnWeChat = findViewById(R.id.btnWeChat);
        Button btnApple = findViewById(R.id.btnApple);
        TextView register = findViewById(R.id.register);

        LoginViewModel vm = new ViewModelProvider(this).get(LoginViewModel.class);

        vm.loginSuccess.observe(this, success -> {
            if (Boolean.TRUE.equals(success)) {
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, TabsActivity.class));
                finish();
            }
        });
        vm.errorMessage.observe(this, msg -> {
            if (msg != null && !msg.isEmpty()) {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        tvForget.setOnClickListener(v -> Toast.makeText(this, "忘记密码（示例）", Toast.LENGTH_SHORT).show());
        btnWeChat.setOnClickListener(v -> Toast.makeText(this, "微信登录（示例）", Toast.LENGTH_SHORT).show());
        btnApple.setOnClickListener(v -> Toast.makeText(this, "Apple 登录（示例）", Toast.LENGTH_SHORT).show());
        register.setOnClickListener(v -> Toast.makeText(this, "注册（示例）", Toast.LENGTH_SHORT).show());

        // 点击“登录”按钮后的业务流程
        loginBtn.setOnClickListener(v -> {
            String email = emailEt.getText() != null ? emailEt.getText().toString().trim() : "";
            String pwd = pwdEt.getText() != null ? pwdEt.getText().toString().trim() : "";

            // 清理上次校验产生的错误提示
            tilEmail.setError(null);
            tilPassword.setError(null);

            boolean hasError = false;
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                tilEmail.setError("邮箱格式不正确");
                hasError = true;
            }
            if (pwd.length() < 6) {
                tilPassword.setError("密码至少6位");
                hasError = true;
            }
            if (hasError) return;
            // 到SQLite中校验账号密码
            vm.login(email, pwd);
        });
    }
}
