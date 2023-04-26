package com.example.`e-shopping`.tokenGenerator

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.`e-shopping`.myData.CustomClaim
import com.example.`e-shopping`.myData.TokenConfig
import java.util.*

interface TokenGenerator{
    fun generate(
        tokenConfig: TokenConfig,
        vararg claims:CustomClaim
    ):String
}
class TokenGeneratorImpl:TokenGenerator{
    override fun generate(tokenConfig: TokenConfig, vararg claims: CustomClaim):String {
        var token = JWT.create()
            .withAudience(tokenConfig.audience)
            .withIssuer(tokenConfig.issuer)
            .withExpiresAt(Date(System.currentTimeMillis() + tokenConfig.expiresIn))
        claims.forEach { claim->
            token = token.withClaim(claim.name, claim.value)
        }
        return token.sign(Algorithm.HMAC256(tokenConfig.secret))
    }

}