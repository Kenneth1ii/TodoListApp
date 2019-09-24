package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String Key_item_text = "item text";
    public static final String Key_item_pos = "item position";
    public static final int Edit_text_code = 20;


    List<String> items;

    Button btnAdd;                  // handlers
    EditText etItem;
    RecyclerView rvItem;
    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd); // handlers get by id.
        etItem = findViewById(R.id.editem);
        rvItem = findViewById(R.id.rvitems);

        loadItems();

        ItemAdapter.OnLongClickListener onLongClickListener = new ItemAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                // delete the item from the model
                items.remove(position);
                // notify the adapter
                itemAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Item Removed",Toast.LENGTH_LONG).show();
                saveItem();
            }
        };


        ItemAdapter.onClickListener onClickListener = new ItemAdapter.onClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.d("Main activity","singleclicked" + position);

                // create new edit activity
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                // pass the data being edited
                i.putExtra( Key_item_text, items.get(position));
                i.putExtra(Key_item_pos, position);
                // display the edit activity
                startActivityForResult(i,Edit_text_code);
            }
        };


        itemAdapter = new ItemAdapter(items , onLongClickListener, onClickListener); // from constructor class.
        rvItem.setAdapter(itemAdapter);
        rvItem.setLayoutManager(new LinearLayoutManager(this)); //vertical

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = etItem.getText().toString();
                // Add item to node
                items.add(todoItem);
                // Notify adapter that an item is inserted.
                itemAdapter.notifyItemInserted(items.size()-1);
                etItem.setText("");
                // Quick text notification.
                Toast.makeText(getApplicationContext(),"Item Added",Toast.LENGTH_LONG).show();
                saveItem();
            }
        });
     }



     // handle the results of the edit activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == Edit_text_code) {
            // Retrieve updated text value
            String itemText = data.getStringExtra(Key_item_text);
            // extract the original position of edit item from position key
            int position = data.getExtras().getInt(Key_item_pos);

            // update the model at the right position with new item text
            items.set(position, itemText);
            //notify adapter
            itemAdapter.notifyItemChanged(position);
            //persist the changes
            saveItem();
            Toast.makeText(getApplicationContext(), "Item updated", Toast.LENGTH_SHORT).show();
        } else {
            Log.w("MainActivity", "Unknown call to onActivityResult");
        }
    }



    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
     }

     // Load files
    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("Main Activity", "Error Reading items", e);
            items = new ArrayList<>();
        }
    }
    // Saved
    private void saveItem() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("Main Activity" ,"Error writing items", e);
        }
    }
}
