package tw.edu.pu.s1092181.finalexam

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import tw.edu.pu.s1092181.finalexam.ui.theme.FinalExamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinalExamTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main() {
    val navController = rememberNavController()
    val context = LocalContext.current
    var showMenu by remember { mutableStateOf(false) }

    Column {
        TopAppBar(
            title = { Text(text = "進步手扎") },

            navigationIcon = {
                IconButton(onClick = {
                    Toast.makeText(context, "進步手扎", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(Icons.Default.Menu, contentDescription = "導航圖標")
                }
            },

            actions = {
                IconButton(
                    onClick = {
                        Toast.makeText(context, "作者：余汶芯、林佳儀", Toast.LENGTH_SHORT)
                            .show()
                    }
                ) {
                    Icon(Icons.Rounded.AccountBox, contentDescription = "作者")
                }

                IconButton(
                    onClick = { showMenu = true }
                ) {
                    Icon(Icons.Default.MoreVert, contentDescription = "更多")
                }

                DropdownMenu(
                    expanded = showMenu, onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("主頁面") },
                        onClick = { navController.navigate("JumpFirst") })

                    DropdownMenuItem(
                        text = { Text("零錢認識") },
                        onClick = { navController.navigate("JumpSecond") })

                    DropdownMenuItem(
                            text = { Text("垃圾分類") },
                    onClick = { navController.navigate("JumpThird") })
                    DropdownMenuItem(
                        text = { Text("音樂遊戲") },
                        onClick = { navController.navigate("JumpFirth") })
                }
            }
        )

        NavHost(navController = navController, startDestination = "JumpFirst") {
            composable("JumpFirst") {
                FirstScreen(navController = navController)
            }
            composable("JumpSecond") {
                SecondScreen(navController = navController)
            }
            composable("JumpThird") {
                ThirdScreen(navController = navController)
            }
            composable("JumpFirth") {
                FirthScreen(navController = navController)
            }
        }
    }
}

@Composable
fun FirstScreen(navController: NavController) {
    val context = LocalContext.current
    var mper = MediaPlayer()

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(200.dp), // 設置圖片大小為200dp
            )

            Text(
                text = "人生不設限，希望無止限",
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center // 文字置中
            )

            Button(
                onClick = { openWebsite(context, "https://www.youtube.com/watch?v=XaH0FKS-MBo&list=PLXbqYgjIdb1JGaJHe2bQ6j3An15e3R6qi&index=1") }
            ) {
                Text(text = "天主教聖歌")
            }

            Button(
                onClick = { openMap(context, "24.2267756,120.5771591?q= police office") }
            ) {
                Text(text = "附近警局")
            }

            Button(onClick = {
                navController.navigate("JumpSecond")
            }) {
                Text(text = "零錢認識")
            }
            Button(onClick = {
                navController.navigate("JumpThird")
            }) {
                Text(text = "垃圾分類")
            }
            Button(onClick = {
                navController.navigate("JumpFirth")
            }) {
                Text(text = "音樂遊戲")
            }
        }
    }
}

// 輔助函數
private fun openWebsite(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

private fun openEmail(context: Context, email: String) {
    val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
    context.startActivity(intent)
}

private fun searchKeyword(context: Context, keyword: String) {
    val intent = Intent(Intent.ACTION_WEB_SEARCH)
    intent.putExtra(SearchManager.QUERY, keyword)
    context.startActivity(intent)
}

private fun openMap(context: Context, location: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:$location"))
    context.startActivity(intent)
}

@Composable
fun SecondScreen(navController: NavController) {
    val pictures = listOf(
        R.drawable.one to "1元",
        R.drawable.five to "5元",
        R.drawable.ten to "10元",
        R.drawable.fifty to "50元"
    )

    val firstRowPictures = pictures.take(2)
    val secondRowPictures = pictures.takeLast(2)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 上排圖片
        Row(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            firstRowPictures.forEach { (picture, text) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = picture),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp) // 設置統一的尺寸
                    )
                    Text(
                        text = text
                    )
                }
            }
        }

        // 下排圖片
        Row(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            secondRowPictures.forEach { (picture, text) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 如果是圖片 "five"，則放大至與其他圖片相同的大小
                    val sizeModifier = if (picture == R.drawable.five) {
                        Modifier.size(100.dp)
                    } else {
                        Modifier.size(100.dp)
                    }
                    Image(
                        painter = painterResource(id = picture),
                        contentDescription = null,
                        modifier = sizeModifier
                    )
                    Text(
                        text = text
                    )
                }
            }
        }

        // 返回按鈕
        Button(
            onClick = { navController.navigate("JumpFirst") },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "返回")
        }
    }
}

@Composable
fun ThirdScreen(navController: NavController) {
    val pictures = listOf(
        R.drawable.bottle to "寶特瓶",
        R.drawable.glass to "玻璃",
        R.drawable.iron to "鐵鋁罐",
        R.drawable.other to "一般垃圾" ,
        R.drawable.paper to "紙類",
        R.drawable.plastic to "塑膠"
    )

    val firstRowPictures = pictures.take(3)
    val secondRowPictures = pictures.takeLast(3)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 上排圖片
        Row(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            firstRowPictures.forEach { (picture, text) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = picture),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp) // 設置統一的尺寸
                    )
                    Text(
                        text = text
                    )
                }
            }
        }

        // 下排圖片
        Row(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            secondRowPictures.forEach { (picture, text) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 如果是圖片 "five"，則放大至與其他圖片相同的大小
                    val sizeModifier = if (picture == R.drawable.five) {
                        Modifier.size(100.dp)
                    } else {
                        Modifier.size(100.dp)
                    }
                    Image(
                        painter = painterResource(id = picture),
                        contentDescription = null,
                        modifier = sizeModifier
                    )
                    Text(
                        text = text
                    )
                }
            }
        }

        // 返回按鈕
        Button(
            onClick = { navController.navigate("JumpFirst") },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "返回")
        }
    }
}

@Composable
fun FirthScreen(navController: NavHostController) {
    val pictures = listOf(
        R.drawable.c to "Do",
        R.drawable.d to "Re",
        R.drawable.e to "Mi",
        R.drawable.f to "Fa",
        R.drawable.g to "Sol",
        R.drawable.a to "La",
        R.drawable.b to "Si",
        R.drawable.c2 to "Do2"
    )
    val firstRowPictures = pictures.take(4)
    val secondRowPictures = pictures.takeLast(4)

    val mediaPlayerMap = mapOf(
        "Re" to MediaPlayer.create(LocalContext.current, R.raw.d2),
        "Mi" to MediaPlayer.create(LocalContext.current, R.raw.e2),
        "Fa" to MediaPlayer.create(LocalContext.current, R.raw.f1),
        "Sol" to MediaPlayer.create(LocalContext.current, R.raw.g1),
        "La" to MediaPlayer.create(LocalContext.current, R.raw.a1),
        "Si" to MediaPlayer.create(LocalContext.current, R.raw.b1),
        "Do" to MediaPlayer.create(LocalContext.current, R.raw.c1),
        "Do2" to MediaPlayer.create(LocalContext.current, R.raw.do22)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 上排圖片
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            firstRowPictures.forEach { (picture, text) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = picture),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp) // 設置統一的尺寸
                    )
                    ClickableText(
                        text = AnnotatedString(text),
                        onClick = {
                            mediaPlayerMap[text]?.start()
                        }
                    )
                }
            }
        }

        // 下排圖片
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            secondRowPictures.forEach { (picture, text) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 如果是圖片 "five"，則放大至與其他圖片相同的大小
                    val sizeModifier = if (picture == R.drawable.c) {
                        Modifier.size(50.dp)
                    } else {
                        Modifier.size(50.dp)
                    }
                    Image(
                        painter = painterResource(id = picture),
                        contentDescription = null,
                        modifier = sizeModifier
                    )
                    ClickableText(
                        text = AnnotatedString(text),
                        onClick = {
                            mediaPlayerMap[text]?.start()
                        }
                    )
                }
            }
        }

        // 返回按鈕
        Button(
            onClick = { navController.navigate("JumpFirst") },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "返回")
        }
    }
}