package Sysc4806Group.demo.config;

import Sysc4806Group.demo.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user@test.com").password(passwordEncoder().encode("user1password")).roles("USER")
//                .and()
//                .withUser("admin@amazin.com").password(passwordEncoder().encode("root")).roles("ADMIN");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
//                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//                  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers("/", "/resources/**", "/signin", "/signup", "/logout", "/**/*.js", "/**/*.css", "/static/**").permitAll()
                .antMatchers("/editBook/**", "/updateBook/**", "/uploadBook").hasRole("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/signin").usernameParameter("email").permitAll()
//                .and().antMatchers("/editBook/**", "/updateBook/**", "/uploadBook").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true);
//                .defaultSuccessUrl("/profile").and().logout().logoutSuccessUrl("/logout").permitAll();
//        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/icon/**");
    }


}