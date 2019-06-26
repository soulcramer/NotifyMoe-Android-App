package app.soulcramer.arn.remote.test.factory

import app.soulcramer.arn.core.test.factory.DataFactory.Factory.randomUuid
import app.soulcramer.arn.remote.model.user.AvatarModel
import app.soulcramer.arn.remote.model.user.CoverModel
import app.soulcramer.arn.remote.model.user.UserModel
import app.soulcramer.arn.remote.model.user.graphql.GraphQlAvatarModel
import app.soulcramer.arn.remote.model.user.graphql.GraphQlCoverModel
import app.soulcramer.arn.remote.model.user.graphql.GraphQlUserModel
import java.time.OffsetDateTime

/**
 * Factory class for User related instances
 */
class UserFactory {

    companion object Factory {

        fun makeUserResponse(): UserModel {
            return makeUserModel()
        }

        fun makeUserModel(): UserModel {
            return UserModel(
                randomUuid(),
                randomUuid(),
                randomUuid(),
                AvatarModel(randomUuid()),
                CoverModel(randomUuid()),
                OffsetDateTime.now()
            )
        }

        fun makeGraphQlUserModel(): GraphQlUserModel {
            return GraphQlUserModel(randomUuid(),
                randomUuid(),
                randomUuid(),
                GraphQlAvatarModel(randomUuid()),
                GraphQlCoverModel(randomUuid()),
                OffsetDateTime.now()
            )
        }
    }
}