package may.internship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText email, password;
    TextView create_acc,forgotPassword;
    SQLiteDatabase db;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences(constantdata.pref,MODE_PRIVATE);
        login = findViewById(R.id.login_but);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        create_acc = findViewById(R.id.create_acc);

        db = openOrCreateDatabase("first_app", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USER_DATA(NAME VARCHAR(100),CONTACT BIGINT(12),EMAIL VARCHAR(100),PASSWORD VARCHAR(15),DOB VARCHAR(15),GENDER VARCHAR(20),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        forgotPassword = findViewById(R.id.main_forgot_password);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new common(MainActivity.this, Forgot_password_activity.class);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().trim().equals("")) {
                    new common(v, "Enter Email");
                    //      Snackbar.make(v, "Enter Email", Snackbar.LENGTH_SHORT).show();
                } else if (password.getText().toString().trim().equals("")) {
                    // Snackbar.make(v, " Enter Password ", Snackbar.LENGTH_SHORT).show();
                    new common(v, "Enter Password");
                } else if (password.getText().toString().trim().length() < 6) {
                    new common(v, "Password must be at least 6 characters");
                    // Snackbar.make(v, " Password must be at least 6 characters", Snackbar.LENGTH_SHORT).show();
                } else {
                    String loginQuery = "SELECT * FROM USER_DATA WHERE (EMAIL='"+email.getText().toString()+"' OR CONTACT='"+email.getText().toString()+"') AND PASSWORD='"+password.getText().toString()+"'";
                    Cursor cursor = db.rawQuery(loginQuery,null);

                    if(cursor.getCount()>0){
                        while (cursor.moveToNext()){
                            String sName = cursor.getString(0);
                            String sEmail = cursor.getString(1);
                            String sContact = cursor.getString(2);
                            String sPassword = cursor.getString(3);
                            String sDob = cursor.getString(4);
                            String sGender = cursor.getString(5);
                            String sCity = cursor.getString(6);
                            Log.d("LOGIN_USER_DATA",sName+"\n"+sEmail+"\n"+sContact+"\n"+sPassword+"\n"+sDob+"\n"+sGender+"\n"+sCity);

                            System.out.println("Login Successfully");
                            Log.d("LOGIN", "Login Successfully");
                            Log.e("LOGIN", "Login Successfully");
                            new common(MainActivity.this, "Login Successfully");
                            new common(v,"Login Successfully");

                            sp.edit().putString(constantdata.NAME,sName).commit();
                            sp.edit().putString(constantdata.EMAIL,sEmail).commit();
                            sp.edit().putString(constantdata.CONTACT,sContact).commit();
                            sp.edit().putString(constantdata.PASSWORD,sPassword).commit();
                            sp.edit().putString(constantdata.DOB,sDob).commit();
                            sp.edit().putString(constantdata.GENDER,sGender).commit();
                            sp.edit().putString(constantdata.CITY,sCity).commit();

                            new common(MainActivity.this,DashbordActivity.class);

                                /*Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("NAME",sName);
                                intent.putExtras(bundle);
                                startActivity(intent);*/
                        }
                    }
                    else{
                        new common(MainActivity.this, "Login Unsuccessfully");
                        new common(v,"Login Unsuccessfully");
                    }
                }
            }
        });
        create_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new common(MainActivity.this, signup_page.class);
                //startActivity(new Intent(MainActivity.this, signup_page.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        finishAffinity();
    }}
//                Snackbar.make(MainActivity.this.login,"successfully",10000).show();
//                Snackbar.make(v,"successful",Snackbar.LENGTH_LONG).show();