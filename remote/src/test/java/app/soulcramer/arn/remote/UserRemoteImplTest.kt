package app.soulcramer.arn.remote

import app.soulcramer.arn.data.model.UserEntity
import app.soulcramer.arn.remote.mapper.UserEntityMapper
import app.soulcramer.arn.remote.mapper.UserModelMapper
import app.soulcramer.arn.remote.model.user.UserModel
import app.soulcramer.arn.remote.test.factory.UserFactory
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserRemoteImplTest {

    private lateinit var entityMapper: UserEntityMapper

    private lateinit var modelMapper: UserModelMapper

    @MockK
    private lateinit var notifyMoeService: NotifyMoeService

    private lateinit var userRemoteImpl: UserRemoteImpl

    @Before
    fun setUp() {
        notifyMoeService = mockk()
        entityMapper = UserEntityMapper()
        modelMapper = UserModelMapper()
        userRemoteImpl = UserRemoteImpl(notifyMoeService, entityMapper, modelMapper)
    }

    // <editor-fold desc="Get User">
    @Test
    fun getUserCompletes() {
        runBlocking {
            val user = UserFactory.makeUserResponse()
            stubNotifyMoeServiceGetUser(user)
            userRemoteImpl.getUser(user.id)
            coVerify { notifyMoeService.getUserById(user.id) }
        }
    }

    @Test
    fun getUserReturnsData() {
        runBlocking {
            val user = UserFactory.makeUserResponse()
            stubNotifyMoeServiceGetUser(user)

            val responseUser = userRemoteImpl.getUser("")
            assertThat(responseUser).isInstanceOf(UserEntity::class.java)
        }
    }
    // </editor-fold>

    private fun stubNotifyMoeServiceGetUser(user: UserModel) {
        coEvery { notifyMoeService.getUserById(any()) } returns user
    }
}