package com.izertis.baseapp.back.test.proxy;

import com.izertis.abstractions.search.PageImplHelper;
import com.izertis.baseapp.service.dto.UserDto;
import com.izertis.baseapp.service.filter.UserFilter;
import com.izertis.baseapp.service.mapper.MapperConfig;
import com.izertis.baseapp.service.model.User;
import com.izertis.baseapp.service.proxy.UserProxy;
import com.izertis.baseapp.service.proxy.impl.UserProxyImpl;
import com.izertis.baseapp.service.service.UserService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class UserProxyTest {
    /**
     * User proxy
     */
    @Autowired
    private UserProxy proxy;

    /**
     * User service mock bean.
     */
    @MockBean
    private UserService service;

    @TestConfiguration
    @Import(MapperConfig.class)
//    @Import(TestApplication.class)
    static class UserProxyTestConfiguration {
        @Bean
        public UserProxy userProxy() {
            return new UserProxyImpl();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }
    }

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void setUp() {
        // Sample data
        final User user1 = new User();
        user1.setId("1");
        user1.setUsername("user1");

        final User user2 = new User();
        user2.setId("2");
        user2.setUsername("user2");
        
        // Mock save
        Mockito.when(this.service.save(any(User.class))).thenAnswer(invocation -> {
            final User user = invocation.getArgument(0);
            user.setId("1");
            return user;
        });
        
        // Mock saveAll
        Mockito.when(this.service.save(any(List.class))).thenAnswer(invocation -> {
            final Iterable<User> users = invocation.getArgument(0);
            
            for(User user : users) {
                user.setId(user.getName());
            }
            
            return users;
        });

        // Mock find
        Mockito.when(this.service.find(any())).thenAnswer(invocation -> {
            final String id = invocation.getArgument(0);
            if ("1".equals(id)) {
                return Optional.of(user1);
            } else if ("2".equals(id)) {
                return Optional.of(user2);
            } else {
                return Optional.empty();
            }
        });

        // Mock findPaginated
        Mockito.when(this.service.findPaginated(any(UserFilter.class), any(Pageable.class))).thenAnswer(invocation -> {
            final List<User> elements = Lists.newArrayList(user1, user2);
            final Pageable page = invocation.getArgument(1);
            return new PageImplHelper<User>(elements, page, elements.size());
        });
        
        // Mock findAll
        Mockito.when(this.service.findAll()).thenAnswer(invocation -> {
            return Lists.newArrayList(user1, user2);
        });
    }

    @Test
    public void whenSaveNewUser_thenUserHasId() {
        final UserDto user = new UserDto();
        user.setUsername("test");
        final UserDto newUser = this.proxy.save(user);

        assertThat(newUser.getId()).isEqualTo("1");
        assertThat(newUser.getUsername()).isEqualTo(user.getUsername());
    }
    
    @Test
    public void whenSaveListOfUsers_thenReturnList() {
        final UserDto user = new UserDto();
        user.setUsername("test");
        final UserDto user2 = new UserDto();
        user.setUsername("test2");
        final List<UserDto> users = this.proxy.save(Lists.newArrayList(user, user2));
        
        assertThat(users).isNotEmpty();
    }
    
    @Test
    public void whenUpdateuser_thenUserHasId() {
        final UserDto user = new UserDto();
        user.setUsername("test");
        final UserDto newUser = this.proxy.save(user);

        assertThat(newUser.getId()).isEqualTo("1");
        assertThat(newUser.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void whenFindById_thenReturnUser() {
        final Optional<UserDto> user1 = this.proxy.find("1");
        assertThat(user1).isNotEmpty();
        assertThat(user1.get().getId()).isEqualTo("1");

        final Optional<UserDto> user2 = this.proxy.find("2");
        assertThat(user2).isNotEmpty();
        assertThat(user2.get().getId()).isEqualTo("2");

        final Optional<UserDto> user3 = this.proxy.find("3");
        assertThat(user3).isEmpty();
    }

    @Test
    public void whenFindPaginated_thenReturnPage() {
        final UserFilter filter = new UserFilter();
        final Page<UserDto> page = this.proxy.findPaginated(filter, PageRequest.of(0, 10));
        assertThat(page.getNumberOfElements()).isNotEqualTo(0);
    }
    
    @Test
    public void whenFindAll_thenReturnList() {
        assertThat(proxy.findAll()).isNotEmpty();
    }
}
