package com.wx.demo.base.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 配置用户存储和拦截请求
 * Spring Security提供了注解，Servlet API，JSP Tag，系统API等多种方式进行集成
 * 最常用的是第一种注解的方式，包含@Secured, @PreAuthorize, @PreFilter, @PostAuthorize和@PostFilter五个注解。
 * 要开启Spring方法级安全，你需要在已经添加了@Configuration注解的类上再添加@EnableGlobalMethodSecurity注解
 * 可以配置多个参数:
 * prePostEnabled: 决定Spring Security的前注解 [@PreAuthorize,@PostAuthorize,..] 是否可用
 * secureEnabled: 决定是否Spring Security的保障注解 [@Secured] 是否可用
 * secured 注释是用来定义业务方法的安全配置属性的列表。您可以在需要安全[角色/权限等]的方法上指定 @Secured，
 * 用法: @Secured({ "ROLE_DBA", "ROLE_ADMIN" })
 * 并且只有那些角色/权限的用户才可以调用该方法。如果有人不具备要求的角色/权限但试图调用此方法，
 * 将会抛出AccessDenied异常，它有一个局限就是不支持Spring EL表达式
 * Spring的 @PreAuthorize/@PostAuthorize 注解更适合方法级的安全,也支持Spring 表达式语言，
 * 提供了基于表达式的访问控制，@PreAuthorize 注解适合进入方法前的权限验证，@PostAuthorize，在方法执行后再进行权限验证
 * 用法: @PreAuthorize("hasRole('ADMIN') AND hasRole('DBA')")
 * Created by wangxiong on 2017/3/21.
 */

@Configuration
//禁用Boot的默认Security配置，配合@Configuration启用自定义配置
@EnableWebSecurity
//启用Security注解
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private CustomUserDetailsServiceImpl userDetailsService;

    /**
     * 配置拦截请求
     * Request层面的配置，用于配置URL的保护形式，和login页面
     * 通过拦截器Filter保护请求
     * =================
     * authorizeRequests()方法返回的对象还有更多的方法用于细粒度地保护请求。如下所示:
     * access(String) 如果给定的SpEL表达式返回的结果是true，就允许访问
     * anonymous() 允许匿名的用户访问
     * authenticated() 允许认证过的用户访问
     * fullyAuthenticated() 如果用户是完整认证的话(不是通过Remember Me功能认证的),就允许访问
     * hasAnyAuthority(String) 如果用户具备给定权限中某一个的话，就允许访问
     * hasAnyRole(String) 如果用户具备给定角色中某一个的话，就允许访问
     * hasAuthority(String) 如果用户具备给定权限的话，就允许访问
     * hasRole(String) 如果用户具备给定角色的话，就允许访问
     * hasIpAddress(String) 如果请求来自给定IP的话，就允许访问
     * not() 对其他访问方法发结果求反
     * permitAll() 无条件允许访问
     * denyAll() 无条件拒绝所有访问
     * rememberMe() 如果用户是通过RememberMe功能认证的，允许访问
     * =================
     * 我们可以将任意数量的antMatchers()、regexMatchers()和anyRequest()连接起来，以满足Web应用安全规则的需要。
     * 注意，将最不具体的路径（如anyRequest()）放在最后面。如果不这样做，那不具体的路径配置将会覆盖掉更为具体的路径配置
     * 使用Spring表达式进行安全保护
     * 借助access()方法，我们可以将SpEL作为声明访问限制的一种方式。例如，如下就是使用SpEL表达式来声明具有“ROLE_SPITTER”角色才能访问“/spitter/me”URL：
     * Spring Security支持的所有SpEL表达式
     * authentication 用户的认证对象
     * hasAnyRoles(roles) 如果用户被授予了其中的任何一个角色，返回true
     * hasIpAddress(IP address) 用户来自指定IP的话，返回true
     * isAnonymous() 如果当前用户是匿名用户的话，返回true
     * isAuthenticated() 如果当前用户是认证过了的话，返回true
     * isFullyAuthenticated() 如果用户是完整认证的话(不是通过Remember Me功能认证的),返回true
     * isRememberMe() 如果当前用户是通过RememberMe自动认证的，返回true
     * permitAll() 结果始终为true
     * denyAll 结果始终为false
     * principal() 用户的principal对象
     * =================
     * Spring Security拦截请求的另外一种方式：强制通道的安全性
     * 通过HTTP发送的数据没有经过加密，黑客就有机会拦截请求并且能够看到他们想看的数据。
     * 这就是为什么敏感信息要通过HTTPS来加密发送的原因。传递到configure()方法中的HttpSecurity对象，
     * 除了具有authorizeRequests()方法以外，还有一个requiresChannel()方法，
     * 借助这个方法能够为各种URL模式声明所要求的通道（如HTTPS）。
     * 在注册表单中，用户会希望敏感信息（用户不希望泄露的信息，如信用卡号等）是私密的。
     * 为了保证注册表单的数据通过HTTPS传送，我们可以在配置中添加requiresChannel()方法
     * .and().requiresChannel().antMatchers("spitter/form").requiresSecure();需要通过HTTPS传送
     * .antMatchers("/").requiresInsecure();不需要通过HTTPS传送
     *  =================
     * 防止跨站请求伪造
     * 这是跨站请求伪造（cross-site request forgery，CRSF）的一个简单样例。简单来讲，
     * 入过一个站点欺骗用户提交请求到其他服务器的话，就会发生CSRF攻击，这可能会带来很严重的后果。
     * 从Spring Security3.2开始，默认就会启用CSRF攻击。
     * Spring Security通过一个同步token的方式来实现CSRF防护。它会拦截状态变化的请求并检查CSRF token。
     * 如果请求不包含CSRF token，或token不能与服务器端的token相匹配，请求将会失败，并抛出CsrfException。
     * Spring Security已经简化了将token放到请求的属性中这一任务
     * 使用Thymeleaf，只要标签的action属性添加了Thymeleaf命名空间前缀，那么就会自动生成一个“_csrf”隐藏域
     * 使用JSP作为页面模板的话，要做的事非常类似
     * <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
     * =================
     * 认证用户
     * ==自定义登录页面
     * 实际上，在重写configure(HttpSecurity)之前，我们都能使用一个简单却功能完备的登录页。
     * 但是，一旦重写了configure(HttpSecurity)方法，就是失去了这个简单的登录界面。不过，这个功能要找回也容易。
     * 我们只需要在configure(HttpSecurity)方法中，调用formLogin()
     * ==启用HTTP Basic认证
     * 如果要启用HTTP Basic认证的话，只需要在configure()方法所传入的HttpSecurity对象上调用httpBasic()即可。
     * 另外，还可以通过调用realmName()方法指定域
     * ==启用Remember-me功能
     * 默认情况下，这个功能是通过在Cookie中存储一个token完成的，这个token最多两周内有效，
     * 在这里，我们指定这个token最多四周内有效（2,419,200秒）。存储在cookie中的token包含用户名、密码、过期时间和
     * 一个私匙——在写入cookie前都进行了MD5哈希。默认情况下，私匙的名为SpringSecured，
     * 但是这里我们将其设置为exampleKey，使他专门用于exampleKey应用。
     * 既然Remember-me功能已经启用，我们需要有一种方式来让用户表明他们希望应用程序记住他们。
     * 为了实现这一点，登录请求必须包含一个名为remember-me的参数。在登录表单中，增加一个简单复选框就可以完成这件事
     * <input id="remember-me" name="remember-me" type="checkbox"/>
     * <lable for="remember-me" class="inline">Remember me</label>
     * ==退出功能
     * 退出功能是通过Servlet容器的Filter实现的（默认情况下），这个Filter会拦截针对“/logout”的请求。
     * 在新版本的SpringSecurity中，出于安全的考虑（防止CSRF攻击），已经修改了LogoutFilter，
     * 使得Get方式的“/logout”请求不可用。必须以POST方式发起对该链接的请求才能生效。
     * 因此，为应用添加退出功能只需要添加如下表单即可（如下以Thymeleaf代码片段的形式进行了展现）
     * <form th:action="@{/logout}" method="POST">
     *     <button type="submit">退出登录</button>
     * </form>
     * 提交这个表单，会发起对“/logout”的请求，这个请求会被Spring Security的LogoutFilter所处理。
     * 用户会退出应用，所有的Remember-me token都会被清楚掉。在退出完成后，用户浏览器将会重定向到“/login?logout”
     *
     * //若一个用户使用用户名为"user"认证并且没有退出，同一个名为“user”的试图再次认证时，
     * 第一个用户的session将会强制销毁，并设置到"/login?expired"的url
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/admin/**").hasAnyRole("ADMIN","USER")
//                .antMatchers("/api/**").authenticated()
//                .anyRequest().permitAll()
//                .and()
//                .exceptionHandling()
//                .accessDeniedPage("/403.html");
//
//        http.csrf().disable();
//
//        http.httpBasic();
//
//        http.formLogin()
//                .usernameParameter("username") // 默认是 username
//                .passwordParameter("password") // 默认是 password
//                .loginPage("/admin/login") // 默认是 /login with an HTTP get
//                .loginProcessingUrl("/login") //定义登录表单提交url,默认是 /login with an HTTP post
//                .failureUrl("/admin/login?error") //登录失败后重定向，默认是 /login?error
//                .defaultSuccessUrl("/admin", false) //登录成功后跳转页面，true表示永远跳转，false表示跳转到之前页面
//                .permitAll();
//
//        http.logout()
//                .logoutUrl("/logout") //默认为/logout
//                .logoutSuccessUrl("/admin/login?logout")//用户浏览器将会重定向到“/login?logout”
//                .permitAll();
//
//        http.rememberMe()
//                .tokenValiditySeconds(2419200)
//                .key("exampleKey");
//
//        http.sessionManagement()
//                .maximumSessions(1)
//                .expiredUrl("/login?expired"); // session销毁后将会重定向到“/login?expired"


        http
            // 由于使用的是JWT，我们这里不需要csrf
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            // 基于token，所以不需要session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

            // 允许对于网站静态资源的无授权访问
//            .antMatchers(
//                    HttpMethod.GET,
//                    "/",
//                    "/*.html",
//                    "/favicon.ico",
//                    "/**/*.html",
//                    "/**/*.css",
//                    "/**/*.js"
//            ).permitAll()
            // 对于获取token的rest api要允许匿名访问
            .antMatchers("/oauth/**").permitAll()
            // 除上面外的所有请求全部需要鉴权认证
            .anyRequest().authenticated();

            // 添加JWT filter
            http
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

            // 禁用缓存
            http.headers().cacheControl();

    }

    /**
     * Web层面的配置，一般用来配置无需安全检查的路径，如：忽略js/css/img等静态资源
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置不拦截规则
        // web.ignoring().antMatchers("/assets/**","/**/favicon.ico");
        web.ignoring().antMatchers(
            HttpMethod.GET,
            "/",
            "/*.html",
            "/favicon.ico",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js"
        );
    }

    /**
     * 配置用户存储
     * 使用基于内存的用户存储和使用数据库存储，关系型数据库和菲关系型数据库
     * 用于配置Authentication，身份验证配置，
     * 用于注入自定义身份验证Bean和密码校验规则，如: 用户和角色的查询方法
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 通过inmMemoryAuthentication()方法，使用基于内存的用户存储
        // withUser()方法返回的是UserDetailsManagerConfigurer.UserDetailsBuilder，
        // 这个对象提供了多个进一步配置用户的方法，如上面的为用户设置密码的password()方法、授予用户多个角色权限的roles()方法。
        // UserDetailsManagerConfigurer.UserDetailsBuilder对象的更多方法如下:
        // accountExpired(boolean) 定义账号是否过期
        // accountLocked(boolean) 定义账号是否锁定
        // and 用来连接配置
        // authorities(GrantedAuthority...) 授予某个用户一项或者多项权限
        // authorities(List<? extends GrantedAuthority>) 授予某个用户一项或者多项权限
        // authorities(String ...) 授予某个用户一项或者多项权限
        // credentialsExpired(boolean) 定义凭证是否已经过期
        // disabled(boolean) 定义账号是否已经禁用
        // password(String) 定义用户的密码
        // roles(String ...) 授予某个用户一项或者多个角色
        // roles()方法是authorities()方法的简写形式。roles()方法所给定的值都会添加一个“ROLE_”前缀，
        // 并将其作为权限授予给用户,roles("USER")等价于authorities("ROLE_USER")
//        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER")
//                .and().withUser("admin").password("password").roles("USER","ADMIN");


        // 基于数据库表进行认证
        // Spring Security内部默认的查询语句是写定的，可能在某些情况下并不适用。我们可以按照自己的方式配置查询
        // 借助passwordEncoder()方法可以指定一个密码转码器
        // passwordEncoder()方法可以接受Spring Security中PasswordEncoder接口的任意实现。
        // Spring Security的加密模块包括三个这样的实现：BCryptPasswordEncoder、NoOpPasswordEncoder和StandardPasswordEncoder
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("select username,password,true from Spitter where username=?")
//                .authoritiesByUsernameQuery("select username,'ROLE_USER' from Spitter where username=?")
//                .passwordEncoder(new StandardPasswordEncoder("53cr3t"));


        // 配置自定义的用户服务,自定义UserDetailsService接口实现,
        // 我们需要做的就是实现loadUserByUsername()方法，根据给定的用户名来查找用户。
        // 该方法会返回代表给定用户的UserDetails对象
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
        //自定义自己的验证规则
        //auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
