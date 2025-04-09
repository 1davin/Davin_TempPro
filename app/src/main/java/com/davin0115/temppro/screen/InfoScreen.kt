package com.davin0115.temppro.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.davin0115.cconverter.ui.theme.poppinsFamily
import com.davin0115.temppro.R
import com.davin0115.temppro.ui.theme.MainColor
import com.davin0115.temppro.ui.theme.SecondColor

@Composable
fun InfoScreen(navController: NavHostController){
    Scaffold(
        topBar = {
            GradientTopBarInfo(
                navController = navController
            )
        }
    ) { innerPadding ->
        ScreenContent(
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier){
    Column(
        modifier = modifier.padding(20.dp)
    ){
        Text(
            text = stringResource(R.string.definition),
            fontFamily = poppinsFamily,
            fontSize =  17.sp)
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .padding(bottom = 20.dp)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(12.dp)
                )
        ){
            Text(
                text = stringResource(R.string.units),
                modifier = Modifier.padding(
                    top = 15.dp,
                    start = 15.dp
                ),
                fontFamily = poppinsFamily,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.ctof),
                modifier = Modifier.padding(15.dp),
                fontFamily = poppinsFamily
            )
            Text(
                text = stringResource(R.string.ftoc),
                modifier = Modifier.padding(15.dp),
                fontFamily = poppinsFamily
            )
            Text(
                text = stringResource(R.string.ctok),
                modifier = Modifier.padding(15.dp),
                fontFamily = poppinsFamily
            )
            Text(
                text = stringResource(R.string.ktoc),
                modifier = Modifier.padding(15.dp),
                fontFamily = poppinsFamily
            )
        }
        Box (
            modifier = Modifier
                .background(Color.LightGray)
        ){
            Image(
                painter = painterResource(id = R.drawable.tempimage),
                contentDescription = ""
            )
        }
    }
}

@Composable
fun GradientTopBarInfo(
    navController : NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(112.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MainColor,
                        SecondColor
                    )
                )
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = WindowInsets.statusBars
                        .asPaddingValues()
                        .calculateTopPadding(),
                    start = 16.dp,
                    end = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {navController.popBackStack()}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = Color.White
                )
            }
        }
    }
}
