package com.assessment.accountapp.client

import com.assessment.accountapp.query.Audit
import graphql.kickstart.spring.webclient.boot.GraphQLRequest
import graphql.kickstart.spring.webclient.boot.GraphQLResponse
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class AppGraphQLWebClient (private var graphQLWebClient: GraphQLWebClient) {
    private val LOGGER = LoggerFactory.getLogger(AppGraphQLWebClient::class.java)
   fun saveAudit(audit: Audit,  token: String) {
       val query: String = "mutation {\n" +
               "  addAudit ("+ audit.toString() +") {\n" +
               "    id\n" +
               "    method\n" +
               "    account_id\n" +
               "    start_balance\n" +
               "    end_balance\n" +
               "    created_at\n" +
               "  }\n" +
               "}"
       val request: GraphQLRequest = GraphQLRequest.builder().header("Authorization", token).query(query).build()
       val response: GraphQLResponse? = graphQLWebClient.post(request).block()
       LOGGER.info("Audit response: $response")
   }
}