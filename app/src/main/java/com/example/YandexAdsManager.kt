package com.example

import android.app.Activity
import android.content.Context
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.MobileAds

object YandexAdsManager {
  const val BANNER_ID = "R-M-20208443-1"
  const val APP_OPEN_ID = "R-M-20208443-2"
  const val INTERSTITIAL_ID = "R-M-20208443-3"
  const val BANNER_2_ID = "R-M-20208443-4"

  fun initialize(context: Context) {
    MobileAds.initialize(context) {}
  }
}
