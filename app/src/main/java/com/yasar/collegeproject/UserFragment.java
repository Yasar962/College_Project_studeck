package com.yasar.collegeproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserFragment extends Fragment {

    private Button attendancebtn, feebtn, button_logout;
    private TextView usernameText, emailText;
    private FirebaseUser currentUser;
    private DatabaseReference userRef;

    public UserFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        // Initialize UI
        button_logout = view.findViewById(R.id.logout);
        attendancebtn = view.findViewById(R.id.attendance);
        feebtn = view.findViewById(R.id.fee);
        usernameText = view.findViewById(R.id.usernameText);
        emailText = view.findViewById(R.id.emailText);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String uid = currentUser.getUid();
            userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String name = snapshot.child("name").getValue(String.class);
                        String email = snapshot.child("enroll").getValue(String.class);

                        usernameText.setText(name != null ? name : "No Name");
                        emailText.setText(email != null ? email : "No Email");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    usernameText.setText("Error loading name");
                    emailText.setText("Error loading email");
                }
            });
        }

        button_logout.setOnClickListener(view1 -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(requireContext(), loginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        feebtn.setOnClickListener(view12 -> {
            String url = "https://eazypay.icicibank.com/userIPut.action";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
        attendancebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), att_activity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
