package com.imversed.example.data

import cosmos.bank.v1beta1.QueryOuterClass

data class DenomsMetadata(
    val items: List<DenomMetadata>,
    val nextPageToken: NextPageToken

) {
    companion object {
        fun from(value: QueryOuterClass.QueryDenomsMetadataResponse): DenomsMetadata {
            return DenomsMetadata(
                value.metadatasList.map(DenomMetadata.Companion::from),
                NextPageToken.from(value.pagination)
            )
        }
    }
}
