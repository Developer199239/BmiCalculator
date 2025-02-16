package com.example.bmicalculator.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bmicalculator.R
import com.example.bmicalculator.viewmodel.BmiViewModel
import com.example.bmicalculator.viewmodel.BmiViewModel.BmiCalculator.statusMap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BmiCalculatorPage(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Bmi Calculator") }
            )
        }
    ) {
        val bmiViewModel: BmiViewModel = viewModel()
        val uiState = bmiViewModel.bmiUiState.value
        Column(
            modifier = modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Image(
                painter = painterResource(id = R.drawable.bmi),
                contentDescription = null,
                modifier = Modifier.weight(.5f)

            )
            Spacer(modifier = Modifier.height(30.dp))
            EditNumberField(
                value = uiState.weight,
                label = "Weight (in kg)",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                onValueChange = bmiViewModel::updateWeight,
                modifier
            )
            EditNumberField(
                value = uiState.height,
                label = "Height (in meter)",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                onValueChange = bmiViewModel::updateHeight,
                modifier
            )

            Button(onClick = {
                bmiViewModel.calculateBmi()
            }) {
                Text(text = "Calculate")
            }

            BmiResult(uiState.bmi, uiState.status, modifier = Modifier.weight(1.5f))
        }

    }
}



@Composable
fun EditNumberField(
    value: String,
    label: String,
    keyboardOptions: KeyboardOptions,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField( value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        label = { Text(text = label) },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Composable
fun BmiResult(
    bmi: String,
    status: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Your BMI: $bmi",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if(status.isNotBlank()) {
            for(key in statusMap.keys) {
                val color = if (status == key) Color.LightGray else Color.Transparent
                val fontWeight = if (status == key) FontWeight.Bold else FontWeight.Normal
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp).fillMaxWidth()
                        .background(color = color)
                ) {
                    Text(text = key, fontWeight = fontWeight, modifier = Modifier.weight(1f))
                    Text(text = statusMap[key]!!, fontWeight = fontWeight)
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun BmiCalculatorPagePreview() {
    BmiCalculatorPage()
}