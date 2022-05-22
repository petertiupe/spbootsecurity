package de.tiupe.spbootsecurity.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager


@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .antMatchers("/", "/home").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .permitAll()
            .and()
            .logout()
            .permitAll()
    }


    /*

    * Der AuthenticationManagerBuilder dient dazu, das Authentication Verfahren zu ändern. Spring bietet die folgende
    * Methode an, um dem AuthenticationManagerBuilder mitzuteilen, welcher AuthenticationManager für SpringSecurity
    * verwendet werden soll.
    *
    * Der Authentication Manager benötigt immer einen Passwort-Encoder, ansonsten wirft Spring einen Fehler
    *
    * IllegalArgumentException: There is no PasswordEncoder mapped....
    *
    * Da man sich hier in einer Configuration-Klasse befindet, kann man die Bean gleich hier definieren.
    *
    * Durch das Setzen des AuthenticationManagers funktioniert der UseDetailService so nicht mehr.
    *
    * Die Funktion lasse ich dennoch zur Dokumentation und zum Testen auch in diesem Branch stehen. Beide Codestellen
    * gehen gegen den InMemorySpeicher und haben daher ein Konkurenz-Problem.
    *
    * */
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.inMemoryAuthentication()
                .withUser("Peter")
                .password("peterwars")
                .roles("USER")
            // mit der and()-Funktion kann man beliebig weitere User hinzufügen
            .and()
                .withUser("Tina")
                .password("tinawars")
                .roles("USER")
    }

    /*
    * Siehe Kommentar zu
    *
    *   @see override fun configure(auth: AuthenticationManagerBuilder?)
    *
    * */
    @Bean
    fun getPasswordEncoder(): PasswordEncoder = NoOpPasswordEncoder.getInstance()

    @Bean
    public override fun userDetailsService(): UserDetailsService {
        val user = User.withDefaultPasswordEncoder()
            .username("user")
            .password("password")
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(user)
    }
}