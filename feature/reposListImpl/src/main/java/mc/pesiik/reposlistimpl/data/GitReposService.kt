package mc.pesiik.reposlistimpl.data

import retrofit2.http.GET
import retrofit2.http.Query

internal interface GitReposService {
    @GET("/search/repositories")
    suspend fun searchRepos(
        @Query("q") q: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
        @Query("sort") sort: String?,
    ): ReposDto
}