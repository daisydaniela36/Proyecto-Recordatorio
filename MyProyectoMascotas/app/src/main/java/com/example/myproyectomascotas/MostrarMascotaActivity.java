package com.example.myproyectomascotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MostrarMascotaActivity extends AppCompatActivity {

    private EditText edt_Cnombre;
    private EditText edt_CnumeroChip;
    private EditText edt_Cfecha;
    private EditText edt_Cespecie;
    private Spinner spn_CtipoAnimal;
    private Spinner spn_Csexo;
    private FirebaseAuth firebaseAuth;
    private Button btn_Recordatorio;
    private Button btn_Editar;
    private Button btn_Eliminar;

    String id;

    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_mascota);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        edt_Cnombre = (EditText) findViewById(R.id.edt_Nombre);
        edt_CnumeroChip = (EditText) findViewById(R.id.edt_NumeroChip);
        edt_Cfecha = (EditText) findViewById(R.id.edt_Fecha);
        edt_Cespecie = (EditText) findViewById(R.id.edt_Especie);
        spn_CtipoAnimal = (Spinner) findViewById(R.id.spn_tipoAnimal);
        spn_Csexo = (Spinner) findViewById(R.id.spn_sexo);
        btn_Editar = (Button) findViewById(R.id.btn_Editar);
        btn_Eliminar = (Button) findViewById(R.id.btn_Eliminar);
        btn_Recordatorio = (Button) findViewById(R.id.btn_AgregarRecordatorio);
        firebaseAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference();


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

        btn_Editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id2 = firebaseAuth.getCurrentUser().getUid();

                String nombreE = edt_Cnombre.getText().toString();
                String numeroChipE = edt_CnumeroChip.getText().toString();
                String fechaE = edt_Cfecha.getText().toString();
                String especieE = edt_Cespecie.getText().toString();
                String tipoMascotaE = spn_CtipoAnimal.getSelectedItem().toString();
                String sexoE = spn_Csexo.getSelectedItem().toString();

                Map<String, Object> map = new HashMap<>();
                map.put("nombre", nombreE);
                map.put("numeroChip", numeroChipE);
                map.put("fecha", fechaE);
                map.put("especie", especieE);
                map.put("tipo_Mascota", tipoMascotaE);
                map.put("sexo", sexoE);


                mDataBase.child("usuario").child(id2).child("mascota").child(id).updateChildren(map);

            }
        });

        btn_Eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id2 = firebaseAuth.getCurrentUser().getUid();
                mDataBase.child("usuario").child(id2).child("mascota").child(id).removeValue();

                Intent intent = new Intent(MostrarMascotaActivity.this, PerfilActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, PerfilActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

