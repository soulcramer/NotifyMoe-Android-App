package studio.lunabee.arn.util

import android.os.SystemClock
import android.support.v4.util.ArrayMap

import java.util.concurrent.TimeUnit

/**
 * Utility class that decides whether we should fetch some data or not.
 */
class RateLimiter<in KEY>(timeout: Int, timeUnit: TimeUnit) {
    private val timestamps = ArrayMap<KEY, Long>()
    private val timeout = timeUnit.toMillis(timeout.toLong())

    @Synchronized
    fun shouldFetch(key: KEY): Boolean {
        val lastFetched = timestamps[key]
        val now = now()
        if (lastFetched == null || now - lastFetched > timeout) {
            timestamps[key] = now
            return true
        }
        return false
    }

    private fun now() = SystemClock.uptimeMillis()

    @Synchronized
    fun reset(key: KEY) {
        timestamps.remove(key)
    }
}