package app.soulcramer.arn.ui.user

import app.soulcramer.arn.domain.interactor.GetUser
import app.soulcramer.arn.domain.interactor.Result
import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.test.factory.UserFactory
import app.soulcramer.arn.ui.common.Data
import app.soulcramer.arn.ui.common.Error
import app.soulcramer.arn.ui.common.Loading
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [26])
class UserViewModelTest {

    private lateinit var mockGetUser: GetUser

    @Before
    fun setup() {
        mockGetUser = mockk()
    }

    @Test
    fun `Given failed user loading When updating state Then viewState should be error`() = runBlocking {
        mockGetUserFailure()
        val userViewModel = UserViewModel(mockGetUser)

        userViewModel.handle(UserContext.Action.LoadUser("qzd"))

        assertThat(userViewModel.state.value?.status).isEqualTo(Error)
    }

    @Test
    fun `Given ongoing user loading When updating state Then state should be same except loading`() = runBlocking {
        mockGetUserLoading()
        val userViewModel = UserViewModel(mockGetUser)

        userViewModel.handle(UserContext.Action.LoadUser("qzd"))

        assertThat(userViewModel.state.value?.status).isEqualTo(Loading)
    }

    @Test
    fun `Given ongoing user loading When updating state Then state should be containt new user`() = runBlocking {
        val testUser = UserFactory.makeUser()
        mockGetUserSuccess(testUser)
        val userViewModel = UserViewModel(mockGetUser)

        userViewModel.handle(UserContext.Action.LoadUser("qzd"))

        val state = userViewModel.state.value

        assertThat(state?.status).isEqualTo(Data)
        assertThat(state?.avatar).isEqualTo(testUser.avatarUrl)
        assertThat(state?.cover).isEqualTo(testUser.coverUrl)
        assertThat(state?.name).isEqualTo(testUser.nickname)
        assertThat(state?.title).isEqualTo(testUser.role)
    }

    private fun mockGetUserFailure() {
        coEvery { mockGetUser.invoke(any()) } returns Result.Failure(Exception("failed"))
    }

    private fun mockGetUserLoading() {
        coEvery { mockGetUser.invoke(any()) } returns Result.Loading
    }

    private fun mockGetUserSuccess(user: User) {
        coEvery { mockGetUser.invoke(any()) } returns Result.Success(user)
    }

}