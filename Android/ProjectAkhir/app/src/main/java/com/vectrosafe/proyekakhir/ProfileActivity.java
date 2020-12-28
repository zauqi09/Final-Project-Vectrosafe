package com.vectrosafe.proyekakhir;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.vectrosafe.proyekakhir.model.Nasabah;
import com.vectrosafe.proyekakhir.viewmodels.UserViewModel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {
    String nama_nasabah,tgl_lahir, no_hp,alamat,no_rek;

    UserViewModel userViewModel;
    ProgressDialog progressDialog;
    SharedPreferences sharedPref;
    String token,id_auth,id_nasabah,username_auth;

    @BindView(R.id.bt_logout)
    Button bt_logout;

    @BindView(R.id.tv_user_profile)
    TextView tv_user;

    @BindView(R.id.tv_nama_profile)
    TextView tv_nama;

    @BindView(R.id.tv_alamat_profile)
    TextView tv_alamat;

    @BindView(R.id.tv_no_hp_profile)
    TextView tv_no_hp;

    @BindView(R.id.tv_no_rekening_profile)
    TextView tv_no_rek;

    @BindView(R.id.tv_tgl_lahir_profile)
    TextView tv_tgl_lahir;

    @BindView(R.id.tv_back_profile)
    ImageView iv_back_profile;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.init();
        sharedPref = getSharedPreferences("com.vectrosafe.proyekakhir", Context.MODE_PRIVATE);
        token = "Bearer "+sharedPref.getString("com.vectrosafe.proyekakhir.token","-");
        username_auth = sharedPref.getString("com.vectrosafe.proyekakhir.username_auth","-");
        ButterKnife.bind(this);
        initViewData();
        onClickGroup();

    }

    void onClickGroup(){
        iv_back_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(ProfileActivity.this);
                progressDialog.setMessage("Silahkan Tunggu...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                SharedPreferences sharedPref = getSharedPreferences("com.vectrosafe.proyekakhir", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("com.vectrosafe.proyekakhir.token", "");
                editor.putString("com.vectrosafe.proyekakhir.id_auth", "");
                editor.putString("com.vectrosafe.proyekakhir.password", "");
                editor.putString("com.vectrosafe.proyekakhir.username_auth", "");
                editor.apply();
                progressDialog.dismiss();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onBackPressed() {
        Intent intent =
                new Intent( ProfileActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id_auth", id_auth);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void initViewData(){
        progressDialog = new ProgressDialog(ProfileActivity.this);
        progressDialog.setMessage("Silahkan Tunggu...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Bundle bundle = getIntent().getExtras();
        getIntent().setAction("Already created");
        id_auth=bundle.getString("id_auth","");
        nama_nasabah=bundle.getString("nama_nasabah","");
        no_hp=bundle.getString("no_hp_nasabah","");
        no_rek=bundle.getString("no_rek_nasabah","");
        alamat=bundle.getString("alamat_nasabah","");
        tgl_lahir=bundle.getString("tgl_lahir_nasabah","");
        id_nasabah=bundle.getString("id_nasabah","");
        String tokenAuth = "Bearer "+token;
        Log.d("token", tokenAuth);
        Log.d("id", id_auth);
        Log.d("username", username_auth);
        progressDialog.dismiss();

        Log.d("nama", nama_nasabah);
        tv_nama.setText(nama_nasabah);
        tv_alamat.setText(alamat);
        tv_no_hp.setText(no_hp);
        tv_tgl_lahir.setText(tgl_lahir);
        tv_no_rek.setText(no_rek);
        tv_user.setText(username_auth);

    }
    protected void onResume() {
        Log.v("Example", "onResume");

        String action = getIntent().getAction();
        // Prevent endless loop by adding a unique action, don't restart if action is present
        if(action == null || !action.equals("Already created")) {
            Log.v("Example", "Force restart");
            SharedPreferences sharedPref = getSharedPreferences("com.vectrosafe.proyekakhir", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("com.vectrosafe.proyekakhir.token", "");
            editor.putString("com.vectrosafe.proyekakhir.id_auth", "");
            editor.putString("com.vectrosafe.proyekakhir.password", "");
            editor.putString("com.vectrosafe.proyekakhir.username_auth", "");
            editor.apply();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        // Remove the unique action so the next time onResume is called it will restart
        else
            getIntent().setAction(null);

        super.onResume();
    }

}
