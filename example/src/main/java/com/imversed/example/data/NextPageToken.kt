package com.imversed.example.data

import com.google.protobuf.ByteString
import cosmos.base.query.v1beta1.Pagination

data class NextPageToken(
    val key: String? = null,
    val limit: Long? = null,
    val offset: Long? = null,
    val reverse: Boolean? = null,
    val total: Long = 0L
) {
    fun toPageRequest(): Pagination.PageRequest {
        val builder = Pagination.PageRequest.newBuilder()

        key
            ?.let(ByteString::copyFromUtf8)
            ?.let(builder::setKey)

        limit?.let(builder::setLimit)
        offset?.let(builder::setOffset)
        reverse?.let(builder::setReverse)

        return builder.build()
    }

    companion object {
        fun from(value: Pagination.PageResponse): NextPageToken {
            val nextKey = value.nextKey.takeIf { !it.isEmpty }?.toStringUtf8()
            return NextPageToken(
                key = nextKey,
                total = value.total
            )
        }

        fun from(limit: Long, offset: Long): NextPageToken {
            return NextPageToken(
                limit = limit,
                offset = offset
            )
        }

        fun from(key: String): NextPageToken {
            return NextPageToken(
                key = key
            )
        }
    }
}

