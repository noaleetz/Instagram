package noaleetz.com.instagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import noaleetz.com.instagram.Models.BitmapScaler;

public class HomeActivity extends AppCompatActivity implements ProfileFragment.OnFragmentInteractionListener{

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d("HomeActivity", "Fragment Interaction");
    }

    private BottomNavigationView nvBottomBar;
    private ViewPager vpFragmentContainer;
    private ImageView ivImageToPost;

    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;


    private final List<Fragment> fragments = new ArrayList<>();


    // initialize adapter to give correct page
    private FragmentAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        fragments.add(new FeedFragment());
        fragments.add(new NewPostFragment());
        fragments.add(new ProfileFragment());

        adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);


        vpFragmentContainer = findViewById(R.id.vpFragmentContainer);
        vpFragmentContainer.setAdapter(adapter);
        nvBottomBar = findViewById(R.id.nvBottomBar);
        ivImageToPost = findViewById(R.id.ivImageToPost);



        vpFragmentContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                switch(i) {
                    case 0:
                        nvBottomBar.setSelectedItemId(R.id.ic_home);
                        break;
                    case 1:
                        nvBottomBar.setSelectedItemId(R.id.ic_add);
                        onLaunchCamera();
                        break;
                    case 2:
                        nvBottomBar.setSelectedItemId(R.id.ic_profile);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int i) {

            }



        });



        nvBottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.ic_home:
                        vpFragmentContainer.setCurrentItem(0,true);
                        return true;
                    case R.id.ic_add:
                        vpFragmentContainer.setCurrentItem(1,true);
                        return true;
                    case R.id.ic_profile:
                        vpFragmentContainer.setCurrentItem(2,true);
                        return true;
                    default:
                        return false;
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
        Uri fileProvider = FileProvider.getUriForFile(HomeActivity.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(HomeActivity.this.getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }



    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(HomeActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

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
                // RESIZE BITMAP, see section below
                ImageView ivImageToPost = ((NewPostFragment) fragments.get(1)).ivImageToPost;
                Bitmap resizedBitmap = BitmapScaler.scaleToFitWidth(takenImage, ivImageToPost.getWidth());
                // Load the taken image
                ivImageToPost.setImageBitmap(resizedBitmap);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }



}
