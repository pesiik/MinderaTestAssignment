package mc.pesiik.repodetailsimpl.interactor

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import mc.pesiik.data.cache.TemporaryCache
import mc.pesiik.repodetailsapi.domain.RepoDetailsRepository
import mc.pesiik.repodetailsapi.domain.model.RepoDetails
import mc.pesiik.reposlistapi.domain.Repo
import mc.pesiik.reposlistapi.domain.User
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RepoDetailsInteractorTest {

    private lateinit var temporaryCache: TemporaryCache
    private lateinit var repoDetailsRepository: RepoDetailsRepository
    private lateinit var interactor: RepoDetailsInteractor

    @Before
    fun setup() {
        temporaryCache = mockk()
        repoDetailsRepository = mockk()
        interactor = RepoDetailsInteractor(temporaryCache, repoDetailsRepository)
    }

    @Test
    fun `WHEN getRepoDetails with valid id THEN return repo data`() = runTest {
        // Given
        val repoId = 123
        val expectedRepo = Repo(
            id = repoId,
            name = "TestRepo",
            starsCount = 2,
            forksCount = 5,
            lastUpdated = "2024-01-10T12:00:00Z",
            user = User(
                login = "owner",
                avatarUrl = "https://example.com/avatar.png",
            ),
            description = "Test description",
            language = "Kotlin"
        )
        val expectedDetails = RepoDetails(
            name = "TestRepo",
            description = "Test description",
            starsCount = 100,
            forksCount = 50,
            subscribersCount = 75,
            lastUpdated = "2024-01-15T10:30:00Z"
        )
        every {
            temporaryCache.getById<Repo>(repoId)
        } returns expectedRepo
        coEvery {
            repoDetailsRepository.getRepoDetails("owner", "TestRepo")
        } returns expectedDetails

        // When
        val result = interactor.getRepoDetails(repoId)

        // Then
        Assert.assertEquals(expectedRepo, result.first)
        Assert.assertEquals(expectedDetails, result.second)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `WHEN getRepoDetails with invalid id THEN throw exception`() = runTest {
        // Given
        val repoId = 999
        every {
            temporaryCache.getById<Repo>(repoId)
        } returns null

        // When
        interactor.getRepoDetails(repoId)

        // Then - exception is thrown
    }
}