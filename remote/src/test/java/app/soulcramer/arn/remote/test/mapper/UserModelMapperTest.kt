package app.soulcramer.arn.remote.test.mapper

import app.soulcramer.arn.remote.mapper.UserModelMapper
import app.soulcramer.arn.remote.test.factory.UserFactory
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserModelMapperTest {

    private lateinit var userModelMapper: UserModelMapper

    @Before
    fun setUp() {
        userModelMapper = UserModelMapper()
    }

    @Test
    fun mapFromRemoteMapsData() {
        val graphQlUserModel = UserFactory.makeGraphQlUserModel()
        val userModel = userModelMapper.mapFromRemote(graphQlUserModel)

        assertThat(userModel.id).isEqualTo(graphQlUserModel.ID)
        assertThat(userModel.nick).isEqualTo(graphQlUserModel.Nick)
        assertThat(userModel.role).isEqualTo(graphQlUserModel.Role)
        assertThat(userModel.avatar.extension).isEqualTo(graphQlUserModel.Avatar.Extension)
        assertThat(userModel.cover.extension).isEqualTo(graphQlUserModel.Cover.Extension)
        assertThat(userModel.proExpires).isEqualTo(graphQlUserModel.ProExpires)
    }
}