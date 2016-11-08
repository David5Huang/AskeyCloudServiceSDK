package askey.com.tw.authapi;

import android.view.View;
import android.view.animation.Animation;

/**
 * Created by david5_huang on 2016/8/24.
 */
public class Script {
    private View actView;
    private Animation actAnim;
    private Animation.AnimationListener animationListener;

    public Script(View actView, Animation actAnim){
        this.actView = actView;
        this.actAnim = actAnim;
    }

    public Script(View actView, Animation actAnim, Animation.AnimationListener animaListener){
        this.actView = actView;
        this.actAnim = actAnim;
        this.animationListener = animaListener;
        this.actAnim.setAnimationListener(animaListener);
    }

    public View getActView() {
        return actView;
    }

    public void setActView(View actView) {
        this.actView = actView;
    }

    public Animation getActAnim() {
        return actAnim;
    }

    public void setActAnim(Animation actAnim) {
        this.actAnim = actAnim;
    }

    public Animation.AnimationListener getAnimationListener() {
        return animationListener;
    }

    public void setAnimationListener(Animation.AnimationListener animationListener) {
        this.animationListener = animationListener;
    }
}
