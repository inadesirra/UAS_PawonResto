package com.example.pawonresto;

import static com.example.pawonresto.MyApplication.CHANNEL_1_ID;
import static com.example.pawonresto.MyApplication.CHANNEL_2_ID;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.pawonresto.Dao.UserDao;
import com.example.pawonresto.Database.UserDatabase;
import com.example.pawonresto.Preferences.UserPreferences;
import com.example.pawonresto.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private MaterialButton btnLogin, btnRegister;
    FirebaseAuth firebaseAuth;
    UserPreferences userPreferences;
    private SharedPreferences preferences;
    public static final int mode = Activity.MODE_PRIVATE;
    String namaTemp;
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initiate firebaseauthentication
        firebaseAuth = FirebaseAuth.getInstance();

        notificationManager = NotificationManagerCompat.from(this);
        userPreferences = new UserPreferences(LoginActivity.this);
        btnRegister = findViewById(R.id.btn_register);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if(!etEmail.getText().toString().equals("") && !etPassword.getText().toString().equals("")) {
                    //login
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
                                        Toast.makeText(getApplicationContext(), "Email Atau Password Salah!",
                                                Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                });
                            } else {
                                String name = user.getNamaLengkap();
                                loadPreferences();
                                savePreferences(etEmail.getText().toString().trim());
                                userPreferences.setLogin(email, password, name);
                                sendOnChannel1(v);
                                checkLogin();
                            }
                        }
                    }).start();
//                    User user = userDao.login(etEmail, etPassword);
                   // String name = user.getNamaLengkap();
                   // userPreferences.setLogin(etEmail, etPassword, name);

//                    loadPreferences();
//                    savePreferences(email.getText().toString().trim());
//                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                                finish();
//                            }
//                            else if (!(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()))
//                                Toast.makeText(LoginActivity.this, "Salah Format Email!", Toast.LENGTH_SHORT).show();
//                            else if(password.getText().toString().length()<6)
//                                Toast.makeText(LoginActivity.this, "Jumlah karakter password harus lebih dari 6!", Toast.LENGTH_SHORT).show();
//                            else {
//                                Toast.makeText(LoginActivity.this, "Email / password wrong", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
                }else{
                    if(etEmail.getText().toString().equals(""))
                        Toast.makeText(LoginActivity.this, "Email masih kosong!", Toast.LENGTH_SHORT).show();
                    else if (!(Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()))
                        Toast.makeText(LoginActivity.this, "Salah Format Email!", Toast.LENGTH_SHORT).show();
                    else if(etPassword.getText().toString().equals(""))
                        Toast.makeText(LoginActivity.this, "Password masih kosong!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText( LoginActivity.this,"Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }

    private void loadPreferences() {
        String name = "profile";
        preferences = getSharedPreferences(name, mode);
        if (preferences != null) {
            namaTemp = preferences.getString("Selamat Datang, ", "");
        }
    }

    private void savePreferences(String nama1) {
        SharedPreferences.Editor editor = preferences.edit();
        if (!nama1.isEmpty()) {
            editor.putString("Selamat Datang, ", nama1);
            editor.apply();
        } else {
            Toast.makeText(this, "Fill correctly", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkLogin() {
        if (userPreferences.checkLogin()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    public void sendOnChannel1(View v) {
        String title = "Berhasil Login";
        String message = "Terima kasih";

        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("toastMessage", message);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_emoji_food_beverage_24)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.RED)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
                .build();

        notificationManager.notify(1, notification);
    }

    public void sendOnChannel2(View v) {
        String title = "Berhasil Login";
        String message = "Terima kasih";

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_baseline_emoji_food_beverage_24)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify(2, notification);
    }
}
