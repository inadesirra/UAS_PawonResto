package com.example.pawonresto.adapter;

import static com.android.volley.Request.Method.DELETE;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pawonresto.Api.ApiPenawaran;
import com.example.pawonresto.R;
import com.example.pawonresto.model.Penawaran;
import com.example.pawonresto.ui.penawaran.AddEditPenawaran;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterPenawaran extends RecyclerView.Adapter<AdapterPenawaran.adapterUserViewHolder> {

    private List<Penawaran> penawaranList;
    private List<Penawaran> penawaranListFiltered;
    private Context context;
    private View view;
    private AdapterPenawaran.deleteItemListener pListener;

    public AdapterPenawaran(Context context, List<Penawaran> penawaranList, AdapterPenawaran.deleteItemListener pListener) {
        this.context = context;
        this.penawaranList = penawaranList;
        this.penawaranListFiltered = penawaranList;
        this.pListener = pListener;
    }

    public interface deleteItemListener {
        void deleteItem( Boolean delete);
    }

    @NonNull
    @Override
    public adapterUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.item_penawaran, parent, false);
        return new adapterUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterUserViewHolder holder, int position) {
        final Penawaran penawaran = penawaranListFiltered.get(position);

        holder.txtJudul.setText(penawaran.getJudul());
        holder.txtDeskripsi.setText(penawaran.getDeskripsi());
        Glide.with(context)
                .load(penawaran.getImgURL())
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.ivGambar);

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Bundle data = new Bundle();
                data.putSerializable("penawaran", penawaran);
                data.putString("status", "edit");
                AddEditPenawaran addEditPenawaran = new AddEditPenawaran();
                addEditPenawaran.setArguments(data);
                loadFragment(addEditPenawaran);
            }
        });

        holder.ivHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Anda yakin ingin menghapus penawaran ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMahasiswa(penawaran.getJudul());

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notifyDataSetChanged();
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (penawaranListFiltered != null) ? penawaranListFiltered.size() : 0;
    }

    public class adapterUserViewHolder extends RecyclerView.ViewHolder {
        private TextView txtJudul, txtDeskripsi;
        private ImageButton ivEdit, ivHapus;
        private ImageView ivGambar;

        public adapterUserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtJudul = (TextView) itemView.findViewById(R.id.tv_judulPenawaran);
            txtDeskripsi = (TextView) itemView.findViewById(R.id.tv_descPenawaran);
            ivGambar = (ImageView) itemView.findViewById(R.id.ivGambar);
            ivEdit = (ImageButton) itemView.findViewById(R.id.btn_edit);
            ivHapus  = (ImageButton) itemView.findViewById(R.id.btn_delete);
        }
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String userInput = charSequence.toString().toLowerCase();
                if (userInput.isEmpty()) {
                    penawaranListFiltered = penawaranList;
                }
                else {
                    List<Penawaran> filteredList = new ArrayList<>();
                    for(Penawaran penawaran : penawaranList) {
                        if(penawaran.getJudul().toLowerCase().contains(userInput) ||
                                penawaran.getDeskripsi().toLowerCase().contains(userInput)) {
                            filteredList.add(penawaran);
                        }
                    }
                    penawaranListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = penawaranListFiltered;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                penawaranListFiltered = (ArrayList<Penawaran>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    //Fungsi menghapus data mahasiswa
    public void deleteMahasiswa(String npm){
        //Pendeklarasian queue
        RequestQueue queue = Volley.newRequestQueue(context);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menghapus data penawaran");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Memulai membuat permintaan request menghapus data ke jaringan
        StringRequest stringRequest = new StringRequest(DELETE, ApiPenawaran.URL_DELETE + npm, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    //Mengubah response string menjadi object
                    JSONObject obj = new JSONObject(response);
                    //obj.getString("message") digunakan untuk mengambil pesan message dari response
                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    pListener.deleteItem(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }

    public void loadFragment(Fragment fragment) {
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_fragment_penawaran,fragment)
                .addToBackStack(null)
                .commit();
    }
}