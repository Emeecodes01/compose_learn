package com.mobigods.composelearn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.mobigods.composelearn.ui.theme.ComposeLearnTheme


class ProfileCardActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeLearnTheme {
                UserListApplication()
            }
        }
    }
}


@Composable
fun UserListApplication(userList: List<UserProfile> = userProfileList) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "user_list") {
        composable("user_list") {
            HomeScreen(userList, navController)
        }

        composable("user_details/{userId}",
            arguments = listOf(navArgument("userId") {
                type = NavType.IntType
            })
        ) { backStack ->
            val userId = backStack.arguments?.getInt("userId")
            ProfileDetailScreen(userId) {
                navController.navigateUp()
            }
        }
    }
}


@Composable
fun HomeScreen(userList: List<UserProfile>, navController: NavController?) {
    Scaffold(
        topBar = {
            AppBar(
                "Users", Icons.Default.Home,
                Modifier.padding(horizontal = 12.dp)
            )
        }
    ) {

        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {

            LazyColumn {
                items(userList) { user ->
                    ProfileCard(user) {
                        navController?.navigate("user_details/${user.id}")
                    }
                }
            }

        }
    }
}


@Composable
fun AppBar(title: String, icon: ImageVector, modifier: Modifier) {
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "Home",
                modifier = modifier
            )
        },
        title = { Text(text = title) }
    )
}


@Composable
fun ProfileCard(userProfile: UserProfile, userClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 12.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .clickable { userClicked.invoke() },
        elevation = 8.dp,
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentSize(align = Alignment.CenterStart),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfilePicture(userProfile.pictureUrl, userProfile.status, 70.dp)
            ProfileContent(userProfile.name, userProfile.status)
        }

    }
}


@Composable
fun ProfilePicture(profileUrl: String, status: Boolean, imageSize: Dp) {
    Card(
        shape = CircleShape,
        modifier = Modifier
            .padding(end = 16.dp)
            .width(imageSize)
            .height(imageSize),
        border = BorderStroke(2.dp, color = if (status) Color.Green else Color.Red)
    ) {
        Image(
            painter = rememberImagePainter(
                data = profileUrl,
                builder = {
                    transformations(CircleCropTransformation())
                }),
            contentDescription = null,
        )
    }

}

@Composable
fun ProfileContent(userName: String, status: Boolean, isDetails: Boolean = false) {
    Column(
        modifier = Modifier.wrapContentSize(Alignment.Center),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = if (isDetails) Alignment.CenterHorizontally else Alignment.Start
    ) {
        Text(
            text = userName,
            style = MaterialTheme.typography.h5.copy(
                color = if (status) Color.Black else Color.LightGray,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = stringResource(id = if (status) R.string.active else R.string.inactive),
            style = MaterialTheme.typography.body2.copy(color = if (status) Color.Black else Color.LightGray)
        )
    }
}


@Composable
fun ProfileDetailScreen(userId: Int?, iconClicked: () -> Unit) {
    val id = userId ?: 0

    val user = userProfileList.find { it.id == id }

    Scaffold(

        topBar = {
            AppBar("User Details", Icons.Default.ArrowBack,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clickable {
                        iconClicked.invoke()
                    })
        }
    ) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize(align = Alignment.TopCenter),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfilePicture(user!!.pictureUrl, user.status, 200.dp)
                ProfileContent(user.name, user.status, true)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileDetailScreenPreview() {
    ComposeLearnTheme {
        ProfileDetailScreen(0) {}
    }
}


@Preview
@Composable
fun MainScreenPreview() {
    ComposeLearnTheme {
        HomeScreen(userProfileList, null)
    }
}