package teemoDevs.MainWebApplication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.core.user.OAuth2User;
import teemoDevs.MainWebApplication.auth.domain.CustomOAuth2User;

@Configuration
@EnableOAuth2Client
public class WebSecurityConfigurerAdapterImpl extends WebSecurityConfigurerAdapter {

    // CustomAuth2UserService를 @Component로 등록해야 함
    @Autowired
    private OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/home", "/error", "/webjars/**", "/resources/**", "/login**").permitAll()
                .anyRequest().authenticated()
                .and().oauth2Login()
                            .userInfoEndpoint()
                                    .customUserType(CustomOAuth2User.class, "teemo")
                                    .userService(oAuth2UserService);
    }

}