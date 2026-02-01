package mc.pesiik.reposlistimpl.interactor

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import mc.pesiik.reposlistapi.domain.Repo
import mc.pesiik.reposlistapi.domain.ReposRepository
import mc.pesiik.reposlistapi.domain.User
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SearchReposOrPaginateInteractorTest {

    private lateinit var reposRepository: ReposRepository
    private lateinit var interactor: SearchReposOrPaginateInteractor

    @Before
    fun setup() {
        reposRepository = mockk()
        interactor = SearchReposOrPaginateInteractor(reposRepository)
    }

    @Test
    fun `WHEN searchRepos THEN call repository with correct parameters`() = runTest {
        // Given
        val query = "kotlin"
        val page = 1
        val sort = "stars"
        val expectedRepos = listOf(
            Repo(
                id = 1,
                name = "repo1",
                starsCount = 100,
                forksCount = 50,
                lastUpdated = "2024-01-10T12:00:00Z",
                user = User(
                    login = "owner1",
                    avatarUrl = "https://example.com/avatar1.png"
                ),
                description = "Test repo 1",
                language = "Kotlin"
            )
        )
        coEvery {
            reposRepository.searchRepos(
                query = query,
                perPage = 30,
                page = page,
                sort = sort
            )
        } returns expectedRepos

        // When
        val result = interactor.searchRepos(query, page, sort)

        // Then
        assertEquals(expectedRepos, result)
        coVerify(exactly = 1) {
            reposRepository.searchRepos(
                query = query,
                perPage = 30,
                page = page,
                sort = sort
            )
        }
    }

    @Test
    fun `WHEN hasToPaginate with position at threshold THEN return true`() {
        // Given
        val lastVisiblePosition = 15
        val currentPage = 1

        // When
        val result = interactor.hasToPaginate(lastVisiblePosition, currentPage)

        // Then
        assertTrue(result)
    }

    @Test
    fun `WHEN hasToPaginate with position above threshold THEN return true`() {
        // Given
        val lastVisiblePosition = 20
        val currentPage = 1

        // When
        val result = interactor.hasToPaginate(lastVisiblePosition, currentPage)

        // Then
        assertTrue(result)
    }

    @Test
    fun `WHEN hasToPaginate with position below threshold THEN return false`() {
        // Given
        val lastVisiblePosition = 10
        val currentPage = 1

        // When
        val result = interactor.hasToPaginate(lastVisiblePosition, currentPage)

        // Then
        assertFalse(result)
    }

    @Test
    fun `WHEN hasToPaginate on second page at threshold THEN return true`() {
        // Given
        val lastVisiblePosition = 45
        val currentPage = 2

        // When
        val result = interactor.hasToPaginate(lastVisiblePosition, currentPage)

        // Then
        assertTrue(result)
    }

    @Test
    fun `WHEN hasToPaginate on second page below threshold THEN return false`() {
        // Given
        val lastVisiblePosition = 40
        val currentPage = 2

        // When
        val result = interactor.hasToPaginate(lastVisiblePosition, currentPage)

        // Then
        assertFalse(result)
    }
}