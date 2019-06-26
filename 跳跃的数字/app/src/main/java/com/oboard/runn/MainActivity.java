package com.oboard.runn;

import android.app.Activity;
import android.os.Bundle;
import android.animation.ValueAnimator;
import android.widget.ImageView;
import android.view.WindowManager;
import android.view.Window;
import android.view.View;
import android.os.Build;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.util.AttributeSet;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.FrameLayout;

public class MainActivity extends Activity {

	ImageView startImage;
	LinearLayout lay;
	
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
					loadmain();
					
				}
			}
		});
		ani.start();
		
		
		
    }
	public void loadmain() {
		setContentView(R.layout.activity_main);
		lay = findViewById(R.id.activitymainLinearLayout);
		loaddisk();
		loaddisk();
		loaddisk();
		loaddisk();
	}
    public void loaddisk() {
		lay.addView(new DiskView(this));
	}
	
	class DiskView extends FrameLayout {
		private ImageView mImg;
		
		private TextView mTitleTv;

		public DiskView(Context context) {
			super(context);

			// 加载布局
			LayoutInflater.from(context).inflate(R.layout.view_disk, this);

			// 获取控件
			mImg = findViewById(R.id.viewdiskImageView);
			mTitleTv = findViewById(R.id.viewdiskTextView);

		}

		// 点击事件
		public void setListener(OnClickListener listener) {
			mTitleTv.setOnClickListener(listener);
		}

		// 设置标题的方法
		public void setTitleText(String title) {
			mTitleTv.setText(title);
		}
	}
}
