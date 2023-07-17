package br.com.aldivio.estudos.instagram.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AuthenticationRequiredException;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.aldivio.estudos.instagram.R;
import br.com.aldivio.estudos.instagram.helpers.FirebaseUtils;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkUserLogin();
        initComponents();
        setListeners();
    }

    private void checkUserLogin(){
        FirebaseAuth firebaseAuth = FirebaseUtils.getAuth();
        if(firebaseAuth.getCurrentUser() != null){
            finishLogin();
        }
    }

    private void initComponents() {
        editTextEmail = findViewById(R.id.activity_login_edit_text_email);
        editTextPassword = findViewById(R.id.activity_login_edit_text_password);
        textViewRegister = findViewById(R.id.activity_login_text_view_register);
        buttonLogin = findViewById(R.id.activity_login_button_login);
        progressBar = findViewById(R.id.activity_login_progress_bar);
    }

    private void setListeners() {
        buttonLogin.setOnClickListener(v -> {
            login();
        });
        textViewRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void login() {
        String email = this.editTextEmail.getText().toString();
        String password = this.editTextPassword.getText().toString();
        if(!validEntries(email,password))return;
        progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth firebaseAuth = FirebaseUtils.getAuth();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.INVISIBLE);
                boolean isSuccess = task.isSuccessful();
                if(isSuccess){
                    Toast.makeText(LoginActivity.this, "Sucesso no login!", Toast.LENGTH_SHORT).show();
                    finishLogin();
                }else{
                    Toast.makeText(LoginActivity.this, "Ocorreu um erro no login.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validEntries(String email, String password) {
        String messageError = "";
        if (TextUtils.isEmpty(email)) {
            messageError += "É necessario preencher o email!\n";
        }
        if (TextUtils.isEmpty(password)) {
            messageError += "É necessário preencher a senha!";
        }
        if (!TextUtils.isEmpty(messageError)) {
            Toast.makeText(this, messageError, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private void finishLogin(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}