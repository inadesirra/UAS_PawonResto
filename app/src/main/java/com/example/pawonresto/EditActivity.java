package com.example.pawonresto;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pawonresto.Dao.UserDao;
import com.example.pawonresto.Database.UserDatabase;
import com.example.pawonresto.Database.UserDatabaseClient;
import com.example.pawonresto.Preferences.UserPreferences;
import com.example.pawonresto.model.User;
import com.example.pawonresto.ui.home.HomeFragment;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditActivity extends AppCompatActivity {
    ImageButton ibPrev;
    CircleImageView imgProfil;
    MaterialButton btnAmbil, btnKonfirmasi;
    EditText etNama;
    UserPreferences userPreferences;
    Thread timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ibPrev = findViewById(R.id.ib_prev);
        imgProfil = findViewById(R.id.iv_profil);
        btnAmbil = findViewById(R.id.btn_ambil);
        btnKonfirmasi = findViewById(R.id.btn_konfirmasi);
        etNama = findViewById(R.id.et_nama);
        userPreferences = new UserPreferences(EditActivity.this);

        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etNama.getText().toString();
                String email = userPreferences.getUserLogin().getEmail();
                String password = userPreferences.getUserLogin().getPassword();

                if (etNama.getText().toString().equals("")) {
                    startActivity(new Intent(EditActivity.this, MainActivity.class));
                } else {
//                    addUser();
//                    Intent intent = new Intent(EditActivity.this, MainActivity.class);
//                    startActivity(intent);

                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            User user = userDao.login(email, password);
                            if (user == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Terjadi Kesalahan!",
                                                Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                });
                            } else {
//                                Toast.makeText(EditActivity.this, "Edit Profil Berhasil!", Toast.LENGTH_SHORT).show();
                                timer = new Thread() {
                                    @Override
                                    public void run() {
                                        try {
                                            synchronized (this) {
                                                wait(3000);
                                            }
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        } finally {
                                            userPreferences.getUserLogin().setNamaLengkap(name);
                                            userDao.updateName(name, email, password);
                                            startActivity(new Intent(EditActivity.this, MainActivity.class));
                                        }
                                    }
                                };
                                timer.start();
                            }
                        }
                    }).start();
                }
            }
        });

        ibPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                startActivity(new Intent(EditActivity.this, MainActivity.class));
                finish();
            }
        });
    }

//    private void addUser() {
//        final String name = etNama.getText().toString();
//        String email = userPreferences.getUserLogin().getEmail();
//        String password = userPreferences.getUserLogin().getPassword();
//
//        class AddUser extends AsyncTask<Void, Void, Void> {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
//                UserDao userDao = userDatabase.userDao();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        User user = userDao.login(email, password);
//                        if (user == null) {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(getApplicationContext(), "Terjadi Kesalahan!",
//                                            Toast.LENGTH_SHORT)
//                                            .show();
//                                }
//                            });
//                        } else {
//                            userPreferences.getUserLogin().setNamaLengkap(name);
//                            userDao.updateName(name, email, password);
//                        }
//                    }
//                }).start();
//
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void unused) {
//                super.onPostExecute(unused);
//                Toast.makeText(EditActivity.this, "Edit Profil Berhasil!", Toast.LENGTH_SHORT).show();
//                getUser();
//            }
//        }
//
//        AddUser addUser = new AddUser();
//        addUser.execute();
//    }
//
//    private void getUser() {
//        class GetUser extends AsyncTask<Void, Void, List<User>> {
//            @Override
//            protected List<User> doInBackground(Void... voids) {
//                List<User> userList = UserDatabaseClient.getInstance(getApplicationContext())
//                        .getDatabase()
//                        .userDao()
//                        .getAll();
//                return userList;
//            }
//
//            @Override
//            protected void onPostExecute(List<User> users) {
//                super.onPostExecute(users);
//                user
//            }
//        }
//    }
}