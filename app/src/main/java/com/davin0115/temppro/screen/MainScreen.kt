@file:Suppress("DEPRECATION")

package com.davin0115.temppro.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.davin0115.cconverter.ui.theme.poppinsFamily
import com.davin0115.temppro.R
import com.davin0115.temppro.navigation.Screen
import com.davin0115.temppro.ui.theme.Greyy
import com.davin0115.temppro.ui.theme.MainColor
import com.davin0115.temppro.ui.theme.SecondColor
import com.davin0115.temppro.ui.theme.TempProTheme
import com.davin0115.temppro.viewmodel.TemperatureViewModel

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview(){
    TempProTheme {
        MainScreen(rememberNavController(), viewModel = viewModel())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, viewModel: TemperatureViewModel){
    Scaffold(
        topBar = {
            GradientTopBar(
                title = stringResource(id = R.string.app_name),
                onActionClick = {
                    navController.navigate(Screen.History.route)
                }
            )
        }
    ) { innerPadding ->
        ScreenContent(
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel,
            navController
        )
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier, viewModel: TemperatureViewModel, navController: NavHostController) {
    var input by rememberSaveable { mutableStateOf("") }
    var inputError by rememberSaveable { mutableStateOf(false) }
    var selectedUnit by rememberSaveable { mutableStateOf("Celsius") }


    val context = LocalContext.current
    listOf("Celsius", "Fahrenheit", "Kelvin")
    val inputValue = input.toFloatOrNull() ?: 0f
    val (celsius, fahrenheit, kelvin) = convertTemperature(inputValue, selectedUnit)
    val isDark = isSystemInDarkTheme()
    val borderColor = if (isDark) Color.Gray else Color.LightGray


    Column(
        modifier = modifier
            .padding(top = 10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Temperature Converter",
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 35.sp,
            lineHeight = 48.sp
        )
        UnitDropdownWithInput(
            input = input,
            onInputChange = { input = it; inputError = false },
            selectedUnit = selectedUnit,
            onUnitChange = { selectedUnit = it },
            inputError = inputError
        )

        Spacer(modifier = Modifier.height(8.dp))
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp))
                .padding(16.dp)
        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            )
            {
                Column {
                    Text("Celsius: ", fontFamily = poppinsFamily, fontWeight = FontWeight.Bold)
                    Text("$celsius")
                    Text("Fahrenheit: ", fontFamily = poppinsFamily, fontWeight = FontWeight.Bold)
                    Text("$fahrenheit")
                    Text("Kelvin: ", fontFamily = poppinsFamily, fontWeight = FontWeight.Bold)
                    Text(text = "$kelvin")
                    Button(
                        onClick = {
                            shareData(
                                context = context,
                                message = context.getString(R.string.template_share, input, selectedUnit,
                                    celsius, fahrenheit, kelvin)
                            )
                        },
                        modifier = Modifier
                            .padding(top = 15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MainColor,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        Text(text = stringResource(R.string.share),
                            fontFamily = poppinsFamily,
                            fontWeight = FontWeight.SemiBold)
                    }
                }
                IconButton(
                    onClick = {navController.navigate(Screen.Info.route)},
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.Top)
                )
                {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "",
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .padding(top = 5.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(MainColor, SecondColor)
                    ),
                    shape = RoundedCornerShape(30.dp)
                )
                .clip(RoundedCornerShape(30.dp))
        ) {
            Button(
                onClick = {
                    if (input.isBlank()) {
                        inputError = true
                        return@Button
                    }

                    val result = "$input $selectedUnit → ${celsius}C, ${fahrenheit}F, ${kelvin}K"
                    viewModel.addToHistory(result)

                    showToast(context, context.getString(R.string.input_success))
                },
                modifier = Modifier,
                contentPadding = PaddingValues(horizontal = 60.dp, vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                elevation = ButtonDefaults.buttonElevation(0.dp)
            ) {
                Text(
                    text = stringResource(R.string.save),
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun GradientTopBar(
    title: String,
    onActionClick: () -> Unit
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
            Text(
                text = title,
                color = Color.White,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )

            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = "History",
                    tint = Color.White
                )
            }
        }
    }
}



fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


fun convertTemperature(inputValue: Float, selectedUnit: String): Triple<Float, Float, Float> {
    val celsius = when (selectedUnit) {
        "Fahrenheit" -> (inputValue - 32) * 5 / 9
        "Kelvin" -> inputValue - 273.15f
        else -> inputValue
    }
    val fahrenheit = (celsius * 9 / 5) + 32
    val kelvin = celsius + 273.15f
    return Triple(celsius, fahrenheit, kelvin)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitDropdownWithInput(
    input: String,
    onInputChange: (String) -> Unit,
    selectedUnit: String,
    onUnitChange: (String) -> Unit,
    inputError: Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    val units = listOf("Celsius", "Fahrenheit", "Kelvin")
    val isDark = isSystemInDarkTheme()


    OutlinedTextField(
        value = input,
        onValueChange = onInputChange,
        shape = RoundedCornerShape(35.dp),
        label = {
            Text(text = stringResource(R.string.label),
                fontFamily = poppinsFamily,
                color = MaterialTheme.colorScheme.onBackground,
            ) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = if (isDark) MainColor else MainColor,
            unfocusedBorderColor = if (isDark) Color.Gray else Color.LightGray,
            cursorColor = if (isDark) Color.White else Color.Black,
            errorBorderColor = Color.Red
           ),

    trailingIcon = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = { expanded = true },
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                    interactionSource = remember { MutableInteractionSource() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .height(60.dp)
                        .background(color = Color.Transparent)
                ) {
                    Text(
                        text = selectedUnit,
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Icon(
                        Icons.Outlined.ArrowDropDown,
                        tint = if (isDark) Color.Gray else Color.Black,
                        contentDescription = "Select Unit"
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {

                    units.forEach { unit ->
                        DropdownMenuItem(
                            onClick = {
                                onUnitChange(unit)
                                expanded = false
                            },
                            text = {
                                Text(
                                    text = unit,
                                    fontFamily = poppinsFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                        )
                    }
                }
            }
        },
        isError = inputError,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

private fun shareData(context: Context, message: String){
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT,message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}



