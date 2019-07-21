package app.soulcramer.arn.remote.test.mapper

import app.soulcramer.arn.remote.mapper.UserEntityMapper
import app.soulcramer.arn.remote.test.factory.UserFactory
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
    fun mapFromRemoteMapsData() {
        val userModel = UserFactory.makeUserModel()
        val userEntity = userEntityMapper.mapFromRemote(userModel)

        assertThat(userEntity.id).isEqualTo(userModel.id)
        assertThat(userEntity.nickname).isEqualTo(userModel.nick)
        assertThat(userEntity.role).isEqualTo(userModel.role)
        assertThat(userEntity.hasAvatar).isEqualTo(true)
        assertThat(userEntity.hasCover).isEqualTo(false)
        assertThat(userEntity.proExpiresDate).isEqualTo(userModel.proExpires)
    }
}