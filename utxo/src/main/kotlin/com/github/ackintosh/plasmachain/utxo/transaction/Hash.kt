package com.github.ackintosh.plasmachain.utxo.transaction

data class Hash(val value: String) {
    companion object {
        val GENERATION = Hash(
            (ByteArray(32) { 0.toByte() })
                .map { String.format("%02X", it) }
                .joinToString("")
        )
        val ZERO = GENERATION
    }
}
