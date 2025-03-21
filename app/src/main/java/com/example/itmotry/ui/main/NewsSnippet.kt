package com.example.itmotry.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.itmotry.R
import com.skydoves.landscapist.glide.GlideImage

@Composable
internal fun NewsSnippet(
    newsItem: NewsItem,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.large,
            )
            .padding(16.dp)
    ) {
        Text(
            text = newsItem.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp),
        )
        Text(
            text = newsItem.source,
            style = MaterialTheme.typography.bodyLarge,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(bottom = 12.dp),
        )
        GlideImage(
            imageModel = { newsItem.urlToImage },
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp),
                )
            },
            failure = {
                Icon(
                    painter = painterResource(R.drawable.default_img),
                    contentDescription = null,
                    modifier = Modifier
                        .width(400.dp)
                        .height(200.dp),
                )
            },
            modifier = Modifier
                .width(400.dp)
                .height(200.dp),
        )
    }
}
