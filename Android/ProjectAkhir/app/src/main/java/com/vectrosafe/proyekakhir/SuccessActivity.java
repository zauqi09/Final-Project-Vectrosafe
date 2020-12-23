package com.vectrosafe.proyekakhir;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.vectrosafe.proyekakhir.viewmodels.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SuccessActivity extends AppCompatActivity {
    String id_auth;
    @BindView(R.id.bt_confirm_payment)
    MaterialButton bt_back;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        id_auth =bundle.getString("id_auth","");
        onClickGroup();
        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {

            finish();
            return;
        }
    }

    private void onClickGroup() {
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id_auth", String.valueOf(id_auth));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    public void onBackPressed() {
        Intent a = new Intent(SuccessActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id_auth", id_auth);
        a.putExtras(bundle);
        startActivity(a);
    }
}
