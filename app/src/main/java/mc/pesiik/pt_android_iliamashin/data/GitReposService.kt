package mc.pesiik.pt_android_iliamashin.data

import mc.pesiik.pt_android_iliamashin.data.model.ReposDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GitReposService {
    @GET("/search/repositories")
    suspend fun searchRepos(
        @Query("q") q: String
    ): ReposDto
}