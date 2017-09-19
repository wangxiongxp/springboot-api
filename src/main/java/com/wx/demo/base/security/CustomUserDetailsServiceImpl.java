package com.wx.demo.base.security;

import com.wx.demo.entity.User;
import com.wx.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by wangxiong on 2017/3/21.
 */

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private static Logger log = LoggerFactory.getLogger(CustomUserDetailsServiceImpl.class);

    @Autowired
    private UserService userService ;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.getByName(userName);

        if (null == user) {
            throw new UsernameNotFoundException(String.format("User %s does not exist!", userName));
        }

        log.info(user.getUserName());
        log.info(user.getUserPassword());

        return new CustomUserDetails(user);
    }

}
