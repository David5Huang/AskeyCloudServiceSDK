package tw.com.askey.ia4test;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

/**
 * Created by david5_huang on 2016/8/25.
 */
abstract public class BasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
    }

    protected Animation getAlphaAnim(long duration){
        Animation alphaAnim = new AlphaAnimation(0f, 1f);
        alphaAnim.setDuration(duration);
        return alphaAnim;
    }

    protected Animation getTransAlphaAnim(long duration){
        AnimationSet anim = new AnimationSet(false);

        Animation alphaAnim = getAlphaAnim(duration);
        alphaAnim.setStartTime(100);

        Animation transAnim = new TranslateAnimation(0, 0, 100, 0);
        transAnim.setDuration(duration);

        anim.addAnimation(alphaAnim);
        anim.addAnimation(transAnim);

        return  anim;

    }

    protected Animation getR2LTransAnim(long duration, long startTime){

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        Animation transAnim = new TranslateAnimation(width, 0, 0, 0);
        transAnim.setDuration(duration);
        transAnim.setStartOffset(startTime);

        return transAnim;
    }
}
