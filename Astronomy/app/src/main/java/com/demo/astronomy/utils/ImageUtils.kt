package com.demo.astronomy.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import com.demo.astronomy.App
import java.io.ByteArrayOutputStream

/**
 * Class to encode/decode images
 */
object ImageUtils {

    /**
     * Encodes the image bitmap to string
     *
     * @param bitmap: Image bitmap
     *
     * @return String: Image bitmap string
     */
    fun encodeImage(bitmap: Bitmap?): String {
        val baos = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    /**
     * Decodes the image bitmap String to image bitmap
     *
     * @return Bitmap : Image bitmap
     */
    fun decodeImage(): Bitmap? {
        var imageBitmap: Bitmap? = null

        val sharedPref = App.appContext.getSharedPreferences(
            CommonConstants.PACKAGE,
            AppCompatActivity.MODE_PRIVATE
        )
        val previouslyEncodedImage = sharedPref.getString(CommonConstants.IMAGE_DATA, "")
        if (!previouslyEncodedImage.equals("", ignoreCase = true)) {
            val b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT)
            imageBitmap = BitmapFactory.decodeByteArray(b, 0, b.size)
        }
        return imageBitmap

    }
}