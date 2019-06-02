package app.soulcramer.arn.remote

import app.soulcramer.arn.data.model.UserEntity
import app.soulcramer.arn.remote.mapper.UserEntityMapper
import app.soulcramer.arn.remote.model.UserModel
import app.soulcramer.arn.remote.test.factory.UserFactory
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class UserRemoteImplTest {

    private lateinit var entityMapper: UserEntityMapper
    @MockK
    private lateinit var notifyMoeService: NotifyMoeService

    private lateinit var userRemoteImpl: UserRemoteImpl

    @Before
    fun setUp() {
        notifyMoeService = mockk()
        entityMapper = UserEntityMapper()
        userRemoteImpl = UserRemoteImpl(notifyMoeService, entityMapper)
    }

    //<editor-fold desc="Get User">
    @Test
    fun getUserCompletes() {
        val user = UserFactory.makeUserResponse()
        stubNotifyMoeServiceGetUser(user)
        userRemoteImpl.getUser(user.id)
        verify { notifyMoeService.getUserById(user.id) }
    }

    @Test
    fun getUserReturnsData() {
        val user = UserFactory.makeUserResponse()
        stubNotifyMoeServiceGetUser(user)

        val responseUser = userRemoteImpl.getUser("")
        assertThat(responseUser).isInstanceOf(UserEntity::class.java)
    }
    //</editor-fold>

    private fun stubNotifyMoeServiceGetUser(user: UserModel) {
        every { notifyMoeService.getUserById(any()) } returns Response.success(user)
    }
}