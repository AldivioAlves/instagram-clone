package br.com.aldivio.estudos.instagram.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.com.aldivio.estudos.instagram.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPassword;
    private Button buttonRegister;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initComponents();
        setListeners();
    }


    private void initComponents(){
        editTextName = findViewById(R.id.activity_register_edit_text_name);
        editTextEmail = findViewById(R.id.activity_register_edit_text_email);
        editTextPassword = findViewById(R.id.activity_register_edit_text_password);
        buttonRegister = findViewById(R.id.activity_register_button_register);
        progressBar = findViewById(R.id.activity_register_progress_bar);
    }

    private void setListeners(){
        buttonRegister.setOnClickListener(v -> {

        });

    }
}