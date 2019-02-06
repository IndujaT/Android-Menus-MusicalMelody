package com.example.induja.musicalmelodies;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<SongItem> songList = new ArrayList<SongItem>();
    ListView listView;
    ArrayAdapter<SongItem> adapter;

    ArrayList<String> remove_list = new ArrayList<>();
    ListView remove_list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SongItem si1 = new SongItem("Comfortably Numb", "Pink Floyd", "https://en.wikipedia.org/wiki/Comfortably_Numb", "https://www.youtube.com/watch?v=LTseTg48568", "https://en.wikipedia.org/wiki/Pink_Floyd");
        SongItem si2 = new SongItem("Wish You Were Here", "Pink Floyd", "https://en.wikipedia.org/wiki/Wish_You_Were_Here_(Pink_Floyd_album)", "https://www.youtube.com/watch?v=3j8mr-gcgoI", "https://en.wikipedia.org/wiki/Pink_Floyd");
        SongItem si3 = new SongItem("Shape of You", "Ed Sheeran", "https://en.wikipedia.org/wiki/Shape_of_You", "https://www.youtube.com/watch?v=JGwWNGJdvx8", "https://en.wikipedia.org/wiki/Ed_Sheeran");
        SongItem si4 = new SongItem("So Far Away", "Avenged Sevenfold", "https://en.wikipedia.org/wiki/So_Far_Away_(Avenged_Sevenfold_song)", "https://www.youtube.com/watch?v=A7ry4cx6HfY", "https://en.wikipedia.org/wiki/Avenged_Sevenfold");
        SongItem si5 = new SongItem("Sweet Child O' Mine", "Guns 'N Roses", "https://en.wikipedia.org/wiki/Sweet_Child_o%27_Mine", "https://www.youtube.com/watch?v=bRfc_Y_AsLo", "https://en.wikipedia.org/wiki/Guns_N%27_Roses");
        SongItem si6 = new SongItem("Dear God","Avenged Sevenfold","https://en.wikipedia.org/wiki/Avenged_Sevenfold_(album)","https://www.youtube.com/watch?v=mzX0rhF8buo","https://en.wikipedia.org/wiki/Avenged_Sevenfold");

        songList.add(si1);
        songList.add(si2);
        songList.add(si3);
        songList.add(si4);
        songList.add(si5);
        songList.add(si6);

        listView = (ListView) findViewById(R.id.listView);
        registerForContextMenu(listView);              /*Specifying that there is a Context menu attached to this list - long press : onCreateContextMenu and onContextItemSelected are overridden*/

        adapter = new ArrayAdapter<SongItem>(this, android.R.layout.simple_list_item_2, android.R.id.text1, songList) {   /*pre defined*/
            @Override
            public View getView(int position,View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(songList.get(position).getSongTitle());
                text2.setText(songList.get(position).getBandName());

                text1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,25);
                text2.setTextSize(20);
                text2.setTypeface(null, Typeface.ITALIC);

                return view;
            }

        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String value = songList.get(i).getSongTitle();
                Toast.makeText(getApplicationContext(),"Opening Video for the song " +value,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("video_url",songList.get(i).getVideoUrl());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        menu.setHeaderTitle("Choose an action");
        /*menu.add(0,v.getId(),0,"View the song Video");
        menu.add(0,v.getId(),0,"View the Wikipedia page of the song");
        menu.add(0,v.getId(),0,"View the artist/band wikipedia page");*/
        if(v.getId() == R.id.listView){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.context_menu_list,menu);
            //getMenuInflater().inflate(R.menu.context_menu_list, menu);
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem selectedItem){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) selectedItem.getMenuInfo();
        int index = info.position;

        switch(selectedItem.getItemId()){
            case R.id.view_video:
                Toast.makeText(getApplicationContext(),"Opening the Video",Toast.LENGTH_LONG).show();
                Intent videoIntent = new Intent(MainActivity.this,WebViewActivity.class);
                videoIntent.putExtra("video_url",songList.get(index).getVideoUrl());
                startActivity(videoIntent);
                return true;
            case R.id.view_songs_wiki:
                Toast.makeText(getApplicationContext(),"Opening the Song's Wikipedia",Toast.LENGTH_LONG).show();
                Intent wikiSongIntent = new Intent(MainActivity.this,WebViewActivity.class);
                wikiSongIntent.putExtra("wiki_url",songList.get(index).getWikiUrl());
                startActivity(wikiSongIntent);
                return true;
            case R.id.view_bands_wiki:
                Toast.makeText(getApplicationContext(),"Opening the Band's Wikipedia",Toast.LENGTH_LONG).show();
                Intent wikiBandIntent = new Intent(MainActivity.this,WebViewActivity.class);
                wikiBandIntent.putExtra("wiki_url",songList.get(index).getBandWiki());
                startActivity(wikiBandIntent);
                return true;
            default:
                return super.onContextItemSelected(selectedItem);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu){
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_main,menu);
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        int initValue = 10;
        SubMenu delete_sub_menu = menu.findItem(R.id.delete_song).getSubMenu();
        delete_sub_menu.clear();
        for (int i = 0; i < songList.size();i++){
            delete_sub_menu.add(0,initValue+i,menu.NONE,songList.get(i).getSongTitle().toString());
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menu){
        int item = menu.getItemId();
        switch (item){
            case R.id.add_song:
                addSongToList();
                return true;
            case R.id.delete_song:
                //removeSongFromList();
                return true;
            case R.id.exit_app:
                this.finish();
                System.exit(0);
            default:
                int position = item-10;
                removeSongFromList(position);
                return super.onOptionsItemSelected(menu);
        }
    }
    public void addSongToList(){
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.add_song,null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);

        final EditText songTitle = (EditText) promptsView.findViewById(R.id.enter_song_title);
        final EditText bandName = (EditText)promptsView.findViewById(R.id.enter_band_name);
        final EditText wikiLink = (EditText) promptsView.findViewById(R.id.enter_wiki_link);
        final EditText videoLink = (EditText) promptsView.findViewById(R.id.enter_video_link);
        final EditText bandWikiLink = (EditText) promptsView.findViewById(R.id.enter_band_wiki_link);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // get user input and set it to result
                        // edit text
                        songList.add(new SongItem(songTitle.getText().toString(),bandName.getText().toString(),wikiLink.getText().toString(),videoLink.getText().toString(),bandWikiLink.getText().toString()));
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
    /*public void removeSongFromList(){
        int size = songList.size();
        if(size == 1)
            Toast.makeText(getApplicationContext(),"Only one song present and cannot be removed!",Toast.LENGTH_LONG).show();
        else{
            remove_list.removeAll(remove_list);
            LayoutInflater li = LayoutInflater.from(this);
            View promptsView = li.inflate(R.layout.remove_song,null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setView(promptsView);
            for(int i = 0; i < size ; i++){
                remove_list.add(songList.get(i).getSongTitle().toString());
            }
            final ListView remove_list_view = (ListView )promptsView.findViewById(R.id.delete_song_list);
            ArrayAdapter<String> remove_list_adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_single_choice, android.R.id.text1,remove_list);
            alertDialogBuilder.setSingleChoiceItems(remove_list_adapter, -1,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //selecteditem = which;
                            remove_list_view.setTag(which);
                        }
                    });
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // get user input and set it to result
                                    // edit text
                                    Integer selected = (Integer)remove_list_view.getTag();

                                    songList.remove(songList.get(selected));
                                    System.out.println("SONGLIST SIZE"+songList.size());
                                    adapter.notifyDataSetChanged();
                                    //Toast.makeText(getApplicationContext(), "Only one "+songlist.get(selected).getSongTitle().toString(), Toast.LENGTH_LONG).show();

                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            //AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialogBuilder.show();

        }
    }*/
    public void removeSongFromList(int position){
        int size = songList.size();
        if(size == 1)
            Toast.makeText(getApplicationContext(),"Only one song present and cannot be removed!",Toast.LENGTH_LONG).show();
        else {
            Toast.makeText(getApplicationContext(), "Deleted" + songList.get(position).getSongTitle().toString(), Toast.LENGTH_LONG).show();
            songList.remove(position);
            adapter.notifyDataSetChanged();
        }
    }
}
