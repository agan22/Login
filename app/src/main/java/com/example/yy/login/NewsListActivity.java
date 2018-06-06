package com.example.yy.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class NewsListActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnZJ;
    Button btnYC;
    ProgressBar progressBar;
    ProgressBar progressBar2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        Intent intent=this.getIntent();
        String username=intent.getStringExtra("user");
        Toast.makeText(NewsListActivity.this,"欢迎"+username+"登录",Toast.LENGTH_LONG).show();

        btnYC=(Button)this.findViewById(R.id.btnYC);
        btnZJ=(Button)this.findViewById(R.id.btnZJ);
        btnYC.setOnClickListener(this);
        btnZJ.setOnClickListener(this);
        progressBar=(ProgressBar)this.findViewById(R.id.progressBar);
        progressBar2=(ProgressBar)this.findViewById(R.id.progressBar2);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnYC:
                if (progressBar.getVisibility()==View.GONE){
                    progressBar.setVisibility(View.VISIBLE);
                }else{
                    progressBar.setVisibility(View.GONE);
                }
                break;
            case R.id.btnZJ:
                int progress=progressBar2.getProgress();
                progress+=10;
                progressBar2.setProgress(progress);
                break;
            default:
        }
    }
}
