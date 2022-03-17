package com.imversed.example

import com.imversed.example.data.*
import cosmos.bank.v1beta1.QueryGrpc
import cosmos.bank.v1beta1.QueryOuterClass

class BankClient(
    private val stubBank: QueryGrpc.QueryBlockingStub
) {
    fun totalSupply(nextPageToken: NextPageToken? = null): CoinsList {
        val builder = QueryOuterClass
            .QueryTotalSupplyRequest
            .newBuilder()
        if (nextPageToken != null) {
            builder.pagination = nextPageToken.toPageRequest()
        }
        return stubBank
            .totalSupply(builder.build())
            .let(CoinsList::from)
    }

    fun supplyOf(denom: String): Coin {
        val request = QueryOuterClass
            .QuerySupplyOfRequest
            .newBuilder()
            .setDenom(denom)
            .build()
        return stubBank
            .supplyOf(request)
            .let(Coin::from)
    }

    fun allBalances(address: String, nextPageToken: NextPageToken? = null): CoinsList {
        val builder = QueryOuterClass
            .QueryAllBalancesRequest
            .newBuilder()
            .setAddress(address)
        if (nextPageToken != null) {
            builder.pagination = nextPageToken.toPageRequest()
        }
        return stubBank
            .allBalances(builder.build())
            .let(CoinsList::from)
    }

    fun balance(address: String, denom: String): Coin {
        val request = QueryOuterClass
            .QueryBalanceRequest
            .newBuilder()
            .setAddress(address)
            .setDenom(denom)
            .build()
        return stubBank
            .balance(request)
            .let(Coin::from)
    }

    fun denomMetadata(denom: String): DenomMetadata {
        val request = QueryOuterClass
            .QueryDenomMetadataRequest
            .newBuilder()
            .setDenom(denom)
            .build()
        return stubBank
            .denomMetadata(request)
            .let(DenomMetadata::from)
    }

    fun denomsMetadata(nextPageToken: NextPageToken? = null): DenomsMetadata {
        val builder = QueryOuterClass
            .QueryDenomsMetadataRequest
            .newBuilder()
        if (nextPageToken != null) {
            builder.pagination = nextPageToken.toPageRequest()
        }

        return stubBank
            .denomsMetadata(builder.build())
            .let(DenomsMetadata::from)
    }

    fun params(): Params {
        val request = QueryOuterClass
            .QueryParamsRequest
            .newBuilder()
            .build()
        return stubBank
            .params(request)
            .let(Params::from)
    }
}