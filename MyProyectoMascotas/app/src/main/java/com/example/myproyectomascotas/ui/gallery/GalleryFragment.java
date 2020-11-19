package com.example.myproyectomascotas.ui.gallery;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myproyectomascotas.Model.Mascota;
import com.example.myproyectomascotas.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GalleryFragment extends Fragment {

    private EditText edt_Cnombre;
    private EditText edt_CnumeroChip;
    private EditText edt_Cfecha;
    private EditText edt_Cespecie;
    private Spinner spn_CtipoAnimal;
    private Spinner spn_Csexo;
    private Button btn_CregistrarM;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference bdMascota;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        String id = firebaseAuth.getCurrentUser().getUid();

        bdMascota = FirebaseDatabase.getInstance().getReference("usuario").child(id).child("mascota");

        edt_Cnombre = (EditText) root.findViewById(R.id.edt_Nombre);
        edt_CnumeroChip = (EditText) root.findViewById(R.id.edt_NumeroChip);
        edt_Cfecha = (EditText) root.findViewById(R.id.edt_Fecha);
        edt_Cespecie = (EditText) root.findViewById(R.id.edt_Especie);
        spn_CtipoAnimal = (Spinner) root.findViewById(R.id.spn_tipoAnimal);
        spn_Csexo = (Spinner) root.findViewById(R.id.spn_sexo);
        btn_CregistrarM = (Button) root.findViewById(R.id.btn_Volver);

        btn_CregistrarM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });

        return root;
    }

    private void registrar() {
        String nombre = edt_Cnombre.getText().toString();
        String numero_chip = edt_CnumeroChip.getText().toString();
        String fecha = edt_Cfecha.getText().toString();
        String especie = edt_Cespecie.getText().toString();
        String tipo_Animal = spn_CtipoAnimal.getSelectedItem().toString();
        String sexo = spn_Csexo.getSelectedItem().toString();


        if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(numero_chip)  && !TextUtils.isEmpty(fecha)  && !TextUtils.isEmpty(especie) ) {
            String id = bdMascota.push().getKey();

            Mascota mascota = new Mascota(id,nombre, numero_chip, fecha, especie, tipo_Animal, sexo);
            bdMascota.child(id).setValue(mascota);

            Toast.makeText(getActivity(), "Se registro correctamente", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Debe completar los campos", Toast.LENGTH_SHORT).show();

        }
    }
}