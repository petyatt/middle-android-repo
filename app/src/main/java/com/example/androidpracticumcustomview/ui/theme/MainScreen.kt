package com.example.androidpracticumcustomview.ui.theme

import CustomContainerCompose
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.androidpracticumcustomview.R

/*
Задание:
Реализуйте необходимые компоненты.
*/

@Composable
fun MainScreen() {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), contentAlignment = Alignment.Center
        ) {

            CustomContainerCompose(
                firstChild = {
                    Text(
                        text = stringResource(id = R.string.first_child),
                        style = TextStyle(fontSize = 24.sp)
                    )
                },
                secondChild = {
                    Text(
                        text = stringResource(id = R.string.second_child),
                        style = TextStyle(fontSize = 24.sp)
                    )
                }
            )
        }
    }
}