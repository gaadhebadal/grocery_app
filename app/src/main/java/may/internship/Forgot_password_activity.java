package may.internship;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Forgot_password_activity extends AppCompatActivity {

    EditText contact, password, confirmPassword;
    Button continueButton, submit;

    SQLiteDatabase db;
    SharedPreferences sp;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        db = openOrCreateDatabase("first_app",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USER_DATA(NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT BIGINT(10),PASSWORD VARCHAR(15),DOB VARCHAR(10),GENDER VARCHAR(6),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        contact = findViewById(R.id.forgot_password_contact);
        password = findViewById(R.id.forgot_password_password);
        confirmPassword = findViewById(R.id.forgot_password_confirm_password);
        continueButton = findViewById(R.id.forgot_password_continue);
        submit = findViewById(R.id.forgot_password_submit);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contact.getText().toString().trim().equals("")) {
                    contact.setError("Contact No. Required");
                } else if (contact.getText().toString().trim().length() < 10) {
                    contact.setError("Valid Contact No. Required");
                } else {
                    String contactCheckQuery = "SELECT * FROM USER_DATA WHERE CONTACT='"+contact.getText().toString()+"'";
                    Cursor cursor = db.rawQuery(contactCheckQuery,null);
                    if(cursor.getCount()>0) {
                        contact.setEnabled(false);
                        continueButton.setVisibility(View.GONE);
                        password.setVisibility(View.VISIBLE);
                        confirmPassword.setVisibility(View.VISIBLE);
                        submit.setVisibility(View.VISIBLE);
                    }
                    else{
                        new common(Forgot_password_activity.this,"Contact No. Not Registered");
                    }
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password.getText().toString().trim().equals("")) {
                    password.setError("Password Required");
                } else if (password.getText().toString().trim().length() < 6) {
                    password.setError("Min. 6 Char Password Required");
                } else if (confirmPassword.getText().toString().trim().equals("")) {
                    confirmPassword.setError("Confirm Password Required");
                } else if (confirmPassword.getText().toString().trim().length() < 6) {
                    confirmPassword.setError("Min. 6 Char Password Required");
                } else if (!confirmPassword.getText().toString().matches(password.getText().toString())) {
                    confirmPassword.setError("Password Does Not Match");
                } else {
                    String updateQuery = "UPDATE USER_DATA SET PASSWORD='"+password.getText().toString()+"' WHERE CONTACT='"+contact.getText().toString()+"'";
                    db.execSQL(updateQuery);
                    new common(Forgot_password_activity.this,"Password Changed Successfully");
                    onBackPressed();
                }
            }
        });
    }
}