package com.hendrik.aplikasilistanime;

public class AnimeList {
    String idfilm, namafilm, tanggalrilis, status, episode, sinopsis;
    public AnimeList(String idfilm, String namafilm,String tanggalrilis,String status,String episode,String sinopsis){
        this.idfilm = idfilm;
        this.namafilm = namafilm;
        this.tanggalrilis = tanggalrilis;
        this.status = status;
        this.episode = episode;
        this.sinopsis = sinopsis;
    }

    public String getId(){
        return idfilm;
    }
    public String getNama_film(){
        return namafilm;
    }
    public String getTanggal_rilis(){
        return tanggalrilis;
    }
    public String getStatus(){
        return status;
    }
    public String getEpisode(){
        return episode;
    }
    public String getSinopsis(){
        return sinopsis;
    }
}
