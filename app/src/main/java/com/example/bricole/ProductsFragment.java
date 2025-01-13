package com.example.bricole;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bricole.Adapters.ImageAdapter;
import com.example.bricole.Product;
import com.example.bricole.utils.ImageUploader;
import com.example.bricole.utils.ImageUtils;
import com.example.bricole.utils.FireBaseUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class ProductsFragment extends Fragment {
    //Import all of the inputs of the product form
    EditText editTextProductName, editTextProductDescription, editTextProductPrice, editTextProductQuantity;
    private static final int MAX_IMAGES = 3;
    private RecyclerView recyclerViewImages;
    private ImageAdapter imageAdapter;
    private List<Uri> selectedImages = new ArrayList<>();
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private FireBaseUtils.ImageUploader imageUploader;
    private FirebaseAuth mAuth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Initialize and inflate the products layout
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        imageUploader = new FireBaseUtils.ImageUploader();
        // Initialize the Views elements
        Button buttonPickImages = view.findViewById(R.id.buttonPickImages);
        Button buttonSaveProduct = view.findViewById(R.id.buttonSubmitProduct);
        recyclerViewImages = view.findViewById(R.id.recyclerViewImages);
        editTextProductName = view.findViewById(R.id.editTextProductName);
        editTextProductDescription = view.findViewById(R.id.editTextProductDescription);
        editTextProductPrice = view.findViewById(R.id.editTextProductPrice);
        editTextProductQuantity = view.findViewById(R.id.editTextProductQuantity);


        // Set up RecyclerView
        recyclerViewImages.setLayoutManager(new GridLayoutManager(getContext(), 3));
        imageAdapter = new ImageAdapter(selectedImages);
        recyclerViewImages.setAdapter(imageAdapter);

        // Initialize image picker launcher
        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                selectedImages = ImageUtils.getSelectedImages(result.getData(), MAX_IMAGES, getContext());
                imageAdapter.updateImages(selectedImages);

                if (selectedImages.size() >= MAX_IMAGES) {
                    Toast.makeText(getContext(), "You can select up to 3 images.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Handle image picker button click
        buttonPickImages.setOnClickListener(v -> openImagePicker());

        // Handle save product button click
        buttonSaveProduct.setOnClickListener(v -> saveProduct());

        return view;
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // Allow multiple selections
        imagePickerLauncher.launch(intent);
    }

    private void saveProduct() {
//        if(validateInputs()){
//
//            mAuth = FirebaseAuth.getInstance();
//            String userId = mAuth.getCurrentUser().getUid();
//
//            Double price = Double.parseDouble(editTextProductPrice.getText().toString());
//            int qte = Integer.parseInt(editTextProductQuantity.getText().toString());
//            String name = editTextProductName.getText().toString(),
//            desc = editTextProductDescription.toString().toString();
//
//            Product product = new Product(userId, "",name, desc, price, qte);
//        }else
//        {validateInputs();}
        ImageUploader img = new ImageUploader();
        img.saveImageAlternative(selectedImages,"user01", "Prod°1");


    }

    private boolean validateInputs() {
        boolean isValid = true;

//         Validate Product Name
        if (editTextProductName.getText().toString().trim().isEmpty()) {
            editTextProductName.setError("Product name is required");
            isValid = false;
        }

        // Validate Product Description
        if (editTextProductDescription.getText().toString().trim().isEmpty()) {
            editTextProductDescription.setError("Product description is required");
            isValid = false;
        }

        // Validate Product Price
        String priceText = editTextProductPrice.getText().toString().trim();
        if (priceText.isEmpty()) {
            editTextProductPrice.setError("Product price is required");
            isValid = false;
        } else {
            try {
                double price = Double.parseDouble(priceText);
                if (price <= 0) {
                    editTextProductPrice.setError("Product price must be greater than zero");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                editTextProductPrice.setError("Invalid price format");
                isValid = false;
            }
        }

        // Validate Product Quantity
        String quantityText = editTextProductQuantity.getText().toString().trim();
        if (quantityText.isEmpty()) {
            editTextProductQuantity.setError("Product quantity is required");
            isValid = false;
        } else {
            try {
                int quantity = Integer.parseInt(quantityText);
                if (quantity <= 0) {
                    editTextProductQuantity.setError("Product quantity must be greater than zero");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                editTextProductQuantity.setError("Invalid quantity format");
                isValid = false;
            }
        }

        // Validate Selected Images
        if (selectedImages.isEmpty()) {
            Toast.makeText(getContext(), "Please select at least one image", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

    private void uploadProductImages(String productId, List<Uri> imageUris) {
        imageUploader.uploadImages(productId, imageUris, new FireBaseUtils.ImageUploader.UploadCallback() {
            @Override
            public void onSuccess(List<String> downloadUrls) {
                System.out.println(downloadUrls);
            }

            @Override
            public void onFailure(String errorMessage) {
                System.out.println(errorMessage);

            }
        });
    }

//    private void saveProduct() {
//        if (validateInputs()) {
//            mAuth = FirebaseAuth.getInstance();
//            String userId = mAuth.getCurrentUser().getUid();
//
//            Double price = Double.parseDouble(editTextProductPrice.getText().toString());
//            int qte = Integer.parseInt(editTextProductQuantity.getText().toString());
//            String name = editTextProductName.getText().toString();
//            String desc = editTextProductDescription.getText().toString();
//
//            // Generate a unique product ID
//            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Products");
//            String productId = databaseReference.push().getKey();
//
//            // Create a Product object with the generated ID
//            Product product = new Product(productId, userId, name, desc, price, qte);
//
//            // Save the product to the database
//            databaseReference.child(productId).setValue(product)
//                    .addOnCompleteListener(databaseTask -> {
//                        if (databaseTask.isSuccessful()) {
//                            Toast.makeText(getContext(),
//                                    "Registration successful, but failed to save user data!",
//                                    Toast.LENGTH_LONG).show();
//                        } else {
//                            // Show an error message if database write fails
//                            Toast.makeText(getContext(),
//                                    "Registration  not successful, but failed to save user data!",
//                                    Toast.LENGTH_LONG).show();
//                        }
//                    });
//        } else {
//            validateInputs(); // Show validation errors
//        }
//    }
}
