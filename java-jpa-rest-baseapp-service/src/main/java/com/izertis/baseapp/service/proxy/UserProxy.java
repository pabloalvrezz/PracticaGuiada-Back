package com.izertis.baseapp.service.proxy;

import com.izertis.abstractions.service.DeleteService;
import com.izertis.abstractions.service.QueryService;
import com.izertis.abstractions.service.SaveService;
import com.izertis.baseapp.service.dto.UserDto;
import com.izertis.baseapp.service.filter.UserFilter;
import com.izertis.baseapp.service.model.User;

/**
 * Proxy service for {@link User}. Performs DTO conversion and permission checks.
 */
public interface UserProxy
        extends QueryService<UserDto, String, UserFilter>, SaveService<UserDto>, DeleteService<UserDto, String> {
    /**
     * Unlocks a user account.
     *
     * @param identifier
     *            The identifier
     */
    void undelete(final String identifier);
    
   
}
