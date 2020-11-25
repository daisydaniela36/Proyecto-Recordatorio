package com.example.myproyectomascotas.ui.listarMascota;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListarMascotaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ListarMascotaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}