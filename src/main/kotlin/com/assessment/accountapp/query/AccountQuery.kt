package com.assessment.accountapp.query

import com.assessment.accountapp.entity.Account
import com.assessment.accountapp.service.AccountService
import io.leangen.graphql.annotations.GraphQLMutation
import io.leangen.graphql.annotations.GraphQLQuery
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi
import org.springframework.stereotype.Component

@Component
@GraphQLApi
class AccountQuery (
        private val accountService: AccountService
){
    @GraphQLQuery(name = "accounts")
    fun accounts(): List<Account> =
            accountService.getAccounts()

    @GraphQLMutation(name = "deposit")
    fun deposit(accountId: Long, amount: Double) : Account = accountService.deposit(accountId, amount)

    @GraphQLMutation(name = "withdraw")
    fun withdraw(accountId: Long, amount: Double) : Account = accountService.withdraw(accountId, amount)

    @GraphQLQuery(name = "balance")
    fun balance(accountId: Long) : Double = accountService.balance(accountId)
}