package com.example.bricole.utils;
import static java.security.AccessController.getContext;

import com.example.bricole.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FireBaseUtils {

    private StorageReference storageReference;

    public FireBaseUtils() {
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public static class ImageUploader {

        public interface UploadCallback {
            void onSuccess(List<String> downloadUrls);
            void onFailure(String errorMessage);
        }

        // Method to upload images
        public void uploadImages(String productId, List<Uri> imageUris, UploadCallback callback) {
            // Get current logged-in user ID
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            if (userId == null || userId.isEmpty()) {
                callback.onFailure("User not logged in.");
                return;
            }

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference().child("Products").child(userId).child(productId);

            List<String> downloadUrls = new ArrayList<>();
            AtomicInteger uploadCount = new AtomicInteger(0);

            for (int i = 0; i < imageUris.size(); i++) {
                Uri imageUri = imageUris.get(i);
                StorageReference imageRef = storageRef.child("image" + (i + 1) + ".jpg");

                // Start uploading each image
                imageRef.putFile(imageUri)
                        .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl()
                                .addOnSuccessListener(uri -> {
                                    downloadUrls.add(uri.toString());
                                    if (uploadCount.incrementAndGet() == imageUris.size()) {
                                        callback.onSuccess(downloadUrls);
                                    }
                                })
                                .addOnFailureListener(e -> callback.onFailure("Failed to retrieve download URL: " + e.getMessage())))
                        .addOnFailureListener(e -> callback.onFailure("Failed to upload image: " + e.getMessage()));
            }
        }
    }

    public static void SaveProduct(String productId, Product product){
        // Reference to the "Products" node in the database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Products");

        databaseReference.child(productId).setValue(product)
                .addOnSuccessListener(aVoid -> {
//                    Toast.makeText(getContext(), "sucsses", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
//                    Toast.makeText(getContext(), "sucsess", Toast.LENGTH_SHORT).show();

                });
    }
}
