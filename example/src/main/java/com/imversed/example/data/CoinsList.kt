package com.imversed.example.data

import cosmos.bank.v1beta1.QueryOuterClass

data class CoinsList(
    val items: List<Coin>,
    val nextPageToken: NextPageToken
) {
    companion object {
        fun from(value: QueryOuterClass.QueryTotalSupplyResponse): CoinsList {
            return CoinsList(
                value.supplyList.map(Coin.Companion::from),
                NextPageToken.from(value.pagination)
            )
        }

        fun from(value: QueryOuterClass.QueryAllBalancesResponse): CoinsList {
            return CoinsList(
                value.balancesList.map(Coin.Companion::from),
                NextPageToken.from(value.pagination)
            )
        }
    }
}
