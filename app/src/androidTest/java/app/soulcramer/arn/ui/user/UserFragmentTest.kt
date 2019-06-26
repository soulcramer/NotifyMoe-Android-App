package app.soulcramer.arn.ui.user

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.soulcramer.arn.R
import app.soulcramer.arn.cache.cacheModule
import app.soulcramer.arn.core.test.factory.DataFactory
import app.soulcramer.arn.di.appModule
import app.soulcramer.arn.domain.interactor.GetUser
import app.soulcramer.arn.domain.interactor.Result
import app.soulcramer.arn.domain.model.User
import app.soulcramer.arn.remote.remoteModule
import app.soulcramer.arn.test.util.UserFactory
import app.soulcramer.arn.ui.common.Data
import app.soulcramer.arn.ui.common.Error
import app.soulcramer.arn.ui.common.Loading
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class UserFragmentTest : KoinTest {

    lateinit var mockGetUser: GetUser

    @Before
    fun setUp() {
        mockGetUser = mockk()
        val testModule = module(override = true) {
            factory { mockGetUser }
        }
        loadKoinModules(listOf(appModule, cacheModule, remoteModule, testModule))
    }

    @Test
    fun givenSuccessUserInfo_whenShowingHim_thenAllInfoShown() = runBlocking<Unit> {
        val testUser = UserFactory.makeUser(name = "Scott", role = "editor")
        val testState = UserContext.State(name = testUser.nickname, title = testUser.role, status = Data)
        stubGetUserSuccess(testUser)

        val fragmentBundle = bundleOf(
            "userId" to DataFactory.randomUuid(),
            "userNickname" to "Scott"
        )
        launchFragmentInContainer<UserFragment>(fragmentBundle).onFragment {
            it.userViewModel.updateState { testState }
        }
        delay(TimeUnit.SECONDS.toMillis(1))

        onView(withId(R.id.nickNameTextView)).check(matches(withText(testState.name)))
        onView(withId(R.id.roleTextView)).check(matches(withText(testState.title)))
    }

    @Test
    fun givenErrorUserInfo_whenShowingHim_thenShowError() = runBlocking<Unit> {
        val testUser = UserFactory.makeUser(name = "Scott", role = "editor")
        val testState = UserContext.State(name = testUser.nickname, title = testUser.role, status = Error)
        stubGetUserFailure(Exception())

        val fragmentBundle = bundleOf(
            "userId" to DataFactory.randomUuid(),
            "userNickname" to "Scott"
        )
        launchFragmentInContainer<UserFragment>(fragmentBundle).onFragment {
            it.userViewModel.updateState { testState }
        }
        delay(TimeUnit.SECONDS.toMillis(1))

        onView(withId(R.id.nickNameTextView)).check(matches(withText(testState.name)))
        onView(withId(R.id.roleTextView)).check(matches(withText(testState.title)))
        onView(withId(R.id.statusTextView)).check(matches(withText("error")))
    }

    @Test
    fun givenErrorUserInfo_whenShowingHim_thenShowLoading() = runBlocking<Unit> {
        val testUser = UserFactory.makeUser(name = "Scott", role = "editor")
        val testState = UserContext.State(name = testUser.nickname, title = testUser.role, status = Loading)
        stubGetUserLoading()

        val fragmentBundle = bundleOf(
            "userId" to DataFactory.randomUuid(),
            "userNickname" to "Scott"
        )
        launchFragmentInContainer<UserFragment>(fragmentBundle).onFragment {
            it.userViewModel.updateState { testState }
        }
        delay(TimeUnit.SECONDS.toMillis(1))

        onView(withId(R.id.nickNameTextView)).check(matches(withText(testState.name)))
        onView(withId(R.id.roleTextView)).check(matches(withText(testState.title)))
        onView(withId(R.id.statusTextView)).check(matches(withText("loading")))
    }

    private fun stubGetUserSuccess(user: User) {
        coEvery { mockGetUser(any()) } returns Result.Success(user)
    }

    private fun stubGetUserLoading() {
        coEvery { mockGetUser(any()) } returns Result.Loading
    }

    private fun stubGetUserFailure(exception: Exception) {
        coEvery { mockGetUser(any()) } returns Result.Failure(exception)
    }
}