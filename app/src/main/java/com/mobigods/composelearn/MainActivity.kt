package com.mobigods.composelearn

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.mobigods.composelearn.ui.theme.ComposeLearnTheme


private val nameList: MutableList<String> = mutableListOf("Emmanuel", "Esther", "Jessica")


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLearnTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //Greeting("Android")
                    NameList(names = nameList)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!!!",
        modifier = Modifier
            .padding(all = 20.dp)
            .clickable { },
    style = TextStyle(
        color = Color.Green,
        fontWeight = FontWeight.Bold,
    )
    )
}



@Composable
fun GreetingButton(text: String) {
    Button(onClick = { /*TODO*/ },
        modifier = Modifier
            .size(50.dp)) {
        Greeting(name = text)
    }
}




data class City(val name: String)


@Composable
fun NameList(names: List<String>) {
    val namesState = remember {
        mutableStateListOf("Emmanuel", "Joshua")
    }

    val nameTextState = remember { mutableStateOf("")}

    val mapSaver = mapSaver(
        save = { mapOf("ff" to "", "fgg" to "ttt") },
        restore = {City("ff") }
    )

    val srt = rememberSaveable(stateSaver = mapSaver) {
        mutableStateOf(City(""))
    }



    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.wrapContentSize()
            ){
        for (name in namesState) {
            Text(text = name)
        }

        Spacer(modifier = Modifier.height(20.dp))

        TextField(value = nameTextState.value, onValueChange = { text ->
            nameTextState.value = text
        })

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            namesState.add(nameTextState.value)
            nameTextState.value = ""
        }, modifier = Modifier.fillMaxWidth(0.8f)) {
            Text(text = "Add Name")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NameListPreview() {
    NameList(names = nameList)
}

//@Preview
@Composable
fun SurfaceTest() {
    Surface(color = Color.Black,
    modifier = Modifier.fillMaxSize()) {
        Row (horizontalArrangement = Arrangement.SpaceBetween){

            HorizontalBar(Color.Red)
            HorizontalBar(Color.Magenta)
            HorizontalBar(Color.Blue)
            HorizontalBar(Color.Cyan)
            HorizontalBar(Color.Yellow)

        }

    }
}

@Composable
private fun HorizontalBar(color: Color) {
    Surface(
        color = color,
        modifier = Modifier
            .fillMaxHeight()
            .width(60.dp)
    ) {

    }
}


//@Preview
@Composable
fun GreetingButtonPreview() {
    GreetingButton(text = "Emmanuel")
}


//@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeLearnTheme {
        Greeting("Android")
    }
}