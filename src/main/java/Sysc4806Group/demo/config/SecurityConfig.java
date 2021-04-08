package Sysc4806Group.demo.config;

import Sysc4806Group.demo.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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

    /*public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
        authenticationMgr.inMemoryAuthentication()
                .withUser("userhamna@sysc.ca").password(passwordEncoder().encode("sysc4806-project-w21")).roles("USER")
                .and()
                .withUser("adminhamna@sysc.ca").password(passwordEncoder().encode("sysc4806-project-w21-user")).roles("ADMIN");
    }*/

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
                .antMatchers("/", "/resources/**", "/signin", "/signup", "/logout", "/*.js", "/*.css").permitAll()
                .antMatchers("/editBook/**", "/updateBook/**", "/uploadBook").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/signin").usernameParameter("email").permitAll()
//                .and()
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true);
//                .defaultSuccessUrl("/profile").and().logout().logoutSuccessUrl("/logout").permitAll();
//        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }


}