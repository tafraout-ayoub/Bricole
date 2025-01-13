package com.example.bricole.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ImageUtils {

    public static List<Uri> getSelectedImages(Intent data, int maxImages, Context context) {
        List<Uri> selectedImages = new ArrayList<>();
        if (data.getClipData() != null) {
            int count = data.getClipData().getItemCount();
            for (int i = 0; i < count && selectedImages.size() < maxImages; i++) {
                selectedImages.add(data.getClipData().getItemAt(i).getUri());
            }
        } else if (data.getData() != null) {
            selectedImages.add(data.getData());
        }

        if (selectedImages.size() > maxImages) {
            Toast.makeText(context, "You can select up to " + maxImages + " images.", Toast.LENGTH_SHORT).show();
        }
        return selectedImages;
    }
}

