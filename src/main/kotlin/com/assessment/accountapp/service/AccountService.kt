package com.assessment.accountapp.service

import com.assessment.accountapp.client.AppGraphQLWebClient
import com.assessment.accountapp.entity.Account
import com.assessment.accountapp.query.Audit
import com.assessment.accountapp.repository.AccountRepository
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest
import org.keycloak.KeycloakSecurityContext

@Service
class AccountService(private val request: HttpServletRequest?, private val accountRepository: AccountRepository, private val appGraphQLWebClient: AppGraphQLWebClient?) {

    fun getAccounts(): List<Account> =
            accountRepository.findAll()

    private fun findAccountById(accountId: Long): Account =
            accountRepository.findById(accountId).get()

    private fun save(account: Account) = accountRepository.save(account)

    fun deposit(accountId: Long, amount: Double): Account {
        val account: Account = findAccountById(accountId)
        val startBalance: Double = account.balance
        account.balance += amount
        save(account)
        performAudit("deposit", amount, accountId, startBalance, account.balance)
        return account
    }

    fun withdraw(accountId: Long, amount: Double): Account {
        val account: Account = findAccountById(accountId)
        if(amount <= account.balance) {
            var startBalance: Double = account.balance
            account.balance -= amount
            save(account)
            performAudit("withdraw", amount, accountId, startBalance, account.balance)
        }else{
            throw RuntimeException("You have insufficient funds.")
        }
        return account
    }

    fun balance(accountId: Long): Double {
        val account = findAccountById(accountId)
        return account.balance;
    }

    private fun performAudit(functionName: String, amount: Double, accountId: Long, startBalance: Double, endBalance: Double) {
        val userId = getKeycloakSecurityContext().token?.preferredUsername
        val token = "Bearer " + getKeycloakSecurityContext().tokenString
        val audit = Audit(functionName, amount, accountId, startBalance, endBalance, userId)
        appGraphQLWebClient?.saveAudit(audit, token)
    }

    private fun getKeycloakSecurityContext(): KeycloakSecurityContext {
        return request?.getAttribute(KeycloakSecurityContext::class.java.name) as KeycloakSecurityContext
    }
}