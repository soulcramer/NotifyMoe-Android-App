package app.soulcramer.arn.remote

import app.soulcramer.arn.data.model.UserEntity
import app.soulcramer.arn.data.repository.UserRemote
import app.soulcramer.arn.remote.mapper.UserEntityMapper
import app.soulcramer.arn.remote.model.UserModel
import kotlinx.coroutines.runBlocking

/**
 * Remote implementation for retrieving User instance. This class implements the
 * [UserRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class UserRemoteImpl(private val service: NotifyMoeService,
    private val entityMapper: UserEntityMapper) :
    UserRemote {

    /**
     * Retrieve a [UserEntity] instances from the [NotifyMoeService].
     */
    override fun getUser(userId: String): UserEntity {
        return runBlocking {
            service.getUserById(userId).await().let { response ->
                require(response.isSuccessful)
                requireNotNull(response.body())
                val user = response.body()!!
                entityMapper.mapFromRemote(user)
            }
        }
    }

    /**
     * Retrieve a [UserEntity] instances from the [NotifyMoeService].
     */
    override fun getUserByNickname(nick: String): UserEntity {
        return service.getUserByNickname(nick).let { response ->
            require(response is ApiSuccessResponse<UserModel>)
            val user = response.body
            entityMapper.mapFromRemote(user)
        }
    }

}