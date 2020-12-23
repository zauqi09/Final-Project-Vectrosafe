package com.vectrosafe.proyekakhir;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        operator =bundle.getString("operator","");
        id_nasabah =bundle.getString("id_nasabah","");
        id_produk =bundle.getString("id_produk","");
        id_auth =bundle.getString("id_auth","");
        no_hp =bundle.getString("no_hp","");
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
                Toast.makeText(this,"Error Code : "+TransaksiResponse.getStatus()+" : Oops! Transaksi gagal, silahkan cobalagi!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PaymentRequireActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onBackPressed() {
        Intent intent =
                new Intent( PaymentRequireActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id_auth", id_auth);
        intent.putExtras(bundle);startActivity(intent);
    }

}
