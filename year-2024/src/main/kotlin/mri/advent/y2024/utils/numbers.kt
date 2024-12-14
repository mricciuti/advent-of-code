package mri.advent.y2024.utils

fun Int.isEven() = this % 2 == 0

fun Long.divWithRem(divisor: Long) = this.div(divisor) to this.rem(divisor)