package com.wx.demo.base.auth;

import com.wx.demo.base.security.CustomUserDetails;
import com.wx.demo.base.security.JwtTokenUtil;
import com.wx.demo.dao.UserDao;
import com.wx.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by wangxiong on 2017/9/16.
 */
@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private UserDao userdao;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil,
            UserDao userdao) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userdao = userdao;
    }

    /**
     * 注册
     * @param userToAdd
     * @return
     */
    @Override
    public User register(User userToAdd) {
        final String username = userToAdd.getUserName();
        if(userdao.getByName(username)!=null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userToAdd.getUserPassword();
        userToAdd.setUserPassword(encoder.encode(rawPassword));
        userdao.save(userToAdd);

        return userToAdd;
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

    /**
     * 刷新
     * @param oldToken
     * @return
     */
    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
//        String username = jwtTokenUtil.getUsernameFromToken(token);
//        CustomUserDetails user = (CustomUserDetails) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token)){
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }
}
