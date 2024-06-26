package site.chachacha.fitme.config;

import static org.apache.http.HttpStatus.SC_OK;
import static site.chachacha.fitme.enumstorage.messages.role.MemberRole.ADMIN;
import static site.chachacha.fitme.enumstorage.messages.role.MemberRole.MEMBER;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import site.chachacha.fitme.domain.auth.filter.AuthenticationProcessFilter;
import site.chachacha.fitme.domain.auth.handler.JwtLogoutHandler;

@Configuration
@EnableWebSecurity // Spring Securty 필터가 Spring Filter Chain에 등록된다.
//@EnableGlobalMethodSecurity(securedEnabled = true) // secured 어노테이션 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    // JWT
    private final JwtLogoutHandler jwtLogoutHandler;

    private final AuthenticationProcessFilter authenticationProcessFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable);

        // 기본 페이지, css, image, js 하위 폴더에 있는 자료들은 모두 접근 가능
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/css/**", "/img/**", "/js/**", "/favicon.ico", "/error/**")
                .permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/products/**").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/api/members/**").permitAll()
                .requestMatchers("/api/cart/**").permitAll()
                .requestMatchers("/api/dressroom/**").permitAll()
                .requestMatchers("/api/brands/**").permitAll()
                .requestMatchers("/api/orders/**").permitAll()
                .anyRequest().authenticated()

            );

        // 세션
        http
            .sessionManagement(sessionManagement ->
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 세션을 사용하지 않겠다.
            );

        // Basic 인증
        http
            // http header에 username, password를 넣어서 전송하는 방법을
            .httpBasic(
                // 사용하지 않겠다.
                AbstractHttpConfigurer::disable
            );

        // Filter
        http
            .addFilterAfter(authenticationProcessFilter, LogoutFilter.class);
//                .addFilterAfter(memberAuthenticationFilter(), AuthenticationProcessFilter.class);

        // 인증
        http
            .formLogin(
                AbstractHttpConfigurer::disable // form login 비활성화
            );

        // 로그아웃
        http
            .logout(logout ->
                logout.permitAll()
                    .logoutUrl("/api/auth/signout/v1")
                    .logoutSuccessHandler(
                        (request, response, authentication) -> response.setStatus(SC_OK))
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .addLogoutHandler(jwtLogoutHandler)
            );

        return http.build();
    }

//    @Bean
//    public AuthenticationManager authenticationManager() {
//        return new ProviderManager(memberAuthenticationProvider);
//    }

//    @Bean
//    public MemberAuthenticationFilter memberAuthenticationFilter() {
//        return new MemberAuthenticationFilter(objectMapper, authenticationManager(), memberLogInSuccessHandler, memberLogInFailureHandler);
//    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        //이 부분에서 큰 권한 순서로 ' > ' 를 사용하여 입력해준다. 띄어쓰기도 중요하다.
        roleHierarchy.setHierarchy(ADMIN + " > " + MEMBER);

        return roleHierarchy;
    }
}
