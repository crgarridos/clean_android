package me.cgarrido.cleanandroid.util

import java.util.*
import java.util.concurrent.ThreadLocalRandom

object DataFactory {

    fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    fun randomInt(range: IntRange = 0..1_000_000): Int {
        return ThreadLocalRandom.current().nextInt(range.start, range.endInclusive)
    }

    fun randomLong(): Long {
        return randomInt().toLong()
    }

    fun randomBoolean(): Boolean {
        return Math.random() < 0.5
    }

}