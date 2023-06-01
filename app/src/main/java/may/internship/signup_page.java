package may.internship;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class signup_page extends AppCompatActivity {
    EditText name, email, contact, password, confirmPassword, dateOfBirth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    RadioGroup gender;
    String sGender, sCity;
    Spinner spinner;

    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        name = findViewById(R.id.signup_name);
        email = findViewById(R.id.signup_email);
        contact = findViewById(R.id.signup_contact);
        password = findViewById(R.id.signup_password);
        confirmPassword = findViewById(R.id.signup_confirm_password);
        dateOfBirth = findViewById(R.id.signup_date_of_birth);
        gender = findViewById(R.id.signup_gender);

        spinner = findViewById(R.id.signup_spinner);
        arrayList = new ArrayList<>();
        arrayList.add("Gir-somnath");
        arrayList.add("Junagadh");
        arrayList.add("Ahmedabad");
        arrayList.add("Anand");
        arrayList.add("Vadodara");
        arrayList.add("Surat");
        arrayList.add("Rajkot");
        arrayList.add("Other");

        ArrayAdapter adapter = new ArrayAdapter(signup_page.this, android.R.layout.simple_list_item_1, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        spinner.setAdapter(adapter);

        SQLiteDatabase db;
        db = openOrCreateDatabase("first_app", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USER_DATA(NAME VARCHAR(100),CONTACT BIGINT(12),EMAIL VARCHAR(100),PASSWORD VARCHAR(15),DOB VARCHAR(15),GENDER VARCHAR(20),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        //setOnCheckedChangeListener for radio button
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
               // new common(signup_page.this, radioButton.getText().toString());
                sGender = radioButton.getText().toString();
            }
        });


        // setOnItemSelectedListener for check box
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sCity = arrayList.get(position);
                //new common(signup_page.this,sCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Calendar calendar;
        calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateClick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DAY_OF_MONTH, i2);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                dateOfBirth.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //normal date piker
                //new DatePickerDialog(SignupActivity.this,dateClick,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

                //disable date
                DatePickerDialog pickerDialog = new DatePickerDialog(signup_page.this, dateClick, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                //past date enable
                //pickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                //feature date enable
                pickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                pickerDialog.show();
            }
        });



        Button signup;
        signup = findViewById(R.id.signup_but);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().trim().equals("")) {
                    name.setError("Name Required");
                } else if (contact.getText().toString().trim().equals("")) {
                    contact.setError("Contact No. Required");
                } else if (contact.getText().toString().length() < 10) {
                    contact.setError("Valid Contact No. Required");
                } else if (email.getText().toString().trim().equalsIgnoreCase("")) {
                    email.setError("Email Id Required");
                } else if (!email.getText().toString().matches(emailPattern)) {
                    email.setError("Valid Email Id Required");
                } else if (password.getText().toString().trim().equals("")) {
                    password.setError("Password Required");
                } else if (password.getText().toString().length() < 6) {
                    password.setError("Min.6 Character Password Required");
                } else if (confirmPassword.getText().toString().trim().equals("")) {
                    confirmPassword.setError("Confirm Password Required");
                } else if (confirmPassword.getText().toString().length() < 6) {
                    confirmPassword.setError("Min.6 Character Confirm Password Required");
                } else if (!confirmPassword.getText().toString().matches(password.getText().toString())) {
                    confirmPassword.setError("Confirm Password Does Not Match");
                } else if (dateOfBirth.getText().toString().trim().equals("")) {
                    //dateOfBirth.setError("Please Select Date Of Birth");
                    //new common(signup_page.this, "Please Select Date Of Birth");
                    new common(v, "Please Select Date Of Birth");
                } else if (gender.getCheckedRadioButtonId() == -1) {
                    // new common(signup_page.this, "Please Select Gender");
                    new common(v, "Please Select Gender");
                } else {
                    String selectQuery = "SELECT * FROM USER_DATA WHERE EMAIL='" + email.getText().toString() + "' OR CONTACT='" + contact.getText().toString() + "'";
                    Cursor cursor = db.rawQuery(selectQuery, null);
                    if (cursor.getCount() > 0) {
                        new common(v,"Email Id/Contact No. Already Registered");
                    }else {
                        String insertQuery = "INSERT INTO USER_DATA VALUES ('" + name.getText().toString() + "','" + contact.getText().toString() + "','" + email.getText().toString() + "','" + password.getText().toString() + "','" + dateOfBirth.getText().toString() + "','" + sGender + "','" + sCity + "')";
                        db.execSQL(insertQuery);
                        new common(signup_page.this, "Signup Successfully");
                    }
                }
            }
        });
    }

}