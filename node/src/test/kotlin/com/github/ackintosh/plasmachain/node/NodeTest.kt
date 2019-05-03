package com.github.ackintosh.plasmachain.node

import com.github.ackintosh.plasmachain.utxo.Address
import com.github.ackintosh.plasmachain.utxo.SignatureService
import com.github.ackintosh.plasmachain.utxo.extensions.toHexString
import com.github.ackintosh.plasmachain.utxo.transaction.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey

class NodeTest {
    @Test
    fun sendCoinsToBob() {
        val genesisTransaction = Node.getGenesisBlock().transactions.first()

        val input = Input(
            transactionHash = genesisTransaction.transactionHash(),
            outputIndex = OutputIndex(0u),
            signature = SignatureService.create(
                privateKey = Node.ALICE_KEY_PAIR.private as ECPrivateKey,
                transactionHash = genesisTransaction.transactionHash(),
                outputIndex = OutputIndex(0u)
            ),
            publicKey = Node.ALICE_KEY_PAIR.public as ECPublicKey
        )

        val bob = Address.from(Address.generateKeyPair())

        val output = Output(
            amount = 10,
            address = bob
        )

        val transaction = Transaction(
            inputs = listOf(input),
            outputs = listOf(output)
        )

        Assertions.assertTrue(Node.addTransaction(transaction))
    }

    @Test
    fun incorrectTransactionInput() {
        val incorrectTransactionHash = Hash(ByteArray(32) { 1.toByte() }.toHexString())

        val input = Input(
            transactionHash = incorrectTransactionHash,
            outputIndex = OutputIndex(0u),
            signature = SignatureService.create(
                privateKey = Node.ALICE_KEY_PAIR.private as ECPrivateKey,
                transactionHash = incorrectTransactionHash,
                outputIndex = OutputIndex(0u)
            ),
            publicKey = Node.ALICE_KEY_PAIR.public as ECPublicKey
        )

        val bob = Address.from(Address.generateKeyPair())

        val output = Output(
            amount = 10,
            address = bob
        )

        val transaction = Transaction(
            inputs = listOf(input),
            outputs = listOf(output)
        )

        Assertions.assertFalse(Node.addTransaction(transaction))
    }

    @Test
    fun createNewBlock() {
        val genesisTransaction = Node.getGenesisBlock().transactions.first()

        val input = Input(
            transactionHash = genesisTransaction.transactionHash(),
            outputIndex = OutputIndex(0u),
            signature = SignatureService.create(
                privateKey = Node.ALICE_KEY_PAIR.private as ECPrivateKey,
                transactionHash = genesisTransaction.transactionHash(),
                outputIndex = OutputIndex(0u)
            ),
            publicKey = Node.ALICE_KEY_PAIR.public as ECPublicKey
        )

        val bob = Address.from(Address.generateKeyPair())

        val output = Output(
            amount = 10,
            address = bob
        )

        val transaction = Transaction(
            inputs = listOf(input),
            outputs = listOf(output)
        )

        Assertions.assertTrue(Node.addTransaction(transaction))
        Assertions.assertTrue(Node().createNewBlock())
    }
}