package com.imversed.example

import androidx.annotation.StringRes

class Action(@StringRes val title: Int, val action: () -> Unit)