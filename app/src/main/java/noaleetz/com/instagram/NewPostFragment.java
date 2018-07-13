package noaleetz.com.instagram;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import noaleetz.com.instagram.Models.BitmapScaler;
import noaleetz.com.instagram.Models.Post;

import static android.app.Activity.RESULT_OK;

public class NewPostFragment extends Fragment {

    // Define the listener of the interface type
    // listener will the activity instance containing fragment
    private OnItemSelectedListener listener;

    private EditText etDescriptionInput;
    private EditText etLocationInput;
    private Button btCreate;
    private Button btRefresh;
    private static String imagePath = ""; // TODO - enter image path
    public ImageView ivImageToPost;
    public ImageView ivProfile;
    public TextView tvHandle;
    public TextView tvUsername;

    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;
    private SwipeRefreshLayout swipeContainer;



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


    // Define the events that the fragment will use to communicate
    public interface OnItemSelectedListener {
        // This can be any number of events to be sent to the activity
        public void onNewPostCreated();
        public void switchFragment(int i);
    }

    // Store the listener (activity) that will have events fired once the fragment is attached
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }



        @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        etDescriptionInput = view.findViewById(R.id.etDescriptionInput);
        btCreate = view.findViewById(R.id.btCreate);
        btRefresh = view.findViewById(R.id.btRefresh);
        etLocationInput = view.findViewById(R.id.etLocationInput);
        ivProfile = view.findViewById(R.id.ivProfile);
        ivImageToPost = view.findViewById(R.id.ivImageToPost);
        tvUsername = view.findViewById(R.id.tvUsername);
        tvHandle = view.findViewById(R.id.tvHandle);

        final RoundedCornersTransformation roundedCornersTransformation = new RoundedCornersTransformation(15,15);
        final RequestOptions requestOptions = RequestOptions.bitmapTransform(roundedCornersTransformation);





            ParseUser currentUser = ParseUser.getCurrentUser();

//            TODO- profile pic

//            Log.w("kjh", ParseUser.getCurrentUser().getUsername());
            Glide.with(this)
                    .load(ParseUser.getCurrentUser().getParseFile("profilePic").getUrl())
                    .apply(requestOptions)
                    .into(ivProfile);



//            Glide.with(this)
//                    .load(currentUser.getPic)
//                    .apply(requestOptions)
//                    .into(ivProfile);

            tvUsername.setText(currentUser.getUsername());
            tvHandle.setText("@" + currentUser.getUsername());



            ivImageToPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLaunchCamera();


            }
        });

        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String description = etDescriptionInput.getText().toString();
                final String location = etLocationInput.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();
                final File file = new File(imagePath);
                final ParseFile parseFile = new ParseFile(file);

                parseFile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null) {
                            // no errors saving post
                            Log.d("FeedActivity","saving complete");
                            createPost(description,parseFile,user,location);
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
                listener.onNewPostCreated();
            }
        });


    }

    private void createPost(String description, ParseFile imageFile, ParseUser user,String location) {
        // TODO- create and save post
        final Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setImage(imageFile);
        newPost.setLocation(location);
        newPost.setUser(user);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null) {
                    // no error
                    Log.d("FeedActivity","Create Post Success!");
                    listener.onNewPostCreated();
                    listener.switchFragment(0);
                }
                else{
                    e.printStackTrace();
                }
            }
        });

    }


    public void onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
//        Uri fileProvider = FileProvider.getUriForFile(HomeActivity.this, "com.codepath.fileprovider", photoFile);
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }



    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
//        File mediaStorageDir = new File(HomeActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                imagePath = photoFile.getAbsolutePath();
                // RESIZE BITMAP, see section below
//                ImageView ivImageToPost = ((NewPostFragment) fragments.get(1)).ivImageToPost;
                ImageView ivImageToPost = (ImageView) getActivity().findViewById(R.id.ivImageToPost);



                Bitmap resizedBitmap = BitmapScaler.scaleToFitWidth(takenImage, ivImageToPost.getWidth());

//
                ivImageToPost.setImageBitmap(resizedBitmap);
            } else { // Result was a failure
//                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();

            }
        }
    }







}
