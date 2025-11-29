package com.example.demo2.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.demo2.repository.UserRepository;

public class LoginViewModel extends AndroidViewModel {
    private final UserRepository repo;
    public final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    public final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repo = new UserRepository(application);
    }

    public void login(String email, String password) {
        boolean ok = repo.validateUser(email, password);
        if (ok) {
            String name = repo.getNameByEmail(email);
            repo.saveLogin(name != null ? name : email);
            loginSuccess.postValue(true);
        } else {
            errorMessage.postValue("账号或密码错误");
        }
    }
}

