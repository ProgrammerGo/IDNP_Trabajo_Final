package com.idnp_trabajo_final.views;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/*import com.idnp_trabajo_final.viewmodels.PerfilViewModel;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.DexterActivity;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;*/

//import org.intellij.lang.annotations.JdkConstants;
//import com.idnp_trabajo_final.R;

public class TunesFragment extends Fragment{

    ListView listView;
    String[] items;
    static MediaPlayer mp;//assigning memory loc once or else multiple songs will play at once
    SeekBar sb;
    //ArrayList<File> mySongs;
    final ArrayList<File> mySongs = findSong(Environment.getExternalStorageDirectory());
    Thread updateSeekBar;
    Button pause,next,previous;
    TextView songNameText;
    String sname;
    Timer timer;
    int i=0;
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_tunes);
        //listView = (ListView) findViewById(R.id.listView);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setTitle("Mi Musica");
        Dexter.withActivity(CallerActivity.)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        display();
                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }*/
    //void play(ArrayList mySongs,String songName){
    //}
    void display(){
        //final ArrayList<File> mySongs = findSong(Environment.getExternalStorageDirectory());
        items = new String[ mySongs.size() ];
        for(int i=0;i<mySongs.size();i++){
            //toast(mySongs.get(i).getName().toString());
            items[i] = mySongs.get(i).getName().toString().replace(".mp3","").replace(".wav","");
        }
        ArrayAdapter<String> adp = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adp);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String songName = listView.getItemAtPosition(position).toString();
                /*pause = (Button) view.findViewById(R.id.pause);
                previous = (Button) view.findViewById(R.id.previous);
                next = (Button) view.findViewById(R.id.next);*/
                //sb = (SeekBar) view.findViewById(R.id.seekBar);
                updateSeekBar=new Thread(){
                    @Override
                    public void run(){
                        int totalDuration = mp.getDuration();
                        int currentPosition = 0;
                        while(currentPosition < totalDuration){
                            try{
                                sleep(500);
                                currentPosition=mp.getCurrentPosition();
                                sb.setProgress(currentPosition);
                            }
                            catch (InterruptedException e){
                            }
                        }
                    }
                };
                if(mp != null){
                    mp.stop();
                    mp.release();
                }
                sname = mySongs.get(position).getName().toString();
                //String SongName = i.getStringExtra("songname");
                songNameText.setText(songName);
                songNameText.setSelected(true);
                Uri u = Uri.parse(mySongs.get(position).toString());
                //mp = MediaPlayer.create(getActivity().getApplicationContext(),u);
                mp = MediaPlayer.create(getActivity().getApplicationContext(),u);
                mp.start();
                sb.setMax(mp.getDuration());
                updateSeekBar.start();
                sb.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
                sb.getThumb().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
                sb.setOnSeekBarChangeListener(new
                                                      SeekBar.OnSeekBarChangeListener() {
                                                          @Override
                                                          public void onProgressChanged(SeekBar seekBar, int i,
                                                                                        boolean b) {
                                                          }
                                                          @Override
                                                          public void onStartTrackingTouch(SeekBar seekBar) {
                                                          }
                                                          @Override
                                                          public void onStopTrackingTouch(SeekBar seekBar) {
                                                              mp.seekTo(seekBar.getProgress());

                                                          }
                                                      });
                pause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sb.setMax(mp.getDuration());
                        if(mp.isPlaying()){
                            pause.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                            mp.pause();
                        }
                        else {
                            pause.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                            mp.start();
                        }
                    }
                });
                /*next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mp.stop();
                        mp.release();
                        position = (position + 1)%mySongs.size();
                        Uri u = Uri.parse(mySongs.get( position).toString());
                        // songNameText.setText(getSongName);
                        mp = MediaPlayer.create(getActivity().getApplicationContext(),u);

                        sname = mySongs.get(position).getName().toString().replace(".mp3","").replace(".wav","");
                        songNameText.setText(sname);

                        try{
                            mp.start();
                        }catch(Exception e){}

                    }
                });
                previous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //songNameText.setText(getSongName);
                        mp.stop();
                        mp.release();

                        position=((position-1)<0)?(mySongs.size()-1):(position-1);
                        Uri u = Uri.parse(mySongs.get(position).toString());
                        mp = MediaPlayer.create(getActivity().getApplicationContext(),u);
                        sname = mySongs.get(position).getName().toString().replace(".mp3","").replace(".wav","");
                        songNameText.setText(sname);
                        mp.start();
                    }
                });*/
                //timer = new Timer();
                //if (mySongs.size()>1) playNext();
                //play(mySongs,songName);
                //startActivity(new Intent(getApplicationContext(),PlayerActivity.class)void display(){
                //        //final ArrayList<File> mySongs = findSong(Environment.getExternalStorageDirectory());
                //        items = new String[ mySongs.size() ];
                //        for(int i=0;i<mySongs.size();i++){
                //            //toast(mySongs.get(i).getName().toString());
                //            items[i] = mySongs.get(i).getName().toString().replace(".mp3","").replace(".wav","");
                //        }
                //        ArrayAdapter<String> adp = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
                //        listView.setAdapter(adp);
                //        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                //            @Override
                //            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //                String songName = listView.getItemAtPosition(position).toString();
                //                pause = (Button) view.findViewById(R.id.pause);
                //                previous = (Button) view.findViewById(R.id.previous);
                //                next = (Button) view.findViewById(R.id.next);
                //                sb=(SeekBar) view.findViewById(R.id.seekBar);
                //                updateSeekBar=new Thread(){
                //                    @Override
                //                    public void run(){
                //                        int totalDuration = mp.getDuration();
                //                        int currentPosition = 0;
                //                        while(currentPosition < totalDuration){
                //                            try{
                //                                sleep(500);
                //                                currentPosition=mp.getCurrentPosition();
                //                                sb.setProgress(currentPosition);
                //                            }
                //                            catch (InterruptedException e){
                //                            }
                //                        }
                //                    }
                //                };
                //                if(mp != null){
                //                    mp.stop();
                //                    mp.release();
                //                }
                //                sname = mySongs.get(position).getName().toString();
                //                //String SongName = i.getStringExtra("songname");
                //                songNameText.setText(SongName);
                //                songNameText.setSelected(true);
                //                //play(mySongs,songName);
                //                //startActivity(new Intent(getApplicationContext(),PlayerActivity.class).putExtra("pos",position).putExtra("songs",mySongs).putExtra("songname",songName));
                //            }
                //        });
                //    }
                //
                //    .putExtra("pos",position).putExtra("songs",mySongs).putExtra("songname",songName));
            }
        });
    }

    public void playNext() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mp.reset();
                Uri u = Uri.parse(mySongs.get(++i).toString());
                mp = MediaPlayer.create(getActivity().getApplicationContext(), u);
                mp.start();
                if (mySongs.size() > i+1) {
                    playNext();
                }
            }
        },mp.getDuration()+100);
    }
    public ArrayList<File> findSong(File root){
        ArrayList<File> at = new ArrayList<File>();
        File[] files = root.listFiles();
        for(File singleFile : files){
            if(singleFile.isDirectory() && !singleFile.isHidden()){
                at.addAll(findSong(singleFile));
            }
            else{
                if(singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav")){
                    at.add(singleFile);
                }
            }
        }
        return at;
    }
    //private TextView mTextView;
    //ListView listView;
    //String[] items;

    public static TunesFragment newInstance() {
        return new TunesFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tunes, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
//        songNameText = (TextView) getActivity().findViewById(R.id.txtSongLabel);
      //  pause = (Button) getActivity().findViewById(R.id.pause);
        //previous = (Button) getActivity().findViewById(R.id.previous);
        //next = (Button) getActivity().findViewById(R.id.next);
      //  sb=(SeekBar) getActivity().findViewById(R.id.seekBar);
        //sb=(SeekBar) view.findViewById(R.id.seekBar);
        display();
        return view;
        //listView = (ListView) findViewById(R.id.listView);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setTitle("Mi Musica");
        /*Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        display();
                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();*/
    }
}