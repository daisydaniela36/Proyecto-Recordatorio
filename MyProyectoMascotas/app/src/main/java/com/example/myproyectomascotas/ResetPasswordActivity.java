package com.example.myproyectomascotas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    TextView txt_RVolver;
    EditText edt_VCorreo;
    Button btn_Rcontraseña;

    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    private String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        txt_RVolver = (TextView) findViewById(R.id.txt_Volver);
        edt_VCorreo = (EditText) findViewById(R.id.edt_VCorreo);
        btn_Rcontraseña = (Button) findViewById(R.id.btn_Rcontraseña);

        btn_Rcontraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edt_VCorreo.getText().toString();

                if(!email.isEmpty()){
                    mDialog.setMessage("Espere un momento...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    resetPassword();
                }else{
                    Toast.makeText(ResetPasswordActivity.this,"Porfavor Ingrese correo",Toast.LENGTH_SHORT).show();
                }

            }
        });

        txt_RVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResetPasswordActivity.this,LoginActivity.class));
                finish();
            }
        });
    }

    private void resetPassword() {

        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ResetPasswordActivity.this,"Se le ah enviado un correo para restablecer contraseña",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ResetPasswordActivity.this,"No se puso restablecer contraseña intente nuevamente",Toast.LENGTH_SHORT).show();
                }

                mDialog.dismiss();
            }
        });
    }
}