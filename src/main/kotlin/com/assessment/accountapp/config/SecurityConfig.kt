package com.assessment.accountapp.config

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.keycloak.adapters.springsecurity.management.HttpSessionManager
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter
import org.keycloak.adapters.springsecurity.KeycloakConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import kotlin.jvm.Throws


@KeycloakConfiguration
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
open class SecurityConfig : KeycloakWebSecurityConfigurerAdapter() {

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        val grantedAuthorityMapper = SimpleAuthorityMapper()
        grantedAuthorityMapper.setPrefix("ROLE_")

        val keycloakAuthenticationProvider = keycloakAuthenticationProvider()
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(SimpleAuthorityMapper())
        auth.authenticationProvider(keycloakAuthenticationProvider)
    }


    @Bean
    override fun sessionAuthenticationStrategy(): SessionAuthenticationStrategy {
        return RegisterSessionAuthenticationStrategy(SessionRegistryImpl())
    }

    @Bean
    @ConditionalOnMissingBean(HttpSessionManager::class)
    override fun httpSessionManager(): HttpSessionManager {
        return HttpSessionManager()
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        super.configure(http)
        http.authorizeRequests()
                .antMatchers("/graphql").hasAnyRole("client")
                .anyRequest().permitAll()
        http.csrf().disable();
    }


}