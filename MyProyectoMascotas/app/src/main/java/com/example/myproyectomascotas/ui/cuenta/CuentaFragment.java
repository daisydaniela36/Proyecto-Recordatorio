package com.example.myproyectomascotas.ui.cuenta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myproyectomascotas.LoginActivity;
import com.example.myproyectomascotas.R;
import com.example.myproyectomascotas.ResetPasswordActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;

public class CuentaFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDataBase;
    private EditText edt_Unombre;
    private EditText edt_Udireccion;
    private EditText edt_Utelefono;
    private EditText edt_Ucorreo;
    private EditText edt_Ucontraseña;
    private Button btn_Ueditar;
    private Button btn_Ucambiar_contraseña;
    private ProgressDialog mDialog;
    private String contraseña = "" ;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference();

        View root = inflater.inflate(R.layout.fragment_cuenta, container, false);

        edt_Unombre = (EditText) root.findViewById(R.id.edt_Uombre);
        edt_Udireccion = (EditText) root.findViewById(R.id.edt_Udireccion);
        edt_Utelefono = (EditText) root.findViewById(R.id.edt_Utelefono);
        edt_Ucorreo = (EditText) root.findViewById(R.id.edt_Ucorreo);
        edt_Ucontraseña = (EditText) root.findViewById(R.id.edt_Ucontraseña);
        btn_Ueditar  = (Button) root.findViewById(R.id.btn_Ueditar);
        btn_Ucambiar_contraseña  = (Button) root.findViewById(R.id.btn_Ucambiar_contraseña);


        getUser();

        btn_Ucambiar_contraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contraseña = edt_Ucontraseña.getText().toString();

                if(!contraseña.isEmpty()){
                    resetPassword();
                }else{
                    Toast.makeText(getContext(),"Porfavor Ingrese contraseña",Toast.LENGTH_SHORT).show();
                }

            }
        });


        btn_Ueditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = firebaseAuth.getCurrentUser().getUid();

                String nombre = edt_Unombre.getText().toString();
                String direccion = edt_Udireccion.getText().toString();
                String telefono = edt_Utelefono.getText().toString();
                String correo = edt_Ucorreo.getText().toString();

                Map<String, Object> map = new HashMap<>();
                map.put("nombre", nombre);
                map.put("direccion", direccion);
                map.put("telefono", telefono);
                map.put("correo", correo);

                mDataBase.child("usuario").child(id).updateChildren(map);

            }
        });


        return root;
    }

    private void resetPassword() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updatePassword(edt_Ucontraseña.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(),"Se ah cambiado la contraseña",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(),"Ingrese contraseña con 6 o mas caracteres",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getUser(){
        String id = firebaseAuth.getCurrentUser().getUid();
        mDataBase.child("usuario").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nombre = snapshot.child("nombre").getValue().toString();
                    String direccion = snapshot.child("direccion").getValue().toString();
                    String telefono = snapshot.child("telefono").getValue().toString();
                    String correo = snapshot.child("correo").getValue().toString();

                    edt_Unombre.setText(nombre);
                    edt_Udireccion.setText(direccion);
                    edt_Utelefono.setText(telefono);
                    edt_Ucorreo.setText(correo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}