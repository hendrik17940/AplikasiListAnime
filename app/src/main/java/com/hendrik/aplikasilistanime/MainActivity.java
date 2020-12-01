package com.hendrik.aplikasilistanime;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText edit_ID, edit_Judul, edit_Tanggal_Rilis, edit_Status, edit_Episode, edit_Sinopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d("AnimeList", "onCreate: load main");
        Cons.ACTIVE_FRAGMENT="FirstFragment";
        loadFragment(new FirstFragment());
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
//                if ( Cons.ACTIVE_FRAGMENT.equals("FirstFragment") ) {

                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.form_input_anime_list_layout, null);
                dialog.setView(view);
                dialog.setCancelable(true);

                edit_ID = (EditText) view.findViewById(R.id.et_id);
                edit_Judul = (EditText) view.findViewById(R.id.et_judul);
                edit_Tanggal_Rilis = (EditText) view.findViewById(R.id.et_tanggal_rilis);
                edit_Episode = (EditText) view.findViewById(R.id.et_episode);
                edit_Status = (EditText) view.findViewById(R.id.et_status);
                edit_Sinopsis = (EditText) view.findViewById(R.id.et_sinopsis);


                dialog.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String ID, Judul, Tanggal_Rilis, Status, Episode, Sinopsis;

                        ID = edit_ID.getText().toString();
                        Judul = edit_Judul.getText().toString();
                        Tanggal_Rilis = edit_Tanggal_Rilis.getText().toString();
                        Status = edit_Status.getText().toString();
                        Episode = edit_Episode.getText().toString();
                        Sinopsis = edit_Sinopsis.getText().toString();

                        simpanlistanime(ID, Judul, Tanggal_Rilis, Status, Episode, Sinopsis);
                    }
                });

                dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    private void simpanlistanime(String ID, String Judul, String Tanggal_Rilis, String Status, String Episode, String Sinopsis) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://hendrik-hk17940.000webhostapp.com/daftar_filmnime.php?action=simpan&idfilm="+ ID +
                "&namafilm="+ Judul +"&tanggalrilis="+ Tanggal_Rilis +"&status="+ Status +"&episode="+ Episode +"&sinopsis="+ Sinopsis +"";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                String id, nama, telp;

                if (response.optString("result").equals("true")) {
                    Toast.makeText(getApplicationContext(), "Yeay, data bertambah!", Toast.LENGTH_SHORT).show();

                    loadFragment(new FirstFragment());
                } else {
                    Toast.makeText(getApplicationContext(), "O ow, sepertinya harus dicoba lagi!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Events: ", error.toString());

                Toast.makeText(getApplicationContext(), "Hmm, masalah internet mungkin kuota anda habis atau data yang dimasukkan salah, sepertinya harus dicoba lagi!", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            Log.d("AnimeList ", "onResume: "+ Cons.ACTIVE_FRAGMENT);

            if( Cons.ACTIVE_FRAGMENT.equals("FirstFragment")) {
                loadFragment(new FirstFragment());
            }else if( Cons.ACTIVE_FRAGMENT.equals("SecondFragment")){
                loadFragment(new SecondFragment());
            }
        }catch (Exception e){
            Log.d("AnimeList ", "onResume: "+ e.getMessage() );
        }
    }
    public void loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .addToBackStack(null)
                    .commit();
        };
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}