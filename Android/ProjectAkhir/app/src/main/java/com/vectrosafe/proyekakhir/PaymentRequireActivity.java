package com.vectrosafe.proyekakhir;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.vectrosafe.proyekakhir.model.Request.TransaksiRequest;
import com.vectrosafe.proyekakhir.viewmodels.TransaksiViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentRequireActivity extends AppCompatActivity {
    String token,password;
    String id_nasabah;
    ProgressDialog progressDialog;
    String operator;
    @BindView(R.id.bt_confirm_payment)
    MaterialButton bt_confirm_payment;

    @BindView(R.id.et_password_payment)
    EditText et_password_payment;
    String id_produk, no_hp, id_auth;
    TransaksiViewModel voucherViewModel;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = getSharedPreferences("com.vectrosafe.proyekakhir", Context.MODE_PRIVATE);
        token = "Bearer "+sharedPref.getString("com.vectrosafe.proyekakhir.token","-");
        password = sharedPref.getString("com.vectrosafe.proyekakhir.password","-");
        Log.d("token ",token);
        Log.d("password ",password);
        setTitle(token);
        setContentView(R.layout.activity_payment_requirements);
        voucherViewModel = ViewModelProviders.of(this).get(TransaksiViewModel .class);
        voucherViewModel.init();
        initData();
        ButterKnife.bind(this);
        onClickGroup();
        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {

            finish();
            return;
        }
    }

    private void initData(){
        Bundle bundle = getIntent().getExtras();
        getIntent().setAction("Already created");
        operator =bundle.getString("operator","");
        id_nasabah =bundle.getString("id_nasabah","");
        id_produk =bundle.getString("id_produk","");
        id_auth =bundle.getString("id_auth","");
        no_hp =bundle.getString("no_hp","");
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

    private void onClickGroup(){
        bt_confirm_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String et_password = et_password_payment.getText().toString();
                if (password.equals(et_password)){
                    doPayment(token);
                }
            }
        });
    }

    private void doPayment(String token){
        progressDialog = new ProgressDialog(PaymentRequireActivity.this);
        progressDialog.setMessage("Silahkan Tunggu...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        voucherViewModel.postTransaksiRepository(new TransaksiRequest(Long.parseLong(id_nasabah),operator,Long.parseLong(id_produk),no_hp),token).observe(this, TransaksiResponse -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (TransaksiResponse==null){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (TransaksiResponse.getStatus()==200){
                progressDialog.dismiss();
                System.out.println("Transaksi Berhasil!");
                Intent intent = new Intent(PaymentRequireActivity.this, SuccessActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id_auth", String.valueOf(id_auth));
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                progressDialog.dismiss();
                System.out.println("Transaksi Gagal!");
                showDialog("Oops! Transaksi gagal, silahkan cobalagi!");
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
                Intent intent = new Intent(PaymentRequireActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    public void onBackPressed() {
        Intent intent =
                new Intent( PaymentRequireActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id_auth", id_auth);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
