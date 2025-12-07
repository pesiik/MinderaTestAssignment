package mc.pesiik.pt_android_iliamashin.data

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import mc.pesiik.pt_android_iliamashin.core.cache.ReposTemporaryCache
import mc.pesiik.pt_android_iliamashin.data.mapper.ReposMapper
import mc.pesiik.pt_android_iliamashin.data.model.RepoDto
import mc.pesiik.pt_android_iliamashin.data.model.ReposDto
import mc.pesiik.pt_android_iliamashin.domain.Repo
import org.junit.Assert.assertEquals
import org.junit.Test

class ReposRepositoryImplTest {

    private val service: GitReposService = mockk()
    private val mapper: ReposMapper = mockk()
    private val cache: ReposTemporaryCache = mockk(
        relaxUnitFun = true
    )
    private val repository = ReposRepositoryImpl(
        gitReposService = service,
        reposMapper = mapper,
        reposTemporaryCache = cache,
    )

    @Test
    fun `WHEN searchRepos THEN returns mapped domain repos`() = runBlocking {
        // Given
        val repoDto = RepoDto(
            id = 1,
            name = "Repo1",
            owner = RepoDto.OwnerDto(
                login = "owner1",
                avatarUrl = "http://example.com/avatar1.png"
            ),
            description = "Description 1",
            stargazersCount = 100,
            language = "Kotlin",
            forksCount = 50,
            updatedAt = "2024-01-15T10:30:00Z"
        )
        val reposDto = ReposDto(
            incompleteResults = false,
            items = listOf(repoDto)
        )
        val domainRepo: Repo = mockk()
        coEvery {
            service.searchRepos(
                q = "org",
                perPage = 10,
                page = 1,
                sort = "test",
            )
        } returns reposDto
        coEvery { mapper.mapDtoToDomain(repoDto) } returns domainRepo

        // When
        val result = repository.searchRepos(
            query = "org",
            perPage = 10,
            page = 1,
            sort = "test",
        )

        // Then
        assertEquals(listOf(domainRepo), result)
        coVerify {
            service.searchRepos(
                q = "org",
                perPage = 10,
                page = 1,
                sort = "test",
            )
        }
        coVerify { mapper.mapDtoToDomain(any()) }
        coVerify { cache.putRepo(domainRepo) }
    }
}