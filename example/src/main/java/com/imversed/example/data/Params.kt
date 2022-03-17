package com.imversed.example.data

import cosmos.bank.v1beta1.QueryOuterClass

data class Params(
    val sendEnabledItems: List<SendEnabled>,
    val defaultSendEnabled: Boolean
) {
    companion object {
        fun from(value: QueryOuterClass.QueryParamsResponse): Params {
            val params = value.params
            return Params(
                params.sendEnabledList.map(SendEnabled.Companion::from),
                params.defaultSendEnabled
            )
        }
    }
}
