
package pulltorefresh.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.support.android.designlibdemo.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import pulltorefresh.PullToRefreshBase;

@SuppressLint("SimpleDateFormat")
public class LoadingLayout extends FrameLayout {

    static final int DEFAULT_ROTATION_ANIMATION_DURATION = 150;

    private final ImageView headerImage;

    private final ProgressBar headerProgress;

    private final TextView refreshText;

    private String pullLabel;

    private String refreshingLabel;

    private String releaseLabel;

    private final Animation rotateAnimation, resetRotateAnimation;

    private TextView refreshTime;

    public LoadingLayout(Context context, final int mode, String releaseLabel, String pullLabel,
            String refreshingLabel) {
        super(context);
        ViewGroup header = (ViewGroup)LayoutInflater.from(context)
                .inflate(R.layout.pull_to_refresh_header, this);
        refreshText = (TextView)header.findViewById(R.id.pull_to_refresh_text);
        refreshText.setTextColor(Color.BLACK);
        refreshText.setText(releaseLabel);
        refreshTime = (TextView)header.findViewById(R.id.pull_to_refresh__time);
        refreshTime.setTextColor(Color.BLACK);
        StringBuilder sb = new StringBuilder();
        sb.append(getContext().getString(R.string.refresh_at))
                .append(new SimpleDateFormat("MM-dd HH:mm").format(new Date()));
        refreshTime.setText(sb.toString());

        headerImage = (ImageView)header.findViewById(R.id.pull_to_refresh_image);
        headerProgress = (ProgressBar)header.findViewById(R.id.pull_to_refresh_progress);

        final Interpolator interpolator = new LinearInterpolator();
        rotateAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(interpolator);
        rotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
        rotateAnimation.setFillAfter(true);

        resetRotateAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        resetRotateAnimation.setInterpolator(interpolator);
        resetRotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
        resetRotateAnimation.setFillAfter(true);

        this.releaseLabel = releaseLabel;
        this.pullLabel = pullLabel;
        this.refreshingLabel = refreshingLabel;

        switch (mode) {
            case PullToRefreshBase.MODE_PULL_UP_TO_REFRESH:
                headerImage.setImageResource(R.drawable.pulltorefresh_up_arrow);
                break;
            case PullToRefreshBase.MODE_PULL_DOWN_TO_REFRESH:
            default:
                headerImage.setImageResource(R.drawable.pulltorefresh_down_arrow);
                break;
        }
    }

    public void reset() {
        headerProgress.post(new Runnable() {

            @Override
            public void run() {
                refreshText.setText(pullLabel);
                headerImage.setVisibility(View.VISIBLE);
                headerProgress.setVisibility(View.GONE);
            }
        });
    }

    public void releaseToRefresh() {
        refreshText.setText(releaseLabel);
        StringBuilder sb = new StringBuilder();
        sb.append(getContext().getString(R.string.refresh_at))
                .append(new SimpleDateFormat("MM-dd HH:mm").format(new Date()));
        refreshTime.setText(sb.toString());

        headerImage.clearAnimation();
        headerImage.startAnimation(rotateAnimation);
    }

    public void setPullLabel(String pullLabel) {
        this.pullLabel = pullLabel;
    }

    public void refreshing() {
        refreshText.setText(refreshingLabel);
        headerImage.clearAnimation();
        headerImage.setVisibility(View.INVISIBLE);
        headerProgress.setVisibility(View.VISIBLE);
    }

    public void setRefreshingLabel(String refreshingLabel) {
        this.refreshingLabel = refreshingLabel;
    }

    public void setReleaseLabel(String releaseLabel) {
        this.releaseLabel = releaseLabel;
    }

    public void pullToRefresh() {
        refreshText.setText(pullLabel);
        StringBuilder sb = new StringBuilder();
        sb.append(getContext().getString(R.string.refresh_at))
                .append(new SimpleDateFormat("MM-dd HH:mm").format(new Date()));
        refreshTime.setText(sb.toString());

        headerImage.clearAnimation();
        headerImage.startAnimation(resetRotateAnimation);
    }

    public void setTextColor(int color) {
        refreshText.setTextColor(color);
    }

}
