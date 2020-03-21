package com.hongbojiang.ui.search;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.hongbojiang.R;
import com.hongbojiang.Song;
import com.hongbojiang.SongAdapter;
import com.hongbojiang.ui.favorites.FavoritesFragment;
import com.hongbojiang.ui.home.HomeFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private View root;
    private Button paste;
    private ClipboardManager mClipboardManager;
    private ClipData clipData;
    private TextInputEditText idInput;
    private ProgressBar progressBar;
    private SongAdapter songAdapter;
    private CardView cardView;
    private SearchTaskAsync searchTaskAsync;
    private RecyclerView recyclerView;
    private FloatingActionButton searchButton;
    private List<Song> songs = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);
        root = inflater.inflate(R.layout.fragment_search, container, false);
        //final TextView textView = root.findViewById(R.id.text_gallery);
        searchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        mClipboardManager = (ClipboardManager)(getActivity().getSystemService(CLIPBOARD_SERVICE));
        ClipData clipData = null;
        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (searchTaskAsync != null && searchTaskAsync.getStatus()== AsyncTask.Status.RUNNING)
            searchTaskAsync.cancel(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        paste = root.findViewById(R.id.search_paste_btn);
        searchButton = root.findViewById(R.id.search_Button);
        idInput = root.findViewById(R.id.search_edit_text);
        progressBar = root.findViewById(R.id.progressBar_2);
        recyclerView = root.findViewById(R.id.search_recycler_view);
        cardView = root.findViewById(R.id.search_cardView);
        paste.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mClipboardManager != null)
                    clipData = mClipboardManager.getPrimaryClip();
                ClipData.Item item = null;
                if (clipData != null)
                    item = clipData.getItemAt( 0 );
                String text = null;
                if (item != null) {
                    text = item.getText().toString();
                    text = HomeFragment.handleText( text );
                    if (!text.equals("")) {
                        idInput.setText(text);
                    }
                }
            }
        });
        songAdapter = new SongAdapter(songs);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        songAdapter.setOnItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                songs = songAdapter.getSongs();
                String url;
                if(isCloudMusicInstalled()){
                    url = "orpheus://song/" + songs.get(position).getId();
                }
                else {
                    url = "http://music.163.com" + songs.get(position).getUrl();
                }
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        searchButton.setOnClickListener(new FloatingActionButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                String id = idInput.getText().toString();
                if(!id.isEmpty()&& FavoritesFragment.isNumeric(id)) {
                    progressBar.setVisibility(View.VISIBLE);
                    cardView.setVisibility(View.INVISIBLE);
                    songs = new ArrayList<>();
                    songAdapter.setSongs(songs);
                    searchTaskAsync = new SearchTaskAsync(root, songAdapter, idInput.getText().toString());
                    searchTaskAsync.execute();
                }
            }
        });
    }

    private boolean isCloudMusicInstalled(){
        PackageManager manager = getContext().getPackageManager();
        String packageName ="com.netease.cloudmusic";
        boolean hasInstall;
        try{
            PackageInfo packageInfo = manager.getPackageInfo(packageName,PackageManager.GET_GIDS);
            hasInstall = packageInfo != null;
        }catch(PackageManager.NameNotFoundException e) {
            hasInstall =false;
            e.printStackTrace();
        }
        return hasInstall;
    }

    private static class SearchTaskAsync extends AsyncTask<Void, Void, List<Song>> {
        String id;
        View view;
        SongAdapter adapter;
        List<Song> list = new ArrayList<>();

        public SearchTaskAsync(View view, SongAdapter adapter, String id) {
            this.view = view;
            this.adapter = adapter;
            this.id = id;
        }

        @Override
        protected List<Song> doInBackground(Void... voids) {
            String url = "http://music.163.com/playlist?id=" + id;
            try
            {
                for (Element element : Jsoup.connect( url )
                        .header("Referer", "http://music.163.com/")
                        .header( "Host", "music.163.com" ).get().select( "ul[class=f-hide] a" )){
                    String songUrl = element.attr("href");
                    Song song = new Song(element.text(), songUrl, songUrl.substring(songUrl.indexOf("song?id=") + 8));
                    list.add( song );
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<Song> songs) {
            RecyclerView recyclerView = view.findViewById(R.id.search_recycler_view);
            ProgressBar progressBar = view.findViewById(R.id.progressBar_2);
            CardView cardView = view.findViewById(R.id.search_cardView);
            adapter.setSongs(songs);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            Snackbar.make( view, "查询完毕 共有" + songs.size() + "首歌", Snackbar.LENGTH_LONG ).show();
            progressBar.setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);
            super.onPostExecute(songs);
        }
    }
}

