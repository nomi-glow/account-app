package com.assessment.accountapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class AccountAppApplication

fun main(args: Array<String>) {
	runApplication<AccountAppApplication>(*args)
}



