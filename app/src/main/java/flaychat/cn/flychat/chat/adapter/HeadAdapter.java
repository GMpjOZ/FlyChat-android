package flaychat.cn.flychat.chat.adapter;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
/*TODO
import com.way.app.PushApplication;*/
import flaychat.cn.flychat.R;
import flaychat.cn.flychat.chat.wheel.adapters.AbstractWheelAdapter;

/**
 * /** HeadAdapter
 */
public class HeadAdapter extends AbstractWheelAdapter {
	// Image size
	final int IMAGE_WIDTH = 50;
	final int IMAGE_HEIGHT = 50;

	// Slot machine symbols
	/*TODO
	private final int items[] = PushApplication.heads;*/

	// Cached images
	private List<SoftReference<Bitmap>> images;

	private LayoutInflater inflater;
	private Context context;

	/**
	 * Constructor
	 */
	public HeadAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		/*TODO
		images = new ArrayList<SoftReference<Bitmap>>(items.length);
		for (int id : items) {
			images.add(new SoftReference<Bitmap>(loadImage(id)));
		}*/
	}

	/**
	 * Loads image from resources
	 */
	private Bitmap loadImage(int id) {
		Bitmap bitmap = BitmapFactory
				.decodeResource(context.getResources(), id);
		Bitmap scaled = Bitmap.createScaledBitmap(bitmap, IMAGE_WIDTH,
				IMAGE_HEIGHT, true);
		bitmap.recycle();
		return scaled;
	}

	@Override
	public int getItemsCount() {
		return IMAGE_HEIGHT;
		/*
		return items.length;*/
	}

	@Override
	public View getItem(int index, View cachedView, ViewGroup parent) {
		if (cachedView == null) {
			cachedView = inflater.inflate(R.layout.head_select_layout, null);
		}
		ImageView img = (ImageView) cachedView.findViewById(R.id.head);
		SoftReference<Bitmap> bitmapRef = images.get(index);
		Bitmap bitmap = bitmapRef.get();
		if (bitmap == null) {
/*TODO			bitmap = loadImage(items[index]);*/
			images.set(index, new SoftReference<Bitmap>(bitmap));
		}
		img.setImageBitmap(bitmap);

		return cachedView;
	}
}