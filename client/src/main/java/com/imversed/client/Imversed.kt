package com.imversed.client

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import java.io.Closeable
import java.util.concurrent.TimeUnit

class Imversed : Closeable {
    private lateinit var channel: ManagedChannel

    val bank get() = cosmos.bank.v1beta1.QueryGrpc.newStub(channel)
    val bankBlocking get() = cosmos.bank.v1beta1.QueryGrpc.newBlockingStub(channel)
    val bankFuture get() = cosmos.bank.v1beta1.QueryGrpc.newFutureStub(channel)

    val auth get() = cosmos.auth.v1beta1.QueryGrpc.newStub(channel)
    val authBlocking get() = cosmos.auth.v1beta1.QueryGrpc.newBlockingStub(channel)
    val authFuture get() = cosmos.auth.v1beta1.QueryGrpc.newFutureStub(channel)

    val authz get() = cosmos.authz.v1beta1.QueryGrpc.newStub(channel)
    val authzBlocking get() = cosmos.authz.v1beta1.QueryGrpc.newBlockingStub(channel)
    val authzFuture get() = cosmos.authz.v1beta1.QueryGrpc.newFutureStub(channel)

    val distribution get() = cosmos.distribution.v1beta1.QueryGrpc.newStub(channel)
    val distributionBlocking get() = cosmos.distribution.v1beta1.QueryGrpc.newBlockingStub(channel)
    val distributionFuture get() = cosmos.distribution.v1beta1.QueryGrpc.newFutureStub(channel)

    val evidence get() = cosmos.evidence.v1beta1.QueryGrpc.newStub(channel)
    val evidenceBlocking get() = cosmos.evidence.v1beta1.QueryGrpc.newBlockingStub(channel)
    val evidenceFuture get() = cosmos.evidence.v1beta1.QueryGrpc.newFutureStub(channel)

    val feegrant get() = cosmos.feegrant.v1beta1.QueryGrpc.newStub(channel)
    val feegrantBlocking get() = cosmos.feegrant.v1beta1.QueryGrpc.newBlockingStub(channel)
    val feegrantFuture get() = cosmos.feegrant.v1beta1.QueryGrpc.newFutureStub(channel)

    val gov get() = cosmos.gov.v1beta1.QueryGrpc.newStub(channel)
    val govBlocking get() = cosmos.gov.v1beta1.QueryGrpc.newBlockingStub(channel)
    val govFuture get() = cosmos.gov.v1beta1.QueryGrpc.newFutureStub(channel)

    val mint get() = cosmos.mint.v1beta1.QueryGrpc.newStub(channel)
    val mintBlocking get() = cosmos.mint.v1beta1.QueryGrpc.newBlockingStub(channel)
    val mintFuture get() = cosmos.mint.v1beta1.QueryGrpc.newFutureStub(channel)

    val params get() = cosmos.params.v1beta1.QueryGrpc.newStub(channel)
    val paramsBlocking get() = cosmos.params.v1beta1.QueryGrpc.newBlockingStub(channel)
    val paramsFuture get() = cosmos.params.v1beta1.QueryGrpc.newFutureStub(channel)

    val slashing get() = cosmos.slashing.v1beta1.QueryGrpc.newStub(channel)
    val slashingBlocking get() = cosmos.slashing.v1beta1.QueryGrpc.newBlockingStub(channel)
    val slashingFuture get() = cosmos.slashing.v1beta1.QueryGrpc.newFutureStub(channel)

    val staking get() = cosmos.staking.v1beta1.QueryGrpc.newStub(channel)
    val stakingBlocking get() = cosmos.staking.v1beta1.QueryGrpc.newBlockingStub(channel)
    val stakingFuture get() = cosmos.staking.v1beta1.QueryGrpc.newFutureStub(channel)

    val upgrade get() = cosmos.upgrade.v1beta1.QueryGrpc.newStub(channel)
    val upgradeBlocking get() = cosmos.upgrade.v1beta1.QueryGrpc.newBlockingStub(channel)
    val upgradeFuture get() = cosmos.upgrade.v1beta1.QueryGrpc.newFutureStub(channel)

    val nft get() = imversed.nft.QueryGrpc.newStub(channel)
    val nftBlocking get() = imversed.nft.QueryGrpc.newBlockingStub(channel)
    val nftFuture get() = imversed.nft.QueryGrpc.newFutureStub(channel)

    val currency get() = imversed.currency.QueryGrpc.newStub(channel)
    val currencyBlocking get() = imversed.currency.QueryGrpc.newBlockingStub(channel)
    val currencyFuture get() = imversed.currency.QueryGrpc.newFutureStub(channel)

    val pools get() = imversed.pools.v1beta1.QueryGrpc.newStub(channel)
    val poolsBlocking get() = imversed.pools.v1beta1.QueryGrpc.newBlockingStub(channel)
    val poolsFuture get() = imversed.pools.v1beta1.QueryGrpc.newFutureStub(channel)

    fun configure(
        host: String,
        port: Int,
        useSecurityTransport: Boolean = true
    ) {
        close()
        val builder = ManagedChannelBuilder
            .forAddress(host, port)
        if (useSecurityTransport) {
            builder.useTransportSecurity()
        } else {
            builder.usePlaintext()
        }
        val channel = builder.build()
        this.channel = channel
    }

    fun configure(channel: ManagedChannel) {
        close()
        this.channel = channel
    }

    override fun close() {
        if (this::channel.isInitialized) {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
        }
    }
}