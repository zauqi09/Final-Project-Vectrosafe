package com.vectrosafe.proyekakhir;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vectrosafe.proyekakhir.R;
import com.vectrosafe.proyekakhir.adapter.TransaksiAdapter;
import com.vectrosafe.proyekakhir.model.Transaksi;
import com.vectrosafe.proyekakhir.util.TextValidator;
import com.vectrosafe.proyekakhir.viewmodels.TransaksiViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiActivity extends AppCompatActivity {
    ArrayList<Transaksi> transaksiArrayList = new ArrayList<>();
    TransaksiAdapter transaksiAdapter;
    String tgl_awal,tgl_akhir;
    @BindView(R.id.TrxRecyclerView)
    RecyclerView rvTransaksi;

    @BindView(R.id.et_tgl_awal)
    EditText et_tgl_awal;

    @BindView(R.id.et_tgl_akhir)
    EditText et_tgl_akhir;

    @BindView(R.id.bt_tglawal)
    Button bt_tglawal;

    String id_nasabah,token,id_auth;

    @BindView(R.id.bt_tgl_akhir)
    Button bt_tgl_akhir;

    @BindView(R.id.submit_mutasi)
    Button submit_mutasi;

    @BindView(R.id.tv_back_mutasi)
    ImageView iv_back_mutasi;

    TransaksiViewModel transaksiViewModel;

    List<Transaksi> transaksis;
    final Calendar myCalendar = Calendar.getInstance();

    ProgressDialog progressDialog;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutasi);
        sharedPref = getSharedPreferences("com.vectrosafe.proyekakhir", Context.MODE_PRIVATE);
        token = "Bearer "+sharedPref.getString("com.vectrosafe.proyekakhir.token","-");
        ButterKnife.bind(this);
        initData();
        onEventGroup();

        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {

            finish();
            return;
        }
    }


    private void initData() {
        Bundle bundle = getIntent().getExtras();
        getIntent().setAction("Already created");
        id_nasabah=bundle.getString("id_nasabah","");
        id_auth=bundle.getString("id_auth","");
        Log.d("id_auth", "initData: "+id_auth);
        if (transaksiAdapter == null) {
            transaksiAdapter = new TransaksiAdapter(TransaksiActivity.this, transaksiArrayList);
            rvTransaksi.setLayoutManager(new LinearLayoutManager(this));
            rvTransaksi.setAdapter(transaksiAdapter);
            rvTransaksi.setItemAnimator(new DefaultItemAnimator());
            rvTransaksi.setNestedScrollingEnabled(true);
            transaksiAdapter.notifyDataSetChanged();
        } else {
            transaksiAdapter.notifyDataSetChanged();
        }
        transaksiViewModel = ViewModelProviders.of(this).get(TransaksiViewModel.class);

        transaksiViewModel.init();
    }
    private void getListTransaksi(String id_nasabah, String tgl_awal, String tgl_akhir, String token ){

        transaksiViewModel.refreshMutasi(id_nasabah,tgl_awal,tgl_akhir, token);
        transaksiViewModel.getTransaksiRepository().observe(this, transaksisResponse -> {
            while (transaksisResponse==null){
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (transaksisResponse.getStatus()==200){
                progressDialog.dismiss();
                transaksis = transaksisResponse.getData();
                transaksiArrayList.clear();
                transaksiArrayList.addAll(transaksis);
                transaksiAdapter.notifyDataSetChanged();
            } else {
                showDialog("Oops! Error memuat data");
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
                Intent intent = new Intent(TransaksiActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    void onEventGroup(){
        iv_back_mutasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        bt_tgl_akhir.setOnClickListener(new View.OnClickListener() {
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "yyyy/MM/dd"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    et_tgl_akhir.setText(sdf.format(myCalendar.getTime()));
                }

            };

            @Override
            public void onClick(View v) {
                new DatePickerDialog(TransaksiActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        bt_tglawal.setOnClickListener(new View.OnClickListener() {
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "yyyy/MM/dd"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    et_tgl_awal.setText(sdf.format(myCalendar.getTime()));
                }

            };

            @Override
            public void onClick(View v) {
                new DatePickerDialog(TransaksiActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        submit_mutasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tgl_awal = et_tgl_awal.getText().toString();
                tgl_akhir = et_tgl_akhir.getText().toString();
                if ((!tgl_awal.equals("")) && (!tgl_akhir.equals(""))){
                    try {
                        System.out.println("if equals");
                        Date tgl_awal_temp=new SimpleDateFormat("yyyy/MM/dd").parse(tgl_awal);
                        Date datenow = new Date();
                        Date tgl_akhir_temp=new SimpleDateFormat("yyyy/MM/dd").parse(tgl_akhir);
                        Long thirtyday = 2592000000L;
                        Long selisih = tgl_akhir_temp.getTime() - tgl_awal_temp.getTime();
                        if (tgl_awal_temp.getTime() == datenow.getTime() ){
                            Toast.makeText(getApplicationContext(),"tanggal awal tidak boleh pada hari ini", Toast.LENGTH_LONG).show();
                        }
                        else if (tgl_awal_temp.getTime() >= datenow.getTime()){
                            Toast.makeText(getApplicationContext(),"tanggal awal tidak boleh lebih dari hari ini", Toast.LENGTH_LONG).show();
                        }
//                        else if (selisih > thirtyday){
//                            System.out.println(selisih);
//                            System.out.println(thirtyday);
//                            Toast.makeText(getApplicationContext(),"selisih tanggal pertama dan kedua tidak boleh lebih dari 30 hari", Toast.LENGTH_LONG).show();
//                        }
                        else {
                            progressDialog = new ProgressDialog(TransaksiActivity.this);
                            progressDialog.setMessage("Silahkan Tunggu...");
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressDialog.show();
                            String input_tgl_awal = tgl_awal.replace('/', '-');
                            String input_tgl_akhir = tgl_akhir.replace('/', '-');
                            getListTransaksi(String.valueOf(id_nasabah),input_tgl_awal,input_tgl_akhir,token);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(),"Tanggal masih kosong, mohon isi untuk melihat data", Toast.LENGTH_LONG).show();
                }


            }
        });

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
    public void onBackPressed() {
        Intent intent =
                new Intent( TransaksiActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        Log.d("id_auth", "onBackPressed: "+id_auth);
        bundle.putString("id_auth", id_auth);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}

