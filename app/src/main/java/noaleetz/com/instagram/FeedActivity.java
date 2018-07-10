package noaleetz.com.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

import noaleetz.com.instagram.Models.Post;





public class FeedActivity extends AppCompatActivity {

    private EditText etDescriptionInput;
    private Button btCreate;
    private Button btRefresh;
    private static final String imagePath = "/sdcard/DCIM/Camera/IMG_20180710_113618.jpg"; // TODO - enter image path


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        //Toolbar toolbar = (Toolbar) findViewById(toolbar);
        //setSupportActionBar(toolbar);

        etDescriptionInput = findViewById(R.id.etDescriptionInput);
        btCreate = findViewById(R.id.btCreate);
        btRefresh = findViewById(R.id.btRefresh);

        // asking user for perm


        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String description = etDescriptionInput.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();
                final File file = new File(imagePath);
                final ParseFile parseFile = new ParseFile(file);

                parseFile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null) {
                            // no errors saving post
                            Log.d("FeedActivity","saving complete");
                            createPost(description,parseFile,user);
                        }
                        else{
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
        btRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTopPosts();
            }
        });



    }
    private void createPost(String description, ParseFile imageFile, ParseUser user) {
        // TODO- create and save post
        final Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setImage(imageFile);
        newPost.setUser(user);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
             if(e == null) {
                 // no error
                Log.d("FeedActivity","Create Post Success!");
             }
             else{
                e.printStackTrace();
             }
            }
        });

    }

    public void onPickPhoto(View view) {
        // creating intent
        // built in act. implicit
        //

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        // img = data.getExtra('....')



        // parse
    }

    private void loadTopPosts() {

        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser();

        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e == null) {
                    // no error
                    for(int i=0;i<objects.size();i++){
                        Log.d("FeedActivity","Post["+i+"] = "
                                + objects.get(i).getDescription()
                                + "\nusername = " + objects.get(i).getUser().getUsername());
                    }
                }
                else{
                    e.printStackTrace();
                }
            }
        });

    }
}
