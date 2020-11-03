package com.example.myproyectomascotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.myproyectomascotas.Model.Mascota;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaMascotaActivity extends AppCompatActivity {

    private List<Mascota> lista_mascota = new ArrayList<Mascota>();
    ArrayAdapter<Mascota> arrayadaptermascota;
    private FirebaseAuth firebaseAuth;
    ListView lbl_Mascota;
    private DatabaseReference mDataBase;

    private Button btn_Volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mascota);

        lbl_Mascota = (ListView) findViewById(R.id.lbl_Mascota);
        mDataBase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        btn_Volver = (Button) findViewById(R.id.btn_Volver);

        btn_Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListaMascotaActivity.this, ProfileActivity.class));
                finish();

            }
        });

        lbl_Mascota.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Mascota m = lista_mascota.get(position);

                Intent i = new Intent(getApplicationContext(), MostrarMascotaActivity.class);

                Intent i2 = new Intent(getApplicationContext(), RecordatorioActivity.class);

                i.putExtra("id", m.getId());
                i.putExtra("nombre", m.getNombre());
                i.putExtra("numeroChip", m.getNumeroChip());
                i.putExtra("fechaN", m.getFecha());
                i.putExtra("especie", m.getEspecie());
                i.putExtra("tipoAnimal", m.getTipo_Mascota());
                i.putExtra("sexo", m.getSexo());

                System.out.println(m.getNombre());

                startActivity(i);



            }
        });
        
        listarMascota();




    }

    private void listarMascota() {
        String id = firebaseAuth.getCurrentUser().getUid();
        mDataBase.child("usuario").child(id).child("mascota").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lista_mascota.clear();
                    for (DataSnapshot objSnaoshot : snapshot.getChildren()) {
                        Mascota m = objSnaoshot.getValue(Mascota.class);
                        lista_mascota.add(m);

                        arrayadaptermascota = new ArrayAdapter<Mascota>(ListaMascotaActivity.this, android.R.layout.simple_list_item_1, lista_mascota);
                        lbl_Mascota.setAdapter(arrayadaptermascota);
                    }



                }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}