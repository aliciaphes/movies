package com.example.android.movies.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.movies.R;
import com.example.android.movies.utils.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OfflineActivity extends AppCompatActivity {

    @BindView(R.id.retry_button)
    Button mRetryButton;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);

        mContext = this;
        ButterKnife.bind(this);

        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utilities.isNetworkAvailable(mContext) && Utilities.isOnline()) {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(mContext, R.string.check_connection, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
