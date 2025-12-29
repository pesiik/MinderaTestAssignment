package mc.pesiik.repodetailsimpl.data

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import mc.pesiik.cache.TemporaryCache
import mc.pesiik.repodetailsapi.domain.model.RepoDetails
import mc.pesiik.reposlistapi.domain.Repo
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class RepoDetailsRepositoryImplTest {

    private lateinit var temporaryCache: TemporaryCache
    private lateinit var gitRepoDetailService: GitRepoDetailService
    private lateinit var mapper: RepoDetailsMapper
    private lateinit var repository: RepoDetailsRepositoryImpl

    @Before
    fun setup() {
        temporaryCache = mockk()
        gitRepoDetailService = mockk()
        mapper = mockk()
        repository = RepoDetailsRepositoryImpl(temporaryCache, gitRepoDetailService, mapper)
    }

    @Test
    fun `WHEN getRepoDetails with valid id THEN return repo from cache`() = runTest {
        // Given
        val repoId = 123
        val expectedRepo = Repo(
            id = repoId,
            name = "TestRepo",
            ownerLogin = "owner",
            ownerAvatarUrl = "http://example.com/avatar.png",
            description = "Test description",
            starsCount = 100,
            language = "Kotlin",
            forksCount = 50,
            lastUpdated = "2024-01-15T10:30:00Z"
        )
        val expectedRepoDetails = RepoDetails(
            name = "TestRepo",
            description = "Test description",
            starsCount = 100,
            forksCount = 50,
            subscribersCount = 75,
            lastUpdated = "2024-01-15T10:30:00Z",
            ownerLogin = "owner",
            ownerAvatarUrl = "http://example.com/avatar.png",
        )
        val dto = RepoDetailsDto(
            name = "TestRepo",
            description = "Test description",
            stargazersCount = 100,
            forksCount = 50,
            subscribersCount = 75,
            updatedAt = "2024-01-15T10:30:00Z"
        )
        every { (temporaryCache.getById<Repo>(repoId)) } returns expectedRepo
        coEvery {
            gitRepoDetailService.getRepo(expectedRepo.ownerLogin, expectedRepo.name)
        } returns dto
        every {
            mapper.mapDtoToDomain(
                repoDto = dto,
                ownerLogin = expectedRepo.ownerLogin,
                ownerAvatarUrl = expectedRepo.ownerAvatarUrl
            )
        } returns expectedRepoDetails

        // When
        val result = repository.getRepoDetails(repoId)

        // Then
        Assert.assertEquals(expectedRepoDetails, result)
    }
}