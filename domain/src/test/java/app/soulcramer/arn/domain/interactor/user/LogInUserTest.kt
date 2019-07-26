package app.soulcramer.arn.domain.interactor.user

import app.soulcramer.arn.core.test.factory.DataFactory
import app.soulcramer.arn.domain.interactor.Result.Failure
import app.soulcramer.arn.domain.interactor.Result.Success
import app.soulcramer.arn.domain.prefs.PreferenceStorage
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LogInUserTest {

    private lateinit var logInUser: LogInUser

    private lateinit var testPreferenceStorage: PreferenceStorage

    @Before
    fun setUp() {
        testPreferenceStorage = mockk()
        logInUser = LogInUser(testPreferenceStorage)
    }

    @Test
    fun `Given a user id logged When log user in Then just success`() {
        runBlocking {
            mockPreferenceStorageSetCurrentUserId()
            val result = logInUser(DataFactory.randomUuid())

            verify { testPreferenceStorage.currentUserId = any() }

            assertThat(result).isInstanceOf(Success::class.java)
        }
    }

    @Test
    fun `Given a blank user id When log user in Then return Error`() {
        runBlocking {
            mockPreferenceStorageSetCurrentUserId()
            val result = logInUser("   ")

            verify(exactly = 0) { testPreferenceStorage.currentUserId = any() }

            assertThat(result).isInstanceOf(Failure::class.java)

            val failure = result as Failure
            assertThat(failure.exception).isInstanceOf(IllegalArgumentException::class.java)
        }
    }

    private fun mockPreferenceStorageSetCurrentUserId() {
        every { testPreferenceStorage.currentUserId = any() } just Runs
    }
}