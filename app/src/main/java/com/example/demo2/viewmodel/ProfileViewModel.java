package com.example.demo2.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.demo2.repository.UserRepository;

public class ProfileViewModel extends AndroidViewModel {
    private final UserRepository repo;
    public final MutableLiveData<String> username = new MutableLiveData<>();
    public final MutableLiveData<String> signature = new MutableLiveData<>();
    public final MutableLiveData<String> avatarPath = new MutableLiveData<>();

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repo = new UserRepository(application);
    }

    public void loadProfile() {
        username.postValue(repo.getUsername());
        signature.postValue(repo.getSignature());
        avatarPath.postValue(repo.getAvatarPath());
    }

    public void updateAvatarPath(String path) {
        repo.setAvatarPath(path);
        avatarPath.postValue(path);
    }

    public void logout() { repo.logout(); }
}

