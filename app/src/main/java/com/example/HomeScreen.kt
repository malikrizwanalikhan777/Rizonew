package com.example

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mobile.ads.banner.BannerAdView

@Composable
fun HomeScreen(onNavigateToLinks: (String) -> Unit) {
  Column(modifier = Modifier.fillMaxSize()) {
    AppHeader("PTV Sports Live")
    LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.weight(1f).padding(16.dp)) {
      item {
        CategoryCard("International", R.drawable.ic_international) { onNavigateToLinks("International") }
      }
      item {
        CategoryCard("League", R.drawable.ic_league) { onNavigateToLinks("League") }
      }
      item {
        CategoryCard("All Matches", R.drawable.ic_all) { onNavigateToLinks("All") }
      }
    }
    AndroidView(
      modifier = Modifier.fillMaxWidth(),
      factory = { context ->
        BannerAdView(context).apply {
          setAdUnitId(YandexAdsManager.BANNER_ID)
          setAdSize(com.yandex.mobile.ads.banner.BannerAdSize.stickySize(context, 300))
          loadAd(com.yandex.mobile.ads.common.AdRequest.Builder().build())
        }
      }
    )
  }
}

@Composable
fun CategoryCard(name: String, iconRes: Int, onClick: () -> Unit) {
  Card(modifier = Modifier.padding(8.dp).clickable(onClick = onClick).size(150.dp)) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
      Image(painter = painterResource(id = iconRes), contentDescription = name, modifier = Modifier.fillMaxSize(0.8f))
      Text(name, style = MaterialTheme.typography.bodyMedium)
    }
  }
}
