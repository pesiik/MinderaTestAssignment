package mc.pesiik.pt_android_iliamashin.data

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import mc.pesiik.pt_android_iliamashin.data.GitReposService
import mc.pesiik.pt_android_iliamashin.data.ReposRepositoryImpl
import mc.pesiik.pt_android_iliamashin.data.mapper.ReposMapper
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
        val domainRepo: Repo = mockk()
        coEvery { service.searchRepos("org") } returns listOf(mockk())
        coEvery { mapper.mapDtoToDomain(any()) } returns domainRepo

        // When
        val result = repository.searchRepos("org")

        // Then
        assertEquals(listOf(domainRepo), result)
        coVerify { service.searchRepos("org") }
        coVerify { mapper.mapDtoToDomain(any()) }
    }
}