package studio.lunabee.arn.common

import java.util.ArrayList

@Suppress("unused")
val Any?.unit
    get() = Unit

operator fun StringBuilder.plusAssign(string: String) = append(string).unit

fun <T> List<T>.changes(
    destination: MutableList<Pair<T, T>> = ArrayList(size)
): MutableList<Pair<T, T>> {
    for (i in 0..size - 2)
        destination += get(i) to get(i + 1)
    return destination
}
