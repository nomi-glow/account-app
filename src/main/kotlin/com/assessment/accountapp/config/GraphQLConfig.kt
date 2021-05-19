package com.assessment.accountapp.config

import com.assessment.accountapp.query.AccountQuery
import graphql.GraphQL
import graphql.schema.GraphQLSchema
import io.leangen.graphql.GraphQLSchemaGenerator
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class GraphQLConfig {

    @Bean
    open fun graphQL(
            accountQuery: AccountQuery
    ): GraphQL {
        val schema = prepareGraphQLSchema(accountQuery)

        return GraphQL.newGraphQL(schema).build()
    }

    private fun prepareGraphQLSchema(
            accountQuery: AccountQuery
    ): GraphQLSchema =
            GraphQLSchemaGenerator()
                    .withBasePackages("com.assessment.accountapp")
                    .withOperationsFromSingletons(accountQuery)
                    .generate()

    @Bean
    open fun keycloakConfigResolver(): KeycloakSpringBootConfigResolver {
        return KeycloakSpringBootConfigResolver()
    }

}