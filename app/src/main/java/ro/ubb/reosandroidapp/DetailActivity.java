package ro.ubb.reosandroidapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import ro.ubb.reosandroidapp.globals.Globals;
import ro.ubb.reosandroidapp.model.Apartment;
import ro.ubb.reosandroidapp.repository.ApartmentRepository;
import ro.ubb.reosandroidapp.service.DownloadImageTask;
import ro.ubb.reosandroidapp.service.ObserverService;

public class DetailActivity extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 1;
    private final ApartmentRepository apartmentRepository = Globals.apartmentRepository;
    private Apartment selectedApartment;
    public TextView titleText;
    public ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);

        titleText = findViewById(R.id.nameTxtDetail);
        imageView = findViewById(R.id.apartmentImageDetail);

        //RECEIVE
        Intent i = this.getIntent();
//        String name = i.getExtras().getString("TITLE_KEY");
//        Bitmap imageExtra = BitmapFactory.decodeByteArray(
//                i.getByteArrayExtra("IMAGE_KEY"), 0,
//                getIntent().getByteArrayExtra("IMAGE_KEY").length);
        String id = i.getExtras().getString("ID_KEY");

        this.selectedApartment = this.apartmentRepository.getApartmentById(id);
        //BIND
        titleText.setText(this.selectedApartment.getTitle());
        new DownloadImageTask(imageView)
                .execute(this.selectedApartment.getImage());
//        imageView.setImageBitmap(BitmapFactory.decodeByteArray(this.selectedApartment.getImage(),
//                0, this.selectedApartment.getImage().length));

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public void editClick(View view) {
        if(Globals.personId.equals(this.selectedApartment.getUserId())) {
            EditText updateNameTextField = findViewById(R.id.updateText);
//        Bitmap image = ((BitmapDrawable) this.imageView.getDrawable()).getBitmap();
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] imageArray = stream.toByteArray();

            EditText updateImageTextField = findViewById(R.id.updateImage);

            this.selectedApartment.setImage(updateImageTextField.getText().toString());
            this.selectedApartment.setTitle(updateNameTextField.getText().toString());
            this.apartmentRepository.updateApartment(Globals.personId, this.selectedApartment);
            ObserverService.notifyAllObservers();
            Intent intent = new Intent(this, Navigation.class);
            startActivity(intent);
            finish();
        }
    }

    public void deleteClick(View view) {
        if(Globals.personId.equals(this.selectedApartment.getUserId())) {
            final Intent intent = new Intent(this, Navigation.class);
            final Apartment apartmentToDelete = this.selectedApartment;
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            apartmentRepository.removeApartment(Globals.personId, apartmentToDelete.getId());
                            ObserverService.notifyAllObservers();

                            startActivity(intent);
                            finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("Are you sure you want to delete this?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }

    }

//    public void choosePicture(View view) {
//        Intent intent = new Intent();
//        // Show only images, no videos or anything else
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        // Always show the chooser (if there are multiple options available)
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//
//            Uri uri = data.getData();
//
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                // Log.d(TAG, String.valueOf(bitmap));
//
//                this.imageView.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}

