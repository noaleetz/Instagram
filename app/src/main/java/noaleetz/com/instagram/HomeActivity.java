package noaleetz.com.instagram;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements ProfileFragment.OnFragmentInteractionListener, NewPostFragment.OnItemSelectedListener {


    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d("HomeActivity", "Fragment Interaction");
    }

    private BottomNavigationView nvBottomBar;
    private ViewPager vpFragmentContainer;
    private ImageView ivImageToPost;



    private final List<Fragment> fragments = new ArrayList<>();

    // initialize adapter to give correct page
    private FragmentAdapter adapter;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);




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


    @Override
    public void onNewPostCreated() {
        ((FeedFragment) fragments.get(0)).loadTopPosts();
    }

    @Override
    public void switchFragment(int i) {
        vpFragmentContainer.setCurrentItem(i,true);
    }


    public void logout(View view) {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
        final Intent i = new Intent(HomeActivity.this,LoginActivity.class);
        startActivity(i);
        finish();


    }
    public void CreatePost(View view) {
        vpFragmentContainer.setCurrentItem(1,true);

    }
}
