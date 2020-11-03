package com.example.myproyectomascotas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myproyectomascotas.Model.Mascota;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarMascotaActivity extends AppCompatActivity {


    private EditText edt_Cnombre;
    private EditText edt_CnumeroChip;
    private EditText edt_Cfecha;
    private EditText edt_Cespecie;
    private Spinner spn_CtipoAnimal;
    private Spinner spn_Csexo;
    private Button btn_CregistrarM;
    private FirebaseAuth firebaseAuth;

    private DatabaseReference bdMascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_mascota);




        firebaseAuth = FirebaseAuth.getInstance();

        String id = firebaseAuth.getCurrentUser().getUid();

        bdMascota = FirebaseDatabase.getInstance().getReference("usuario").child(id).child("mascota");

        edt_Cnombre = (EditText) findViewById(R.id.edt_Nombre);
        edt_CnumeroChip = (EditText) findViewById(R.id.edt_NumeroChip);
        edt_Cfecha = (EditText) findViewById(R.id.edt_Fecha);
        edt_Cespecie = (EditText) findViewById(R.id.edt_Especie);
        spn_CtipoAnimal = (Spinner) findViewById(R.id.spn_tipoAnimal);
        spn_Csexo = (Spinner) findViewById(R.id.spn_sexo);
        btn_CregistrarM = (Button) findViewById(R.id.btn_Volver);

        btn_CregistrarM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });

    }

    public void registrar() {
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

            Toast.makeText(RegistrarMascotaActivity.this, "Se registro correctamente", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(RegistrarMascotaActivity.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();

        }

    }

}




