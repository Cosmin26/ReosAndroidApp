package ro.ubb.reosandroidapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import ro.ubb.reosandroidapp.globals.Globals;
import ro.ubb.reosandroidapp.model.Apartment;
import ro.ubb.reosandroidapp.repository.ApartmentRepository;
import ro.ubb.reosandroidapp.service.ObserverService;

public class AddApartmentActivity extends AppCompatActivity {

//    private ObserverService.Obser remoteService;
    private int PICK_IMAGE_REQUEST = 1;
    private ApartmentRepository apartmentRepository;
    public ImageView imageView;
    public Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apartment);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
        this.apartmentRepository = Globals.apartmentRepository;

        imageView = findViewById(R.id.addApartment);

        spinner = (Spinner) findViewById(R.id.cost_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cost_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    public void addClick(View view) {
        EditText updateNameTextField = findViewById(R.id.addText);
        int cost = Integer.parseInt((String) spinner.getSelectedItem());
        EditText updateImageTextField = findViewById(R.id.addImage);
//        Bitmap image = ((BitmapDrawable) this.imageView.getDrawable()).getBitmap();
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] imageArray = stream.toByteArray();
//        Apartment apartment = new Apartment(updateNameTextField.getText().toString(), imageArray
//                , cost);
        Globals.apartmentRepository.addApartment(Globals.personId, updateNameTextField.getText().toString(), updateImageTextField.getText().toString(),cost);
        ObserverService.notifyAllObservers();
        Intent intent = new Intent(this, Navigation.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        Intent returnIntent = new Intent();
//        returnIntent.putExtra("passed_item", Globals.apartmentRepository.getAll());
        // setResult(RESULT_OK);
        setResult(RESULT_OK, returnIntent); //By not passing the intent in the result, the calling activity will get null data.
        super.finish();
    }

//    public void choosePicture(View view) {
//        Intent intent = new Intent();
//        // Show only images, no videos or anything else
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        // Always show the chooser (if there are multiple options available)
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
//    }
//
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
