package mc.pesiik.repodetailsimpl.data

import retrofit2.http.GET
import retrofit2.http.Path

internal interface GitRepoDetailService {
    @GET("/repos/{owner}/{repo}")
    suspend fun getRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): RepoDetailsDto
}