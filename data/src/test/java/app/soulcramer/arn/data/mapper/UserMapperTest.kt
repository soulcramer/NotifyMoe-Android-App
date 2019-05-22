package app.soulcramer.arn.data.mapper

import app.soulcramer.arn.data.model.UserEntity
import app.soulcramer.arn.data.test.factory.UserFactory
import app.soulcramer.arn.domain.model.User
import org.junit.Assert.assertEquals
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
        assertEquals(userFactoryEntity.name, user.name)
        assertEquals(userFactoryEntity.title, user.title)
        assertEquals(userFactoryEntity.avatar, user.avatar)
    }
}