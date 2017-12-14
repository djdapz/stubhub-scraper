package com.djdapz.stubhub.util;

import com.djdapz.stubhub.domain.Listing;
import com.djdapz.stubhub.domain.ListingResponse;
import com.djdapz.stubhub.domain.Price;
import com.djdapz.stubhub.domain.ProcessedListing;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static java.lang.Math.abs;
import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;

public class Random {
    private static String CHARACTERS = "abcdefghijklmnopqrstuvwxyl1234567890-------";
    private static String URL_SAFE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";

    public static Integer randomInt(Integer bound) {
        int i = new java.util.Random().nextInt() % bound;
        return abs(i);
    }

    public static Integer randomInt() {
        return randomInt(100000);
    }

    private static String randomString() {
        return randomString(50, CHARACTERS);
    }

    public static String randomString(Integer bound) {
        return randomString(bound, CHARACTERS);
    }

    private static String randomUrlSafeString(Integer bound) {
        return randomString(bound, URL_SAFE_CHARACTERS);
    }

    private static String randomString(int length, String characters) {
        return range(0, length)
                .mapToObj(i -> characters.charAt(
                        randomInt(characters.length() - 1)))
                .map(Object::toString)
                .collect(joining(""));
    }

    @SneakyThrows
    public static URL randomUrl() {

        String sb = "http://" +
                randomUrlSafeString(3) +
                "." +
                randomUrlSafeString(20) +
                "." +
                randomUrlSafeString(3);
        return new URL(sb);
    }

    public static Listing randomListing() {
        return new Listing(
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
                randomInt()
        );
    }

    public static ProcessedListing randomProcessedListing() {
        return new ProcessedListing(
                randomInt(),
                randomInt(),
                randomOffsetDateTime(),
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
                randomInt()
        );
    }

    public static OffsetDateTime randomOffsetDateTime() {
        return OffsetDateTime
                .now()
                .minusYears(randomInt(50))
                .minusDays(randomInt(365))
                .minusMinutes(randomInt(60))
                .minusSeconds(randomInt(60))
                .minusNanos(randomInt(1000));
    }


    private static Boolean randomBoolean() {
        return randomInt() % 2 == 1;
    }

    public static ListingResponse randomListingResponse() {
        return new ListingResponse(
                randomInt(),
                randomInt(),
                randomInt(),
                randomInt(),
                randomInt(),
                randomList(Random::randomListing),
                randomList(Random::randomInt),
                randomInt(),
                randomInt()
        );
    }

    private static <RETURN> List<RETURN> randomList(Supplier<RETURN> function) {
        List<RETURN> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(function.get());
        }
        return list;
    }

    private static Price randomPrice() {
        return new Price(
                randomBigDecimal(),
                randomString()
        );
    }

    private static BigDecimal randomBigDecimal() {
        return new BigDecimal(
                randomInt(100000).toString()
                        + "."
                        + randomInt(100000).toString()
        );
    }
}
