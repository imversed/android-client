package com.imversed.example.data

import cosmos.bank.v1beta1.QueryOuterClass
import cosmos.base.v1beta1.CoinOuterClass
import java.math.BigDecimal

data class Coin(
    val amount: BigDecimal,
    val denom: String
) {
    companion object {
        fun from(value: CoinOuterClass.Coin): Coin {
            return Coin(
                BigDecimal(value.amount),
                value.denom
            )
        }

        fun from(value: QueryOuterClass.QuerySupplyOfResponse): Coin {
            return from(value.amount)
        }
        fun from(value: QueryOuterClass.QueryBalanceResponse): Coin {
            return from(value.balance)
        }
    }
}
