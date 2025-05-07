package com.yasar.collegeproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class notes_adapter extends FirebaseRecyclerAdapter<notes_model,notes_adapter.viewholder> {
    public notes_adapter(@NonNull FirebaseRecyclerOptions<notes_model> options) {
        super(options);
    }

    @Override

    protected void onBindViewHolder(@NonNull viewholder holder, int position, @NonNull notes_model model) {
        holder.title.setText(model.getTitle());
        Log.d("BindViewHolder", "Note: " + model.getTitle());
        holder.itemView.setOnClickListener(v -> {
            String url = model.getUrl();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            v.getContext().startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        int count = super.getItemCount();
        Log.d("NotesAdapter", "Item Count: " + count);
        return count;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_recycle,parent,false);
        return new viewholder(view);
    }

    class viewholder extends RecyclerView.ViewHolder{
        TextView title;
        public viewholder(View itemview){
            super(itemview);
            title=(TextView)itemview.findViewById(R.id.notes_title);

        }
    }
}


