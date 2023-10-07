package com.example.WorldApp;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Firebase {

    public void create() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("C:\\Users\\User\\Downloads\\WorldApp (1)\\WorldApp\\src\\main\\resources\\static\\memorylord-81a9b-firebase-adminsdk-w2g6r-137d5d359d.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);


    }
    public void baad(String fieldName) throws ExecutionException, InterruptedException, IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            create();
        }
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("Feedback").document("feedback");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot documentSnapshot = future.get();
        if (documentSnapshot.exists()) {
            Map<String, Object> data = documentSnapshot.getData();
            if (data != null) {
                int currentValue = ((Long) data.get(fieldName)).intValue();
                int newValue = currentValue + 1;
                Map<String, Object> updateData = new HashMap<>();
                updateData.put(fieldName, newValue);
                ApiFuture<WriteResult> updateFuture = docRef.update(updateData);
                System.out.println("Update time: " + updateFuture.get().getUpdateTime());
            }
        }
    }


    public void addData(String email , String topic , String decription) throws ExecutionException, InterruptedException, IOException {

        if (FirebaseApp.getApps().isEmpty()) {
            create();
        }
        Firestore db = FirestoreClient.getFirestore();

        Map<String, Object> docData = new HashMap<>();
        docData.put("ideea", decription);
// Add a new document (asynchronously) in collection "cities" with id "LA"
        ApiFuture<WriteResult> future = db.collection("Feedback").document("user").collection(email).document(topic).set(docData);
// ...
// future.get() blocks on response
        System.out.println("Update time : " + future.get().getUpdateTime());



    }





}
