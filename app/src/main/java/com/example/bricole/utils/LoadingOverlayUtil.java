package com.example.bricole.utils;


import android.view.View;
import android.widget.FrameLayout;

public class LoadingOverlayUtil {

    public static void showLoading(FrameLayout loadingOverlay) {
        if (loadingOverlay != null) {
            loadingOverlay.setVisibility(View.VISIBLE);
        }
    }

    public static void hideLoading(FrameLayout loadingOverlay) {
        if (loadingOverlay != null) {
            loadingOverlay.setVisibility(View.GONE);
        }
    }
}
