package com.tac.blaze.ui;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tac.blaze.R;

import java.util.Objects;

public class BasicInfoActivity extends AppCompatActivity {

    private TextInputEditText fname,lname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);
        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first= Objects.requireNonNull(fname.getText()).toString();
                String last= Objects.requireNonNull(lname.getText()).toString();

                if(first!=null && last!=null) {
                    Toast.makeText(BasicInfoActivity.this, first, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BasicInfoActivity.this, RegisterActivity.class);
                    intent.putExtra("FIRST", first);
                    intent.putExtra("LAST", last);
                    Toast.makeText(BasicInfoActivity.this, first+last, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(BasicInfoActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
