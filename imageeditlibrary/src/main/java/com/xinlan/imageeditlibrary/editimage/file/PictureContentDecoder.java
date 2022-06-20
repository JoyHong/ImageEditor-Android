package com.xinlan.imageeditlibrary.editimage.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.IOException;

public class PictureContentDecoder extends PictureDefaultDecoder {

    public PictureContentDecoder(Context context, Uri uri) {
        super(context, uri);
    }

    @Override
    public Bitmap decode(BitmapFactory.Options options) {
        Uri uri = getUri();
        if (uri == null) {
            return null;
        }
        Bitmap bitmap = null;
        try (ParcelFileDescriptor parcelFileDescriptor = mContext.getContentResolver().openFileDescriptor(uri, "r")) {
            if (parcelFileDescriptor != null) {
                bitmap = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor(), null, options);
            }
        } catch (IOException ignored) {
        }
        if (bitmap != null) {
            return bitmap;
        }
        return super.decode(options);
    }
}
