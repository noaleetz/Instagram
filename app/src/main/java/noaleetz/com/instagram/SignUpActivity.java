package noaleetz.com.instagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;

public class SignUpActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    Button btRegister;
    EditText etEmail;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etUsername = findViewById(R.id.etUsername); // etUsername3 etUsername
        etPassword = findViewById(R.id.etPassword);
        btRegister = findViewById(R.id.btRegister);
        etEmail = findViewById(R.id.etEmail);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String email = etEmail.getText().toString();
                signup(username,password,email);
            }
        });

    }

    private void signup(String username, String password,String email) {
        final ParseUser user = new ParseUser();
// Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_myperson);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] image = stream.toByteArray();
        final ParseFile parseFile = new ParseFile(image);

        parseFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                user.put("profilePic",parseFile);
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            // no errors!
                            Log.d("LoginActivity","Sign Up was successful!");
                            final Intent i = new Intent(SignUpActivity.this,HomeActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            // Sign up didn't succeed. Look at the ParseException
                            // to figure out what went wrong
                            Log.e("SignUpActivity","Sign Up Failed");
                            e.printStackTrace();
                        }
                    }
                });
            }
        });







        //user.setEmail("email@example.com");
// Set custom properties
        //user.put("phone", "650-253-0000");
// Invoke signUpInBackground

    }
}
