package com.djdapz.stubhub.util

import com.djdapz.stubhub.domain.Price
import com.djdapz.stubhub.domain.ProcessedListing
import com.djdapz.stubhub.domain.StubhubListing
import java.lang.Math.abs
import java.math.BigDecimal
import java.net.URL
import java.time.LocalDateTime

val CHARACTERS = "abcdefghijklmnopqrstuvwxyl1234567890-------"
val URL_SAFE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz"



fun randomInt(bound: Int = 100000): Int = abs(java.util.Random().nextInt() % bound)

fun randomString(bound: Int = 50): String = randomString(bound, CHARACTERS)

private fun randomUrlSafeString(bound: Int?): String = randomString(bound!!, URL_SAFE_CHARACTERS)

private fun randomString(length: Int = 50, characters: String = CHARACTERS): String =
        (1..length)
                .map { characters[randomInt(characters.length - 1)] }
                .joinToString( separator = "")

fun randomUrl(): URL = URL(
        "http://" + randomUrlSafeString(3)
                + "." + randomUrlSafeString(20)
                + "." + randomUrlSafeString(3))

fun randomStubhubListing(): StubhubListing =
        StubhubListing(
                randomInt(),
                randomPrice(),
                randomPrice(),
                randomInt(),
                randomString(),
                randomInt(),
                randomString(),
                randomString(),
                randomString(),
                randomInt(),
                randomString(),
                randomInt(),
                randomBoolean(),
                randomString(),
                randomString(),
                randomInt())

fun randomProcessedListing() =
        ProcessedListing(
                randomStubhubListing(),
                randomLocalDateTime(),
                randomInt())


fun randomLocalDateTime(): LocalDateTime =
        LocalDateTime
                .now()
                .minusYears(randomInt(50).toLong())
                .minusDays(randomInt(365).toLong())
                .minusMinutes(randomInt(60).toLong())
                .minusSeconds(randomInt(60).toLong())
                .minusNanos(randomInt(1000).toLong())

fun randomBoolean(): Boolean = randomInt() % 2 == 1

fun <T> randomList(producer: () -> T): List<T> = (1..randomInt(5)).map { producer() }

private fun randomPrice(): Price = Price(randomBigDecimal(), randomString())

private fun randomBigDecimal() = BigDecimal(
        randomInt(100000).toString()
                + "."
                + randomInt(100000).toString())
