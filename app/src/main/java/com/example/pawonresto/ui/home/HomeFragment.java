package com.example.pawonresto.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pawonresto.EditActivity;
import com.example.pawonresto.LoginActivity;
import com.example.pawonresto.Preferences.UserPreferences;
import com.example.pawonresto.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    private TextView btnEdit, btnLogout, tvWelcome;

    FirebaseAuth firebaseAuth;
    StorageReference storageReference;
    CircleImageView imgProfil;
    String currentPhotoPath;
    private SharedPreferences preferences;

    UserPreferences userPreferences;
    public static final int mode = Activity.MODE_PRIVATE;
    private String nama = "";

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userPreferences = new UserPreferences(HomeFragment.this.getContext());

        btnEdit = view.findViewById(R.id.btnEdit);
        btnLogout = view.findViewById(R.id.btnLogout);
        tvWelcome = view.findViewById(R.id.tvWelcome);

        userPreferences.getUserLogin();
        checkLogin();
        tvWelcome.setText("Selamat Datang, " + userPreferences.getUserLogin().getNamaLengkap());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(HomeFragment.this.getContext(), EditActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPreferences.logout();
                Intent intent = new Intent(HomeFragment.this.getContext(), LoginActivity.class);
                startActivity(intent);
                Toast.makeText(HomeFragment.this.getContext(), "Baiii baiii", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPreferences() {
        String name = "profile";
        preferences = requireContext().getSharedPreferences(name, mode);
        if (preferences != null) {
            nama = preferences.getString("Selamat Datang, ", "");
        }
    }

    private void checkLogin() {
        if (!userPreferences.checkLogin()){
            startActivity(new Intent(HomeFragment.this.getContext(), LoginActivity.class));
        }
    }
}