package app.soulcramer.arn.data.mapper

import app.soulcramer.arn.data.model.UserEntity
import app.soulcramer.arn.data.test.factory.UserFactory
import app.soulcramer.arn.domain.model.User
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserMapperTest {
    private lateinit var userMapper: UserMapper

    @Before
    fun setUp() {
        userMapper = UserMapper()
    }

    @Test
    fun mapFromEntityMapsData() {
        val userEntity = UserFactory.makeUserEntity()
        val user = userMapper.mapFromEntity(userEntity)

        assertUserDataEquality(userEntity, user)
    }

    @Test
    fun mapToEntityMapsData() {
        val cachedUser = UserFactory.makeUser()
        val userFactoryEntity = userMapper.mapToEntity(cachedUser)

        assertUserDataEquality(userFactoryEntity, cachedUser)
    }

    private fun assertUserDataEquality(userFactoryEntity: UserEntity, user: User) {
        assertThat(user.id).isEqualTo(userFactoryEntity.id)
        assertThat(user.nickname).isEqualTo(userFactoryEntity.nickname)
        assertThat(user.role).isEqualTo(userFactoryEntity.role)
        assertThat(user.proExpiresDate).isEqualTo(userFactoryEntity.proExpiresDate)
        assertThat(user.hasAvatar).isEqualTo(userFactoryEntity.hasAvatar)
        assertThat(user.hasCover).isEqualTo(userFactoryEntity.hasCover)
    }
}