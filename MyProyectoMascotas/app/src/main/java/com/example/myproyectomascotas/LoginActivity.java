package com.example.myproyectomascotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText  edt_VCorreo, edt_VContraseña;
    Button btnIngresar,  btn_ir_crearCuenta;
    TextView txt_Rcontraseña;

    private FirebaseAuth firebaseAuth;


    private String correo;
    private String contraseña;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        edt_VCorreo = (EditText) findViewById(R.id.edt_VCorreo);
        edt_VContraseña = (EditText) findViewById(R.id.edt_VContraseña);
        txt_Rcontraseña = (TextView) findViewById(R.id.txt_Rcontraseña);
        btnIngresar = (Button) findViewById(R.id.btn_Rcontraseña);
        btn_ir_crearCuenta=findViewById(R.id.btn_ir_crearCuenta);


        txt_Rcontraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
                finish();
            }
        });



        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                correo = edt_VCorreo.getText().toString();
                contraseña = edt_VContraseña.getText().toString();

                if(!correo.isEmpty() && !contraseña.isEmpty()){
                    loginUsuario();

                }else{
                    Toast.makeText(LoginActivity.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_ir_crearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                finish();
            }
        });
    }

    private void loginUsuario(){
        firebaseAuth.signInWithEmailAndPassword(correo, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this,PerfilActivity.class));
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Correo o Contraseña incorrectos",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}