package br.com.aldivio.estudos.instagram.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import br.com.aldivio.estudos.instagram.R;
import br.com.aldivio.estudos.instagram.helpers.FirebaseUtils;
import br.com.aldivio.estudos.instagram.models.User;

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


    private void initComponents() {
        editTextName = findViewById(R.id.activity_register_edit_text_name);
        editTextEmail = findViewById(R.id.activity_register_edit_text_email);
        editTextPassword = findViewById(R.id.activity_register_edit_text_password);
        buttonRegister = findViewById(R.id.activity_register_button_register);
        progressBar = findViewById(R.id.activity_register_progress_bar);
    }

    private void setListeners() {
        buttonRegister.setOnClickListener(v -> {
            if (!validEntries()) return;
            progressBar.setVisibility(View.VISIBLE);
            registerUser();
        });
    }

    private boolean validEntries() {
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String messageError = "";

        if (TextUtils.isEmpty(name)) {
            messageError += "É necessário preencher um nome\n";
        }
        if (TextUtils.isEmpty(email)) {
            messageError += "É necessário preencher um email válido\n";
        }
        if (TextUtils.isEmpty(password)) {
            messageError += "É necessário preencher uma senha!";
        }
        if (!TextUtils.isEmpty(messageError)) {
            Toast.makeText(this, messageError, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void registerUser() {
        FirebaseAuth firebaseAuth = FirebaseUtils.getAuth();
        String name =editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.INVISIBLE);
                    boolean isSuccess = task.isSuccessful();
            if (isSuccess) {
                FirebaseUser firebaseUser = task.getResult().getUser();
                if (firebaseUser != null) {
                    User user = new User();
                    user.setId(firebaseUser.getUid());
                    user.setEmail(email);
                    user.setName(name);
                    user.save();
                    Toast.makeText(RegisterActivity.this, "Sucesso no Cadastro", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Ocorreu um erro no cadastro!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            String messageError = "";
            try {
                throw task.getException();
            } catch (FirebaseAuthWeakPasswordException e) {
                messageError = "Digite uma senha mais forte!";
            } catch (FirebaseAuthInvalidCredentialsException e) {
                messageError = "Digite um email válido!";
            } catch (FirebaseAuthUserCollisionException e) {
                messageError = "Já existe um cadastro com esses dados!";
            } catch (Exception e) {
                messageError = "Ocorreu um erro no cadastro: " + e.getMessage();
            }
            Toast.makeText(RegisterActivity.this, messageError, Toast.LENGTH_LONG).show();
        });
    }
}