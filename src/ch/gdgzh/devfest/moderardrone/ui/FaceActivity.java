package ch.gdgzh.devfest.moderardrone.ui;

import com.codeminders.ardrone.DroneVideoListener;

import ch.gdgzh.devfest.moderardrone.R;
import android.media.FaceDetector;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.View;
import android.widget.Toast;

public class FaceActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);
		setContentView(new BitmapView(this));
	}

	
	@SuppressLint({ "DrawAllocation", "DrawAllocation" })
	private class BitmapView extends View implements DroneVideoListener {

		private final Context context;
		private static final int NUMBER_OF_FACES = 1;
		private int NUMBER_OF_FACE_DETECTED;
		private FaceDetector.Face[] faces;
		private Bitmap bitmap;


		public BitmapView(Context context) {
			super(context);
			this.context = context;
		}
		
		private void recognizeFaces() {
			BitmapFactory.Options bitmapFatoryOptions = new BitmapFactory.Options();
			bitmapFatoryOptions.inPreferredConfig = Bitmap.Config.RGB_565;
			bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.gisele_bundchen, bitmapFatoryOptions);
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			faces = new FaceDetector.Face[NUMBER_OF_FACES];
			FaceDetector faceDetector = new FaceDetector(width, height,
					NUMBER_OF_FACES);
			NUMBER_OF_FACE_DETECTED = faceDetector.findFaces(bitmap, faces);
			Toast.makeText(context,
					"Number of faces detected: " + NUMBER_OF_FACE_DETECTED,
					Toast.LENGTH_LONG).show();
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			canvas.drawBitmap(bitmap, 0, 0, null);
			Paint myPaint = new Paint();
			myPaint.setColor(Color.GREEN);
			myPaint.setStyle(Paint.Style.STROKE);
			myPaint.setStrokeWidth(3);

			for (int count = 0; count < NUMBER_OF_FACE_DETECTED; count++) {
				FaceDetector.Face face = faces[count];
				PointF midPoint = new PointF();
				face.getMidPoint(midPoint);

				float eyeDistance = face.eyesDistance();
				canvas.drawRect(midPoint.x - eyeDistance, midPoint.y - eyeDistance,
						midPoint.x + eyeDistance, midPoint.y + eyeDistance, myPaint);
			}
		}
		
		@Override
		public void frameReceived(int startX, int startY, int w, int h,
				int[] rgbArray, int offset, int scansize) {
			bitmap = Bitmap.createBitmap(rgbArray, w, h, Bitmap.Config.ARGB_8888);
			recognizeFaces();
			
			//redraw view
			this.invalidate();
		}
	}



}
