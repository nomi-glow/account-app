package com.assessment.accountapp.query


class Audit(
        var method: String?,
        var amount: Double?,
        var account_id: Long?,
        var start_balance: Double?,
        var end_balance: Double?,
        var user_id: String?
){
    override fun toString(): String{
        return "audit: { method: \"$method\", amount: $amount, account_id: $account_id, start_balance: $start_balance, end_balance: $end_balance, user_id: \"$user_id\" }"
    }
}