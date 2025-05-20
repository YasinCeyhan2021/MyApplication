package com.example.myapplication;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class User {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public User() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    // Mevcut kullanıcıyı döner
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    // Firestore'a userId ekler
    private void addUserIdToFirestore(String userId) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("userId", userId);

        db.collection("users")
                .document(userId)
                .set(userMap)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "User ID Firestore'a eklendi: " + userId))
                .addOnFailureListener(e -> Log.w("Firestore", "User ekleme hatası", e));
    }

    /**
     * Kullanıcı var mı kontrol eder, yoksa anonim giriş yapar.
     * Başarılıysa listener çağrılır.
     */
    public void checkOrCreateUser(OnCompleteListener<AuthResult> listener) {
        FirebaseUser currentUser = getCurrentUser();

        if (currentUser != null) {
            Log.d("Auth", "Mevcut kullanıcı bulundu: " + currentUser.getUid());
            // Mevcut kullanıcı olduğunda, başarılı bir sonuçla listener çağırmak için:
            listener.onComplete(Tasks.forResult(null)); // null yerine uygun AuthResult verilebilir veya custom Task
        } else {
            mAuth.signInAnonymously()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            FirebaseUser newUser = task.getResult().getUser();
                            if (newUser != null) {
                                Log.d("Auth", "Yeni anonim kullanıcı: " + newUser.getUid());
                                addUserIdToFirestore(newUser.getUid());
                            }
                        }
                        listener.onComplete(task);
                    });
        }
    }

}
