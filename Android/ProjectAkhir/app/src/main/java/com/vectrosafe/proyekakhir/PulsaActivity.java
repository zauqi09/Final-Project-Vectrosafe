package com.vectrosafe.proyekakhir;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.vectrosafe.proyekakhir.adapter.VoucherAdapter;
import com.vectrosafe.proyekakhir.model.Voucher;
import com.vectrosafe.proyekakhir.networking.VoucherApi;
import com.vectrosafe.proyekakhir.util.TextValidator;
import com.vectrosafe.proyekakhir.viewmodels.UserViewModel;
import com.vectrosafe.proyekakhir.viewmodels.VoucherViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PulsaActivity extends AppCompatActivity {
    @BindView(R.id.et_no_hp)
    EditText et_no_hp;
    String id_auth,no_hp,id_nasabah;
    VoucherViewModel voucherViewModel;
    VoucherAdapter voucherAdapter;
    ProgressDialog progressDialog;
    @BindView(R.id.listpulsa)
    RecyclerView listpulsa;
    ArrayList<Voucher> voucherArrayList = new ArrayList<>();
    List<Voucher> vouchers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulsa);
        voucherViewModel = ViewModelProviders.of(this).get(VoucherViewModel.class);
        voucherViewModel.init();
        ButterKnife.bind(this);
        initData();
        EventGroup();
        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {

            finish();
            return;
        }
    }

    void initData(){
        Bundle bundle = getIntent().getExtras();
        id_nasabah=bundle.getString("id_nasabah","");
        id_auth=bundle.getString("id_auth","");
        if (voucherAdapter == null) {
            voucherAdapter = new VoucherAdapter(PulsaActivity.this, voucherArrayList);
            listpulsa.setLayoutManager(new GridLayoutManager(this,2));

            listpulsa.setAdapter(voucherAdapter);
            voucherAdapter.notifyDataSetChanged();
            //rvProduct.setItemAnimator(new DefaultItemAnimator());
        } else {
            voucherAdapter.notifyDataSetChanged();
        }
    }

    void EventGroup(){
        et_no_hp.addTextChangedListener(new TextValidator(et_no_hp) {
            @Override public void validate(EditText et_nohp, String text) {
                /* Insert your validation rules here */
                no_hp = et_no_hp.getText().toString();

                if (no_hp.length()>4){
                    String ident_no_hp = no_hp.substring(0, 4);
                    if (ident_no_hp.equals("0811")||ident_no_hp.equals("0812")||ident_no_hp.equals("0813")||ident_no_hp.equals("0822")||ident_no_hp.equals("0821")) {
                        et_no_hp.setError("Telkomsel detected");
                        if (no_hp.length()>11){
                            getListPulsaTsel();
                            voucherAdapter.Add_NoHP(no_hp);
                            voucherAdapter.Add_IdNasabah(id_nasabah);
                            voucherAdapter.Add_IdAuth(id_auth);
                        }
                    } else if (ident_no_hp.equals("0817")||ident_no_hp.equals("0818")||ident_no_hp.equals("0819")||ident_no_hp.equals("0859")||ident_no_hp.equals("0878")) {
                        et_no_hp.setError("XL detected");
                        if (no_hp.length()>11){
                            getListPulsaXL();
                            voucherAdapter.Add_NoHP(no_hp);
                            voucherAdapter.Add_IdNasabah(id_nasabah);
                            voucherAdapter.Add_IdAuth(id_auth);
                        }
                    }
                }

                if (et_no_hp.getText().toString().length()<=10) {
                    et_no_hp.setError("Mohon masukkan no handphone anda yang benar.");
                }

            }
        });
    }

    private void getListPulsaXL(){
        voucherViewModel.refreshXL();
        voucherViewModel.getXL().observe(this, voucherResponse -> {
            while (voucherResponse==null){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            vouchers = voucherResponse.getData();
            voucherArrayList.clear();
            voucherArrayList.addAll(vouchers);
            voucherAdapter.notifyDataSetChanged();
        });
    }

    private void getListPulsaTsel(){
        voucherViewModel.refreshTSEL();
        voucherViewModel.getTsel().observe(this, voucherResponse -> {
            while (voucherResponse==null){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            vouchers = voucherResponse.getData();
            for (Voucher obj: vouchers){
                System.out.println(obj.getNama_produk());
            }
            voucherArrayList.clear();
            voucherArrayList.addAll(vouchers);
            voucherAdapter.notifyDataSetChanged();
        });
    }
    public void onBackPressed() {
        Intent intent =
                new Intent( PulsaActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id_auth", id_auth);
        intent.putExtras(bundle);startActivity(intent);
    }
}
