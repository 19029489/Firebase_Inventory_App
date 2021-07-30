package com.example.firebaseinventoryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class AddItemActivity extends AppCompatActivity {

    private static final String TAG = "AddItemActivity";

    private EditText etName, etCost;
    private Button btnSave;
    private ArrayList<String> al = new ArrayList<String>();
    private List alChecked = new ArrayList<String>();
    private LinearLayout layout;

    private FirebaseFirestore db;
    private CollectionReference colRef;
    private DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        etName = (EditText) findViewById(R.id.editTextName);
        etCost = (EditText) findViewById(R.id.editTextCost);
        btnSave = (Button) findViewById(R.id.buttonSave);
        layout = (LinearLayout) findViewById(R.id.linearLayout);

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

                        //Adding checkbox dynamically
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

                        btnSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (!(etName.getText().toString().equalsIgnoreCase("") && etCost.getText().toString().equalsIgnoreCase(""))) {
                                    Item item = new Item(etName.getText().toString(), Integer.parseInt(etCost.getText().toString()), alChecked);

                                    colRef
                                            .add(item)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                                    Intent i = new Intent(AddItemActivity.this, MainActivity.class);
                                                    startActivity(i);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error adding document", e);
                                                }
                                            });

                                    finish();
                                }
                            }
                        });
                    }
                });


    }
}