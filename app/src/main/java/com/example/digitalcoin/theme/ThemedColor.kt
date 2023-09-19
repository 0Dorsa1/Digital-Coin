package com.example.digitalcoin.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val Colors.primaryText: Color
    @Composable
    get() = if (isLight) Black001 else White001

val Colors.secondaryText: Color
    @Composable
    get() = if (isLight) Gray010 else Gray000

val Colors.hintText: Color
    @Composable
    get() = if (isLight) Gray002 else Gray003

val Colors.primaryBackground: Color
    @Composable
    get() = if (isLight) White003 else Black002

val Colors.cardBackground: Color
    @Composable
    get() = if (isLight) White000 else Blue000

val Colors.toolbarBackground: Color
    @Composable
    get() = if (isLight) Blue003 else Blue001

val Colors.divider: Color
    @Composable
    get() = if (isLight) Gray001 else Blue004
