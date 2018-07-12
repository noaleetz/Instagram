package noaleetz.com.instagram;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    String imagePath;


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





}
