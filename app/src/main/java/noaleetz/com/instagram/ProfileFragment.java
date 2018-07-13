package noaleetz.com.instagram;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import noaleetz.com.instagram.Models.Post;

import static android.support.constraint.Constraints.TAG;


public class ProfileFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    RecyclerView rvSquare;
    private ProfilePostAdapter adapter;
    private List<Post> posts;



    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        rvSquare = view.findViewById(R.id.rvSquare);

        initRecycler();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void initRecycler() {
        posts = new ArrayList<>();
        adapter = new ProfilePostAdapter(getContext(), posts);
        rvSquare.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvSquare.addItemDecoration(
                new GridMargin(
                        getResources().getDimensionPixelSize(R.dimen.item_post_margin_h),
                        getResources().getDimensionPixelSize(R.dimen.item_post_grid_margin_v),
                        GridMargin.GRID
                )
        );
        rvSquare.setAdapter(adapter);


        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().getForUser(ParseUser.getCurrentUser()).withUser().orderByLastCreated();

        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    Log.d(TAG, Integer.toString(objects.size()));
                    for (int i = 0; i < objects.size(); i++) {
                        Log.d(TAG, "Post [" + i + "] = " + objects.get(i).getDescription()
                                + "\nusername: " + objects.get(i).getUser().getUsername());
                    }
                    posts.clear();
                    posts.addAll(objects);
                    adapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });

    }
}
