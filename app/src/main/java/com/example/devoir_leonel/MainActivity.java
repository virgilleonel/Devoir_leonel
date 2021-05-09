package com.example.devoir_leonel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    Client client;
    ArrayList<String> Client=new ArrayList<>();

    EditText email;
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.email=(EditText) findViewById(R.id.email_ind);
        this.password=(EditText) findViewById(R.id.password);
        databaseReference= FirebaseDatabase.getInstance().getReference("customers");

    }
    public void Addmovie(View view){

    }


    public void goToAdd(View v){
        Intent i= new Intent(this,Inscription.class);
        startActivity(i);
    }

    public  void  gotclient(View view){
        databaseReference= FirebaseDatabase.getInstance().getReference("customers");

        String emailget=email.getText().toString();
        String passget=password.getText().toString();
        Query recherche=databaseReference.orderByChild("email").equalTo(emailget);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Client client=new Client();
              for(DataSnapshot ds: snapshot.getChildren()){
                  Client s=ds.getValue(Client.class);
                  String E=s.getEmail();
                  String p=s.getPassword();
                  if(E.equals(emailget) && p.equals(passget)){

                      String nom =s.getNom();
                      int age =s.getAge();
                      String email =s.getEmail();
                    //  System.out.println(nom);
                      //System.out.println(age);
                      //System.out.println(email);
                     Intent j=new Intent(MainActivity.this,Reservation.class);
                            j.putExtra("vnom",nom);
                            j.putExtra("vemail",email);
                            j.putExtra("vage",age);
                    startActivity(j);
                  }
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,"User not exist ",Toast.LENGTH_LONG).show();
            }
        });



    }

}