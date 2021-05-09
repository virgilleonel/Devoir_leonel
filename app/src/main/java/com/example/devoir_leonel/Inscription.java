package com.example.devoir_leonel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Inscription extends AppCompatActivity {
    EditText nom;
    EditText password;
    EditText age;
    EditText email;
    DatabaseReference databaseReference;
    Button valider;
    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        this.age=(EditText) findViewById(R.id.age);
        this.nom=(EditText)findViewById(R.id.surname);
        this.password=(EditText)findViewById(R.id.name);
        this.email=(EditText)findViewById(R.id.email);
        valider=(Button)findViewById(R.id.ajouter);
    }

   /*private Boolean validateEmail() {
        String val = email.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]";

        if (val.isEmpty()) {
            System.out.print(val);
            email.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            email.setError("Invalid email address");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }*/

    public  void inscription(View view){
        String  nomC= nom.getText().toString();
        String  passwordC= password.getText().toString();
        String  emailC= email.getText().toString();
        String   ageC=age.getText().toString();

        if( nomC.equals("") || passwordC.equals("") || emailC.equals("") ) {
            AlertDialog alertDialog;
            alertDialog=new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Attention");
            alertDialog.setMessage("Champ(s) vide ou addresse email incorrecte !!!");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();

        } else if (Integer.parseInt(ageC)<12){
            Toast.makeText(getApplicationContext(),"Veuillez entrer un nombre entre 0 et 20 ",Toast.LENGTH_LONG).show();
        }
          else {
                int aged=Integer.parseInt(ageC);
                Client client=new Client();
                client.setAge(aged);
                client.setEmail(emailC);
                client.setNom(nomC);
                client.setPassword(passwordC);
                databaseReference= FirebaseDatabase.getInstance().getReference("customers").child(emailC);
                databaseReference.setValue(client);
                 }
        }

  public void home(View view){
        Intent i=new Intent(Inscription.this,MainActivity.class);
        startActivity(i);
   }

}


