package com.izertis.baseapp.service.test.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.izertis.baseapp.service.model.User;
import com.izertis.baseapp.service.repository.UserRepository;
import com.izertis.baseapp.service.service.UserService;

/**
 * Test for {@link UserService}
 */
@DataJpaTest
class UserRepositoryTest {
    /**
     * Entity Manager
     */
    @Autowired
    private TestEntityManager entityManager;
    
    /**
     * User repository
     */
    @Autowired
    private UserRepository userRepository;
    
    @Test
    void whenFindByUsername_thenReturnUser() {
        // given
        User user = new User();
        user.setUsername("john");
        entityManager.persist(user);
        entityManager.flush();
        
        // when
        Optional<User> found = userRepository.findByUsername(user.getUsername());
        
        //then
        assertThat(found.get().getUsername()).isEqualTo(user.getUsername());
    }
    
    @Test
    void whenSetAccountNonLocked_thenUserMustBeLocked() {
        // given
        User user = new User();
        user.setUsername("john");
        user.setAccountNonLocked(false);
        entityManager.persist(user);
        entityManager.flush();
        entityManager.detach(user);
        
        // when
        userRepository.setAccountNonLocked(true, user.getId());
        
        //then
        User found = userRepository.getById(user.getId());
        assertThat(found.isAccountNonLocked()).isTrue();
        entityManager.detach(found);
        
        // when
        userRepository.setAccountNonLocked(false, user.getId());
        
        //then
        found = userRepository.getById(user.getId());
        assertThat(found.isAccountNonLocked()).isFalse();
        entityManager.detach(found);
    }
    
}
