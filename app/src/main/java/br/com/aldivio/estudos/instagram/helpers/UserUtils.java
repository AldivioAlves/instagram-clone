package br.com.aldivio.estudos.instagram.helpers;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import br.com.aldivio.estudos.instagram.models.User;

public class UserUtils {

    public static FirebaseUser getFirebaseUser() {
        FirebaseAuth firebaseAuth = FirebaseUtils.getAuth();
        return firebaseAuth.getCurrentUser();
    }

    public static User getDataCurrentUser(FirebaseUserResultInterface firebaseUserResultInterface) {
        String uid = getFirebaseUser().getUid();
        DatabaseReference userRef = FirebaseUtils.getDatabaseReference()
                .child(Constants.USERS).child(uid);
         userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot = task.getResult();
                User user = dataSnapshot.getValue(User.class);
                firebaseUserResultInterface.onResult(user);
            }
        });
         return null;
    }

    public interface FirebaseUserResultInterface {
        void onResult(User user);
    }

}
