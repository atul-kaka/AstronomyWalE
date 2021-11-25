package com.demo.astronomy.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.demo.astronomy.model.APODModel
import com.demo.astronomy.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * APOD repository to call api service
 */
object APODRepository {

    val apodModel = MutableLiveData<APODModel>()

    fun getAPODServiceApiCall(): MutableLiveData<APODModel> {

        val call = RetrofitClient.apiInterface.getAPODData()

        call.enqueue(object : Callback<APODModel> {
            override fun onFailure(call: Call<APODModel>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())

            }

            override fun onResponse(
                call: Call<APODModel>,
                response: Response<APODModel>
            ) {
                if(response.isSuccessful) {
                    Log.v("DEBUG : ", response.body().toString())

                    val data = response.body()

                    apodModel.value = data
                    if (data != null) {
                        apodModel.value = APODModel(
                            data.mCopyright,
                            data.mDate,
                            data.mExplanation,
                            data.mMediaType,
                            data.mServiceVersion,
                            data.mHDUrl,
                            data.mUrl,
                            data.mTitle
                        )
                    }
                }
            }
        })

        return apodModel
    }
}
