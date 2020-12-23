package com.vectrosafe.proyekakhir;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.vectrosafe.proyekakhir.model.Request.RegisterRequest;
import com.vectrosafe.proyekakhir.model.Response.ApiResponse;
import com.vectrosafe.proyekakhir.model.User;
import com.vectrosafe.proyekakhir.viewmodels.UserViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {
    private MaterialButton bt_register;
    String password,username,passwordCfr,namalengkap,tanggal_lahir,alamat, no_hp;
    private TextInputEditText et_username,et_password,et_passwordConfirm,et_namalengkap, et_tanggal_lahir,et_alamat,et_no_hp;
    UserViewModel userViewModel;
    private Button bt_datepicker;
    User user;
    ProgressDialog progressDialog;
    final Calendar myCalendar = Calendar.getInstance();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        onClickGroup();
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.init();
        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {

            finish();
            return;
        }
    }

    private void initView(){
        bt_datepicker=findViewById(R.id.bt_datepicker);
        et_alamat = findViewById(R.id.et_alamat_reg);
        et_tanggal_lahir=findViewById(R.id.et_tanggallahir);
        bt_register=findViewById(R.id.bt_Register_reg);
        et_username=findViewById(R.id.et_username_reg);
        et_namalengkap=findViewById(R.id.et_fullname_reg);
        et_password=findViewById(R.id.et_password_reg);
        et_no_hp=findViewById(R.id.et_no_hp_reg);
        et_passwordConfirm=findViewById(R.id.et_passwordConfirm_reg);
    }

    void onClickGroup() {
        bt_datepicker.setOnClickListener(new View.OnClickListener(){
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }

            };

            @Override
            public void onClick(View v) {
                new DatePickerDialog(RegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(RegisterActivity.this);
                progressDialog.setMessage("Silahkan Tunggu...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                password = et_password.getText().toString();
                username = et_username.getText().toString();
                passwordCfr = et_passwordConfirm.getText().toString();
                namalengkap= et_namalengkap.getText().toString();
                no_hp= et_no_hp.getText().toString();
                tanggal_lahir = et_tanggal_lahir.getText().toString();
                alamat = et_alamat.getText().toString();
                if (password.equals(passwordCfr)){
                    try {
                        postRegister();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }
                else {
                    Toast.makeText(getApplicationContext(),"Password tidak sama", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

     private void showDialog(String text){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);


        // set pesan dari dialog
        alertDialogBuilder
                .setMessage(text)
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false);

        // membuat alert dialog dari builder
         if (text.equals("Registrasi gagal")){
             alertDialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                     // do something
                     dialogInterface.cancel();

                 }
             });
         } else {
             alertDialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                     // do something
                     Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                     startActivity(intent);
                 }
             });
         }
         AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }


    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        et_tanggal_lahir.setText(sdf.format(myCalendar.getTime()));
    }
    public void onBackPressed() {
        Intent a = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(a);
    }

    private void postRegister() throws ParseException {
        Date tgl_akhir_temp=new SimpleDateFormat("yyyy/MM/dd").parse(tanggal_lahir);
        userViewModel.postRegister(new RegisterRequest(username, namalengkap,password,tgl_akhir_temp, no_hp, alamat)).observe(this, UserResponse -> {
            while (UserResponse.getData() ==null){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String message = "Registrasi gagal";
            if (UserResponse.getStatus().equals("200")) {
                user = UserResponse.getData();
                progressDialog.dismiss();
                message = "Registrasi sukses, silahkan login";
            }
            showDialog(message);
        });
    }
}
