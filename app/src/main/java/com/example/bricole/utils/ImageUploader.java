package com.example.bricole.utils;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.net.Uri;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageUploader {

    private final Cloudinary cloudinary;
    private final DatabaseReference databaseReference;

    public ImageUploader() {
        // Initialize Cloudinary
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dug1ddkjx",
                "api_key", "382692217971161",
                "api_secret", "kDWH1PlCzcZmG-L1KqbKv1UGGhU",
                "secure", true
        ));

        // Initialize Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("images");
    }

    public void saveImageAlternative(List<Uri> selectedImages, String userId, String productId) {
        for (Uri imageUri : selectedImages) {
            // Convert Uri to file path (depends on your app's file management)
            String filePath = imageUri.getPath();

            // Upload image to Cloudinary
            new Thread(() -> {
                try {
                    Map uploadResult = cloudinary.uploader().upload(filePath, ObjectUtils.emptyMap());
                    String imageUrl = (String) uploadResult.get("url");

                    // Prepare image metadata
                    String imageId = databaseReference.push().getKey();
                    long timestamp = System.currentTimeMillis();
                    Map<String, Object> imageData = new HashMap<>();
                    imageData.put("image_id", imageId);
                    imageData.put("user_id", userId);
                    imageData.put("belong_to_product_id", productId);
                    imageData.put("timestamp", timestamp);
                    imageData.put("urlImg", imageUrl);

                    // Save metadata to Firebase Realtime Database
                    databaseReference.child(imageId).setValue(imageData).addOnSuccessListener(aVoid -> {
                        System.out.println("Image metadata successfully stored with ID: " + imageId);
                    }).addOnFailureListener(e -> {
                        System.err.println("Failed to store image metadata: " + e.getMessage());
                    });
                } catch (IOException e) {
                    System.err.println("Failed to upload image to Cloudinary: " + e.getMessage());
                }
            }).start();
        }
    }
}
