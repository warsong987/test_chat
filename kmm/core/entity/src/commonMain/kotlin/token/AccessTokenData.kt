package token

data class AccessTokenData(
    val accessToken: String,
    val refreshToken: String,
    val userName: String
)
