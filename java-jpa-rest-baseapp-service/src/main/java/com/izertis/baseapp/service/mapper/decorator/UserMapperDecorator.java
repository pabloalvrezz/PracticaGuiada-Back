package com.izertis.baseapp.service.mapper.decorator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.izertis.abstractions.search.PageImplHelper;
import com.izertis.baseapp.service.dto.UserDto;
import com.izertis.baseapp.service.mapper.UserMapper;
import com.izertis.baseapp.service.model.User;

/**
 * MapStruct Mapper decorator for {@link User}.
 */
public abstract class UserMapperDecorator implements UserMapper {

    /**
     * Delegate {@link User} mapper.
     */
    @Autowired
    @Qualifier("delegate")
    private UserMapper delegate;

    /**
     * The password encoder.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc}
     */
    @Override
    public PageImplHelper<UserDto> convertToDto(final Page<User> page) {
        if (page == null) {
            return null;
        }

        return new PageImplHelper<>(this.delegate.convertToDto(page.getContent()),
                PageRequest.of(page.getNumber(), page.getSize(), page.getSort()), page.getTotalElements());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User convertFromDto(final UserDto dto) {
        if (dto == null) {
            return null;
        }

        final User user = this.delegate.convertFromDto(dto);
        this.updateUserPassword(dto, user);
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User updateFromDto(final UserDto dto, final User entity) {
        if (dto == null) {
            return null;
        }

        final User user = this.delegate.updateFromDto(dto, entity);
        this.updateUserPassword(dto, user);
        return user;
    }

    /**
     * Encode user password it it is changed.
     *
     * @param dto
     *            the DTO:
     * @param entity
     *            the entity
     */
    private void updateUserPassword(final UserDto dto, final User entity) {
        if (StringUtils.isNotBlank(dto.getPassword())
                && (dto.isPasswordChanged() || StringUtils.isBlank(dto.getId()))) {
            entity.setPassword(this.passwordEncoder.encode(dto.getPassword()));
        }
    }
}
