package com.vectrosafe.proyekakhir.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.vectrosafe.proyekakhir.R;
import com.vectrosafe.proyekakhir.model.Transaksi;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder> {

    Context context;
    ArrayList<Transaksi> transaksi;

    public TransaksiAdapter(Context context, ArrayList<Transaksi> transaksi) {
        this.context = context;
        this.transaksi = transaksi;
    }

    @NonNull
    @Override
    public TransaksiAdapter.TransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaksi, parent, false);
        return new  TransaksiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiAdapter.TransaksiViewHolder holder, int position) {
        holder.no_transaksi.setText(transaksi.get(position).getNo_transaksi().toString());
        holder.tv_tipe.setText(transaksi.get(position).getTipe());
        holder.tv_mutasi.setText(transaksi.get(position).getMutasi().toString());
        holder.tv_saldo.setText(transaksi.get(position).getSaldo().toString());
        holder.tv_waktu.setText(transaksi.get(position).getWaktu().toString());
        holder.tv_keterangan.setText(transaksi.get(position).getKeterangan());
//        Picasso.get().load(transaksi.get(position).getUrlToImage()).into(holder.nasabahIv);
        holder.TrxlL.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),transaksi.get(position).getNo_transaksi().toString(),Toast.LENGTH_SHORT).show();
            };
        });

    }

    @Override
    public int getItemCount() {
        return transaksi.size();
    }

    public class TransaksiViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.no_transaksi)
        TextView no_transaksi;


        @BindView(R.id.tv_tipe)
        TextView tv_tipe;

        @BindView(R.id.tv_mutasi)
        TextView tv_mutasi;

        @BindView(R.id.tv_saldo)
        TextView tv_saldo;

        @BindView(R.id.tv_waktu)
        TextView tv_waktu;

        @BindView(R.id.tv_keterangan)
        TextView tv_keterangan;

        @BindView(R.id.transaksiLl)
        LinearLayout TrxlL;
        public TransaksiViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
