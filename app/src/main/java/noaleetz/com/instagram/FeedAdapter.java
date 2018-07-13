package noaleetz.com.instagram;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.parse.ParseException;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import noaleetz.com.instagram.Models.Post;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    // 1- pass in post array (give adapter data)

    public List<Post> mposts;
    private Context mcontext;

    public FeedAdapter(List<Post> posts) {
        mposts = posts;





    }
//2- inflate EACH row, cache references into ViewHolder

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mcontext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mcontext);

        View postView = inflater.inflate(R.layout.item_post, parent, false);


        //getItemViewType();


        return new ViewHolder(postView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Post post = mposts.get(position);

        try {
            Glide.with(mcontext)
                    .load(post.getImage().getFile())
                    .into(holder.ivPostedImage);


            // TODO - set profile pic of poster of post

        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tvCaption.setText(post.getDescription());
        holder.tvLocation.setText(post.getLocation());
        holder.tvUsername.setText(post.getUser().getUsername());

        final RoundedCornersTransformation roundedCornersTransformation = new RoundedCornersTransformation(15,15);
        final RequestOptions requestOptions = RequestOptions.bitmapTransform(roundedCornersTransformation);



        Glide.with(mcontext)
                .load(post.getUser().getParseFile("profilePic").getUrl())
                .apply(requestOptions)
                .into(holder.ivProfile);


        // TODO- require location whe making post

        holder.tvLocation.setText(post.getLocation());

    }

    @Override
    public int getItemCount() {
        return mposts.size();
    }

    //4- make a ViewHolder class

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivPostedImage;

        public TextView tvUsername;
        public TextView tvLocation;
        public TextView tvCaption;
        public ImageView ivProfile;


        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            //findViewById lookups
            ivProfile = (ImageView) itemView.findViewById(R.id.ivProfile);
            ivPostedImage = (ImageView) itemView.findViewById(R.id.ivPostedImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvLocation = (TextView) itemView.findViewById(R.id.tvLocation);
            tvCaption = (TextView) itemView.findViewById(R.id.tvCaption);


        }

        @Override
        public void onClick(View view) {
            final Intent i = new Intent(mcontext,DetailActivity.class);
            mcontext.startActivity(i);
        }



    }

    // Clean all elements of the recycler
    public void clear() {
        mposts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mposts.addAll(list);
        notifyDataSetChanged();
    }
}

