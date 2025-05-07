package com.yasar.collegeproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<ChatMessage> chatList;

    public ChatAdapter(List<ChatMessage> chatList) {
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatMessage message = chatList.get(position);
        if (message.isUser()) {
            holder.userText.setVisibility(View.VISIBLE);
            holder.aiText.setVisibility(View.GONE);
            holder.userText.setText(message.getMessage());
        } else {
            holder.aiText.setVisibility(View.VISIBLE);
            holder.userText.setVisibility(View.GONE);
            holder.aiText.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView userText, aiText;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            userText = itemView.findViewById(R.id.user_text);
            aiText = itemView.findViewById(R.id.ai_text);
        }
    }
}
