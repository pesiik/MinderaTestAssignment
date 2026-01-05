package mc.pesiik.repodetailsimpl.data

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import mc.pesiik.data.cache.TemporaryCache
import mc.pesiik.repodetailsapi.domain.model.RepoDetails
import mc.pesiik.reposlistapi.domain.Repo
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class RepoDetailsRepositoryImplTest {

    private lateinit var gitRepoDetailService: GitRepoDetailService
    private lateinit var mapper: RepoDetailsMapper
    private lateinit var repository: RepoDetailsRepositoryImpl

    @Before
    fun setup() {
        gitRepoDetailService = mockk()
        mapper = mockk()
        repository = RepoDetailsRepositoryImpl(gitRepoDetailService, mapper)
    }

    @Test
    fun `WHEN getRepoDetails with valid id THEN return repo from cache`() = runTest {
        // Given
        val expectedRepoDetails = RepoDetails(
            name = "TestRepo",
            description = "Test description",
            starsCount = 100,
            forksCount = 50,
            subscribersCount = 75,
            lastUpdated = "2024-01-15T10:30:00Z",
        )
        val dto = RepoDetailsDto(
            name = "TestRepo",
            description = "Test description",
            stargazersCount = 100,
            forksCount = 50,
            subscribersCount = 75,
            updatedAt = "2024-01-15T10:30:00Z"
        )
        coEvery {
            gitRepoDetailService.getRepo("owner", "repo")
        } returns dto
        every {
            mapper.mapDtoToDomain(repoDto = dto)
        } returns expectedRepoDetails

        // When
        val result = repository.getRepoDetails(
            owner = "owner",
            repo = "repo"
        )

        // Then
        Assert.assertEquals(expectedRepoDetails, result)
    }
}