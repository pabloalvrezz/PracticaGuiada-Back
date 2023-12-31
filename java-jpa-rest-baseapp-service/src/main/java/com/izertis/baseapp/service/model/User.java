package com.izertis.baseapp.service.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.izertis.baseapp.service.util.JpaConstants;
import com.izertis.baseapp.service.util.ValidationConstants;
import com.izertis.libraries.audit.jpa.model.Auditable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.bytebuddy.dynamic.TypeResolutionStrategy.Lazy;

/**
 * Application user.
 */
@Entity
@Table(name = User.TABLE, indexes = { @Index(columnList = User.Columns.NAME) }, uniqueConstraints = {
        @UniqueConstraint(columnNames = User.Columns.EMAIL), @UniqueConstraint(columnNames = User.Columns.USERNAME) })
@Getter
@Setter
@ToString(includeFieldNames = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class User extends Auditable implements UserDetails {

    /**
     * Version ID.
     */
    private static final long serialVersionUID = -8605786237765754616L;

    /**
     * The id.
     */
    @Id
    @GeneratedValue(generator = JpaConstants.HIBERNATE_UUID_GENERATOR_NAME)
    @GenericGenerator(name = JpaConstants.HIBERNATE_UUID_GENERATOR_NAME, strategy = JpaConstants.HIBERNATE_UUID_GENERATOR_STRATEGY)
    @Column(name = Columns.ID)
    @EqualsAndHashCode.Include
    private String id;

    /**
     * User real name.
     */
    @Size(min = 1, max = ValidationConstants.MAX_LENGTH_DEFAULT)
    @Column(name = Columns.NAME, nullable = true, length = ValidationConstants.MAX_LENGTH_DEFAULT)
    private String name;

    /**
     * Email.
     */
    @Column(name = Columns.EMAIL, nullable = false)
    private String email;

    /**
     * Flag that indicates whether user is enabled or not.
     */
    @Column(name = Columns.ENABLED, nullable = true, columnDefinition = "Boolean default true")
    private boolean enabled = true;

    /**
     * Flag that indicates whether credentials are expired or not.
     */
    @Column(name = Columns.CREDENTIALS_NON_EXPIRED, nullable = true, columnDefinition = "Boolean default true")
    private boolean credentialsNonExpired = true;

    /**
     * Flag that indicates whether account are expired or not.
     */
    @Column(name = Columns.ACCOUNT_NON_EXPIRED, nullable = true, columnDefinition = "Boolean default true")
    private boolean accountNonExpired = true;

    /**
     * Flag that indicates whether account is expired or not.
     */
    @Column(name = Columns.ACCOUNT_NON_LOCKED, nullable = true, columnDefinition = "Boolean default true")
    private boolean accountNonLocked = true;

    /**
     * User password.
     */
    @Column(name = Columns.PASSWORD, nullable = false)
    private String password;

    /**
     * Password recovery hash.
     */
    @Column(name = Columns.PASSWORD_RECOVERY_HASH, nullable = true)
    private String passwordRecoveryHash;

    /**
     * User name
     */
    @Column(name = Columns.USERNAME, nullable = false)
    private String username;

    /**
     * Country
     */
    @Column(name = Columns.COUNTRY, nullable = true)
    private String country;

    /**
     * City
     */
    @Column(name = Columns.CITY, nullable = true)
    private String city;

    /**
     * Language
     */
    @Column(name = Columns.LANGUAGE, nullable = true)
    private String language;

    /**
     * Address
     */
    @Column(name = Columns.ADDRESS, nullable = true)
    private String address;

    /**
     * Favourite products
     */
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "User_Favourites", joinColumns = @JoinColumn(name = "UserId"), inverseJoinColumns = @JoinColumn(name = "ProductId"))
    private Set<Product> favourites = new HashSet<Product>();

    /**
     * Role list.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = UserRole.TABLE, joinColumns = { @JoinColumn(name = UserRole.Columns.USER_ID) })
    @Column(name = Columns.ROLE, nullable = true)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    /**
     * Version
     */
    @Version
    private Integer version;

    /**
     * Metodo usado para añadir un favorito a la lista
     * de favoritos de cada usuario
     * 
     * @param product
     *            que se quiere añadir a la lista
     */
    public void addFavourite(Product product) {
        favourites.add(product);
        product.getUsers().add(this);
    }

    /**
     * Metodo usado para eliminar un favorito de la lista
     * de favoritos de cada usuario
     * 
     * @param product
     *            que se quiere eliminar a la lista
     */
    public void removeFavourite(Product product) {
        favourites.remove(product);
        product.getUsers().remove(this);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
     */
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.transform(
                Collections.unmodifiableList(Lists.newArrayList(this.roles == null ? new HashSet<Role>() : this.roles)),
                USER_AUTHORITY_TRANSFORMATION);
    }

    /**
     * Function to transform database user authorities into spring security format.
     */
    private static final Function<Role, GrantedAuthority> USER_AUTHORITY_TRANSFORMATION = Role::getGrantedAuthority;

    private <K, V, Q extends K> List<V> transform(final List<Q> input, final java.util.function.Function<K, V> tfunc) {
        if (null == input) {
            return Collections.emptyList();
        }
        return input.stream().map(tfunc).collect(Collectors.toList());
    }

    /**
     * Table name.
     */
    public static final String TABLE = "APPLICATION_USER";

    /**
     * Column name constants.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    static final class Columns {

        /**
         * User name column.
         */
        protected static final String USERNAME = "USERNAME";

        /**
         * Country column.
         */
        protected static final String COUNTRY = "COUNTRY";

        /**
         * City column.
         */
        protected static final String CITY = "CITY";

        /**
         * Language column.
         */
        protected static final String LANGUAGE = "LANGUAGE";

        /**
         * Address column.
         */
        protected static final String ADDRESS = "ADDRESS";

        /**
         * User type column.
         */
        protected static final String USER_TYPE = "USER_TYPE";

        /**
         * ID column.
         */
        protected static final String ID = "ID";

        /**
         * Name column.
         */
        protected static final String NAME = "NAME";

        /**
         * eMail column.
         */
        protected static final String EMAIL = "EMAIL";

        /**
         * Password column.
         */
        @SuppressWarnings("squid:S2068")
        protected static final String PASSWORD = "PASSWORD";

        /**
         * Enabled column.
         */
        protected static final String ENABLED = "ENABLED";

        /**
         * Credentials non expired column.
         */
        protected static final String CREDENTIALS_NON_EXPIRED = "CREDENTIALS_NON_EXPIRED";

        /**
         * Account non expired column.
         */
        protected static final String ACCOUNT_NON_EXPIRED = "ACCOUNT_NON_EXPIRED";

        /**
         * Account non locked column.
         */
        protected static final String ACCOUNT_NON_LOCKED = "ACCOUNT_NON_LOCKED";

        /**
         * Password recovery hash column.
         */
        @SuppressWarnings("squid:S2068")
        protected static final String PASSWORD_RECOVERY_HASH = "PASSWORD_RECOVERY_HASH";

        /**
         * Roles column.
         */
        protected static final String ROLE = "ROLE";
    }
}
