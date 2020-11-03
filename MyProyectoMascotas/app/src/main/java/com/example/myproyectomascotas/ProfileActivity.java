package com.example.myproyectomascotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private Button btn_CCerrarSesion;
    private Button btnAgregarMascota;
    private Button btnListarMascota;
    private FirebaseAuth firebaseAuth;
    private TextView txt_Bienvenido;
    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference();

        txt_Bienvenido = (TextView) findViewById(R.id.txtBienvenido);

        btn_CCerrarSesion = (Button) findViewById(R.id.btnCerrarSesion);
        btnAgregarMascota = (Button) findViewById(R.id.btnAgregarMascota);
        btnListarMascota = (Button) findViewById(R.id.btn_Listar);

        btn_CCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();

            }
        });

        btnAgregarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, RegistrarMascotaActivity.class));
                finish();

            }
        });

        btnListarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ListaMascotaActivity.class));
                finish();

            }
        });

        getUser();
    }

    private void getUser(){
        String id = firebaseAuth.getCurrentUser().getUid();
        mDataBase.child("usuario").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nombre = snapshot.child("nombre").getValue().toString();

                    txt_Bienvenido.setText("Bienvenid@ "+nombre);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
