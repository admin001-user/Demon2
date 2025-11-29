package com.example.demo2.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.demo2.model.InfoItem;
import com.example.demo2.repository.InfoRepository;
import java.util.List;

public class InfoViewModel extends AndroidViewModel {
    private final InfoRepository repo;
    public final MutableLiveData<List<InfoItem>> items = new MutableLiveData<>();

    public InfoViewModel(@NonNull Application application) {
        super(application);
        repo = new InfoRepository();
        items.setValue(repo.loadItems());
    }
}

