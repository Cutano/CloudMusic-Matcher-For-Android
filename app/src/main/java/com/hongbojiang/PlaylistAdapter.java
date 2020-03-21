package com.hongbojiang;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.hongbojiang.ui.favorites.FavoritesFragment;
import com.hongbojiang.ui.favorites.FavoritesViewModel;
import com.hongbojiang.ui.home.HomeFragment;

import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    private FavoritesViewModel favoritesViewModel;
    private List<Playlist> playlists;
    private ClipboardManager mClipboardManager;
    private OnItemClickListener mOnItemClickListener;
    private ClipData clipData;
    private Context context;
    private View editPopup;

    public PlaylistAdapter(List<Playlist> playlists, Context context) {
        this.playlists = playlists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.indexText.setText(String.valueOf(position + 1));
        holder.nameText.setText(playlists.get(position).getName());
        holder.idText.setText(playlists.get(position).getId());
        editPopup = LayoutInflater.from(context).inflate(R.layout.edit_playlist_item_popup, null);
        favoritesViewModel = new ViewModelProvider((FragmentActivity)context).get(FavoritesViewModel.class);
        final EditText editNameText = editPopup.findViewById(R.id.edit_playlist_popup_name_text);
        final EditText editIdText = editPopup.findViewById(R.id.edit_playlist_popup_id_text);
        final PopupWindow editItemPopup = new PopupWindow(editPopup);
        editIdText.setText(holder.idText.getText());
        editNameText.setText(holder.nameText.getText());
        editItemPopup.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        editItemPopup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        editItemPopup.setOutsideTouchable(true);
        editItemPopup.setFocusable(true);
        editItemPopup.setAnimationStyle(R.style.showPopupAnimation);
        mClipboardManager = (ClipboardManager)(context.getSystemService(CLIPBOARD_SERVICE));
        clipData = null;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clipData = ClipData.newPlainText("ID", holder.idText.getText().toString());
                mClipboardManager.setPrimaryClip(clipData);
                Snackbar.make(((Activity)context).findViewById(R.id.fav_constraint), "复制成功", Snackbar.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        holder.copyBtn.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                clipData = ClipData.newPlainText("ID", holder.idText.getText().toString());
                mClipboardManager.setPrimaryClip(clipData);
                Snackbar.make(((Activity)context).findViewById(R.id.fav_constraint), "复制成功", Snackbar.LENGTH_SHORT).show();
            }
        });
        holder.editBtn.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                editItemPopup.showAtLocation(((Activity)context).findViewById(R.id.fav_constraint), Gravity.CENTER, 0, 0);
                FavoritesFragment.dimBehind(editItemPopup);
            }
        });
        editPopup.findViewById(R.id.edit_playlist_popup_ok_btn).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = editNameText.getText().toString();
                String id = editIdText.getText().toString();
                if(!name.isEmpty()&&!id.isEmpty()&& FavoritesFragment.isNumeric(id)) {
                    final Playlist playlistToDelete = playlists.get(holder.getAdapterPosition());
                    favoritesViewModel.deletePlaylist(playlistToDelete);
                    Playlist playlist = new Playlist(name, id);
                    favoritesViewModel.addPlaylist(playlist);
                    setPlaylists(favoritesViewModel.getPlaylists().getValue());
                    notifyDataSetChanged();
                    editItemPopup.dismiss();
                }
            }
        });
        editPopup.findViewById(R.id.edit_playlist_popup_del_btn).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Playlist playlistToDelete = playlists.get(holder.getAdapterPosition());
                favoritesViewModel.deletePlaylist(playlistToDelete);
                setPlaylists(favoritesViewModel.getPlaylists().getValue());
                notifyDataSetChanged();
                editItemPopup.dismiss();
                Snackbar.make(((Activity)context).findViewById(R.id.fav_constraint), "已删除歌单", Snackbar.LENGTH_LONG)
                        .setAction("撤销", new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                favoritesViewModel.addPlaylist(playlistToDelete);
                                setPlaylists(favoritesViewModel.getPlaylists().getValue());
                                notifyDataSetChanged();
                            }
                        }).show();
            }
        });
        editPopup.findViewById(R.id.edit_playlist_popup_cancel_btn).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                editItemPopup.dismiss();
            }
        });
        editPopup.findViewById(R.id.edit_playlist_popup_paste_btn).setOnClickListener(new Button.OnClickListener(){
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
                    editIdText.setText(text);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
        this.notifyDataSetChanged();
    }

    public void addData(int pos, Playlist playlist) {
        playlists.add(pos,playlist);
        notifyItemChanged(pos);
        notifyItemRangeChanged(pos,playlists.size() - pos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView indexText;
        TextView nameText;
        TextView idText;
        ImageButton editBtn;
        ImageButton copyBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            indexText = itemView.findViewById(R.id.playlist_index_text);
            nameText = itemView.findViewById(R.id.playlist_name_text);
            idText = itemView.findViewById(R.id.playlist_id_text);
            editBtn = itemView.findViewById(R.id.playlist_item_edit_button);
            copyBtn = itemView.findViewById(R.id.playlist_item_copy_button);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
