package jp.co.dac.sdk.ma.sample;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import jp.co.dac.ma.sdk.widget.DACVideoPlayerView;
import jp.co.dac.ma.sdk.widget.player.VideoPlayer;

import static com.google.common.truth.Truth.assertThat;
import static jp.co.dac.sdk.ma.sample.TestUtil.getAdVideoPlayer;
import static jp.co.dac.sdk.ma.sample.TestUtil.takeScreenshot;
import static jp.co.dac.sdk.ma.sample.TestUtil.waitPlayerUntilPaused;
import static jp.co.dac.sdk.ma.sample.TestUtil.waitPlayerUntilPlayed;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ScenarioVASTOnlyAdTest {

    private final static String AD_TAG_URL = "http://webdemo.dac.co.jp/sdfactory/stsn/top.php?filepath=stsn/unit/fruitsbear.xml";

    private final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

    @Rule
    public final ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Rule
    public final Timeout timeout = new Timeout(1, TimeUnit.MINUTES);

    private MainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = activityRule.getActivity();
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                activity.populateOnlyAdFragment(AD_TAG_URL);
            }
        });
        instrumentation.waitForIdleSync();

        Intents.init();
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

    /**
     * 1. start ad video
     * 2. completed ad video and paused video
     */
    @Test
    public void startAdVideo_afterPauseVideoPlayer() throws Exception {
        waitPlayerUntilPlayed((DACVideoPlayerView) activity.findViewById(R.id.ad_video_player));
        takeScreenshot(instrumentation, activity, "playing-ad-video");

        waitPlayerUntilPaused((DACVideoPlayerView) activity.findViewById(R.id.ad_video_player));
        takeScreenshot(instrumentation, activity, "paused-ad-video");

        final VideoPlayer adVideoPlayer = getAdVideoPlayer(activity);
        assertThat(adVideoPlayer.isPlaying()).isFalse();
        assertThat(adVideoPlayer).isEqualTo(getAdVideoPlayer(activity));
    }
}
