package br.com.aldivio.estudos.instagram.helpers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseUtils {
    private static DatabaseReference databaseReference;
    private static FirebaseAuth firebaseAuth;
    private static StorageReference storageReference;

    public static FirebaseAuth getAuth(){
        if(firebaseAuth == null){
            firebaseAuth  = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

    public static DatabaseReference getDatabaseReference(){
        if(databaseReference == null){
            databaseReference  = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }

    public static StorageReference getStorage(){
        if(storageReference == null){
            storageReference = FirebaseStorage.getInstance().getReference();
        }
        return storageReference;
    }

}
