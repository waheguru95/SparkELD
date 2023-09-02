package com.example.eld.alert;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eld.R;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for the dialog activity
        setContentView(R.layout.dialog_layout);

        // Get the dialog message from the intent
        String message = getIntent().getStringExtra("message");

        // Set the dialog message
        TextView messageTextView = findViewById(R.id.message_textview);
        messageTextView.setText(message);

        // Set up the "OK" button
        Button okButton = findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the dialog activity
                finish();
            }
        });
    }
}

