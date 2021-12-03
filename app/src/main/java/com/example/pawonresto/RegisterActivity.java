package com.example.pawonresto;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pawonresto.Dao.UserDao;
import com.example.pawonresto.Database.UserDatabase;
import com.example.pawonresto.Database.UserDatabaseClient;
import com.example.pawonresto.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private EditText etNama, etEmail, etPassword;
    private MaterialButton btnRegister;
    private ImageButton ibPrev;

//    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initiate firebaseauthentication
        firebaseAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etNama = findViewById(R.id.et_nama);

        ibPrev = findViewById(R.id.ib_prev);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setEmail(etEmail.getText().toString());
                user.setPassword(etPassword.getText().toString());
                user.setNamaLengkap(etNama.getText().toString());

                if(!etNama.getText().toString().equals("")
                        && !etPassword.getText().toString().equals("")
                        && !etEmail.getText().toString().equals("")) {
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            userDao.insert(user);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Berhasil Registrasi!",
                                            Toast.LENGTH_SHORT)
                                            .show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                }
                            });
                        }
                    }).start();
//                            }
//                            else if (!(Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()))
//                                Toast.makeText(RegisterActivity.this, "Salah Format Email!", Toast.LENGTH_SHORT).show();
//                            else if(etPassword.getText().toString().length()<6)
//                                Toast.makeText(RegisterActivity.this, "Jumlah karakter password harus lebih dari 6!", Toast.LENGTH_SHORT).show();
//                            else {
//                                Toast.makeText(RegisterActivity.this, "Regristrasi Gagal!", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
                }else{
                    if(etEmail.getText().toString().equals(""))
                        Toast.makeText(RegisterActivity.this, "Email masih kosong!", Toast.LENGTH_SHORT).show();
                    else if (!(Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()))
                        Toast.makeText(RegisterActivity.this, "Salah Format Email!", Toast.LENGTH_SHORT).show();
                    else if(etPassword.getText().toString().equals(""))
                        Toast.makeText(RegisterActivity.this, "Password masih kosong!", Toast.LENGTH_SHORT).show();
                    else if(etPassword.getText().toString().length()<6)
                        Toast.makeText(RegisterActivity.this, "Jumlah karakter password harus lebih dari 6!", Toast.LENGTH_SHORT).show();
                    else if(etNama.getText().toString().equals(""))
                        Toast.makeText(RegisterActivity.this, "Nama Lengkap masih kosong!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(RegisterActivity.this, "Regristrasi Gagal!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ibPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void addUser() {
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();
        final String nama = etNama.getText().toString();

        class AddUser extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                User user = new User();
                user.setEmail(email);
                user.setPassword(password);
                user.setNamaLengkap(nama);

                UserDatabaseClient.getInstance(RegisterActivity.this).getDatabase()
                        .userDao()
                        .insert(user);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(RegisterActivity.this, "Registration Complete", Toast.LENGTH_SHORT).show();
            }
        }

        AddUser add = new AddUser();
        add.execute();
    }
}