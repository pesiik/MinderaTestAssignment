package mc.pesiik.pt_android_iliamashin.data

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import mc.pesiik.pt_android_iliamashin.data.GitReposService
import mc.pesiik.pt_android_iliamashin.data.ReposRepositoryImpl
import mc.pesiik.pt_android_iliamashin.data.mapper.ReposMapper
import mc.pesiik.pt_android_iliamashin.data.model.RepoDto
import mc.pesiik.pt_android_iliamashin.data.model.ReposDto
import mc.pesiik.pt_android_iliamashin.domain.Repo
import org.junit.Assert.assertEquals
import org.junit.Test

class ReposRepositoryImplTest {

    private val service: GitReposService = mockk()
    private val mapper: ReposMapper = mockk()
    private val repository = ReposRepositoryImpl(
        gitReposService = service,
        reposMapper = mapper
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
        )
        val reposDto = ReposDto(
            incompleteResults = false,
            items = listOf(repoDto)
        )
        val domainRepo: Repo = mockk()
        coEvery { service.searchRepos("org") } returns reposDto
        coEvery { mapper.mapDtoToDomain(repoDto) } returns domainRepo

        // When
        val result = repository.searchRepos("org")

        // Then
        assertEquals(listOf(domainRepo), result)
        coVerify { service.searchRepos("org") }
        coVerify { mapper.mapDtoToDomain(any()) }
    }
}