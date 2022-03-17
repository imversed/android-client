package com.imversed.example.data

import cosmos.bank.v1beta1.Bank

data class DenomUnit(
    val denom: String,
    val exponent: Int,
    val aliases: List<String>
) {
    companion object {
        fun from(value: Bank.DenomUnit): DenomUnit {
            return DenomUnit(
                value.denom,
                value.exponent,
                value.aliasesList
            )
        }
    }
}
