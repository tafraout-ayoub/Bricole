package com.example.bricole;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class test1 extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener{
    CheckBox checkBarcelona, checkRalMadrid, checkLiverpool;
    RadioGroup favPlayersRb ;
    ProgressBar progressBar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test1);

        checkBarcelona = findViewById(R.id.check1);
        checkRalMadrid = findViewById(R.id.check2);
        checkLiverpool = findViewById(R.id.check3);

        favPlayersRb = findViewById(R.id.rdGroup);
        progressBar = findViewById(R.id.progress);

//        if (checkBarcelona.isChecked()){Toast.makeText(test1.this, "You Have Chosen ", Toast.LENGTH_LONG).show();}
        checkBarcelona.setOnCheckedChangeListener(this);
        checkRalMadrid.setOnCheckedChangeListener(this);
        checkLiverpool.setOnCheckedChangeListener(this);

        favPlayersRb.setOnCheckedChangeListener(this);

//        checkBarcelona.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (checkBarcelona.isChecked()){Toast.makeText(test1.this, "You Have Chosen the best! ", Toast.LENGTH_LONG).show();}
//                else {Toast.makeText(test1.this, "You need to Choose !! ", Toast.LENGTH_LONG).show();}
//            }
//        });
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
        if (isChecked){
            Toast.makeText(test1.this, "you chose"+buttonView.getText().toString(), Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(test1.this, "You need to Choose!! ", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        String value="";
        if (checkedId == R.id.rbMessi){value = "Messi";progressBar.setVisibility(View.GONE);}
        else if (checkedId == R.id.rbLeva){value = "Leva";progressBar.setVisibility(View.GONE);}
        else if (checkedId == R.id.rbRonaldo){value = "Ronaldo";progressBar.setVisibility(View.VISIBLE);}
        else {value = "Unknown";}
        Toast.makeText(test1.this, "you have chosen => "+value+" Fav Player,", Toast.LENGTH_SHORT).show();
    }
}
