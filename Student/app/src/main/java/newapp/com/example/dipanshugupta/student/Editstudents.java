package newapp.com.example.dipanshugupta.student;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Editstudents extends AppCompatActivity {
    private Button b1;
    private EditText ed1,ed2,ed3,ed4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editstudents);

        b1=(Button)findViewById(R.id.Update);
        ed1=(EditText) findViewById(R.id.editText4) ;
        ed2=(EditText) findViewById(R.id.editText5) ;
        ed3=(EditText) findViewById(R.id.editText7) ;
        ed4=(EditText) findViewById(R.id.editText8) ;

        SharedPreferences sharedPref=getSharedPreferences("userinfo", Context.MODE_PRIVATE);

        String name=sharedPref.getString("name","");
        String address=sharedPref.getString("address","");
        String email=sharedPref.getString("email","");
        String mobile=sharedPref.getString("mobile","");
        ed1.setText(name);
        ed2.setText(address);
        ed3.setText(email);
        ed4.setText(mobile);


        getSupportActionBar().setTitle("Edit Students");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void update(View view){
        SharedPreferences sharedPref=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPref.edit();

        editor.putString("name",ed1.getText().toString());
        editor.putString("address",ed2.getText().toString());
        editor.putString("email",ed3.getText().toString());
        editor.putString("mobile",ed4.getText().toString());
        editor.apply();
        Toast.makeText(this,"Updated!",Toast.LENGTH_LONG).show();
    }
}
