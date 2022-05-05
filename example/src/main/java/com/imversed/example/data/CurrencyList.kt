package com.imversed.example.data

data class CurrencyList(
    val items: List<Currency>,
    val nextPageToken: NextPageToken
) {
    companion object {
        fun from(value: imversed.currency.QueryOuterClass.QueryAllCurrencyResponse): CurrencyList {
            return CurrencyList(
                value.currencyList.map(Currency.Companion::from),
                NextPageToken.from(value.pagination)
            )
        }
    }
}
