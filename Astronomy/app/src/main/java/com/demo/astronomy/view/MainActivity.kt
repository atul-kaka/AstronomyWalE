package com.demo.astronomy.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.demo.astronomy.R
import com.demo.astronomy.utils.AppUtils
import com.demo.astronomy.utils.CommonConstants
import com.demo.astronomy.utils.ImageUtils
import com.demo.astronomy.viewmodel.MainActivityViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.Executors

/**
 * MainActivity to display image and details
 */
class MainActivity : AppCompatActivity() {
    private lateinit var astronomyImage: ImageView
    private lateinit var titleText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var layout: LinearLayout

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        astronomyImage = findViewById(R.id.img_nasa)
        titleText = findViewById(R.id.txt_title)
        descriptionText = findViewById(R.id.txt_description)
        progressBar = findViewById(R.id.progressBar)
        layout = findViewById(R.id.ll_layout)

        if (AppUtils.isUserNotVisitingFirstTime() && !AppUtils.isInternetConnected()) {
            showSnackbar()
            showLocalAPODData()
        } else if (AppUtils.isUserNotVisitingFirstTime()) {
            showLocalAPODData()
        } else {
            callAPODService()
        }
    }


    /**
     * Displays the APOD data
     */
    private fun showLocalAPODData() {
        val sharedPref = getSharedPreferences(CommonConstants.PACKAGE, MODE_PRIVATE)
        titleText.text = sharedPref.getString(CommonConstants.TITLE, "")
        descriptionText.text = sharedPref.getString(CommonConstants.EXPLANATION, "")
        astronomyImage.setImageBitmap(ImageUtils.decodeImage())
    }

    /**
     * calls APD service
     */
    private fun callAPODService() {
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        progressBar.visibility = View.VISIBLE
        layout.visibility = View.GONE
        mainActivityViewModel.getAPODModel()!!.observe(this, { apodModel ->
            progressBar.visibility = View.GONE
            layout.visibility = View.VISIBLE
            val title = apodModel.mTitle
            val explanation = apodModel.mExplanation
            titleText.text = title
            descriptionText.text = explanation
            val url = apodModel.mUrl
            displayImage(url)
            AppUtils.storeAPODDetails(AppUtils.getCurrentDate(), title, explanation)

        })
    }


    /**
     * Loads image from url
     *
     * @param url: String
     */
    private fun displayImage(url: String?) {

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap?
        executor.execute {

            try {
                val `in` = java.net.URL(url).openStream()
                image = BitmapFactory.decodeStream(`in`)
                handler.post {
                    astronomyImage.setImageBitmap(image)
                    val sharedPreferences =
                        getSharedPreferences(CommonConstants.PACKAGE, MODE_PRIVATE)
                    sharedPreferences.edit()
                        .putString(CommonConstants.IMAGE_DATA, ImageUtils.encodeImage(image))
                        .apply()
                }
            } catch (e: Exception) {
            }
        }
    }


    /**
     * Displays the snackbar
     */
    private fun showSnackbar() {
        Snackbar.make(this, layout, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
            .show()
    }
}