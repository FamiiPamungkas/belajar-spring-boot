package com.famipam.security.mapper;

import com.famipam.security.dto.user.SimpleUser;
import com.famipam.security.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public class SimpleUserMapper implements Function<User, SimpleUser> {

    /**
     * Applies this function to the given argument.
     *
     * @param user the function argument
     * @return the function result
     */
    @Override
    public SimpleUser apply(User user) {
        String fullname = user.getFirstname() + " " + user.getLastname();
        return new SimpleUser(
                user.getId(),
                user.getUsername(),
                fullname.trim(),
                user.getActive()
        );
    }
}
