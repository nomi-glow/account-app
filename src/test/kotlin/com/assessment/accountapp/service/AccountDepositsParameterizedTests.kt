package com.assessment.accountapp.service

import com.assessment.accountapp.entity.Account
import com.assessment.accountapp.repository.AccountRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.keycloak.KeycloakSecurityContext
import org.keycloak.representations.AccessToken
import org.keycloak.representations.IDToken
import org.mockito.Mockito
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*
import java.util.stream.Stream
import javax.servlet.http.HttpServletRequest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountDepositsParameterizedTests {

    @MockBean
    val httpServletRequest: HttpServletRequest = Mockito.mock(HttpServletRequest::class.java)

    @MockBean
    var accountRepository: AccountRepository = Mockito.mock(AccountRepository::class.java)

    @org.mockito.Mock
    var accountService: AccountService = AccountService(httpServletRequest, accountRepository, null)


    @BeforeAll
    public final fun setup() {
        Mockito.`when`(accountRepository.findById(1))
                .thenReturn(Optional.of(accounts()[0]))

        Mockito.`when`(accountRepository.findById(2))
                .thenReturn(Optional.of(accounts()[1]))

        Mockito.`when`(accountRepository.save(mocks()[0])).thenReturn(mocks()[0])
        Mockito.`when`(accountRepository.save(mocks()[1])).thenReturn(mocks()[1])

        val keycloakSecurityContext = KeycloakSecurityContext("tokensttring", AccessToken(), "idToken", IDToken())

        Mockito.`when`(httpServletRequest.getAttribute(KeycloakSecurityContext::class.java.name)).thenReturn(keycloakSecurityContext)
    }

    @ParameterizedTest(name = "given \"{0}\" and  \"{1}\", when making a deposit, then it should return {2}")
    @MethodSource("accountArguments")
    fun `given input accountId and an deposit amount, then is should return balance plus amount`(
            accountId: Long,
            amount: Double,
            expected: Double
    ) {

        val actual = accountService.deposit(accountId, amount)
        Assertions.assertThat(actual.balance).isEqualTo(expected)
    }

    private companion object {
        @JvmStatic
        fun accountArguments() = Stream.of(
                Arguments.of(1, 200, 4200.50),
                Arguments.of(2, 6.00, 50006.0)
        )
    }

    private fun accounts(): List<Account> {
        val account1 = Account(1, "account1", 4000.50)
        val account2 = Account(2, "account2", 50000.00)
        return arrayListOf(account1, account2)
    }

    private fun mocks(): List<Account> {
        val account1 = Account(1, "account1", 4200.50)
        val account2 = Account(2, "account2", 50006.0)
        return arrayListOf(account1, account2)
    }
}