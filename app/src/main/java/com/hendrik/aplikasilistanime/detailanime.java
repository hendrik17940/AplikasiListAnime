package com.hendrik.aplikasilistanime;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

public class detailanime extends AppCompatActivity {
    ProgressBar pb;
    EditText et_id,et_judul,et_tanggal_rilis ,et_episode,et_status,et_sinopsis;
    Button bt_hapus, bt_ubah;
    String idfilm, namafilm, tanggalrilis,status,episode,sinopsis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_anime);

        pb = (ProgressBar) findViewById(R.id.pb);
        et_id =(EditText) findViewById(R.id.et_id);
        et_judul =(EditText) findViewById(R.id.et_judul);
        et_tanggal_rilis =(EditText) findViewById(R.id.et_tanggal_rilis);
        et_episode =(EditText) findViewById(R.id.et_episode);
        et_status =(EditText) findViewById(R.id.et_status);
        et_sinopsis =(EditText) findViewById(R.id.et_sinopsis);
        bt_ubah = (Button) findViewById(R.id.bt_ubah);
        bt_hapus = (Button) findViewById(R.id.bt_hapus);

        Bundle bundle = null;
        bundle = this.getIntent().getExtras();

        idfilm = bundle.getString("b_idfilm");
        namafilm = bundle.getString("b_namafilm");
        tanggalrilis = bundle.getString("b_tanggal_rilis");
        status = bundle.getString("b_status");
        episode = bundle.getString("b_episode");
        sinopsis = bundle.getString("b_sinopsis");

        et_id.setText(idfilm);
        et_judul.setText(namafilm);
        et_tanggal_rilis.setText(tanggalrilis);
        et_status.setText(status);
        et_episode.setText(episode);
        et_sinopsis.setText(sinopsis);

        bt_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idfilm = et_id.getText().toString();
                namafilm = et_judul.getText().toString();
                tanggalrilis = et_tanggal_rilis.getText().toString();
                status = et_status.getText().toString();
                episode = et_episode.getText().toString();
                sinopsis = et_sinopsis.getText().toString();

                pb.setVisibility(ProgressBar.VISIBLE); //munculkan progressbar

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://hendrik-hk17940.000webhostapp.com/daftar_filmnime.php?action=ubah&idafilm="+idfilm+"&namafilm="+namafilm+"&tanggalrilis="+tanggalrilis+"&status="+status+"&episode="+episode+"&sinopsis="+sinopsis;


                JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                if (response.optString("result").equals("true")){
                                    Toast.makeText(getApplicationContext(), "Data"+ idfilm+"terubah!", Toast.LENGTH_SHORT).show();

                                    finish(); //tutup activity
                                }else{
                                    Toast.makeText(getApplicationContext(), "O ow, sepertinya harus dicoba lagi", Toast.LENGTH_SHORT).show();
                                    pb.setVisibility(ProgressBar.GONE); //sembunyikan progress bar
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("Events: ", error.toString());

                        Toast.makeText(getApplicationContext(), "Hmm, masalah internet atau data yang kamu masukkan", Toast.LENGTH_SHORT).show();

                        pb.setVisibility(ProgressBar.GONE); //sembunyikan progress bar
                    }
                });

                queue.add(jsObjRequest);
            }
        });

        bt_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(ProgressBar.VISIBLE); //tampilkan progress bar

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://hendrik-hk17940.000webhostapp.com/daftar_filmnime.php?action=hapus&idafilm="+ idfilm;

                JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                if (response.optString("result").equals("true")){
                                    Toast.makeText(getApplicationContext(), "Data terhapus!", Toast.LENGTH_SHORT).show();

                                    finish(); //tutup activity
                                }else{
                                    Toast.makeText(getApplicationContext(), "O ow, sepertinya harus dicoba lagi", Toast.LENGTH_SHORT).show();
                                    pb.setVisibility(ProgressBar.GONE); //sembunyikan progress bar
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("Events: ", error.toString());

                        pb.setVisibility(ProgressBar.GONE); //sembunyikan progress bar

                        Toast.makeText(getApplicationContext(), "Hmm, masalah internet atau data yang kamu masukkan", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(jsObjRequest);
            }
        });
    }

}

