package app.soulcramer.arn.domain.interactor.user

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

class LogOutUserTest {

    private lateinit var logOutUser: LogOutUser

    private lateinit var testPreferenceStorage: PreferenceStorage

    @Before
    fun setUp() {
        testPreferenceStorage = mockk()
        logOutUser = LogOutUser(testPreferenceStorage)
    }

    @Test
    fun `Given have user logged When log out user from current session Just run`() {
        runBlocking {
            mockPreferenceStorageSetCurrentUserId()
            val result = logOutUser()

            verify(exactly = 1) { testPreferenceStorage.currentUserId = null }

            assertThat(result).isInstanceOf(Success::class.java)
        }
    }

    private fun mockPreferenceStorageSetCurrentUserId() {
        every { testPreferenceStorage.currentUserId = null } just Runs
    }
}