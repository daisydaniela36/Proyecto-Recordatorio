package com.example.myproyectomascotas.ui.listarMascota;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myproyectomascotas.Model.Mascota;
import com.example.myproyectomascotas.MostrarMascotaActivity;
import com.example.myproyectomascotas.R;
import com.example.myproyectomascotas.RecordatorioActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListarMascotaFragment extends Fragment {

    private List<Mascota> lista_mascota = new ArrayList<Mascota>();
    ArrayAdapter<Mascota> arrayadaptermascota;
    private FirebaseAuth firebaseAuth;
    ListView lbl_Mascota;
    private DatabaseReference mDataBase;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_listar_mascota, container, false);


        lbl_Mascota = (ListView) root.findViewById(R.id.lbl_Mascota);
        mDataBase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        

        lbl_Mascota.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Mascota m = lista_mascota.get(position);

                Intent i = new Intent(getActivity(), MostrarMascotaActivity.class);

                Intent i2 = new Intent(getActivity(), RecordatorioActivity.class);

                i.putExtra("id", m.getId());
                i.putExtra("nombre", m.getNombre());
                i.putExtra("numeroChip", m.getNumeroChip());
                i.putExtra("fechaN", m.getFecha());
                i.putExtra("especie", m.getEspecie());
                i.putExtra("tipoAnimal", m.getTipo_Mascota());
                i.putExtra("sexo", m.getSexo());

                System.out.println(m.getId());

                startActivity(i);



            }
        });

        listarMascota();
        return root;

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

                    arrayadaptermascota = new ArrayAdapter<Mascota>(getActivity(), android.R.layout.simple_list_item_1, lista_mascota);
                    lbl_Mascota.setAdapter(arrayadaptermascota);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}