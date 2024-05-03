package com.example.practica3

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.practica3.ui.theme.Practica3Theme

class CardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val showsDatabase : ShowsDatabase = ShowsDatabase(this)
        val showID = intent.getIntExtra("showID",0)
        val show: Show = showsDatabase.shows.get(showID-1)

        setContent {
            Practica3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CardViewGUI(show)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardViewGUI(show: Show) {
    val activity = (LocalContext.current as? Activity)
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surfaceVariant

        ) {
            IconButton(onClick = {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(show.officialSite)
                context.startActivity(intent)
            }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Descripción del icono"
                )
            }
        }
    }
    Column {
        TopAppBar(title = {
            IconButton(onClick = { activity?.finish() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Botón de retroceso"
                )
            }
        })
        Column(
            Modifier
                .fillMaxWidth(),
        ) {
            Row {
                ElevatedCard(
                    modifier = Modifier
                        .size(width = 175.dp, height = 250.dp)
                        .padding(12.dp),
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth(),
                    ) {
                        Box(
                            contentAlignment = Alignment.TopStart,
                        ) {
                            AsyncImage(
                                model = show.image.original,
                                contentDescription = "Poster del show",
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(1.dp))
                            ) {
                                Text(
                                    text = show.rating.average.toString(),
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.surfaceVariant)
                                        .padding(2.dp),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = show.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )

                    val modifier = Modifier.padding(vertical = 4.dp)

                    Text(
                        text = boldTitleRegularText("Genres: ", getGenresString(show)), modifier = modifier)
                    Text(text = boldTitleRegularText("Premiered: ", show.premiered), modifier = modifier)
                    Text(text = boldTitleRegularText("Country: ", "${show.network.country.name}, ${show.network.country.code}"), modifier = modifier)
                    Text(text = boldTitleRegularText("Language: ", show.language), modifier = modifier)
                }
            }

            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Text(
                text = "Summary",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = show.summary.replace(Regex("<.*?>"), ""),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

fun getGenresString(show: Show): String {
    var genresString = ""
    for ((index, item) in show.genres.withIndex()) {
        if (index == show.genres.size - 1) {
            genresString += item
        }
        else
            genresString = "$genresString$item, "
    }

    return genresString
}

fun boldTitleRegularText(title: String, regular: String): AnnotatedString {
    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(title)
        }
        append(regular)
    }

    return text
}