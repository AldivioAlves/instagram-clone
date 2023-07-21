package br.com.aldivio.estudos.instagram.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.aldivio.estudos.instagram.R;
import br.com.aldivio.estudos.instagram.activities.UpdateAccountActivity;

public class ProfileFragment extends Fragment {
    private TextView textViewTotalFollowing, textviewTotalPublications, textViewTotalFollowers;
    private Button buttonEditProfile;

    public ProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initComponents(view);
        setListeners();
        return view;
    }

    private void initComponents(View view) {
        textViewTotalFollowers = view.findViewById(R.id.fragment_profile_text_view_total_followers);
        textViewTotalFollowing = view.findViewById(R.id.fragment_profile_text_view_total_following);
        textviewTotalPublications = view.findViewById(R.id.fragment_profile_text_view_total_publications);
        buttonEditProfile = view.findViewById(R.id.fragment_profile_button_edit_profile);
    }
    private  void setListeners(){
        buttonEditProfile.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), UpdateAccountActivity.class);
            startActivity(intent);
        });
    }
}