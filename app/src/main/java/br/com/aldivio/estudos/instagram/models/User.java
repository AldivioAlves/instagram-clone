package br.com.aldivio.estudos.instagram.models;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

import br.com.aldivio.estudos.instagram.helpers.Constants;
import br.com.aldivio.estudos.instagram.helpers.FirebaseUtils;

public class User implements Serializable {
    private String name, email, id, password, photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void save() {
        DatabaseReference databaseReference = FirebaseUtils.getDatabaseReference();
        databaseReference
                .child(Constants.Users)
                .child(this.id)
                .setValue(this);
    }
}
