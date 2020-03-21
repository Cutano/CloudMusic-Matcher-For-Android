package com.hongbojiang.ui.matchResult;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.hongbojiang.R;
import com.hongbojiang.Song;
import com.hongbojiang.SongAdapter;
import com.hongbojiang.ui.home.HomeViewModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MatchResultFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private View root;
    private RecyclerView recyclerView;
    private SongAdapter songAdapter;
    private ProgressBar progressBar;
    private CardView cardView;
    private MatchTaskAsync matchTask;
    private FloatingActionButton shareButton;
    private List<Song> songs = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_match_result, container, false);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (matchTask.getStatus()== AsyncTask.Status.RUNNING)
            matchTask.cancel(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.match_result_recycler_view);
        progressBar = getActivity().findViewById(R.id.progressBar_1);
        cardView = getActivity().findViewById(R.id.cardview_1);
        shareButton = getActivity().findViewById(R.id.shareButton);
        shareButton.setEnabled(false);
        TextView summary_1 = getActivity().findViewById(R.id.summery_text_main);
        TextView summary_2 = getActivity().findViewById(R.id.summery_text_secondary);
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
                if (isCloudMusicInstalled()) {
                    url = "orpheus://song/" + songs.get(position).getId();
                } else {
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
        shareButton.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                songs = songAdapter.getSongs();
                Intent textIntent = new Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                textIntent.putExtra(Intent.EXTRA_TEXT, getSharingText());
                startActivity(Intent.createChooser(textIntent, "分享"));
            }
        });
        progressBar.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.GONE);
        summary_1.setText("");
        summary_2.setText("");
        matchTask = new MatchTaskAsync(root, songAdapter, homeViewModel.getFirstID().getValue(), homeViewModel.getSecondID().getValue());
        matchTask.execute();
    }

    private boolean isCloudMusicInstalled() {
        PackageManager manager = getContext().getPackageManager();
        String packageName = "com.netease.cloudmusic";
        boolean hasInstall;
        try {
            PackageInfo packageInfo = manager.getPackageInfo(packageName, PackageManager.GET_GIDS);
            hasInstall = packageInfo != null;
        } catch (PackageManager.NameNotFoundException e) {
            hasInstall = false;
            e.printStackTrace();
        }
        return hasInstall;
    }

    private String getSharingText() {
        String id1 = homeViewModel.getFirstID().getValue(), id2 = homeViewModel.getSecondID().getValue();
        StringBuilder builder = new StringBuilder();
        for (Song item : songs) {
            builder.append(item.getName()).append("\n");
        }
        builder.append("\n分享自网易云音乐歌单匹配器 https://www.coolapk.com/apk/com.hongbojiang");
        return "ID为" + id1 + "的歌单与ID为" + id2 + "的歌单共有" + songs.size() + "首歌相同\n\n" + builder.toString();
    }

    private static class MatchTaskAsync extends AsyncTask<Void, Void, List<Song>> {
        String id_1, id_2;
        View view;
        SongAdapter adapter;
        List<Song> list = new ArrayList<>();
        List<Song> songs = new ArrayList<>();
        Set<Song> set = new HashSet<>();

        public MatchTaskAsync(View view, SongAdapter adapter, String id_1, String id_2) {
            this.view = view;
            this.adapter = adapter;
            this.id_1 = id_1;
            this.id_2 = id_2;
        }

        @Override
        protected List<Song> doInBackground(Void... voids) {
            String url = "http://music.163.com/playlist?id=" + id_1;
            try {
                for (Element element : Jsoup.connect(url)
                        .header("Referer", "http://music.163.com/")
                        .header("Host", "music.163.com").get().select("ul[class=f-hide] a")) {
                    String songUrl = element.attr("href");
                    Song song = new Song(element.text(), songUrl, songUrl.substring(songUrl.indexOf("song?id=") + 8));
                    set.add(song);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            url = "http://music.163.com/playlist?id=" + id_2;
            try {
                for (Element element : Jsoup.connect(url)
                        .header("Referer", "http://music.163.com/")
                        .header("Host", "music.163.com").get().select("ul[class=f-hide] a")) {
                    String songUrl = element.attr("href");
                    Song song = new Song(element.text(), songUrl, songUrl.substring(songUrl.indexOf("song?id=") + 8));
                    list.add(song);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (Song song : list) {
                if (set.contains(song)) {
                    songs.add(song);
                }
            }

            return songs;
        }

        @Override
        protected void onPostExecute(List<Song> songs) {
            RecyclerView recyclerView = view.findViewById(R.id.match_result_recycler_view);
            ProgressBar progressBar = view.findViewById(R.id.progressBar_1);
            TextView summary_1 = view.findViewById(R.id.summery_text_main);
            TextView summary_2 = view.findViewById(R.id.summery_text_secondary);
            CardView cardView = view.findViewById(R.id.cardview_1);
            FloatingActionButton shareButton = view.findViewById(R.id.shareButton);
            shareButton.setEnabled(true);
            adapter.setSongs(songs);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            Snackbar.make(view, "匹配完毕 共有" + songs.size() + "首歌相同", Snackbar.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);
            if (songs.size()==0) {
                summary_1.setText("无相同歌曲");
                super.onPostExecute(songs);
                return;
            }
            else {
                summary_1.setText(new StringBuilder().append("有").append(songs.size()).append("首相同的歌曲").toString());
                summary_2.setText(new StringBuilder().append("在歌单1中有").append((songs.size() * 100) / set.size()).append("%的歌曲相同，").append("在歌单2中有").append((songs.size() * 100) / list.size()).append("%的歌曲相同").toString());
            }
            super.onPostExecute(songs);
        }
    }
}
