package com.example.demo2.fragment;

/*
 * 我的页面Fragment：直接复用个人中心布局与交互逻辑。
 * 支持选择头像、中心裁剪与缩放保存，并提供退出登录入口。
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo2.R;
import com.example.demo2.viewmodel.ProfileViewModel;

public class MineFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvSign = view.findViewById(R.id.tvSignature);
        ImageView avatar = view.findViewById(R.id.ivAvatar);
        android.widget.Button btnLogout = view.findViewById(R.id.btnLogout);

        ProfileViewModel vm = new ViewModelProvider(this).get(ProfileViewModel.class);
        vm.username.observe(getViewLifecycleOwner(), tvName::setText);
        vm.signature.observe(getViewLifecycleOwner(), tvSign::setText);
        vm.avatarPath.observe(getViewLifecycleOwner(), path -> {
            if (path == null) return;
            try {
                Bitmap bm = BitmapFactory.decodeFile(path);
                if (bm != null) avatar.setImageBitmap(bm);
            } catch (Exception ignored) {}
        });
        vm.loadProfile();

        // 打开系统相册选择图片并处理
        ActivityResultLauncher<String> pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                (Uri uri) -> {
                    if (uri == null) return;
                    try {
                        java.io.InputStream is = requireActivity().getContentResolver().openInputStream(uri);
                        if (is == null) return;
                        Bitmap src = BitmapFactory.decodeStream(is);
                        is.close();
                        if (src == null) return;
                        // 中心裁剪为正方形并缩放至256x256
                        Bitmap square = cropCenterSquare(src);
                        Bitmap scaled = Bitmap.createScaledBitmap(square, 256, 256, true);
                        String saved = saveBitmapToFile(scaled);
                        if (saved != null) {
                            avatar.setImageBitmap(scaled);
                            vm.updateAvatarPath(saved);
                            Toast.makeText(getContext(), "头像已更新", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "头像更新失败", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        avatar.setOnClickListener(v -> pickImageLauncher.launch("image/*"));

        // 退出登录：清除登录态并返回登录页
        btnLogout.setOnClickListener(v -> {
            vm.logout();
            Toast.makeText(getContext(), "已退出登录", Toast.LENGTH_SHORT).show();
            startActivity(new android.content.Intent(requireActivity(), com.example.demo2.MainActivity.class));
            requireActivity().finish();
        });

        int[] rowIds = new int[]{R.id.rowProfile, R.id.rowFavorites, R.id.rowHistory, R.id.rowSettings, R.id.rowAbout, R.id.rowFeedback};
        String[] labels = new String[]{"个人信息", "我的收藏", "浏览历史", "设置", "关于我们", "意见反馈"};
        for (int i = 0; i < rowIds.length; i++) {
            LinearLayout row = view.findViewById(rowIds[i]);
            final String label = labels[i];
            row.setOnClickListener(v -> Toast.makeText(getContext(), "点击了" + label, Toast.LENGTH_SHORT).show());
        }
    }

    private Bitmap cropCenterSquare(Bitmap src) {
        int w = src.getWidth();
        int h = src.getHeight();
        int size = Math.min(w, h);
        int x = (w - size) / 2;
        int y = (h - size) / 2;
        try {
            return Bitmap.createBitmap(src, x, y, size, size);
        } catch (Exception e) {
            return src;
        }
    }

    private String saveBitmapToFile(Bitmap bm) {
        try {
            java.io.File dir = new java.io.File(requireActivity().getFilesDir(), "avatars");
            if (!dir.exists()) dir.mkdirs();
            java.io.File f = new java.io.File(dir, "avatar_" + System.currentTimeMillis() + ".png");
            java.io.FileOutputStream fos = new java.io.FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return f.getAbsolutePath();
        } catch (Exception e) {
            return null;
        }
    }
}
