package com.example.`e-shopping`.hashPassword

import com.example.`e-shopping`.myData.SaltedHash
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.security.SecureRandom

interface HashPassword{
    fun generateSaltedHash(value:String,saltLength:Int = 32):SaltedHash
    fun verifySaltedHash(value: String, saltedHash: SaltedHash):Boolean
}
class HashPasswordImpl:HashPassword{
    override fun generateSaltedHash(password: String, saltLength: Int): SaltedHash {
        val salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLength)
        val saltAsHex = Hex.encodeHexString(salt)
        val hash = DigestUtils.sha256Hex(saltAsHex+password)
        return SaltedHash(saltValue = saltAsHex, hashValue = hash)
    }

    override fun verifySaltedHash(password: String, saltedHash: SaltedHash): Boolean {
        return DigestUtils.sha256Hex(saltedHash.saltValue+password) == saltedHash.hashValue
    }

}