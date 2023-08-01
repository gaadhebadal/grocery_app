package may.internship;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
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

public class ProfileActivity extends AppCompatActivity {
    EditText name, email, contact, dateOfBirth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Button edit_profile,submit;
    SharedPreferences sp;
    RadioButton male,female;
    Calendar calendar;
    RadioGroup gender;
    String sGender, sCity;
    Spinner spinner;
    boolean isSelect = false;
    SQLiteDatabase db;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = findViewById(R.id.profile_name);
        email = findViewById(R.id.profile_email);
        contact = findViewById(R.id.profile_contact);
        dateOfBirth = findViewById(R.id.profile_date_of_birth);
        gender = findViewById(R.id.profile_gender);
        spinner = findViewById(R.id.profile_spinner);
        submit=findViewById(R.id.profile_submit_but);
        edit_profile=findViewById(R.id.profile_edit_but);
        male = findViewById(R.id.profile_male);
        female = findViewById(R.id.profile_female);

        arrayList = new ArrayList<>();
        arrayList.add("Gir-somnath");
        arrayList.add("Junagadh");
        arrayList.add("Ahmedabad");
        arrayList.add("Anand");
        arrayList.add("Vadodara");
        arrayList.add("Surat");
        arrayList.add("Rajkot");
        arrayList.add("Other");

        ArrayAdapter adapter = new ArrayAdapter(ProfileActivity.this, android.R.layout.simple_list_item_1, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        spinner.setAdapter(adapter);
        sp = getSharedPreferences(constantdata.PREF,MODE_PRIVATE);


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


                if(isSelect){
                    //disable date
                    DatePickerDialog pickerDialog = new DatePickerDialog(ProfileActivity.this, dateClick, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    //past date enable
                    //pickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                    //feature date enable
                    pickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    pickerDialog.show();
                }

            }
        });
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSelect = true;
                setData(isSelect);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
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
                } else if (dateOfBirth.getText().toString().trim().equals("")) {
                    //dateOfBirth.setError("Please Select Date Of Birth");
                    //new common(signup_page.this, "Please Select Date Of Birth");
                    new common(v, "Please Select Date Of Birth");
                } else if (gender.getCheckedRadioButtonId() == -1) {
                    // new common(signup_page.this, "Please Select Gender");
                    new common(v, "Please Select Gender");
                } else {
                    String updateQuery = "UPDATE USER_DATA SET NAME='"+name.getText().toString()+"',EMAIL='"+email.getText().toString()+"',DOB='"+dateOfBirth.getText().toString()+"',GENDER='"+sGender+"',CITY='"+sCity+"' WHERE CONTACT='"+contact.getText().toString()+"'";
                    db.execSQL(updateQuery);
                    new common(ProfileActivity.this,"Profile Update Successfully");

                    sp.edit().putString(constantdata.NAME,name.getText().toString()).commit();
                    sp.edit().putString(constantdata.EMAIL,email.getText().toString()).commit();
                    sp.edit().putString(constantdata.DOB,dateOfBirth.getText().toString()).commit();
                    sp.edit().putString(constantdata.GENDER,sGender).commit();
                    sp.edit().putString(constantdata.CITY,sCity).commit();

                    isSelect = false;
                    setData(isSelect);
                }
//                    String selectQuery = "SELECT * FROM USER_DATA WHERE EMAIL='" + email.getText().toString() + "' OR CONTACT='" + contact.getText().toString() + "'";
//                    Cursor cursor = db.rawQuery(selectQuery, null);
//                    if (cursor.getCount() > 0) {
//                        new common(v,"Email Id/Contact No. Already Registered");
//                    }else {
//                        String insertQuery = "INSERT INTO USER_DATA VALUES ('" + name.getText().toString() + "','" + contact.getText().toString() + "','" + email.getText().toString() + "','" + dateOfBirth.getText().toString() + "','" + sGender + "','" + sCity + "')";
//                        db.execSQL(insertQuery);
//                        new common(ProfileActivity.this, "Signup Successfully");

            }
        });
        setData(isSelect);
    }

    private void setData(boolean isSelect) {
        name.setEnabled(isSelect);
        email.setEnabled(isSelect);
        contact.setEnabled(false);
        male.setEnabled(isSelect);
        female.setEnabled(isSelect);
        gender.setEnabled(isSelect);
        spinner.setEnabled(isSelect);

        if(isSelect){
            edit_profile.setVisibility(View.GONE);
            submit.setVisibility(View.VISIBLE);
        }
        else{
            edit_profile.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);
        }
        name.setText(sp.getString(constantdata.NAME,""));
        email.setText(sp.getString(constantdata.EMAIL,""));
        contact.setText(sp.getString(constantdata.CONTACT,""));
        dateOfBirth.setText(sp.getString(constantdata.DOB,""));
        sGender = sp.getString(constantdata.GENDER,"");
        if(sGender.equalsIgnoreCase(getResources().getString(R.string.male))){
            male.setChecked(true);
        }
        else if(sGender.equalsIgnoreCase(getResources().getString(R.string.female))){
            female.setChecked(true);
        } else if(sGender.equalsIgnoreCase(getResources().getString(R.string.other))){
            female.setChecked(true);
        } else{

        }

        sCity = sp.getString(constantdata.CITY,"");
        int iCityPosition = 0;
        for(int i=0;i<arrayList.size();i++){
            if(sCity.equalsIgnoreCase(arrayList.get(i))){
                iCityPosition = i;
                break;
            }
        }
        spinner.setSelection(iCityPosition);

    }
}