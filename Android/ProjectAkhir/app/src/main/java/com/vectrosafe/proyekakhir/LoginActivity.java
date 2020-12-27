package com.vectrosafe.proyekakhir;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.vectrosafe.proyekakhir.model.Request.LoginRequest;
import com.vectrosafe.proyekakhir.model.User;
import com.vectrosafe.proyekakhir.viewmodels.UserViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private MaterialButton bt_login, bt_register;
    private TextInputEditText et_username,et_password;
    UserViewModel userViewModel;
    User user;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        onClickGroup();
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.init();

    }
    private void initView(){
        bt_login=findViewById(R.id.bt_login);
        bt_register=findViewById(R.id.bt_register);
        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);
    }
    void onClickGroup(){
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = et_password.getText().toString();
                String username = et_username.getText().toString();
                Log.d("uname-pw", "onClick: uname: "+username+", pw: "+password);
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Silahkan Tunggu...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                try {
                    doLogin(username, password);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void doLogin(String email, String password ) throws InterruptedException {
        userViewModel.postLogin(new LoginRequest(email,password)).observe(this, userResponse -> {
            while (userResponse==null){
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            progressDialog.dismiss();
            if (userResponse.getStatus().equals("200")){
                user = userResponse.getData();
                user.setToken(userResponse.getToken());
                //SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences sharedPref = getSharedPreferences("com.vectrosafe.proyekakhir", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("com.vectrosafe.proyekakhir.token", user.getToken());
                editor.putString("com.vectrosafe.proyekakhir.id_auth", String.valueOf(user.getId_auth()));
                editor.putString("com.vectrosafe.proyekakhir.password", user.getPassword());
                editor.apply();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id_auth", String.valueOf(user.getId_auth()));
                bundle.putString("username_auth", user.getUsername());
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                showDialog(userResponse.getMessage());
            }

        });
    }

    private void showDialog(String msg){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);


        // set pesan dari dialog
        alertDialogBuilder
                .setMessage(msg)
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false).setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do something
                dialogInterface.cancel();

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }



    public void onBackPressed() {
//        Intent a = new Intent(Intent.ACTION_MAIN);
//        a.addCategory(Intent.CATEGORY_HOME);
//        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(a);
        System.exit(1);
    }
}