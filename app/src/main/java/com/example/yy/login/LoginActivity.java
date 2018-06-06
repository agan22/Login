package com.example.yy.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    Button btnLoin;
    EditText username;
    EditText userpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLoin=(Button)this.findViewById(R.id.btnLoin);
        username=(EditText)this.findViewById(R.id.username);
        userpass=(EditText)this.findViewById(R.id.userpass) ;
        btnLoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user=username.getText().toString();
                String pass=userpass.getText().toString();
                if (user.equalsIgnoreCase("tom")&&pass.equalsIgnoreCase("123")) {
                    Intent intent = new Intent(LoginActivity.this, NewsListActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
            }
        });
    }
}
