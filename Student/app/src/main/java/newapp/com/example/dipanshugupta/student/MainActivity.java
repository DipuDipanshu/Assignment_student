package newapp.com.example.dipanshugupta.student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import static android.R.id.message;

public class MainActivity extends AppCompatActivity {
    private StorageReference mStorageRef;
    private Button b2;
    private TextView ed1,ed2,ed3,ed4;
    private ImageView im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        b2=(Button)findViewById(R.id.button4);
        ed1=(TextView) findViewById(R.id.textView);
        ed2=(TextView) findViewById(R.id.textView2);
        ed3=(TextView) findViewById(R.id.textView3);
        ed4=(TextView) findViewById(R.id.textView4);
        im=(ImageView)findViewById(R.id.imageView3);

        SharedPreferences sharedPref=getSharedPreferences("userinfo", Context.MODE_PRIVATE);

        String name=sharedPref.getString("name","");
        String address=sharedPref.getString("address","");
        String email=sharedPref.getString("email","");
        String mobile=sharedPref.getString("mobile","");
        ed1.setText(name);
        ed2.setText(address);
        ed3.setText(email);
        ed4.setText(mobile);

        StorageReference riversRef = mStorageRef.child("images/rivers.jpg");
        Uri uri=Uri.parse(sharedPref.getString("uri",""));

        Picasso.with(MainActivity.this).load(uri).fit().centerCrop().into(im);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent("newapp.com.example.dipanshugupta.student.AddStudent");
                startActivity(intent);

            }
        });


    }
    public void emailto(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, "email");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        Intent mailer = Intent.createChooser(intent, null);
        startActivity(mailer);
    }

    public void callto(View view){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0123456789"));
        startActivity(intent);
    }

    public void mapto(View view){
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=20.5666,45.345"));
        startActivity(intent);
    }

    public void edit(View view){
        Intent intent=new Intent("newapp.com.example.dipanshugupta.student.Editstudents");
        startActivity(intent);
    }

}
