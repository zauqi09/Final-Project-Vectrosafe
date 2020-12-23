package com.vectrosafe;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.vectrosafe.util.TextValidator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.vectrosafe.model.NomorHP;


public class LoginActivity extends AppCompatActivity {
    private MaterialButton bt_login;
    private TextInputEditText et_no_hp;

    String operator,no_hp;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        onClickGroup();


    }
    private void initView(){
        bt_login=findViewById(R.id.bt_login);
        et_no_hp=findViewById(R.id.et_no_hp);
    }
    void onClickGroup(){
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String no_hp = et_no_hp.getText().toString();
                if (operator.equals("Telkomsel")){
                    System.out.println(operator+" "+no_hp);
                    doLogin(no_hp,operator);
                } else if (operator.equals("XL")){
                    System.out.println(operator+" "+no_hp);
                    doLogin(no_hp,operator);
                }
            }
        });
        et_no_hp.addTextChangedListener(new TextValidator(et_no_hp) {
            @Override public void validate(EditText et_nohp, String text) {
                /* Insert your validation rules here */
                no_hp = et_no_hp.getText().toString();
                if (no_hp.length()>4){
                    String ident_no_hp = no_hp.substring(0, 4);
                    if (ident_no_hp.equals("0811")||ident_no_hp.equals("0812")||ident_no_hp.equals("0813")||ident_no_hp.equals("0822")||ident_no_hp.equals("0821")) {
                        et_no_hp.setError("Telkomsel detected");
                        if (no_hp.length()>11){
                            operator = "Telkomsel";
                        }
                    } else if (ident_no_hp.equals("0817")||ident_no_hp.equals("0818")||ident_no_hp.equals("0819")||ident_no_hp.equals("0859")||ident_no_hp.equals("0878")) {
                        et_no_hp.setError("XL detected");
                        if (no_hp.length()>11){
                            operator = "XL";
                        }
                    }
                }

                if (et_no_hp.getText().toString().length()<=10) {
                    et_no_hp.setError("Mohon masukkan no handphone anda yang benar.");
                }

            }
        });
    }

    private void doLogin(String no_hp,String operator) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("no_hp", no_hp);
        bundle.putString("operator", operator);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}