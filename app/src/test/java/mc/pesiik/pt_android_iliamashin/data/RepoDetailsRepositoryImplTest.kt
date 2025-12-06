package mc.pesiik.pt_android_iliamashin.data

import io.mockk.every
import io.mockk.mockk
import mc.pesiik.pt_android_iliamashin.core.navigation.cache.ReposTemporaryCache
import mc.pesiik.pt_android_iliamashin.domain.Repo
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RepoDetailsRepositoryImplTest {

    private lateinit var reposTemporaryCache: ReposTemporaryCache
    private lateinit var repository: RepoDetailsRepositoryImpl

    @Before
    fun setup() {
        reposTemporaryCache = mockk()
        repository = RepoDetailsRepositoryImpl(reposTemporaryCache)
    }

    @Test
    fun `WHEN getRepoDetails with valid id THEN return repo from cache`() {
        // Given
        val repoId = 123
        val expectedRepo = Repo(
            id = repoId,
            name = "TestRepo",
            ownerLogin = "owner",
            ownerAvatarUrl = "http://example.com/avatar.png",
            description = "Test description",
            starCount = 100,
            language = "Kotlin",
            forkCount = 50,
            watcherCount = 75,
            lastUpdated = "2024-01-15T10:30:00Z"
        )
        every {  (reposTemporaryCache.getRepoById(repoId)) } returns expectedRepo

        // When
        val result = repository.getRepoDetails(repoId)

        // Then
        assertEquals(expectedRepo, result)
    }
}