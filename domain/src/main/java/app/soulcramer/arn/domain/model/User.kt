package app.soulcramer.arn.domain.model

import java.time.OffsetDateTime

/**
 * Representation for a [User] fetched from an external layer data source
 */
data class User(
    val id: String,
    val nickname: String,
    val role: String,
    val proExpiresDate: OffsetDateTime?,
    val hasAvatar: Boolean,
    val hasCover: Boolean
) {
    /**
     * The implementation of `hasValidNickName` is the same from NotifyMoe, since the generated user names are starting with
     * the first letters of the platform the user logged on. This is working only because the NotifyMoe capitalize the user
     * nicknames
     */
    fun hasValidNickName(): Boolean {
        return !(
            nickname.startsWith("g") ||
                nickname.startsWith("fb") ||
                nickname.startsWith("t") ||
                nickname.isEmpty()
            )
    }

    val avatarUrl: String
        get() {
            return "https://media.notify.moe/images/avatars/large/$id.webp"
        }

    val coverUrl: String
        get() {
            if (hasCover) return "https://media.notify.moe/images/avatars/large/$id.webp"
            return "https://media.notify.moe/images/elements/default-cover.jpg"
        }

    val isPro: Boolean
        get() {
            val proExpireDate = proExpiresDate ?: return false
            return proExpireDate.isAfter(OffsetDateTime.now())
        }
}