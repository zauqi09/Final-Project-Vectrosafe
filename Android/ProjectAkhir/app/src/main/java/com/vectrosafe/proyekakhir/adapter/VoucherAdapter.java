package com.vectrosafe.proyekakhir.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vectrosafe.proyekakhir.PaymentRequireActivity;
import com.vectrosafe.proyekakhir.R;
import com.vectrosafe.proyekakhir.model.Voucher;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.ProductViewHolder> {
    Context context;
    ArrayList<Voucher> products;
    String no_hp,id_nasabah,id_auth;
    public VoucherAdapter(Context context, ArrayList<Voucher> products) {
        this.context = context;
        this.products = products;
    }
    public void Add_NoHP(String no_hp){
        this.no_hp= no_hp;
    }
    public void Add_IdAuth(String id_auth){
        this.id_auth= id_auth;
    }
    public void Add_IdNasabah(String id){
        this.id_nasabah= id;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new  ProductViewHolder(view);
    }

    String kursIndo(Long rp){
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        String strRp = kursIndonesia.format(rp);
        return strRp;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.nominalTextView.setText(String.valueOf(products.get(position).getNama_produk()));

        holder.hargaTextView.setText("Harga : "+kursIndo(products.get(position).getHarga_produk()));
        Log.d("nominal", "onBindViewHolder: "+ products.get(position).getNama_produk());holder.productRl.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),products.get(position).getNama_produk().toString(), Toast.LENGTH_SHORT).show();
                Intent intent =
                        new Intent( context, PaymentRequireActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("no_hp", no_hp);
                bundle.putString("operator", products.get(position).getOperator());
                bundle.putString("id_auth", id_auth);
                Log.d("id_nasabah", "onClick: "+id_nasabah);
                bundle.putString("id_nasabah", id_nasabah);
                bundle.putString("id_produk", products.get(position).getId_produk().toString());
                bundle.putString("code", products.get(position).getNama_produk());
                bundle.putString("price", products.get(position).getHarga_produk().toString());
                intent.putExtras(bundle);
                context.startActivity(intent);
            };
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.nominalTextView)
        TextView nominalTextView;
        @BindView(R.id.HargaTextView)
        TextView hargaTextView;
        @BindView(R.id.nominalRl)
        RelativeLayout productRl;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
