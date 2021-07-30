package com.example.firebaseinventoryapp;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView lvItem;
    private ArrayList<Item> alItem;
    private ArrayAdapter aaItem;

    private FirebaseFirestore db;
    private CollectionReference colRef;
    private DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItem = (ListView)findViewById(R.id.listViewItems);

        db = FirebaseFirestore.getInstance();

        colRef = db.collection("items");

        alItem = new ArrayList<Item>();
        aaItem = new ItemAdapter(getApplicationContext(), R.layout.row, alItem);
        lvItem.setAdapter(aaItem);

        colRef
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot snapshot,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }

                    alItem.clear();
                    aaItem.notifyDataSetChanged();
                    
                    for (QueryDocumentSnapshot snapshot1 : snapshot) {
                        if (snapshot != null) {
                            Item item = snapshot1.toObject(Item.class);
                            alItem.add(item);
                            item.setId(snapshot1.getId());
                        }
                        aaItem.notifyDataSetChanged();
                    }

                }
            });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.addItem) {

            Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}