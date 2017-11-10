package ro.ubb.reosandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView nameTxt;
    ImageView img;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        nameTxt = findViewById(R.id.nameTxtDetail);
        img = findViewById(R.id.apartmentImageDetail);

        //RECEIVE
        Intent i = this.getIntent();
        String name = i.getExtras().getString("TITLE_KEY");
        Integer imgs = i.getExtras().getInt("TAG_KEY");
        position = i.getExtras().getInt("POSITION_KEY");

        //BIND
        nameTxt.setText(name);
        img.setImageResource(imgs);
        img.setTag(imgs);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void btnClick(View view) {
        EditText updateNameTextField = findViewById(R.id.updateText);

        String title = updateNameTextField.getText().toString();


        Intent intent = new Intent(this, Navigation.class);
        intent.putExtra("TITLE_KEY", title);
        intent.putExtra("POSITION_KEY", position);
        startActivity(intent);

        //PACK DATA
    }
}

