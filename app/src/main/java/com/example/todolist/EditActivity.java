package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    EditText etItem;
    Button btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etItem = findViewById(R.id.etItem);
        btnSave = findViewById(R.id.btnSave);

        getSupportActionBar().setTitle("Edit Item");

        // When user is done editing. They click save button
        etItem.setText( getIntent().getStringExtra(MainActivity.Key_item_text) );
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create intent of the results
                Intent intent  = new Intent();
                // pass the results ( Results of editing )
                intent.putExtra(MainActivity.Key_item_text, etItem.getText().toString());
                intent.putExtra(MainActivity.Key_item_pos, getIntent().getExtras().getInt(MainActivity.Key_item_pos));
                // Set the results of the intent
                setResult(RESULT_OK, intent);
                // finish the activity, close the screen and go back
                finish();
            }
        });
    }
}
