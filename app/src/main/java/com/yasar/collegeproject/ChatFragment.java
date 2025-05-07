package com.yasar.collegeproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatFragment extends Fragment {

    private RecyclerView chatRecyclerView;
    private EditText messageInput;
    private ImageButton sendButton;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatList = new ArrayList<>();

    private final OkHttpClient client = new OkHttpClient();
    private final String GEMINI_API_KEY = "AIzaSyBze7IkXCRIzFjtHnD345qBgaR6kMAxt_s";
    private final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + GEMINI_API_KEY;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        chatRecyclerView = view.findViewById(R.id.chat_recycler_view);
        messageInput = view.findViewById(R.id.message_input);
        sendButton = view.findViewById(R.id.send_button);

        chatAdapter = new ChatAdapter(chatList);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatRecyclerView.setAdapter(chatAdapter);


        sendMessageToGemini("Introduce Yourself as a ai assistant for my app called studeck");
        sendButton.setOnClickListener(v -> {
            String userMessage = messageInput.getText().toString().trim();
            if (!userMessage.isEmpty()) {
                addMessage(userMessage, true);
                messageInput.setText("");
                sendMessageToGemini(userMessage);
            }
        });

        return view;
    }

    private void addMessage(String message, boolean isUser) {
        requireActivity().runOnUiThread(() -> {
            chatList.add(new ChatMessage(message, isUser));
            chatAdapter.notifyItemInserted(chatList.size() - 1);
            chatRecyclerView.scrollToPosition(chatList.size() - 1);
        });
    }

    public void sendMessageToGemini(String prompt) {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");

        JsonObject part = new JsonObject();
        part.addProperty("text", prompt);

        JsonObject content = new JsonObject();
        JsonArray partsArray = new JsonArray();
        partsArray.add(part);

        JsonObject contentItem = new JsonObject();
        contentItem.add("parts", partsArray);

        JsonArray contents = new JsonArray();
        contents.add(contentItem);

        JsonObject bodyObj = new JsonObject();
        bodyObj.add("contents", contents);

        String requestBodyString = bodyObj.toString();
        RequestBody body = RequestBody.create(requestBodyString, JSON);

        Log.d("ChatFragment", "Request URL: " + GEMINI_API_URL);
        Log.d("ChatFragment", "Request Body: " + requestBodyString);

        Request request = new Request.Builder()
                .url(GEMINI_API_URL)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                String errorMessage = "Failed: " + e.getMessage();
                Log.e("ChatFragment", errorMessage);
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseBody = response.body().string();
                Log.d("ChatFragment", "Response Code: " + response.code());
                Log.d("ChatFragment", "Response Body: " + responseBody);

                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = new Gson().fromJson(responseBody, JsonObject.class);
                        JsonArray candidates = jsonObject.getAsJsonArray("candidates");
                        if (candidates != null && candidates.size() > 0) {
                            JsonObject candidate = candidates.get(0).getAsJsonObject();
                            JsonObject responseContent = candidate.getAsJsonObject("content");
                            JsonArray parts = responseContent.getAsJsonArray("parts");
                            String reply = parts.get(0).getAsJsonObject().get("text").getAsString();
                            addMessage(reply.trim(), false);
                        } else {
                            showError("Empty response from Gemini");
                        }
                    } catch (Exception e) {
                        showError("Error parsing JSON: " + e.getMessage());
                    }
                } else {
                    showError("Error: " + response.code() + " " + response.message());
                }
            }
        });
    }

    private void showError(String message) {
        Log.e("ChatFragment", message);
        requireActivity().runOnUiThread(() ->
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show()
        );
    }
}