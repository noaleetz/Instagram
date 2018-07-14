package noaleetz.com.instagram.Models;

import org.parceler.Parcel;

import java.util.Date;

@Parcel
public class PostObj {
    public String avatarUrl;
    public String username;
    public String imageUrl;
    public String description;
    public Date date;
    public int likeCount;
}
