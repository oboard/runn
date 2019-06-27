package com.oboard.runn;
import android.widget.ScrollView;
import android.content.Context;
import android.util.AttributeSet;

public class RLScrollView extends ScrollView{
	public RLScrollView(Context context) {
		super(context);
	}
	public RLScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public RLScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public interface OnScrollChangedListener{
		public void onScrollChanged(int x, int y, int oldxX, int oldY);
	}
	private OnScrollChangedListener onScrollChangedListener;
	/**
	 * 
	 * @param onScrollChangedListener
	 */
	public void setOnScrollListener(OnScrollChangedListener onScrollChangedListener){
		this.onScrollChangedListener=onScrollChangedListener;
	}
	@Override
	protected void onScrollChanged(int x, int y, int oldX, int oldY){
		super.onScrollChanged(x, y, oldX, oldY);
		if(onScrollChangedListener!=null){
			onScrollChangedListener.onScrollChanged(x, y, oldX, oldY);
		}
	}
	
	

	@Override
	public void fling(int velocity) {
		super.fling(velocity / 1000);
	}
}
