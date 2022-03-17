package com.imversed.example.data

import cosmos.bank.v1beta1.Bank

data class SendEnabled(
    val denom: String,
    val enabled: Boolean
) {
    companion object {
        fun from(value: Bank.SendEnabled): SendEnabled {
            return SendEnabled(value.denom, value.enabled)
        }
    }
}
