package com.oboard.runn;

import android.app.Activity;
import android.os.Bundle;
import android.animation.ValueAnimator;
import android.widget.ImageView;

public class MainActivity extends Activity {

	ImageView startImage;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
		
		startImage = findViewById(R.id.startimage);
		ValueAnimator ani = ValueAnimator.ofFloat(0, 0.8f,0);

		ani.setDuration(5000).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			public void onAnimationUpdate(ValueAnimator animaion) {
				float a = animaion.getAnimatedValue();
				startImage.setAlpha(a);
			}
		});
		ani.start();
		
    }
    
}
