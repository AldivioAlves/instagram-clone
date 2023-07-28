package br.com.aldivio.estudos.instagram.models;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import br.com.aldivio.estudos.instagram.helpers.Constants;
import br.com.aldivio.estudos.instagram.helpers.FirebaseUtils;

public class User implements Serializable {

    public User(String name, String email, String id, String photo) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.photo = photo;
    }

    public User(){}

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
                .child(Constants.USERS)
                .child(this.id)
                .setValue(this);
    }

    public void updateUser(){
        String userId = FirebaseUtils.getAuth().getUid();
        if(userId ==null) return;
        DatabaseReference userRef = FirebaseUtils.getDatabaseReference()
                .child(Constants.USERS).child(userId);

        Map<String,Object> userValues = convertToMap();
        userRef.updateChildren(userValues);
    }
    public Map<String, Object> convertToMap(){
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("email", getEmail());
        userMap.put("name", getName());
        userMap.put("photo",getPhoto());
        return userMap;
    }
}
