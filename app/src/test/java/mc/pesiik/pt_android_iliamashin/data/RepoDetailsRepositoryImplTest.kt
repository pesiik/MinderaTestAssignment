package mc.pesiik.pt_android_iliamashin.data

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import mc.pesiik.pt_android_iliamashin.core.cache.ReposTemporaryCache
import mc.pesiik.pt_android_iliamashin.data.mapper.RepoDetailsMapper
import mc.pesiik.pt_android_iliamashin.data.model.RepoDetailsDto
import mc.pesiik.pt_android_iliamashin.domain.Repo
import mc.pesiik.pt_android_iliamashin.domain.RepoDetails
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RepoDetailsRepositoryImplTest {

    private lateinit var reposTemporaryCache: ReposTemporaryCache
    private lateinit var gitRepoDetailService: GitRepoDetailService
    private lateinit var mapper: RepoDetailsMapper
    private lateinit var repository: RepoDetailsRepositoryImpl

    @Before
    fun setup() {
        reposTemporaryCache = mockk()
        gitRepoDetailService = mockk()
        mapper = mockk()
        repository = RepoDetailsRepositoryImpl(reposTemporaryCache, gitRepoDetailService, mapper)
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
            lastUpdated = "2024-01-15T10:30:00Z"
        )
        val dto = RepoDetailsDto(
            name = "TestRepo",
            description = "Test description",
            stargazersCount = 100,
            forksCount = 50,
            subscribersCount = 75,
            updatedAt = "2024-01-15T10:30:00Z"
        )
        every { (reposTemporaryCache.getRepoById(repoId)) } returns expectedRepo
        coEvery {
            gitRepoDetailService.getRepo(expectedRepo.ownerLogin, expectedRepo.name)
        } returns dto
        every { mapper.mapDtoToDomain(dto) } returns expectedRepoDetails

        // When
        val result = repository.getRepoDetails(repoId)

        // Then
        assertEquals(expectedRepoDetails, result)
    }
}