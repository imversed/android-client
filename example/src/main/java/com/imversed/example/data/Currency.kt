package com.imversed.example.data

data class Currency(
    val denom: String,
    val icon: String,
    val owner: String
) {
    companion object {
        fun from(value: imversed.currency.CurrencyOuterClass.Currency): Currency {
            return Currency(
                value.denom,
                value.icon,
                value.owner
            )
        }
    }
}
