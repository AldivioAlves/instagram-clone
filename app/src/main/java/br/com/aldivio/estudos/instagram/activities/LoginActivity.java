package br.com.aldivio.estudos.instagram.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.com.aldivio.estudos.instagram.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();
        setListeners();
    }

    private void initComponents(){
        editTextEmail = findViewById(R.id.activity_login_edit_text_email);
        editTextPassword = findViewById(R.id.activity_login_edit_text_password);
        textViewRegister = findViewById(R.id.activity_login_text_view_register);
        buttonLogin = findViewById(R.id.activity_login_button_login);
        progressBar = findViewById(R.id.activity_login_progress_bar);
    }

    private void setListeners(){
        buttonLogin.setOnClickListener(v -> {

        });
        textViewRegister.setOnClickListener(v->{
            Intent intent = new Intent(this,RegisterActivity.class);
            startActivity(intent);
        });
    }
}