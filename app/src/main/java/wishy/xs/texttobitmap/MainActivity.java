package wishy.xs.texttobitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    Button button;
    ImageView imageView;
    EditText textView;

    // these matrices will be used to move and zoom image
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.convertButton);
        imageView = (ImageView)findViewById(R.id.image);
        textView = (EditText) findViewById(R.id.text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageBitmap(getViewImage1());
                imageView.setOnTouchListener(MainActivity.this);
            }
        });
    }


    private Bitmap getViewImage1() {
        textView.measure(0, 0);       //must call measure!
        int w = textView.getWidth();
        int h = textView.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        textView.setDrawingCacheEnabled(true);
        textView.measure(View.MeasureSpec.makeMeasureSpec(canvas.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(canvas.getHeight(), View.MeasureSpec.EXACTLY));
        textView.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());

        // draw the bitmap from the drawingcache to the canvas
        canvas.drawBitmap(textView.getDrawingCache(), 0, 0, null);

        // disable drawing cache
        textView.setDrawingCacheEnabled(false);
        return bmp;
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        switch(motionEvent.getActionMasked())
        {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int x_cord = (int) motionEvent.getRawX();
                int y_cord = (int) motionEvent.getRawY();
                if (x_cord > 3000) {
                    x_cord = 500;
                }
                if (y_cord > 3000) {
                    y_cord = 500;
                }
                layoutParams1.leftMargin = x_cord - 250;
                layoutParams1.topMargin = y_cord - 250;
                imageView.setLayoutParams(layoutParams1);
                break;
            default:
                break;
        }
        return true;
    }
}
