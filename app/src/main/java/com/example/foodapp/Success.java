package com.example.foodapp;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Success extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_success);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView imageViewSuccess = findViewById(R.id.imageViewSuccess);
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewSubtitle = findViewById(R.id.textViewSubtitle);

        // Load animation
        Animation zoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        
        // Start animation on image
        imageViewSuccess.startAnimation(zoomIn);
        
        // Add a slight delay for text animations if desired, or start together
        textViewTitle.startAnimation(zoomIn);
        textViewSubtitle.startAnimation(zoomIn);

        // Play success sound
        playSuccessSound();
    }

    private void playSuccessSound() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), notification);
            if (mp != null) {
                mp.start();
                // Release player after sound finishes
                mp.setOnCompletionListener(MediaPlayer::release);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backToHome(View view) {
        Intent intent = new Intent(Success.this, AccessoriesList.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
