package com.imversed.example

import com.imversed.example.data.CurrencyList
import com.imversed.example.data.NextPageToken
import imversed.currency.QueryGrpc
import imversed.currency.QueryOuterClass

class CurrencyClient(
    private val stubCurrency: QueryGrpc.QueryBlockingStub
) {
    fun currencyAll(nextPageToken: NextPageToken? = null): CurrencyList {
        val builder = QueryOuterClass
            .QueryAllCurrencyRequest
            .newBuilder()
        if (nextPageToken != null) {
            builder.pagination = nextPageToken.toPageRequest()
        }
        return stubCurrency
            .currencyAll(builder.build())
            .let(CurrencyList::from)
    }

}