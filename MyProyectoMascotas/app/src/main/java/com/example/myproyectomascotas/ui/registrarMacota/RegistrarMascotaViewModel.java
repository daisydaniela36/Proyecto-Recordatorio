package com.example.myproyectomascotas.ui.registrarMacota;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegistrarMascotaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RegistrarMascotaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}