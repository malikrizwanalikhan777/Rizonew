package com.example

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mobile.ads.banner.BannerAdView

@Composable
fun LinksScreen(category: String, onNavigateToPlayer: (String) -> Unit) {
  val links = when (category) {
    "International" -> listOf("https://malikfaizangamerr-coder.github.io/Rizzonewchannel/#ch=1", "https://malikfaizangamerr-coder.github.io/Rizzonewchannel/#ch=5", "https://malikfaizangamerr-coder.github.io/Rizzonewchannel/#ch=2", "https://malikfaizangamerr-coder.github.io/Rizzonewchannel/#ch=7")
    "League" -> listOf("https://malikfaizangamerr-coder.github.io/Rizzonewchannel/#ch=1", "https://malikfaizangamerr-coder.github.io/Rizzonewchannel/#ch=5", "https://malikfaizangamerr-coder.github.io/Rizzonewchannel/#ch=4", "https://malikfaizangamerr-coder.github.io/Rizzonewchannel/#ch=8")
    else -> (1..11).map { "https://malikfaizangamerr-coder.github.io/Rizzonewchannel/#ch=$it" }
  }

  Column(modifier = Modifier.fillMaxSize()) {
    AppHeader("$category Matches")
    LazyVerticalGrid(columns = GridCells.Fixed(4), modifier = Modifier.weight(1f).padding(16.dp)) {
      items(links.size) { index ->
        Card(modifier = Modifier.padding(8.dp).clickable { onNavigateToPlayer(links[index]) }.height(100.dp)) {
          Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            Text("Channel ${index + 1}${if(index == 4) " (Ads Free)" else ""}")
          }
        }
      }
    }
    AndroidView(
      modifier = Modifier.fillMaxWidth(),
      factory = { context ->
        BannerAdView(context).apply {
          setAdUnitId(YandexAdsManager.BANNER_2_ID)
          setAdSize(com.yandex.mobile.ads.banner.BannerAdSize.stickySize(context, 300))
          loadAd(com.yandex.mobile.ads.common.AdRequest.Builder().build())
        }
      }
    )
  }
}
