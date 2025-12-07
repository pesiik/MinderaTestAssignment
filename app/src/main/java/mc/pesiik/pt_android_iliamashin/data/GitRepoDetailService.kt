package mc.pesiik.pt_android_iliamashin.data

import mc.pesiik.pt_android_iliamashin.data.model.RepoDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GitRepoDetailService {
    @GET("/repos/{owner}/{repo}")
    suspend fun getRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): RepoDetailsDto
}