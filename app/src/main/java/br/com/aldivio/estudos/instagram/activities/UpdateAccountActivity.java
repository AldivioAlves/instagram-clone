package br.com.aldivio.estudos.instagram.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import br.com.aldivio.estudos.instagram.R;
import de.hdodenhof.circleimageview.CircleImageView;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class UpdateAccountActivity extends AppCompatActivity {

    private TextInputEditText editTextName, editTextEmail;
    private Button buttonSave;
    private CircleImageView imageProfile;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        initComponents();
        setListeners();
    }

    private void initComponents(){
        editTextName = findViewById(R.id.activity_update_account_edit_text_name);
        editTextEmail = findViewById(R.id.activity_update_account_edit_text_email);
        buttonSave = findViewById(R.id.activity_update_account_button_save);
        imageProfile = findViewById(R.id.activity_update_account_image_profile);
        toolbar = findViewById(R.id.main_toolbar);
        toolbar.setTitle("Editar Perfil");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
    }
    private void setListeners(){
        buttonSave.setOnClickListener(v->{

        });
    }
}