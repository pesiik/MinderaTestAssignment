package mc.pesiik.pt_android_iliamashin.data.mapper

import mc.pesiik.pt_android_iliamashin.data.mapper.ReposMapper
import mc.pesiik.pt_android_iliamashin.data.model.RepoDto
import mc.pesiik.pt_android_iliamashin.domain.Repo
import org.junit.Assert.assertEquals
import org.junit.Test

class ReposMapperTest {

    private val mapper = ReposMapper()

    @Test
    fun `WHEN map dto THEN get correct domain`() {
        // Given
        val repoDto = RepoDto(
            name = "TestRepo",
            owner = RepoDto.OwnerDto(
                avatarUrl = "http://example.com/avatar.png"
            ),
            description = "This is a test repository"
        )

        // When
        val repoDomain = mapper.mapDtoToDomain(repoDto)

        // Then
        val expectedDomain = Repo(
            name = "TestRepo",
            ownerAvatarUrl = "http://example.com/avatar.png",
            description = "This is a test repository"
        )

        assertEquals(expectedDomain, repoDomain)
    }
}