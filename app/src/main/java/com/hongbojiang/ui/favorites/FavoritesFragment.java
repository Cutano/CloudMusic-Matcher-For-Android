package com.hongbojiang.ui.favorites;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.hongbojiang.Playlist;
import com.hongbojiang.PlaylistAdapter;
import com.hongbojiang.R;
import com.hongbojiang.ui.home.HomeFragment;

import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel favoritesViewModel;
    private FloatingActionButton addButton;
    private List<Playlist> playlists;
    private PlaylistAdapter playlistAdapter;
    private RecyclerView recyclerView;
    private ClipboardManager mClipboardManager;
    private ClipData clipData;
    private View addPopup;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritesViewModel = new ViewModelProvider(requireActivity()).get(FavoritesViewModel.class);
        root = inflater.inflate(R.layout.fragment_favorites, container, false);
        favoritesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        favoritesViewModel.getPlaylists().observe(getViewLifecycleOwner(), new Observer<List<Playlist>>() {
            @Override
            public void onChanged(List<Playlist> lists) {
                playlists = lists;
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addButton = getActivity().findViewById(R.id.add_fav_playlist_button);
        recyclerView = getActivity().findViewById(R.id.playlist_list);
        mClipboardManager = (ClipboardManager)(getActivity().getSystemService(CLIPBOARD_SERVICE));
        clipData = null;
        addPopup = LayoutInflater.from(getContext()).inflate(R.layout.add_playlist_item_popup, null);
        playlists = favoritesViewModel.getPlaylists().getValue();
        playlistAdapter = new PlaylistAdapter(playlists, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(playlistAdapter);
        final EditText addNameText = addPopup.findViewById(R.id.add_playlist_popup_name_text);
        final EditText addIdText = addPopup.findViewById(R.id.add_playlist_popup_id_text);
        final PopupWindow addItemPopup = new PopupWindow(addPopup);
        addItemPopup.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        addItemPopup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        addItemPopup.setOutsideTouchable(true);
        addItemPopup.setFocusable(true);
        addItemPopup.setAnimationStyle(R.style.showPopupAnimation);
        addPopup.findViewById(R.id.add_playlist_popup_cancel_btn).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                addItemPopup.dismiss();
            }
        });
        addPopup.findViewById(R.id.add_playlist_popup_ok_btn).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = addNameText.getText().toString();
                String id = addIdText.getText().toString();
                if(!name.isEmpty()&&!id.isEmpty()&&isNumeric(id)) {
                    Playlist playlist = new Playlist(name, id);
                    favoritesViewModel.addPlaylist(playlist);
                    playlistAdapter.setPlaylists(playlists);
                    playlistAdapter.notifyDataSetChanged();
                    addItemPopup.dismiss();
                }
            }
        });
        addPopup.findViewById(R.id.add_playlist_popup_paste_btn).setOnClickListener(new Button.OnClickListener(){
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
                    text = HomeFragment.handleText(text);
                    addIdText.setText(text);
                }
            }
        });
        addButton.setOnClickListener(new FloatingActionButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                addItemPopup.showAtLocation(root, Gravity.CENTER, 0, 0);
                dimBehind(addItemPopup);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START | ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final Playlist playlistToDelete = playlists.get(viewHolder.getAdapterPosition());
                favoritesViewModel.deletePlaylist(playlistToDelete);
                playlistAdapter.setPlaylists(playlists);
                playlistAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                Snackbar.make(requireActivity().findViewById(R.id.fav_constraint), "已删除歌单", Snackbar.LENGTH_LONG)
                        .setAction("撤销", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                favoritesViewModel.addPlaylist(playlistToDelete);
                                playlistAdapter.setPlaylists(playlists);
                                playlistAdapter.notifyDataSetChanged();
                            }
                        }).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    public static void dimBehind(PopupWindow popupWindow) {
        View container;
        if (popupWindow.getBackground() == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                container = (View) popupWindow.getContentView().getParent();
            } else {
                container = popupWindow.getContentView();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) popupWindow.getContentView().getParent().getParent();
            } else {
                container = (View) popupWindow.getContentView().getParent();
            }
        }
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.5f;
        wm.updateViewLayout(container, p);
    }

    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
}