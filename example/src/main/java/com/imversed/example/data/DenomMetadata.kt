package com.imversed.example.data

import cosmos.bank.v1beta1.Bank
import cosmos.bank.v1beta1.QueryOuterClass

data class DenomMetadata(
    val base: String,
    val description: String,
    val display: String,
    val name: String,
    val symbol: String,
    val denomUnits: List<DenomUnit>
) {
    companion object {
        fun from(value: Bank.Metadata): DenomMetadata {
            return DenomMetadata(
                value.base,
                value.description,
                value.display,
                value.name,
                value.symbol,
                value.denomUnitsList.map(DenomUnit.Companion::from),
            )
        }

        fun from(value: QueryOuterClass.QueryDenomMetadataResponse): DenomMetadata {
            return from(value.metadata)
        }
    }
}
