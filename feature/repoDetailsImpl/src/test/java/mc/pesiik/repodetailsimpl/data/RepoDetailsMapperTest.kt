package mc.pesiik.repodetailsimpl.data

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.Before
import org.junit.Test


class RepoDetailsMapperTest {

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

        // When
        val result = mapper.mapDtoToDomain(dto)

        // Then
        assertEquals("TestRepo", result.name)
        assertEquals("Test description", result.description)
        assertEquals(100, result.starsCount)
        assertEquals(50, result.forksCount)
        assertEquals("2024-01-01T00:00:00Z", result.lastUpdated)
        assertEquals(25, result.subscribersCount)
    }
}