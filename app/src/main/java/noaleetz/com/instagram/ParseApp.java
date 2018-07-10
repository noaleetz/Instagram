package noaleetz.com.instagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import noaleetz.com.instagram.Models.Post;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Post.class);
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("instagram")
                .clientKey("instagram")
                .server("http://noaleetz-fbu-instagram.herokuapp.com/parse")
                .build();

    Parse.initialize(configuration);
    }
}
