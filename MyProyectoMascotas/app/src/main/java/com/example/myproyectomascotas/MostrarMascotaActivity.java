package com.example.myproyectomascotas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class MostrarMascotaActivity extends AppCompatActivity {

    private EditText edt_Cnombre;
    private EditText edt_CnumeroChip;
    private EditText edt_Cfecha;
    private EditText edt_Cespecie;
    private Spinner spn_CtipoAnimal;
    private Spinner spn_Csexo;
    private Button btn_Volver;
    private FirebaseAuth firebaseAuth;
    private int countMascota;
    private Button btn_Recordatorio;
    String id;

    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_mascota);

        edt_Cnombre = (EditText) findViewById(R.id.edt_Nombre);
        edt_CnumeroChip = (EditText) findViewById(R.id.edt_NumeroChip);
        edt_Cfecha = (EditText) findViewById(R.id.edt_Fecha);
        edt_Cespecie = (EditText) findViewById(R.id.edt_Especie);
        spn_CtipoAnimal = (Spinner) findViewById(R.id.spn_tipoAnimal);
        spn_Csexo = (Spinner) findViewById(R.id.spn_sexo);
        btn_Volver = (Button) findViewById(R.id.btn_Volver);
        btn_Recordatorio = (Button) findViewById(R.id.btn_AgregarRecordatorio);
        firebaseAuth = FirebaseAuth.getInstance();

        id = getIntent().getStringExtra("id");
        String nombreM = getIntent().getStringExtra("nombre");
        String numeroChip = getIntent().getStringExtra("numeroChip");
        String fechaN = getIntent().getStringExtra("fechaN");
        String especie = getIntent().getStringExtra("especie");
        String tipoM = getIntent().getStringExtra("tipoAnimal");
        String sexo = getIntent().getStringExtra("sexo");




        edt_Cnombre.setText(nombreM);
        edt_CnumeroChip.setText(numeroChip);
        edt_Cfecha.setText(fechaN);
        edt_Cespecie.setText(especie);
        spn_CtipoAnimal.setSelection(getIndexSpinner(spn_CtipoAnimal, tipoM));
        spn_Csexo.setSelection(getIndexSpinner(spn_Csexo, sexo));


        btn_Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MostrarMascotaActivity.this, ListaMascotaActivity.class));


                finish();

            }
        });



        btn_Recordatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MostrarMascotaActivity.this, RecordatorioActivity.class));

                Intent i = new Intent(getApplicationContext(), RecordatorioActivity.class);
                i.putExtra("id2", id);
                startActivity(i);
                finish();

            }
        });

    }

    public static int getIndexSpinner(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
            }
        }
        return index;
    }


}

