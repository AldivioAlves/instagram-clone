package br.com.aldivio.estudos.instagram.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;

import br.com.aldivio.estudos.instagram.R;
import br.com.aldivio.estudos.instagram.helpers.Constants;
import br.com.aldivio.estudos.instagram.helpers.FirebaseUtils;
import br.com.aldivio.estudos.instagram.helpers.UserUtils;
import br.com.aldivio.estudos.instagram.helpers.Utils;
import br.com.aldivio.estudos.instagram.models.User;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class UpdateAccountActivity extends AppCompatActivity {

    private TextInputEditText editTextName, editTextEmail;
    private Button buttonSave;
    private CircleImageView imageProfile;
    private ProgressBar progressBar;
    private static final int SELECTION_CAMERA = 100;
    private static final int SELECTION_GALLERY = 200;
    User user;
    private final String[] requiredPermissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        initComponents();
        setListeners();
        loadUser();
    }

    private void initComponents() {
        editTextName = findViewById(R.id.activity_update_account_edit_text_name);
        editTextEmail = findViewById(R.id.activity_update_account_edit_text_email);
        editTextEmail.setEnabled(false);
        buttonSave = findViewById(R.id.activity_update_account_button_save);
        imageProfile = findViewById(R.id.activity_update_account_image_profile);
        progressBar = findViewById(R.id.activity_update_account_progress_bar);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        toolbar.setTitle("Editar Perfil");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

    }

    private void setListeners() {
        buttonSave.setOnClickListener(v -> {
            showProgressBar();
            if (bitmap != null) {
                String userUid = null;
                if (FirebaseUtils.getAuth() != null) {
                    userUid = FirebaseUtils.getAuth().getUid();
                }
                if (userUid == null) return;

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                byte[] dataImage = baos.toByteArray();

                StorageReference storageReference = FirebaseUtils.getStorage();
                StorageReference imageRef = storageReference
                        .child(Constants.IMAGES)
                        .child(Constants.PROFILE)
                        .child(userUid)
                        .child(Constants.PHOTO_NAME_TYPE);

                UploadTask uploadTask = imageRef.putBytes(dataImage);

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                Uri uri = task.getResult();
                                user.setPhoto(uri.toString());
                                if (!TextUtils.isEmpty(editTextName.getText())) {
                                    user.setName(editTextName.getText().toString());
                                }
                                user.updateUser();
                                hiddenProgressBar();
                                Toast.makeText(UpdateAccountActivity.this, "Sucesso na atualização :)", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateAccountActivity.this, "Erro no upload da foto :/ ", Toast.LENGTH_SHORT).show();
                        hiddenProgressBar();
                    }
                });
            } else {
                if (!TextUtils.isEmpty(editTextName.getText())) {
                    user.setName(editTextName.getText().toString());
                    user.updateUser();
                    hiddenProgressBar();
                    Toast.makeText(UpdateAccountActivity.this, "Sucesso na atualização :)", Toast.LENGTH_LONG).show();
                }
            }
        });

        imageProfile.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Escolha:")
                    .setPositiveButton("Camera", (dialog, which) -> {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(intent, SELECTION_CAMERA);
                        } else {
                            Toast.makeText(this, "Não foi possível abrir a camera!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Galeria", (dialog, which) -> {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(intent, SELECTION_GALLERY);
                        } else {
                            Toast.makeText(this, "Não foi possível abrir a galeria!", Toast.LENGTH_SHORT).show();
                        }
                    }).create().show();
        });
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hiddenProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) return;

        try {
            switch (requestCode) {
                case SELECTION_CAMERA:
                    bitmap = (Bitmap) data.getExtras().get("data");
                    break;
                case SELECTION_GALLERY:
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    break;
            }

            if (bitmap == null) return;
            Glide.with(this).load(bitmap).into(imageProfile);
            //imageProfile.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int resultPermission : grantResults) {
            if (resultPermission != PackageManager.PERMISSION_GRANTED) {
                alertPermissionsNotGranted();
            }
        }
    }

    private void alertPermissionsNotGranted() {

        DialogInterface.OnClickListener positiveAction = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar os recursos dessa tela é necessário aceitar as permissões.");
        builder.setPositiveButton("Confirmar", positiveAction);
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadUser(){
        Utils.validPermissions(requiredPermissions, this, SELECTION_CAMERA);
        Utils.validPermissions(requiredPermissions, this, SELECTION_GALLERY);

        UserUtils.FirebaseUserResultInterface firebaseUserResultInterface = user -> {
            this.user = user;
            editTextName.setText(user.getName());
            editTextEmail.setText(user.getEmail());
            if (!TextUtils.isEmpty(user.getPhoto())) {
                Glide.with(this)
                        .load(Uri.parse(user.getPhoto()))
                        .into(imageProfile);
            }
        };
        UserUtils.getDataCurrentUser(firebaseUserResultInterface);
    }
}