package com.hendrik.aplikasilistanime;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    View fragment_view;
    ArrayList<AnimeList> animelist;;
    ProgressBar pb;
    SwipeRefreshLayout srl;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View rootView =  inflater.inflate(R.layout.fragment_first, container, false);
        fragment_view = rootView;
        pb = (ProgressBar) rootView.findViewById(R.id.progress_horizontal);
        srl = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                load();

                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        srl.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        srl.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        load();

        return rootView;
    }

    public void load(){
        pb.setVisibility(ProgressBar.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://hendrik-hk17940.000webhostapp.com/daftar_filmnime.php";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String idfilm, namafilm, tanggalrilis, status, episode, sinopsis;
                        animelist = new ArrayList<>();

                        try {
                            JSONArray jsonArray = response.getJSONArray("result");
                            animelist.clear();

                            if (jsonArray.length() != 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject data = jsonArray.getJSONObject(i);

                                    idfilm = data.getString("idfilm").toString().trim();
                                    namafilm = data.getString("namafilm").toString().trim();
                                    tanggalrilis = data.getString("tanggalrilis").toString().trim();
                                    status = data.getString("status").toString().trim();
                                    episode = data.getString("episode").toString().trim();
                                    sinopsis = data.getString("sinopsis").toString().trim();

                                    animelist.add(new AnimeList(idfilm, namafilm, tanggalrilis, status, episode, sinopsis ));
                                }

                                showRecyclerGrid();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        pb.setVisibility(ProgressBar.GONE);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.d("Events: ", error.toString());

                pb.setVisibility(ProgressBar.GONE);
                Toast.makeText(getContext(), "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsObjRequest);
    }

    private void showRecyclerGrid(){
        RecyclerView recyclerView = (RecyclerView) fragment_view.findViewById(R.id.rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        AnimeListAdapter mAdapter = new AnimeListAdapter(getContext(), animelist);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


}