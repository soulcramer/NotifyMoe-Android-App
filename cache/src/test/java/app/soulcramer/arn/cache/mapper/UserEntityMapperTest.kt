package app.soulcramer.arn.cache.mapper

import app.soulcramer.arn.cache.model.CachedUser
import app.soulcramer.arn.cache.test.factory.UserFactory
import app.soulcramer.arn.data.model.UserEntity
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserEntityMapperTest {

    private lateinit var userEntityMapper: UserEntityMapper

    @Before
    fun setUp() {
        userEntityMapper = UserEntityMapper()
    }

    @Test
    fun mapToCachedMapsData() {
        val userEntity = UserFactory.makeUserEntity()
        val cachedUser = userEntityMapper.mapToCached(userEntity)

        assertUserDataEquality(userEntity, cachedUser)
    }

    @Test
    fun mapFromCachedMapsData() {
        val cachedUser = UserFactory.makeCachedUser()
        val userEntity = userEntityMapper.mapFromCached(cachedUser)

        assertUserDataEquality(userEntity, cachedUser)
    }

    private fun assertUserDataEquality(userEntity: UserEntity, cachedUser: CachedUser) {
        assertThat(userEntity.id).isEqualTo(cachedUser.id)
        assertThat(userEntity.nickname).isEqualTo(cachedUser.name)
        assertThat(userEntity.role).isEqualTo(cachedUser.title)
        assertThat(userEntity.avatar).isEqualTo(cachedUser.avatar)
        assertThat(userEntity.cover).isEqualTo(cachedUser.cover)
    }
}