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
import android.animation.Animator;
import android.view.ViewAnimationUtils;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Bitmap;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.renderscript.Allocation;
import android.renderscript.Element;
import jp.wasabeef.blurry.Blurry;
import android.graphics.Color;

public class MainActivity extends Activity {

	View start;
	ImageView img;
	LinearLayout lay;
	RLScrollView scr;
	FrameLayout fra;
	RelativeLayout rel;
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
		rel = findViewById(R.id.activitymainRelativeLayout);
		lay = findViewById(R.id.activitymainLinearLayout);
		scr = findViewById(R.id.activitymainScrollView);
		img = findViewById(R.id.activitygamebackground);
		fra = findViewById(R.id.activitymainFrameLayout);
		scr.setOnScrollListener(new  RLScrollView.OnScrollChangedListener() {
			public void onScrollChanged(int x, int y, int ox, int oy) {
				nearly = 0;
				Iterator it1 = dv.iterator();
				while (it1.hasNext()) {
					DiskView d = (DiskView)it1.next();
					float a = 1 - Math.abs(scr.getHeight() / 2 - (d.getY() - y + d.getHeight() / 2)) / scr.getHeight() / 2;
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
		final DiskView it = new DiskView(this);
		dv.add(it);
		lay.addView(it);
		it.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Blurry.with(v.getContext())
				.radius(50)//模糊半径
				.sampling(10)//缩放大小，先缩小再放大
				.color(Color.argb(66, 0, 0, 0))//颜色
				.async()//是否异步
				.animate(500)//显示动画，目前仅支持淡入淡出，默认时间是300毫秒，仅支持传入控件为ViewGroup
				.capture(rel)
				.into(img);
				fra.setVisibility(View.VISIBLE);
				final int width = scr.getWidth();
				final int height = scr.getHeight();
				final float radius = (float)Math.sqrt(width * width + height * height);//半径
				Animator animator = ViewAnimationUtils.createCircularReveal(fra, Math.round(it.getX() + it.getWidth() / 2), Math.round(it.getY() - scr.getScrollY() + it.getHeight() / 2 + dip2px(60)), it.getWidth() / 2, radius);
				animator.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {

					}
				});
				animator.setDuration(500);
				animator.start();
			}
		});
	}


	public Bitmap blurBitmap(Bitmap bitmap, int radius) {
        //创建一个空bitmap，其大小与我们想要模糊的bitmap大小相同
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //实例化一个新的Renderscript
        RenderScript rs = RenderScript.create(getApplicationContext());
        //创建Allocation对象
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
        //创建ScriptIntrinsicBlur对象，该对象实现了高斯模糊算法
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        //设置模糊半径，0 <radius <= 25
        blurScript.setRadius(radius);

        //执行Renderscript
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);
        //将allOut创建的Bitmap复制到outBitmap
        allOut.copyTo(outBitmap);
        //释放内存占用
        bitmap.recycle();

        //销毁Renderscript。
        rs.destroy();
        return outBitmap;
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
		public void setOnClickListener(OnClickListener listener) {
			mTitleTv.setOnClickListener(listener);
		}

		// 设置标题的方法
		public void setTitleText(String title) {
			mTitleTv.setText(title);
		}

	}
}
