package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.yandex.mobile.ads.appopenad.AppOpenAd
import com.yandex.mobile.ads.appopenad.AppOpenAdEventListener
import com.yandex.mobile.ads.appopenad.AppOpenAdLoadListener
import com.yandex.mobile.ads.appopenad.AppOpenAdLoader
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestConfiguration
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.MobileAds
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoadListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoader

class MainActivity : ComponentActivity() {

    // ========== Tumhari Real Ad IDs ==========
    private val BANNER_1_ID = "R-M-20208443-1"
    private val APP_OPEN_ID = "R-M-20208443-2"
    private val INTERSTITIAL_ID = "R-M-20208443-3"
    private val BANNER_2_ID = "R-M-20208443-4"

    // ========== App Open ==========
    private var appOpenAd: AppOpenAd? = null
    private lateinit var appOpenAdLoader: AppOpenAdLoader
    private var isAppOpenAdShowing = false

    // ========== Interstitial ==========
    private var interstitialAd: InterstitialAd? = null
    private lateinit var interstitialAdLoader: InterstitialAdLoader

    // ========== Banner Views ==========
    private lateinit var banner1View: BannerAdView
    private lateinit var banner2View: BannerAdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // SDK Initialize
        MobileAds.initialize(this) {
            loadAppOpenAd()
            loadInterstitialAd()
        }

        // Banner 1
        banner1View = BannerAdView(this).apply {
            setAdUnitId(BANNER_1_ID)
            setAdSize(BannerAdSize.stickySize(this@MainActivity, BannerAdSize.FULL_WIDTH))
            loadAd(AdRequest.Builder().build())
        }

        // Banner 2
        banner2View = BannerAdView(this).apply {
            setAdUnitId(BANNER_2_ID)
            setAdSize(BannerAdSize.stickySize(this@MainActivity, BannerAdSize.FULL_WIDTH))
            loadAd(AdRequest.Builder().build())
        }

        // App Open lifecycle
        setupAppOpenAdLifecycle()

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen(
                        banner1 = banner1View,
                        banner2 = banner2View,
                        onShowInterstitial = { showInterstitialAd() }
                    )
                }
            }
        }
    }

    @Composable
    fun MainScreen(
        banner1: BannerAdView,
        banner2: BannerAdView,
        onShowInterstitial: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            AndroidView(
                factory = { banner1 },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Rizonew App",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Button(
                onClick = onShowInterstitial,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Show Interstitial Ad")
            }

            Spacer(modifier = Modifier.height(16.dp))

            AndroidView(
                factory = { banner2 },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    // ============================================
    // APP OPEN AD
    // ============================================
    private fun loadAppOpenAd() {
        appOpenAdLoader = AppOpenAdLoader(this)
        val config = AdRequestConfiguration.Builder(APP_OPEN_ID).build()

        appOpenAdLoader.setAdLoadListener(object : AppOpenAdLoadListener {
            override fun onAdLoaded(ad: AppOpenAd) {
                appOpenAd = ad
            }
            override fun onAdFailedToLoad(error: AdRequestError) {
            }
        })
        appOpenAdLoader.loadAd(config)
    }

    private fun setupAppOpenAdLifecycle() {
        val observer = object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                showAppOpenAdIfAvailable()
            }
        }
        ProcessLifecycleOwner.get().lifecycle.addObserver(observer)
    }

    private fun showAppOpenAdIfAvailable() {
        val ad = appOpenAd ?: return
        if (isAppOpenAdShowing) return

        isAppOpenAdShowing = true
        ad.setAdEventListener(object : AppOpenAdEventListener {
            override fun onAdShown() {}
            override fun onAdFailedToShow() {
                appOpenAd = null
                isAppOpenAdShowing = false
                loadAppOpenAd()
            }
            override fun onAdDismissed() {
                appOpenAd = null
                isAppOpenAdShowing = false
                loadAppOpenAd()
            }
            override fun onAdClicked() {}
            override fun onAdImpression() {}
        })
        ad.show(this)
    }

    // ============================================
    // INTERSTITIAL AD
    // ============================================
    private fun loadInterstitialAd() {
        interstitialAdLoader = InterstitialAdLoader(this)
        val config = AdRequestConfiguration.Builder(INTERSTITIAL_ID).build()

        interstitialAdLoader.setAdLoadListener(object : InterstitialAdLoadListener {
            override fun onAdLoaded(ad: InterstitialAd) {
                interstitialAd = ad
            }
            override fun onAdFailedToLoad(error: AdRequestError) {
            }
        })
        interstitialAdLoader.loadAd(config)
    }

    private fun showInterstitialAd() {
        val ad = interstitialAd ?: return
        ad.setAdEventListener(object : InterstitialAdEventListener {
            override fun onAdShown() {}
            override fun onAdFailedToShow() {
                interstitialAd = null
                loadInterstitialAd()
            }
            override fun onAdDismissed() {
                interstitialAd = null
                loadInterstitialAd()
            }
            override fun onAdClicked() {}
            override fun onAdImpression() {}
        })
        ad.show(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        interstitialAd?.destroy()
        interstitialAd = null
    }
}
