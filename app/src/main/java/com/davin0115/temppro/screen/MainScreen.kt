package com.davin0115.temppro.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.davin0115.cconverter.ui.theme.poppinsFamily
import com.davin0115.temppro.R
import com.davin0115.temppro.navigation.Screen
import com.davin0115.temppro.ui.theme.MainColor
import com.davin0115.temppro.ui.theme.TempProTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController){
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name),
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MainColor,
                    titleContentColor = MaterialTheme.colorScheme.onSecondary
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.History.route)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Refresh,
                            contentDescription = stringResource(R.string.history),
                            tint = MaterialTheme.colorScheme.primary
                        )

                    }
                }
            )
        }
    ) { innerPadding ->
        ScreenContent(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    var input by rememberSaveable { mutableStateOf("") }
    var selectedUnit by rememberSaveable { mutableStateOf("Celsius") }

    val units = listOf("Celsius", "Fahrenheit", "Kelvin")
    val inputValue = input.toFloatOrNull() ?: 0f
    val (celsius, fahrenheit, kelvin) = convertTemperature(inputValue, selectedUnit)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Temperature Converter", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        UnitDropdownMenu(selectedUnit, units) { selectedUnit = it }
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Enter temperature") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Celsius: $celsius")
        Text("Fahrenheit: $fahrenheit")
        Text("Kelvin: $kelvin")
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(modifier = Modifier.height(8.dp))
    }
}

fun convertTemperature(inputValue: Float, selectedUnit: String): Triple<Float, Float, Float> {
    val celsius = when (selectedUnit) {
        "Fahrenheit" -> (inputValue - 32) * 5 / 9
        "Kelvin" -> inputValue - 273.15f
        else -> inputValue // Asumsikan input adalah dalam Celsius
    }
    val fahrenheit = (celsius * 9 / 5) + 32
    val kelvin = celsius + 273.15f
    return Triple(celsius, fahrenheit, kelvin)
}

@Composable
fun UnitDropdownMenu(selectedUnit: String, units: List<String>, onSelect: (String) -> Unit) {
    var expandedState by remember { mutableStateOf(false) }
    Box {
        Button(onClick = { expandedState = true }) {
            Text(selectedUnit)
        }
        DropdownMenu(
            expanded = expandedState,
            onDismissRequest = { expandedState = false }
        ) {
            units.forEach { unit ->
                DropdownMenuItem(
                    onClick = {
                        onSelect(unit)
                        expandedState = false
                    },
                    text = { Text(text = unit) }
                )
            }
        }
    }
}





