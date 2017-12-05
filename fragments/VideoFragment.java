package ie.wit.explorewaterford.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import ie.wit.explorewaterford.R;


public class VideoFragment extends Fragment implements YouTubePlayer.OnInitializedListener{

    public static final String DEVELOPER_KEY = "Hidden";
    private static final String VIDEO_ID = "Fbd2W7dyeDA";
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    YouTubePlayer youTubePlayer;
    public static YouTubePlayerFragment youTubePlayerFragment;

    public VideoFragment() {
        //Required empty public constructor
    }

    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.activity_video_fragment, container, false);

        youTubePlayerFragment = new YouTubePlayerFragment();
        youTubePlayerFragment.initialize(VIDEO_ID, this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.yt_player_frame, youTubePlayerFragment);
        fragmentTransaction.commit();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView planTitle = (TextView) getActivity().findViewById(R.id.toolbar_title);
        planTitle.setText("Promo Video");
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.cueVideo(VIDEO_ID);
            //locking fullscreen to portrait, landscape layout not created yet so app would crash if user hit fullscreen and orientation changed to landscape
            youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(getActivity(), RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    "There was an error initializing the YouTubePlayer (%1$s)",
                    youTubeInitializationResult.toString());
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}
