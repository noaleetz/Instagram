package noaleetz.com.instagram;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class NewPostFragment extends Fragment {

    private EditText etDescriptionInput;
    private Button btCreate;
    private Button btRefresh;
    private static final String imagePath = "/sdcard/DCIM/Camera/IMG_20180710_113618.jpg"; // TODO - enter image path


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_post, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etDescriptionInput = view.findViewById(R.id.etDescriptionInput);
        btCreate = view.findViewById(R.id.btCreate);
        btRefresh = view.findViewById(R.id.btRefresh);

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

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
