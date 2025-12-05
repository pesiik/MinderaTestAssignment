package mc.pesiik.pt_android_iliamashin.data

import mc.pesiik.pt_android_iliamashin.data.model.RepoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GitReposService {
    @GET("/orgs/{org}/repos")
    suspend fun searchRepos(
        @Path("org") organization: String
    ): List<RepoDto>
}