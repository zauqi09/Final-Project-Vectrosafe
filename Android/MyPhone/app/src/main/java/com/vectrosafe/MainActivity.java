package com.vectrosafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import com.vectrosafe.model.GetRequest;
import com.vectrosafe.model.NomorHP;
import com.vectrosafe.model.Response.ApiResponse;
import com.vectrosafe.viewmodels.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv_operator)
    TextView tv_operator;

    @BindView(R.id.tv_nomorhp)
    TextView tv_nomorhp;

    @BindView(R.id.tv_masa_aktif)
    TextView tv_masa_aktif;

    @BindView(R.id.tv_pulsa)
    TextView tv_pulsa;

    ProgressDialog progressDialog;
    String operator,nomorhp;
    UserViewModel userViewModel;
    NomorHP user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.init();
        initViewData();

    }

    void initViewData(){
        Bundle bundle = getIntent().getExtras();
        operator=bundle.getString("operator","");
        nomorhp=bundle.getString("no_hp","");
        System.out.println(operator+" "+nomorhp);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Silahkan Tunggu...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        userViewModel.getUser(new GetRequest(nomorhp,operator)).observe(this, userResponse -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progressDialog.dismiss();
            user = userResponse.getData();
            tv_masa_aktif.setText(user.getMasa_aktif().toString());
            tv_operator.setText(operator);
            tv_pulsa.setText(user.getPulsa().toString());
            tv_nomorhp.setText(nomorhp);
        });
    }

}