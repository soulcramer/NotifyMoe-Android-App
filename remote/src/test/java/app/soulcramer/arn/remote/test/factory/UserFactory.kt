package app.soulcramer.arn.remote.test.factory

import app.soulcramer.arn.core.test.factory.DataFactory.Factory.randomUuid
import app.soulcramer.arn.remote.model.UserModel

/**
 * Factory class for User related instances
 */
class UserFactory {

    companion object Factory {

        fun makeUserResponse(): UserModel {
            return makeUserModel()
        }

        fun makeUserModel(): UserModel {
            return UserModel(randomUuid(), randomUuid(), randomUuid())
        }

    }

}