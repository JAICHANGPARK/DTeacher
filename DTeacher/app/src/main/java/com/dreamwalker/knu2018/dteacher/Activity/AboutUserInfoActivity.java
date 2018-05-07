package com.dreamwalker.knu2018.dteacher.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.dreamwalker.knu2018.dteacher.R;
import com.dreamwalker.knu2018.dteacher.Utils.ArcUtils;

import javax.crypto.CipherInputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.arcanimator.ArcAnimator;
import io.codetail.animation.arcanimator.Side;
import io.paperdb.Paper;


/***
 * @author JAICHANGPARK
 */
public class AboutUserInfoActivity extends AppCompatActivity {

    private static final String TAG = "AboutUserInfoActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.edit_fab)
    FloatingActionButton edit_fab;
    @BindView(R.id.avatar)
    CircleImageView circleImageView;

    private CoordinatorLayout layoutMain;
    private RelativeLayout layout_detail;
    private RelativeLayout layoutContent;

    private boolean isOpen = false;

    float startBlueX;
    float startBlueY;

    int endBlueX;
    int endBlueY;

    float startRedX;
    float startRedY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_user_info);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        layoutMain = (CoordinatorLayout) findViewById(R.id.activity_main);
        layout_detail = (RelativeLayout) findViewById(R.id.layout_detail);
        layoutContent = (RelativeLayout) findViewById(R.id.layoutContent);


        // TODO: 2018-02-14 API VERSION 23 이상부터 지원되는 기능이기 때문에 버전코드의 제한을 둔다.
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    Log.e(TAG, "onScrollChange: " + scrollY + "," + oldScrollY);
                    if (scrollY > oldScrollY) {
                        edit_fab.hide();
                    } else if (scrollY < oldScrollY) {
                        edit_fab.show();
                    }
                }
            });
        }
    }

    @OnClick(R.id.edit_fab)
    public void onFabClicked() {
        //viewMenu();
        //animateButton(edit_fab);
        testAmin();
    }

    private void animateButton(final FloatingActionButton mFloatingButton) {

        mFloatingButton.animate().translationXBy(0.5f).translationY(150).translationXBy(-0.9f)
                .translationX(-150).setDuration(300).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                viewMenu();
            }
        });

    }
    public void moveBackFab(){
       /* startBlueX = ArcUtils.centerX(edit_fab);
        startBlueY = ArcUtils.centerY(edit_fab);*/
        ArcAnimator arcAnimator = ArcAnimator.createArcAnimator(edit_fab, startBlueX, startBlueY, 180, Side.LEFT)
                .setDuration(1000);
        arcAnimator.start();
    }

    public void testAmin() {
        if (!isOpen){

            startBlueX = ArcUtils.centerX(edit_fab);
            startBlueY = ArcUtils.centerY(edit_fab);
            Log.e(TAG, "testAmin: startBlueX " +  startBlueX + ", "+ startBlueY);

            endBlueX = (int)(layoutMain.getRight() * 0.85f);
            endBlueY = (int) (layoutMain.getBottom() * 0.9f);
            Log.e(TAG, "testAmin: endBlueX " +  endBlueX + ", "+ endBlueY);
            ArcAnimator arcAnimator = ArcAnimator.createArcAnimator(edit_fab, startBlueX, endBlueY, 180, Side.LEFT)
                    .setDuration(1000);
            arcAnimator.addListener(new com.nineoldandroids.animation.Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(com.nineoldandroids.animation.Animator animation) {
                }

                @Override
                public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
                    //edit_fab.setVisibility(View.INVISIBLE);
                    //viewMenu();
                    revealView();
                }

                @Override
                public void onAnimationCancel(com.nineoldandroids.animation.Animator animation) {

                }

                @Override
                public void onAnimationRepeat(com.nineoldandroids.animation.Animator animation) {

                }
            });
            arcAnimator.start();
            isOpen = true;

        } else {
            int x = layout_detail.getRight();
            int y = layout_detail.getBottom();
            int startRadius = Math.max(layoutContent.getWidth(), layoutContent.getHeight());
            int endRadius = 0;
            edit_fab.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null)));
            edit_fab.setImageResource(R.drawable.ic_mode_edit_black_24dp);
            Animator anim = ViewAnimationUtils.createCircularReveal(layout_detail, x, y, startRadius, endRadius).setDuration(500);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    // TODO: 2018-02-14 스크롤 뷰의 스크롤을 다시 시작하도록 한다.
                    scrollView.setOnTouchListener(null);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    circleImageView.setVisibility(View.VISIBLE);
                    layout_detail.setVisibility(View.GONE);
                    moveBackFab();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            anim.start();
            isOpen = false;
        }
    }


    public void revealView(){
        int x = layoutContent.getRight();
        int y = layoutContent.getBottom();

        int startRadius = 0;
        int endRadius = (int) Math.hypot(layoutMain.getWidth(), layoutMain.getHeight());
        edit_fab.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), android.R.color.white, null)));
        edit_fab.setImageResource(R.drawable.ic_add_black_24dp);

        Animator anim = ViewAnimationUtils.createCircularReveal(layout_detail, x, y, startRadius, endRadius).setDuration(500);
        layout_detail.setVisibility(View.VISIBLE);
        circleImageView.setVisibility(View.GONE);
        // TODO: 2018-02-14 스크롤 뷰의 스크롤을 막는다.
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        anim.start();
    }


    public void viewMenu() {

        if (!isOpen) {

            int x = layoutContent.getRight();
            int y = layoutContent.getBottom();

            int startRadius = 0;
            int endRadius = (int) Math.hypot(layoutMain.getWidth(), layoutMain.getHeight());

            edit_fab.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), android.R.color.white, null)));
            edit_fab.setImageResource(R.drawable.ic_add_black_24dp);

            Animator anim = ViewAnimationUtils.createCircularReveal(layout_detail, x, y, startRadius, endRadius);
            layout_detail.setVisibility(View.VISIBLE);
            circleImageView.setVisibility(View.GONE);
            // TODO: 2018-02-14 스크롤 뷰의 스크롤을 막는다.
            scrollView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            anim.start();
            isOpen = true;

        } else {

            int x = layout_detail.getRight();
            int y = layout_detail.getBottom();

            int startRadius = Math.max(layoutContent.getWidth(), layoutContent.getHeight());
            int endRadius = 0;

            edit_fab.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null)));
            edit_fab.setImageResource(R.drawable.ic_mode_edit_black_24dp);
            Animator anim = ViewAnimationUtils.createCircularReveal(layout_detail, x, y, startRadius, endRadius);

            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    layout_detail.setVisibility(View.GONE);
                    circleImageView.setVisibility(View.VISIBLE);
                    // TODO: 2018-02-14 스크롤 뷰의 스크롤을 다시 시작하도록 한다.
                    scrollView.setOnTouchListener(null);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            anim.start();

            isOpen = false;
        }
    }

}
