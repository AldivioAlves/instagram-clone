package br.com.aldivio.estudos.instagram.helpers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import br.com.aldivio.estudos.instagram.models.User;

public class UserUtils {
    private static final String TAG = "user-utils";

    public static FirebaseUser getFirebaseUser() {
        try {
            FirebaseAuth firebaseAuth = FirebaseUtils.getAuth();
            return firebaseAuth.getCurrentUser();
        } catch (Exception e) {
            return null;
        }
    }

    public static void getDataCurrentUser(FirebaseUserResultInterface firebaseUserResultInterface) {
        if (getFirebaseUser() == null) {
            firebaseUserResultInterface.onResult(null);
            return;
        }
        String uid = getFirebaseUser().getUid();
        DatabaseReference userRef = FirebaseUtils.getDatabaseReference()
                .child(Constants.USERS).child(uid);
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                try {
                    DataSnapshot dataSnapshot = task.getResult();
                    User user = dataSnapshot.getValue(User.class);
                    firebaseUserResultInterface.onResult(user);
                } catch (Exception e) {
                    Log.d(TAG, "erro recuperar o usuário =>> " + e.getMessage());
                    firebaseUserResultInterface.onResult(null);
                }
            }
        });
    }

    public interface FirebaseUserResultInterface {
        void onResult(User user);
    }

}
