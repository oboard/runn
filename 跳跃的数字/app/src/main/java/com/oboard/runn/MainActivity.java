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
import android.widget.ScrollView;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.os.Debug;
import java.util.ArrayList;
import java.util.Iterator;
import android.view.View.OnScrollChangeListener;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

	View start;
	LinearLayout lay;
	RLScrollView scr;
	ArrayList<DiskView> dv = new ArrayList<DiskView>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_start);

		start = findViewById(R.id.startimage);

		ValueAnimator ani = ValueAnimator.ofFloat(0.01f, 0.8f, 0);

		ani.setDuration(2000).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			public void onAnimationUpdate(ValueAnimator animaion) {
				float a = animaion.getAnimatedValue();
				start.setAlpha(a);
				if (a == 0) {
					loadmain();

				}
			}
		});
		ani.start();



    }
	static float nearly = 0;
	static DiskView nearlydv;
	public void loadmain() {
		setContentView(R.layout.activity_main);
		lay = findViewById(R.id.activitymainLinearLayout);
		scr = findViewById(R.id.activitymainScrollView);
		scr.setOnScrollListener(new  RLScrollView.OnScrollChangedListener() {
			public void onScrollChanged(int x, int y, int ox, int oy) {
				nearly = 0;
				Iterator it1 = dv.iterator();
				while (it1.hasNext()) {
					DiskView d = (DiskView)it1.next();
					float a = 1 - Math.abs(scr.getHeight() / 2 - (d.getY() - y + d.getHeight() / 2)) / scr.getHeight()/2;
					d.setScaleX(a);
					d.setScaleY(a);
					d.setAlpha(a);
					if (nearly < a) {
						nearly = a;
						nearlydv = d;
					}

				}
			}
		});
		scr.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				if (event.getAction() == event.ACTION_UP) {
					ValueAnimator ani = ValueAnimator.ofFloat(scr.getScrollY(), nearlydv.getY() + scr.getHeight() / 2 - nearlydv.getHeight() - dip2px(30));

					ani.setDuration(500).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
						public void onAnimationUpdate(ValueAnimator animaion) {
							float a = animaion.getAnimatedValue();
							scr.setScrollY(Math.round(a));
						}
					});
				//	ani.setStartDelay(1000);
					ani.start();
				}		
				return false;
			}
		});
		loaddisk();
		loaddisk();
		loaddisk();
		loaddisk();
		loaddisk();
		loaddisk();
		loaddisk();
		loaddisk();
		loaddisk();
		loaddisk();
		loaddisk();
		loaddisk();
		loaddisk();
		loaddisk();
	}
    public void loaddisk() {
		DiskView it = new DiskView(this);
		dv.add(it);
		lay.addView(it);
	}


	public int dip2px(float dpValue) {
		final float scale = getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
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
