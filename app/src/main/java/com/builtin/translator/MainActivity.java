package com.builtin.translator;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.material.circularreveal.cardview.CircularRevealCardView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech tts;
    private ImageView play_btn;
    private TextView text;
    private String lastTranslatedText = ""; // Variable to store the last translated text
    RelativeLayout result_view;
    private boolean isTTSInitialized = false; // Flag to track TTS initialization


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //edge is handy
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText editText = findViewById(R.id.input_editText);
        CircularRevealCardView translateButton = findViewById(R.id.translate_btn);
        TextView resultTextView = findViewById(R.id.result);
        result_view = findViewById(R.id.result_layout);
        text = findViewById(R.id.text);
        play_btn = findViewById(R.id.play);

        result_view.setVisibility(result_view.GONE);

        // Initialize Python
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        // Initialize TextToSpeech
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(Locale.CHINESE);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    isTTSInitialized = true; // Set the flag to true if TTS is ready
                    // Handle error, language data missing or language not supported
                }
            } else {
                // Initialization failed
            }
        });

        translateButton.setOnClickListener(v -> {
            //start result scenes
            String textToTranslate = editText.getText().toString();
            text.setText(textToTranslate);
            result_view.setVisibility(View.VISIBLE);
            PyObject translate = Python.getInstance().getModule("transp1")
                    .callAttr("translate_and_print", textToTranslate, "zh-TW");
            lastTranslatedText = translate.toString(); // Store the translated text
            resultTextView.setText(lastTranslatedText); // Display the translated text
        });

        play_btn.setOnClickListener(v -> {
            speak(lastTranslatedText); // Speak the stored translated text
        });
    }

    // Modify your speak method to check this flag:
    private void speak(String text) {
        if (tts != null && isTTSInitialized) { // Check the flag here
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
