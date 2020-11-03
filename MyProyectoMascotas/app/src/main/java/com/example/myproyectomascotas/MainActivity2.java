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

public class MainActivity2 extends AppCompatActivity {
    EditText  edt_VCorreo, edt_VContraseña;
    Button btnIngresar;

    private FirebaseAuth firebaseAuth;


    private String correo;
    private String contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        firebaseAuth = FirebaseAuth.getInstance();

        edt_VCorreo = (EditText) findViewById(R.id.edt_VCorreo);
        edt_VContraseña = (EditText) findViewById(R.id.edt_VContraseña);
        btnIngresar = (Button) findViewById(R.id.btn_VIniciarSesion);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                correo = edt_VCorreo.getText().toString();
                contraseña = edt_VContraseña.getText().toString();

                if(!correo.isEmpty() && !contraseña.isEmpty()){
                    loginUsuario();

                }else{
                    Toast.makeText(MainActivity2.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void loginUsuario(){
        firebaseAuth.signInWithEmailAndPassword(correo, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity2.this,ProfileActivity.class));
                    finish();
                }else{
                    Toast.makeText(MainActivity2.this, "Correo o Contraseña incorrectos",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}