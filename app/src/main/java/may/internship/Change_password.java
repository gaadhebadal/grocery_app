package may.internship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Change_password extends AppCompatActivity {
    EditText oldPassword,newPassword,confirmPassword;
    Button submit;
    SQLiteDatabase db;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        sp = getSharedPreferences(constantdata.PREF,MODE_PRIVATE);

        db = openOrCreateDatabase("first_app",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USER_DATA(NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT BIGINT(10),PASSWORD VARCHAR(15),DOB VARCHAR(10),GENDER VARCHAR(6),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        oldPassword = findViewById(R.id.change_password_old);
        newPassword = findViewById(R.id.change_password_new);
        confirmPassword = findViewById(R.id.change_password_confirm);

        submit = findViewById(R.id.change_password_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(oldPassword.getText().toString().trim().equals("")){
                    oldPassword.setError("Old Password Required");
                }
                else if(oldPassword.getText().toString().trim().length()<6){
                    oldPassword.setError("Min. 6 Character Password Required");
                }
                else if(newPassword.getText().toString().trim().equals("")){
                    newPassword.setError("New Password Required");
                }
                else if(newPassword.getText().toString().trim().length()<6){
                    newPassword.setError("Min. 6 Character Password Required");
                }
                else if(newPassword.getText().toString().matches(oldPassword.getText().toString())){
                    newPassword.setError("Same as Old Password Not Allowed");
                }
                else if(confirmPassword.getText().toString().trim().equals("")){
                    confirmPassword.setError("Confirm Password Required");
                }
                else if(confirmPassword.getText().toString().trim().length()<6){
                    confirmPassword.setError("Min. 6 Character Password Required");
                }
                else if(!confirmPassword.getText().toString().matches(newPassword.getText().toString())){
                    confirmPassword.setError("Confirm Password Does Not Match");
                }
                else{
                    String selectQuery = "SELECT * FROM USER_DATA WHERE CONTACT = '"+ sp.getString(constantdata.CONTACT,"") +"' AND PASSWORD='"+oldPassword.getText().toString()+"' ";
                    Cursor cursor = db.rawQuery(selectQuery,null);
                    if(cursor.getCount()>0){
                        String updateQuery = "UPDATE USER_DATA SET PASSWORD='"+newPassword.getText().toString()+"' WHERE CONTACT='"+sp.getString(constantdata.CONTACT,"")+"'";
                        db.execSQL(updateQuery);
                        new common(Change_password.this,"Password Change Successfully");
                        onBackPressed();
                    }
                    else{
                        new common(Change_password.this,"Old Password Does Not Match");
                    }
                }
            }
        });
    }
}