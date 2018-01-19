package newapp.com.example.dipanshugupta.student;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.security.PublicKey;

import static newapp.com.example.dipanshugupta.student.R.id.imageView;

public class AddStudent extends AppCompatActivity {

    private Button button1,button2;
    private EditText ed1,ed2,ed3,ed4;
    private ImageView im;
    private static final int CAMERA_REQUEST_CODE=1;
    private StorageReference mStorageRef;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Student");


        button1=(Button)findViewById(R.id.button2);
        button2=(Button)findViewById(R.id.button3);
        ed1=(EditText) findViewById(R.id.editText);
        ed2=(EditText) findViewById(R.id.editText2);
        ed3=(EditText) findViewById(R.id.editText3);
        ed4=(EditText) findViewById(R.id.editText6);

        im=(ImageView)findViewById(imageView);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(this);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==android.R.id.home){

            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CAMERA_REQUEST_CODE && resultCode==RESULT_OK){
            mProgress.setMessage("Uploading Image...");
            mProgress.show();
            Uri uri=data.getData();

            StorageReference riversRef = mStorageRef.child("photos").child(uri.getLastPathSegment());

            riversRef.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            mProgress.dismiss();
                            @SuppressWarnings("VisibleForTests")
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            SharedPreferences sharedPref=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPref.edit();
                            editor.putString("uri",downloadUrl.toString());
                            editor.apply();
                            Picasso.with(AddStudent.this).load(downloadUrl).fit().centerCrop().into(im);
                            Toast.makeText(AddStudent.this,"Uploading Finished...",Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });

        }
    }
    public void Save(View view)
    {
        SharedPreferences sharedPref=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPref.edit();

        editor.putString("name",ed1.getText().toString());
        editor.putString("address",ed2.getText().toString());
        editor.putString("email",ed3.getText().toString());
        editor.putString("mobile",ed4.getText().toString());
        editor.apply();
        Toast.makeText(this,"saved!",Toast.LENGTH_LONG).show();
    }
}
