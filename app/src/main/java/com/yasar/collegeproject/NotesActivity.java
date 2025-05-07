package com.yasar.collegeproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private notes_adapter adapter;
    private List<notes_model> notesList;
    private DatabaseReference databaseReference;
    Toolbar toolbar_n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        toolbar_n=findViewById(R.id.toolbarn);
        toolbar_n.setTitle("StuDeck");
        toolbar_n.setTitleMarginStart(150);
        toolbar_n.setTitleTextColor(getResources().getColor(R.color.white));

        recyclerView=findViewById(R.id.rv_notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<notes_model> options = new FirebaseRecyclerOptions.Builder<notes_model>()
                .setQuery(FirebaseDatabase.getInstance().getReference()
                        .child("Notes"),notes_model.class).build();
        adapter=new notes_adapter(options);
        recyclerView.setAdapter(adapter);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Automata Unit 1");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("FirebaseTest", "Children count: " + snapshot.getChildrenCount());
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Log.d("FirebaseTest", "Note title: " + snap.child("title").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseTest", "Error: " + error.getMessage());
            }
        });


    }
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }


}