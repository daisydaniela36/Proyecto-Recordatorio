package com.example.myproyectomascotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    EditText edtRut, edtNombre, edtDireccion, edtTelefono, edtCorreo, edtContraseña;
    Button btnRegistrar, btnIngresar;

    FirebaseAuth firebaseAuth;
    DatabaseReference dr;

    private String rut;
    private String nombre;
    private String direccion;
    private String telefono;
    private String correo;
    private String contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtRut = (EditText) findViewById(R.id.edt_Vrut);
        edtNombre = (EditText) findViewById(R.id.edt_Vnombre);
        edtDireccion = (EditText) findViewById(R.id.edt_Vdireccion);
        edtTelefono = (EditText) findViewById(R.id.edt_Vtelefono);
        edtCorreo = (EditText) findViewById(R.id.edt_Vcorreo);
        edtContraseña = (EditText) findViewById(R.id.edt_Vcontraseña);
        btnRegistrar = (Button) findViewById(R.id.btn_Vregistrar);
        btnIngresar = (Button) findViewById(R.id.btn_Vingresar);
        firebaseAuth = FirebaseAuth.getInstance();
        dr = FirebaseDatabase.getInstance().getReference();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                rut = edtRut.getText().toString();
                nombre = edtNombre.getText().toString();
                direccion = edtDireccion.getText().toString();
                telefono = edtTelefono.getText().toString();
                correo = edtCorreo.getText().toString();
                contraseña = edtContraseña.getText().toString();

                if (!rut.isEmpty() && !nombre.isEmpty() && !direccion.isEmpty() && !telefono.isEmpty() && !correo.isEmpty() && !contraseña.isEmpty()) {
                    if (contraseña.length() >= 6) {
                        RegistrarUsuario();
                    }else {
                        Toast.makeText(MainActivity.this, "La contraseña debe tener almenos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(MainActivity.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainActivity2.class));
            }
        });
    }




    private void RegistrarUsuario() {
        firebaseAuth.createUserWithEmailAndPassword(correo, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Map<String, Object> map = new HashMap<>();
                    map.put("rut" , rut);
                    map.put("nombre", nombre);
                    map.put("direccion", direccion);
                    map.put("telefono", telefono);
                    map.put("correo", correo);
                    map.put("contraseña", contraseña);


                    String id = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                    dr.child("usuario").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Usuario Registrado Correctamente", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(MainActivity.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Correo ya esta en uso o este correo no esta registrado por gmail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void onStart(){
        super.onStart();

        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            finish();
        }
    }


}




