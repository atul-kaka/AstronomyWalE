package com.demo.astronomy.model

import com.google.gson.annotations.SerializedName

/**
 * APOD Model class
 */
data class APODModel
    (
    @SerializedName("copyright")
    val mCopyright: String? = null,
    @SerializedName("date")
    val mDate: String? = null,
    @SerializedName("explanation")
    val mExplanation: String? = null,
    @SerializedName("media_type")
    val mMediaType: String? = null,
    @SerializedName("service_version")
    val mServiceVersion: String? = null,
    @SerializedName("hdurl")
    val mHDUrl: String? = null,
    @SerializedName("url")
    val mUrl: String? = null,
    @SerializedName("title")
    val mTitle: String? = null
) {
}