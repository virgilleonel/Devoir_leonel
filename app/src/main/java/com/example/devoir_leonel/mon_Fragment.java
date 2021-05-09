package com.example.devoir_leonel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link mon_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mon_Fragment extends Fragment {

    private EditText nom;
    private EditText places;
    private Button btnAdd;
    DatabaseReference databaseReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootWiew=inflater.inflate(R.layout.fragment_mon_,container,true);
        nom=(EditText) rootWiew.findViewById(R.id.movie);
        places=(EditText) rootWiew.findViewById(R.id.place);
        btnAdd=(Button)rootWiew.findViewById(R.id.buttonAdd);
        btnAdd.setOnClickListener(btnAddListener);
        databaseReference= FirebaseDatabase.getInstance().getReference("movies");
        return  rootWiew;
    }
    private View.OnClickListener btnAddListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String  nomM= nom.getText().toString();
            String  placesM= places.getText().toString();
            int placep=Integer.parseInt(placesM);
            Film film=new Film();
            film.setNom_film(nomM);
            film.setPlaces(placep);
            databaseReference= FirebaseDatabase.getInstance().getReference("movies").child(nomM);
            databaseReference.setValue(film);
        }
    };

}