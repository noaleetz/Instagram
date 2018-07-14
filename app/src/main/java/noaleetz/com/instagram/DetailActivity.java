package noaleetz.com.instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import noaleetz.com.instagram.Models.PostObj;

public class DetailActivity extends AppCompatActivity {

    ImageView ivProfilePhoto;
    TextView tvUsername;
    TextView tvCreatedTime;
    TextView tvLikesCt;
    ImageView ivPostedImage;
    ImageView ivHeartIcon;
    ImageView ivProfilePhotoC;
    ImageView ivProfilePhotoC2;
    TextView tvUsername2;
    Button btSubmit;
    EditText etComment;
    TextView tvResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvUsername = findViewById(R.id.tvUsername);
        ivProfilePhoto = findViewById(R.id.ivProfilePhoto);
        tvCreatedTime = findViewById(R.id.tvCreatedTime);
        tvLikesCt = findViewById(R.id.tvLikesCt);
        ivPostedImage = findViewById(R.id.ivPostedImage);
        ivHeartIcon = findViewById(R.id.ivHeartIcon);
        ivProfilePhotoC = findViewById(R.id.ivProfilePhotoC);
        ivProfilePhotoC2 = findViewById(R.id.ivProfilePhotoC2);
        tvUsername2 = findViewById(R.id.tvUsername2);
        btSubmit = findViewById(R.id.btSubmit);
        etComment = findViewById(R.id.etComment);
        tvResult = findViewById(R.id.tvResult);



        final PostObj post =  Parcels.unwrap(getIntent().getParcelableExtra("post"));

        tvUsername.setText(post.username);
        tvCreatedTime.setText(post.date.toString());
        tvLikesCt.setText(Integer.toString(post.likeCount));
        tvUsername2.setText(post.username);

        tvResult.setVisibility(View.INVISIBLE);




        final RoundedCornersTransformation roundedCornersTransformation = new RoundedCornersTransformation(30,30);
        final RequestOptions requestOptions = RequestOptions.bitmapTransform(roundedCornersTransformation);
        // load post item photo
            Glide.with(DetailActivity.this)
                    .load(post.imageUrl)
                    .apply(requestOptions)
                    .into(ivPostedImage);

        // load user comment profile

            Glide.with(DetailActivity.this)
                    .load(post.avatarUrl)
                    .apply(requestOptions)
                    .into(ivProfilePhotoC2);

        Glide.with(DetailActivity.this)
                .load(post.avatarUrl)
                .apply(requestOptions)
                .into(ivProfilePhoto);

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etComment.setVisibility(View.INVISIBLE);
                String comment = etComment.getText().toString();
                tvResult.setVisibility(View.VISIBLE);
                tvResult.setText(comment);




            }
        });







    }
}
