package com.example.devoir_leonel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Reservation extends AppCompatActivity {
    TextView tv;
    ListView lst;
    EditText button_validation;
    AutoCompleteTextView autoSaisie;
    DatabaseReference databaseReference;
    //String allStudents[]={"sarra","samira","sami"};
    ArrayList<String> film = new ArrayList<>();// = {"Sara", "Samira", "Sami"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
       databaseReference= FirebaseDatabase.getInstance().getReference("movies");
        this.lst=this.findViewById(R.id.list);
        this.autoSaisie=findViewById(R.id.nom_film);
        this.button_validation=findViewById(R.id.nbplace);

        Intent j= getIntent();

        liste();


        ArrayAdapter ar=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, this.film);
          autoSaisie.setAdapter(ar);
          autoSaisie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                  String selectName = ((TextView) view).getText().toString();
                 reservation(selectName);
              }

          });
        tv= (TextView)findViewById(R.id.tv);
        if(j.hasExtra("vnom")){
            String rnom=j.getStringExtra("vnom");
            tv.setText(rnom);
        }
    }

    public void  liste(){
        databaseReference= FirebaseDatabase.getInstance().getReference("movies");
        // place=button_validation.getText().toString();
         databaseReference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 for(DataSnapshot ds: snapshot.getChildren()){
                     Film s=ds.getValue(Film.class);
                     film.add(s.getNom_film());
               }
             }
             @Override
             public void onCancelled(@NonNull DatabaseError error) {
             }
         });
    }


    public void reservation( final String moviename ){
        databaseReference= FirebaseDatabase.getInstance().getReference("movies");
        databaseReference.addValueEventListener(new ValueEventListener() {
            String place=button_validation.getText().toString();
                   int pl=Integer.parseInt(place);
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
             Film film=new Film();
             System.out.println(moviename);
             //System.out.println(pl);
                 for (DataSnapshot ds: snapshot.getChildren()){
                     Film s=ds.getValue(Film.class);
                     String E=s.getNom_film();
                     if(E.equals(moviename)){
                         int d=s.getPlaces()-pl;
                         System.out.println(d);
                        // film.setPlaces(d);
                        // film.setNom_film(E);
                         s.setPlaces(d);
                         s.setNom_film(E);
                         databaseReference= FirebaseDatabase.getInstance().getReference("movies").child(moviename);
                         databaseReference.setValue(s);
                     }
                 }

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        public void homes(View view){
        Intent i=new Intent(Reservation.this,MainActivity.class);
        startActivity(i);
    }
}