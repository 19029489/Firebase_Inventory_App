package com.example.firebaseinventoryapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ItemAdapter extends ArrayAdapter<Item> {

    private ArrayList<Item> item;
    private Context context;
    private TextView tvItem;
    private ImageView ivEdit, ivDelete;

    private FirebaseFirestore db;
    private CollectionReference colRef;
    private DocumentReference docRef;

    public ItemAdapter(Context context, int resource, ArrayList<Item> objects){
        super(context, resource, objects);
        item = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // The usual way to get the LayoutInflater object to
        //  "inflate" the XML file into a View object
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // "Inflate" the row.xml as the layout for the View object
        View rowView = inflater.inflate(R.layout.row, parent, false);

        // Get the TextView object
        tvItem = (TextView) rowView.findViewById(R.id.tvItem);

        Item currentItem = item.get(position);

        tvItem.setText(currentItem.getName());

        ivEdit = rowView.findViewById(R.id.ivEdit);
        ivDelete = rowView.findViewById(R.id.ivDelete);

        db = FirebaseFirestore.getInstance();

        colRef = db.collection("items");
        docRef = colRef.document(currentItem.getId());

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("itemId", currentItem.getId());
                context.startActivity(intent);
            }
        });

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docRef
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error deleting document", e);
                        }
                    });

                Toast.makeText(context, "Item deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });

        // Return the nicely done up View to the ListView
        return rowView;
    }

}