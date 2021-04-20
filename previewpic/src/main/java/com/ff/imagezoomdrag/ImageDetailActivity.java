package com.ff.imagezoomdrag;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageDetailActivity extends Activity implements OnClickListener,
		OnLongClickListener, ViewPager.OnPageChangeListener {
	ZoomDragImageViewPager vp;
	Map<Integer, Bitmap> bigBitmapsCache = new HashMap<>();
	ImageView[] positionGuide;
	public static int url_path = 0;
	public static int local_file_path = 1;
	int pathType ;

	public static Intent getMyStartIntent(Context c, ArrayList<String> screenshot_samples, int defaultPos,int pathType) {
		Intent intent = new Intent(c, ImageDetailActivity.class);
		intent.putExtra("pos", defaultPos);
		intent.putExtra("pathType", pathType);
		intent.putStringArrayListExtra("screenshot_samples",screenshot_samples);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_detail_activity);
		int pos = getIntent().getIntExtra("pos", 0);
		pathType = getIntent().getIntExtra("pathType", url_path);
		ArrayList<String> screenshot_samples = getIntent().getStringArrayListExtra("screenshot_samples");
		initPositionGuideLay(screenshot_samples==null?0:screenshot_samples.size());
		vp = (ZoomDragImageViewPager) findViewById(R.id.vp);
		vp.setAdapter(new MyPagerAdapter(screenshot_samples));
		vp.setOnPageChangeListener(this);
		vp.setOffscreenPageLimit(5);
		vp.setCurrentItem(pos);
	}

	private void initPositionGuideLay(int size) {
		LinearLayout group = (LinearLayout) findViewById(R.id.viewGroup);
		positionGuide = new ImageView[size];
		for (int i = 0; i < size; i++) {
			ImageView imageView = new ImageView(this);
			LayoutParams lp = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp.setMargins(5, 0, 5, 0);
			imageView.setLayoutParams(lp);
			positionGuide[i] = imageView;
			if (i == 0) {
				positionGuide[i]
						.setBackgroundResource(R.mipmap.guid_activity_dot_selected);
			} else {
				positionGuide[i]
						.setBackgroundResource(R.mipmap.guid_activity_dot_normal);
			}
			group.addView(positionGuide[i]);
		}
	}

	public class ViewHolder {
		int pos;
		public ZoomDragImageIV content_iv;
		ProgressBar pb;

		public void setPos(int pos) {
			this.pos = pos;
		}

	}

	public void initView(ViewHolder vh, View v) {
		vh.content_iv = (ZoomDragImageIV) v.findViewById(R.id.content_iv);
		vh.content_iv.dragAndZoomTouchListener = new DragAndZoomTouchListener(vh.content_iv);
		vh.pb = (ProgressBar) v.findViewById(R.id.pb);
		v.setTag(vh);
	}

	public class MyPagerAdapter extends PagerAdapter {

		ArrayList<String> screenshot_samples;
		List<View> pagerViews = new ArrayList<>();

		public MyPagerAdapter(ArrayList<String> screenshot_samples) {
			this.screenshot_samples = screenshot_samples;
			for (String pic : screenshot_samples) {
				View v = View.inflate(ImageDetailActivity.this, R.layout.image_detail_lay, null);
				ViewHolder vh = new ViewHolder();
				initView(vh, v);
				pagerViews.add(v);
			}
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(final View arg0, final int position) {
			View v = pagerViews.get(position);
			final ViewHolder vh = (ViewHolder) v.getTag();
			vh.setPos(position);
			setPreviewBitmap(vh,pathType);
			setPic(vh,pathType);
			((ViewPager) arg0).addView(v);
			return v;
		}

		private void setPic(final ViewHolder vh, final int pathType) {
			String pitureUrl = screenshot_samples.get(vh.pos);
			if(!TextUtils.isEmpty(pitureUrl)) {
				/*try {
					Glide.with(ImageDetailActivity.this).load(pitureUrl).into(new SimpleTarget<Drawable>() {
						@Override
						public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
							vh.content_iv.setImageDrawable(resource);
						}
					});
				} catch (Throwable e) {
				}*/
				Glide.with(ImageDetailActivity.this)
						.asBitmap()
						.load(screenshot_samples.get(vh.pos))
//                                    .crossFade()
//                                    .transition(withCrossFade())
						//加载成功前显示的照片
						.placeholder(R.drawable.place_holder_pic)
						//出错显示的照片
						.error(R.drawable.error)
						//url为空时显示的照片
						.fallback(R.drawable.place_holder_pic)
						//解决第一次加载不显示bug
						.dontAnimate()
						.into(vh.content_iv);
			}
		}

		private void setPreviewBitmap(ViewHolder vh,int pathType) {
			if(pathType == url_path){
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
				if (bitmap != null) {
					vh.content_iv.setImageBitmap(bitmap);
				}
			}
		}

		@Override
		public int getCount() {
			return screenshot_samples == null ? 0 : screenshot_samples.size();
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(pagerViews.get(arg1));
		}

		public View getItem(int position) {
			return pagerViews.get(position);
		}
	}

	@Override
	public void onClick(View v) {
		ViewHolder holder;
		int i = v.getId();
		if (i == R.id.content_iv) {
			finish();
		}
		else {
		}
	}

	@Override
	protected void onDestroy() {
		for (int i : bigBitmapsCache.keySet()) {
			if (bigBitmapsCache.get(i) != null) {
				bigBitmapsCache.get(i).recycle();
			}
		}
		super.onDestroy();
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		for (int i = 0; i < positionGuide.length; i++) {
			positionGuide[i]
					.setBackgroundResource(R.mipmap.guid_activity_dot_normal);
			if (position == i) {
				positionGuide[position]
						.setBackgroundResource(R.mipmap.guid_activity_dot_selected);
			}
		}
		vp.resetImageMatrix();
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public boolean onLongClick(View v) {
		int i = v.getId();
		if (i == R.id.content_iv) {
//			ViewHolder vh = (ViewHolder) ((ViewGroup) v.getParent()).getTag();
//			ad = new AlertDialog.Builder(ImageDetailActivity.this).create();
//			View view = View.inflate(ImageDetailActivity.this,
//					R.layout.dialog_list, null);
//			view.setTag(vh);
//			view.findViewById(R.id.linelay1).setOnClickListener(this);
//			ad.show();
//			ad.setContentView(view);
//			WindowManager.LayoutParams lp = ad.getWindow().getAttributes();
//			lp.width = MyApp.getInstance().getWidthPixels();
//			ad.getWindow().setAttributes(lp);

		} else {
		}
		return false;
	}

}
