package mc.pesiik.repodetailsimpl.data

import mc.pesiik.repodetailsapi.domain.model.RepoDetails
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.Before
import org.junit.Test


internal class RepoDetailsMapperTest {

    private lateinit var mapper: RepoDetailsMapper

    @Before
    fun setUp() {
        mapper = RepoDetailsMapper()
    }

    @Test
    fun `mapDtoToDomain maps all fields correctly`() {
        // Given
        val dto = RepoDetailsDto(
            name = "TestRepo",
            description = "Test description",
            stargazersCount = 100,
            forksCount = 50,
            updatedAt = "2024-01-01T00:00:00Z",
            subscribersCount = 25
        )
        val expected = RepoDetails(
            name = "TestRepo",
            description = "Test description",
            starsCount = 100,
            forksCount = 50,
            lastUpdated = "2024-01-01T00:00:00Z",
            subscribersCount = 25
        )

        // When
        val result = mapper.mapDtoToDomain(
            repoDto = dto,
        )

        // Then
        assertEquals(expected, result)
    }
}