package noaleetz.com.instagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;

import java.util.List;

import noaleetz.com.instagram.Models.Post;

public class ProfilePostAdapter extends RecyclerView.Adapter<ProfilePostAdapter.ViewHolder> {

    public final List<Post> posts;
    private final Context context;

    public ProfilePostAdapter(Context context, List<Post> posts) {
        this.posts = posts;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.item_gridpost, parent, false);

        return new ViewHolder(postView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Post post = posts.get(i);

        try {
            Glide.with(context)
                    .load(post.getImage().getFile())
                    .into(viewHolder.ivPost);


            // TODO - set profile pic of poster of post

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivPost;

        public ViewHolder(View itemView) {
            super(itemView);

            ivPost = (ImageView) itemView.findViewById(R.id.ivPost);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    // TODO- open details of pic in profile grid
//                    final Intent i = new Intent(HomeActivity.this,LoginActivity.class);
//                    startActivity(i);
//                    finish();

                }
            });


//            itemView.setOnClickListener(this);
//
//            //findViewById lookups
//            ivProfile = (ImageView) itemView.findViewById(R.id.ivProfile);
//            ivPostedImage = (ImageView) itemView.findViewById(R.id.ivPostedImage);
//            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
//            tvLocation = (TextView) itemView.findViewById(R.id.tvLocation);
//            tvCaption = (TextView) itemView.findViewById(R.id.tvCaption);
//

        }



    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }
}
