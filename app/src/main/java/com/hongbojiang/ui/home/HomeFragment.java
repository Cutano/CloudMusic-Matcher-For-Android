package com.hongbojiang.ui.home;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.hongbojiang.R;
import com.hongbojiang.ui.favorites.FavoritesFragment;

import static android.content.Context.CLIPBOARD_SERVICE;

public class HomeFragment extends Fragment{

    private HomeViewModel homeViewModel;
    private View root;
    private Button paste_1,paste_2;
    private ClipboardManager mClipboardManager;
    private ClipData clipData;
    private TextInputEditText idInput_1;
    private TextInputEditText idInput_2;
    private ExtendedFloatingActionButton matchButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        //final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getFirstID().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                idInput_1.setText(s);
            }
        });
        homeViewModel.getSecondID().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                idInput_2.setText(s);
            }
        });
        mClipboardManager = (ClipboardManager)(getActivity().getSystemService(CLIPBOARD_SERVICE));
        ClipData clipData = null;
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        paste_1 = getActivity().findViewById(R.id.paste_button_1);
        paste_2 = getActivity().findViewById(R.id.paste_button_2);
        idInput_1 = getActivity().findViewById(R.id.id_input_1);
        idInput_2 = getActivity().findViewById(R.id.id_input_2);
        matchButton = getActivity().findViewById(R.id.match_button);
        matchButton.shrink();
        matchButton.setEnabled(false);
        paste_1.setOnClickListener(new View.OnClickListener() {
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
                    text = handleText( text );
                    homeViewModel.setFirstID(text);
                }
            }
        });
        paste_2.setOnClickListener(new View.OnClickListener() {
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
                    text = handleText( text );
                    homeViewModel.setSecondID(text);
                }
            }
        });
        matchButton.setOnClickListener(new ExtendedFloatingActionButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(FavoritesFragment.isNumeric(idInput_1.getText().toString())&&FavoritesFragment.isNumeric(idInput_2.getText().toString())) {
                    homeViewModel.setFirstID(idInput_1.getText().toString());
                    homeViewModel.setSecondID(idInput_2.getText().toString());
                    NavController controller = Navigation.findNavController(v);
                    controller.navigate(R.id.action_nav_home_to_matchResultFragment);
                }
            }
        });
        idInput_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!idInput_2.getText().toString().isEmpty()){
                    matchButton.extend();
                    matchButton.setEnabled(true);
                }
                if(idInput_2.getText().toString().isEmpty()||idInput_1.getText().toString().isEmpty()){
                    matchButton.shrink();
                    matchButton.setEnabled(false);
                }
            }
        });
        idInput_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!idInput_1.getText().toString().isEmpty()) {
                    matchButton.extend();
                    matchButton.setEnabled(true);
                }
                if(idInput_2.getText().toString().isEmpty()||idInput_1.getText().toString().isEmpty()){
                    matchButton.shrink();
                    matchButton.setEnabled(false);
                }
            }
        });
    }

    public static String handleText ( String text )
    {
        if (isNumeric( text ))
            return text;
        else
        {
            if(text.contains("playlist") && text.contains("/")) {
                String id = text.substring( text.indexOf( "playlist" ) + 9 );
                id = id.substring( 0, id.indexOf( "/" ) );
                return id;
            }
            else {
                return text;
            }
        }
    }

    private static boolean isNumeric(String str)
    {
        for (int i = 0; i < str.length(); i++)
        {
            if (! Character.isDigit( str.charAt( i ) ))
            {
                return false;
            }
        }
        return true;
    }

}