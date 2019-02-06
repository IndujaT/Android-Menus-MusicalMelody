package com.example.induja.musicalmelodies;

/**
 * Created by Induja on 2/25/2018.
 */

public class SongItem {
    String SongTitle;
    String BandName;
    String WikiUrl;
    String VideoUrl;
    String BandWiki;

    public String getSongTitle(){
        return SongTitle;
    }
    public void setSongTitle(String songTitle){
        SongTitle = songTitle;
    }

    public String getBandName(){
        return BandName;
    }
    public void setBandName(String bandName){
        BandName = bandName;
    }

    public String getWikiUrl(){
        return  WikiUrl;
    }
    public void setWikiUrl(String wikiUrl){
        WikiUrl = wikiUrl;
    }

    public String getVideoUrl(){
        return VideoUrl;
    }
    public void setVideoUrl(String videoUrl){
        VideoUrl = videoUrl;
    }

    public String getBandWiki(){
        return BandWiki;
    }
    public void setBandWiki(String bandWiki){
        BandWiki = bandWiki;
    }

    public SongItem(String songTitle, String bandName, String wikiUrl, String videoUrl, String bandWiki){
        SongTitle = songTitle;
        BandName = bandName;
        WikiUrl = wikiUrl;
        VideoUrl = videoUrl;
        BandWiki = bandWiki;
    }
}
