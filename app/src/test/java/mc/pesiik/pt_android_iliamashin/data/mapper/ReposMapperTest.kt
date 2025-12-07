package mc.pesiik.pt_android_iliamashin.data.mapper

import mc.pesiik.pt_android_iliamashin.data.model.RepoDto
import mc.pesiik.reposlistapi.domain.Repo
import org.junit.Assert.assertEquals
import org.junit.Test

class ReposMapperTest {

    private val mapper = ReposMapper()

    @Test
    fun `WHEN map dto THEN get correct domain`() {
        // Given
        val repoDto = RepoDto(
            id = 0,
            name = "TestRepo",
            owner = RepoDto.OwnerDto(
                login = "TestOwner",
                avatarUrl = "http://example.com/avatar.png"
            ),
            description = "This is a test repository",
            stargazersCount = 10,
            language = "Kotlin",
            forksCount = 5,
            updatedAt = "2024-06-01T12:00:00Z"
        )

        // When
        val repoDomain = mapper.mapDtoToDomain(repoDto)

        // Then
        val expectedDomain = Repo(
            id = 0,
            name = "TestRepo",
            ownerLogin = "TestOwner",
            ownerAvatarUrl = "http://example.com/avatar.png",
            description = "This is a test repository",
            starsCount = 10,
            language = "Kotlin",
            forksCount = 5,
            lastUpdated = "2024-06-01T12:00:00Z"
        )

        assertEquals(expectedDomain, repoDomain)
    }
}