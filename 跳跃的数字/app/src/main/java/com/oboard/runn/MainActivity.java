package com.oboard.runn;

import android.app.Activity;
import android.os.Bundle;
import android.animation.ValueAnimator;
import android.widget.ImageView;
import android.view.WindowManager;
import android.view.Window;
import android.view.View;
import android.os.Build;

public class MainActivity extends Activity {

	ImageView startImage;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_start);
		
		startImage = findViewById(R.id.startimage);
		ValueAnimator ani = ValueAnimator.ofFloat(0.01f, 0.8f,0);

		ani.setDuration(2000).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			public void onAnimationUpdate(ValueAnimator animaion) {
				float a = animaion.getAnimatedValue();
				startImage.setAlpha(a);
				if(a==0) {
					setContentView(R.layout.activity_main);
				}
			}
		});
		ani.start();
		
		
		
    }
    
}
