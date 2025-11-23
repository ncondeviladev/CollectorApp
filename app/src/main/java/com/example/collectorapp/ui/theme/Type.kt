package com.example.collectorapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.collectorapp.R



val TitulosFontFamily = FontFamily(
    Font(R.font.stacksansnotch, FontWeight.Bold)
)
val CuerpoFontFamily = FontFamily(
    Font(R.font.redhatdisplay, FontWeight.Normal)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = TitulosFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = TitulosFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp
    ),
    titleLarge = TextStyle(
        fontFamily = TitulosFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 22.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = CuerpoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = CuerpoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = CuerpoFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp
    )
)
