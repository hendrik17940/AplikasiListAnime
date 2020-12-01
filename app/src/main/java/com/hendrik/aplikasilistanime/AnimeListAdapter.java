package com.hendrik.aplikasilistanime;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class AnimeListAdapter extends RecyclerView.Adapter<AnimeListAdapter.GridviewHolder> {
    private List<AnimeList> animeLists;
    private Context context;

    public AnimeListAdapter(Context context, List<AnimeList> animeLists){
        this.animeLists = animeLists;
        this.context = context;
    }

    public GridviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View AnimeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.anime_list_item_layout, parent, false);
        GridviewHolder viewHolder = new GridviewHolder(AnimeView);
        return viewHolder;
    }

    public void onBindViewHolder(AnimeListAdapter.GridviewHolder holder, int position){
        final int pos = position;
        final String idfilm = animeLists.get(position).getId();
        final String nama_anime = animeLists.get(position).getNama_film();
        final String tanggal_rilis = animeLists.get(position).getTanggal_rilis();
        final String status = animeLists.get(position).getStatus();
        final String episode = animeLists.get(position).getEpisode();
        final String sinopsis = animeLists.get(position).getSinopsis();

        holder.idfilm.setText(idfilm);
        holder.namafilm.setText(nama_anime);
        holder.tanggalrilis.setText(tanggal_rilis);
        holder.status.setText(status);
        holder.episode.setText(episode);
        holder.sinopsis.setText(sinopsis);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(context);
                alertDialog.setTitle("Detail Anime");
                alertDialog.setMessage("ID ANIME" + "\n" + idfilm +"\n\n"+ "Judul" + "\n" + nama_anime + "\n\n" + "Tanggal Rilis" + "\n" + tanggal_rilis + "\n\n" + "Status" + "\n" + status + "\n\n" + "Episode" + "\n" + episode + "\n\n" + "Sinopsis" + "\n" + sinopsis);
                alertDialog.setPositiveButton("BATAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.setNegativeButton("LIHAT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Bundle b = new Bundle();
                        b.putString("b_idfilm", idfilm);
                        b.putString("b_namaanime", nama_anime);
                        b.putString("b_tanggalrilis", tanggal_rilis);
                        b.putString("b_status", status);
                        b.putString("b_episode", episode);
                        b.putString("b_sinopsis", sinopsis);

                        Intent intent = new Intent(context, detailanime.class);
                        intent.putExtras(b);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            ((Activity) context).startActivityForResult(intent, 1,b);
                        }
                    }
                });

                alertDialog.setNeutralButton("HAPUS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RequestQueue queue = Volley.newRequestQueue(context);
                        String url = "https://hendrik-hk17940.000webhostapp.com/daftar_filmnime.php?action=hapus&idafilm="+ idfilm + "";

                        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                                Request.Method.POST,
                                url,
                                null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
//                                        String id, nama, telp;

                                        if (response.optString("result").equals("true")){
                                            Toast.makeText(context, "Data terhapus!", Toast.LENGTH_SHORT).show();

                                            animeLists.remove(pos);
                                            notifyItemRemoved(pos);
                                            notifyDataSetChanged();

                                        }else{
                                            Toast.makeText(context, "O ow, sepertinya harus dicoba lagi", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                Log.d("Events: ", error.toString());

                                Toast.makeText(context, "Hmm, masalah internet atau data yang kamu masukkan", Toast.LENGTH_SHORT).show();
                            }
                        });

                        queue.add(jsObjRequest);
                    }
                });
                androidx.appcompat.app.AlertDialog dialog = alertDialog.create();
                dialog.show();
                dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorAccent));
                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(context.getResources().getColor(R.color.colorPrimary));
            }
        });
    }

    @Override
    public int getItemCount() {
        return animeLists.size();
    }

    public class GridviewHolder extends RecyclerView.ViewHolder{
        TextView idfilm ,namafilm, tanggalrilis, status, episode, sinopsis;

        public GridviewHolder(View animeView) {
            super(animeView);

            idfilm = (TextView) animeView.findViewById(R.id.id_anime);
            namafilm = (TextView) animeView.findViewById(R.id.nama_film);
            tanggalrilis = (TextView) animeView.findViewById(R.id.tanggal_rilis);
            status = (TextView) animeView.findViewById(R.id.status);
            episode = (TextView) animeView.findViewById(R.id.episode);
            sinopsis = (TextView) animeView.findViewById(R.id.sinopsis);
        }
    }

}