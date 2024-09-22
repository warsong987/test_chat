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

rootProject.name = "TestChat"
include(":app")
include(":core:components")
include(":utils")
include(":core:feature")
include(":kmm:core:config")
include(":kmm:core:entity")
include(":kmm:core:network")
include(":kmm:shared")
include(":kmm:utils:platform")
include(":kmm:utils:kotlin")
include(":kmm:feature:auth:api")
include(":kmm:feature:auth:impl")
include(":kmm:feature:registration:api")
include(":kmm:feature:registration:impl")
include(":kmm:feature:profile:api")
include(":kmm:feature:profile:impl")
include(":kmm:feature:chat:api")
include(":kmm:feature:chat:impl")
include(":feature:auth")
include(":feature:registration")
include(":feature:profile")
include(":feature:chat")
