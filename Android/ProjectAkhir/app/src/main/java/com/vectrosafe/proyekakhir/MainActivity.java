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
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    String nama_nasabah,tgl_lahir, no_hp,alamat,no_rek;
    String token, id_nasabah, id_auth, username_auth;
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
        getIntent().setAction("Already created");
        id_auth=bundle.getString("id_auth","");
        username_auth=bundle.getString("username_auth","");
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
            if (apiResponse.getStatus()==200){
                nasabah = apiResponse.getData();
                nama_nasabah = nasabah.getNama_lengkap();
                String pattern = "MM-dd-yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                tgl_lahir = simpleDateFormat.format(nasabah.getTgl_lahir());
                no_hp = nasabah.getNo_hp();
                alamat = nasabah.getAlamat();
                no_rek=nasabah.getNo_rekening();
                id_nasabah = String.valueOf(nasabah.getId_nasabah());
                tv_nama.setText(nama_nasabah);
                tv_saldo.setText(kursIndonesia.format(nasabah.getSaldo()));
                tv_no_rekening.setText(no_rek);
            } else {
                ErrorDialog("Session telah habis, silahkan login kembali!");
            }
        });
    }

    private void ErrorDialog(String msg){
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
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Silahkan Tunggu...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                SharedPreferences sharedPref = getSharedPreferences("com.vectrosafe.proyekakhir", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("com.vectrosafe.proyekakhir.token", "");
                editor.putString("com.vectrosafe.proyekakhir.id_auth", "");
                editor.putString("com.vectrosafe.proyekakhir.password", "");
                editor.apply();
                progressDialog.dismiss();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
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
                bundle.putString("id_auth", id_auth);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        iv_nasabah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id_auth",id_auth);
                bundle.putString("username_auth",username_auth);
                bundle.putString("nama_nasabah",nama_nasabah);
                bundle.putString("no_hp_nasabah",no_hp);
                bundle.putString("no_rek_nasabah",no_rek);
                bundle.putString("alamat_nasabah",alamat);
                bundle.putString("tgl_lahir_nasabah",tgl_lahir);
                bundle.putString("id_nasabah",id_nasabah);
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
                bundle.putString("id_auth", id_auth);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    @Override

    public void onBackPressed() {
        showDialog();
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
                        System.exit(1);
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

    @Override
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