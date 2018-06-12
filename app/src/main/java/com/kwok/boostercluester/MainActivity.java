package com.kwok.boostercluester;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener{

    Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.generate_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.generate_button){
            Intent intent = new Intent(this, BoosterActivity.class);
            EditText editText = findViewById(R.id.set_edittext);
            String set = editText.getText().toString();
            if (!set.isEmpty()){
                intent.putExtra("SET", set);
                startActivity(intent);
            }
        }
    }
}
