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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.vectrosafe.proyekakhir.model.Nasabah;
import com.vectrosafe.proyekakhir.viewmodels.UserViewModel;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    String token, id_nasabah, id_auth;
    TextView tv_nama, tv_no_rekening, tv_saldo;
    MaterialButton bt_pulsa;
    ImageView iv_nasabah,iv_history;
    Nasabah nasabah;
    UserViewModel userViewModel;
    ProgressDialog progressDialog;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = getSharedPreferences("com.vectrosafe.proyekakhir", Context.MODE_PRIVATE);
        token = sharedPref.getString("com.vectrosafe.proyekakhir.token","-");
        Log.d("token ",token);
        setTitle(token);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.init();
        initViewData();
        onClickGroup();
        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {
            finish();
        }
    }

    public void initViewData(){
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        bt_pulsa=(MaterialButton) findViewById(R.id.bt_pulsa);
        tv_nama=(TextView) findViewById(R.id.tv_nama);
        tv_no_rekening=(TextView) findViewById(R.id.tv_no_rekening);
        tv_saldo=(TextView) findViewById(R.id.tv_saldo);
        iv_history=(ImageView) findViewById(R.id.history);
        iv_nasabah=(ImageView) findViewById(R.id.nasabah);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Silahkan Tunggu...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Bundle bundle = getIntent().getExtras();
        id_auth=bundle.getString("id_auth","");
        String tokenAuth = "Bearer "+token;
        Log.d("token", tokenAuth);
        Log.d("id", id_auth);
        userViewModel.getNasabah(id_auth,tokenAuth).observe(this, apiResponse->{
            while (apiResponse.getData()==null){
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            progressDialog.dismiss();
            nasabah = apiResponse.getData();
            id_nasabah = String.valueOf(nasabah.getId_nasabah());

            Log.d("nama", nasabah.getNama_lengkap());
            tv_nama.setText(nasabah.getNama_lengkap());
            tv_saldo.setText(kursIndonesia.format(nasabah.getSaldo()));
            tv_no_rekening.setText(nasabah.getNo_rekening());

        });
    }


    public void onClickGroup(){
        bt_pulsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PulsaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id_nasabah", id_nasabah);
                bundle.putString("id_auth", id_auth);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        iv_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TransaksiActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id_nasabah", id_nasabah);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    @Override

    public void onBackPressed() {
        showDialog();
    }
    public void onDestroy(){
        super.onDestroy();
    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Keluar dari aplikasi?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Ya untuk keluar!")
                .setIcon(R.mipmap.ic_vectrosafe_round)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        onDestroy();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

}