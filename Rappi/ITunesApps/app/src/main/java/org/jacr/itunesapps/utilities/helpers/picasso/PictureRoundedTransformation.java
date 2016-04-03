package org.jacr.itunesapps.utilities.helpers.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

/**
 * PictureRoundedTransformation
 * Created by Jesus on 31/03/2016.
 * <p>
 * http://stackoverflow.com/questions/22363515/rounded-corners-with-picasso
 */
public class PictureRoundedTransformation implements com.squareup.picasso.Transformation {

    //<editor-fold desc="Variables">

    private final int radius;
    private final int margin;

    //</editor-fold>

    public PictureRoundedTransformation(final int radius, final int margin) {
        this.radius = radius;
        this.margin = margin;
    }

    //<editor-fold desc="Overrides - Transformation"

    @Override
    public Bitmap transform(final Bitmap source) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawRoundRect(new RectF(margin, margin, source.getWidth()
                - margin, source.getHeight() - margin), radius, radius, paint);
        if (source != output) {
            source.recycle();
        }
        return output;
    }

    @Override
    public String key() {
        return "picasso_rounded";
    }

    //</editor-fold>

}