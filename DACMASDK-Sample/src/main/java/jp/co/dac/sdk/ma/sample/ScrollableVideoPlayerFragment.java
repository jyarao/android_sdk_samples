package jp.co.dac.sdk.ma.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ScrollableVideoPlayerFragment extends Fragment {
    static ScrollableVideoPlayerFragment newInstance() {
        return new ScrollableVideoPlayerFragment();
    }

    private VideoPlayerWithAdPlayback videoPlayerPlayback;
    private VideoPlayerController videoPlayerController;

    public ScrollableVideoPlayerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scrollable_video_player, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        videoPlayerPlayback = (VideoPlayerWithAdPlayback) view.findViewById(R.id.video_player_with_ad_playback);
        videoPlayerController = new VideoPlayerController(getActivity(), videoPlayerPlayback);
        videoPlayerController.play();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (videoPlayerController != null) {
            videoPlayerController.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoPlayerController != null) {
            videoPlayerController.resume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (videoPlayerController != null) {
            videoPlayerController.destroy();
        }
    }
}
