package app.soulcramer.arn.remote.mapper

import app.soulcramer.arn.data.model.UserEntity
import app.soulcramer.arn.remote.model.user.UserModel
import java.time.OffsetDateTime

/**
 * Map a [UserModel] to and from a [UserEntity] instance when data is moving between
 * this later and the Data layer
 */
open class UserEntityMapper : EntityMapper<UserModel, UserEntity> {

    val avatarImageUrl = "https://media.notify.moe/images/avatars/large/"
    val coverImageUrl = "https://media.notify.moe/images/covers/large/"

    /**
     * Map an instance of a [UserModel] to a [UserEntity] model
     */
    override fun mapFromRemote(type: UserModel): UserEntity {
        val isPro = isPro(type.proExpires)
        return UserEntity(
            type.id,
            type.nick,
            type.role,
            type.proExpires,
            hasAvatar(type.avatar.extension),
            hasCover(type.cover.extension, isPro)
        )
    }

    private fun isPro(proExpires: OffsetDateTime?): Boolean {
        val proExpireDate = proExpires ?: return false
        return proExpireDate.isAfter(OffsetDateTime.now())
    }

    private fun hasAvatar(extension: String): Boolean {
        return extension.isNotEmpty()
    }

    private fun hasCover(extension: String, isPro: Boolean): Boolean {
        return extension.isNotEmpty() && isPro
    }
}