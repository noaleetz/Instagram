package noaleetz.com.instagram.Models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {
private static final String KEY_DESCRIPTION = "Description";
private static final String KEY_IMAGE = "Image";
private static final String KEY_USER = "User";
private static final String KEY_LOCATION = "Location";
private static final String KEY_PIC = "ProfilePic";
private static final String KEY_LIKES = "likes";



    public String getTimeCreatedAt() { return getCreatedAt().toString();}

    public int getLikes() { return getInt(KEY_LOCATION);}
    public void  setLikes(int likes) { put(KEY_LIKES,likes);}

    public String getLocation() { return getString(KEY_LOCATION);}
    public void  setLocation(String location) { put(KEY_LOCATION,location);}

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }
    public void setDescription(String description) {
        put(KEY_DESCRIPTION,description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }
    public void setImage(ParseFile image){
        put(KEY_IMAGE,image);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user){
        put(KEY_USER,user);
    }

    public static class Query extends ParseQuery<Post>{
        public Query() {
            super(Post.class);
        }
        public Query getTop(){
            setLimit(20);
            return this;
        }
        public Query withUser() {
               include("User");
               return this;
        }
        public Query orderByLastCreated() {
                orderByDescending("createdAt");
                return this;
        }

        public Query getForUser(ParseUser currentUser) {
            whereEqualTo(KEY_USER, currentUser);
            return this;
        }
    }

}
