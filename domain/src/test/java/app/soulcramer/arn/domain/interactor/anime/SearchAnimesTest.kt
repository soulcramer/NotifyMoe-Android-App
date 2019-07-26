package app.soulcramer.arn.domain.interactor.anime

import app.soulcramer.arn.domain.repository.AnimeRepository
import io.mockk.mockk
import org.junit.Before
import org.junit.Ignore

@Ignore("Waiting to find how to test datasource")
class SearchAnimesTest {

    private lateinit var searchAnimes: SearchAnimes

    private lateinit var testAnimeRepository: AnimeRepository

    @Before
    fun setUp() {
        testAnimeRepository = mockk()
        searchAnimes = SearchAnimes(testAnimeRepository)
    }
}