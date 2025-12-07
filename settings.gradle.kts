pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "PT_Android_iliaMashin"
include(":app")
include(":feature:repoDetailsApi")
include(":feature:repoDetailsImpl")
include(":core:cache")
include(":feature:reposListApi")
include(":core:navigation")
include(":core:resources")
include(":core:viewmodel")
