package com.assessment.accountapp.entity

import javax.persistence.*


@Entity
class Account (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,
        @Column(nullable = false)
        var name: String,
        @Column(nullable = false)
        var balance: Double
){
        override fun equals(other: Any?): Boolean = (other is Account)
                && id == other.id
                && name == other.name
                && balance == other.balance
}