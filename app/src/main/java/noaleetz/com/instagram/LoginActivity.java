package noaleetz.com.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    Button btLogin;
    Button btSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);
        btSignUp = findViewById(R.id.btSignUp);

        if(ParseUser.getCurrentUser() != null) {
            final Intent i = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(i);
            finish();
        }


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                login(username,password);
            }
        });

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(i);
                finish();


            }
        });



    }

    private void login(String username, String password) {
        // TODO - set up login
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null) {
                    // no errors!
                    Log.d("LoginActivity","Login was successful!");
                    final Intent i = new Intent(LoginActivity.this,FeedActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    // Login failed!
                    Log.e("LoginActivity","Login Failed");
                    e.printStackTrace();
                }
            }
        });
    }


}
