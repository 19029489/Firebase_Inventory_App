package com.example.firebaseinventoryapp;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class ItemDetailsActivity extends AppCompatActivity {

    private static final String TAG = "ItemDetailsActivity";

    private EditText etName, etCost;
    private Button btnSave;
    private LinearLayout layout;

    private ArrayList<String> al = new ArrayList<String>();
    private List alChecked = new ArrayList<String>();

    private Item item;

    private FirebaseFirestore db;
    private CollectionReference colRef;
    private DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        etName = (EditText) findViewById(R.id.editTextName);
        etCost = (EditText) findViewById(R.id.editTextCost);
        btnSave = (Button) findViewById(R.id.buttonSave);
        layout = (LinearLayout) findViewById(R.id.linearLayout);

        Intent intent = getIntent();
        String id = (String) intent.getStringExtra("itemId");

        db = FirebaseFirestore.getInstance();

        db.collection("options")
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }

                    for (QueryDocumentSnapshot snapshot1 : snapshot) {
                        if (snapshot != null) {
                            al.add("" + snapshot1.get("name"));
                        }
                    }

//                    Adding checkbox dynamically
                    CheckBox checkBox1 = new CheckBox(getApplicationContext());
                    checkBox1.setText(al.get(0));
                    checkBox1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked == true) {
                                alChecked.add(checkBox1.getText().toString());
                            } else {
                                alChecked.remove(checkBox1.getText().toString());
                            }
                        }
                    });

                    CheckBox checkBox2 = new CheckBox(getApplicationContext());
                    checkBox2.setText(al.get(1));
                    checkBox2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked == true) {
                                alChecked.add(checkBox2.getText().toString());
                            } else {
                                alChecked.remove(checkBox2.getText().toString());
                            }
                        }
                    });

                    CheckBox checkBox3 = new CheckBox(getApplicationContext());
                    checkBox3.setText(al.get(2));
                    checkBox3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked == true) {
                                alChecked.add(checkBox3.getText().toString());
                            } else {
                                alChecked.remove(checkBox3.getText().toString());
                            }
                        }
                    });

                    CheckBox checkBox4 = new CheckBox(getApplicationContext());
                    checkBox4.setText(al.get(3));
                    checkBox4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked == true) {
                                alChecked.add(checkBox4.getText().toString());
                            } else {
                                alChecked.remove(checkBox4.getText().toString());
                            }
                        }
                    });

                    // Add Checkbox to LinearLayout
                    if (layout != null) {
                        layout.addView(checkBox1);
                        layout.addView(checkBox2);
                        layout.addView(checkBox3);
                        layout.addView(checkBox4);
                    }


                    colRef = db.collection("items");

                    docRef = colRef.document(id);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot snapshot = task.getResult();
                                if (snapshot.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + snapshot.getData());
                                    item = snapshot.toObject(Item.class);
                                    etName.setText(item.getName());
                                    etCost.setText("" + item.getCost());
                                    List<String> options = item.getOptions();

                                    if (options.size() > 0){
                                        for (int r = 0; r < options.size(); r++){
                                            if (checkBox1.getText().toString().equalsIgnoreCase(options.get(r))) {
                                                checkBox1.setChecked(true);
                                            } else if (checkBox2.getText().toString().equalsIgnoreCase(options.get(r))) {
                                                checkBox2.setChecked(true);
                                            } else if (checkBox3.getText().toString().equalsIgnoreCase(options.get(r))) {
                                                checkBox3.setChecked(true);
                                            } else if(checkBox4.getText().toString().equalsIgnoreCase(options.get(r))) {
                                                checkBox4.setChecked(true);
                                            }
                                        }
                                    }

                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });

                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!(etName.getText().toString().equalsIgnoreCase("") && etCost.getText().toString().equalsIgnoreCase(""))) {
                                String name = etName.getText().toString();
                                int cost = Integer.parseInt(etCost.getText().toString());
                                Item item = new Item(name, cost, alChecked);
                                docRef.set(item);
                            }

                            Toast.makeText(getApplicationContext(), "Item updated successfully", Toast.LENGTH_SHORT).show();

                            finish();
                        }
                    });
                }

            });


    }
}
