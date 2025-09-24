package dev.sudu.userservicesept23.models;

import dev.sudu.userservicesept23.exceptions.MandatoryFieldException;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseModel {
    private String name;
    private String email;
    private String password;
    @ManyToMany
    private List<Role> roles;

    public User() {}

    User(UserBuilder userBuilder) throws MandatoryFieldException {
        if(userBuilder.getName().isBlank() || userBuilder.getEmail().isBlank() || userBuilder.getPassword().isBlank()){
            throw new MandatoryFieldException("Please enter all mandatory fields");
        }
        this.name = userBuilder.getName();
        this.email = userBuilder.getEmail();
        this.password = userBuilder.getPassword();
    }

    public static UserBuilder getBuilder() {
        return new UserBuilder();
    }

    @Getter
    public static class UserBuilder {
        private String name;
        private String email;
        private String password;
        private List<Role> roles;

        public UserBuilder() {}

        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setRoles(List<Role> roles) {
            this.roles = roles;
            return this;
        }

        public User build() throws MandatoryFieldException {
            return new User(this);
        }
    }
}

// user token
//  1     M
//  1     1  ==> 1:M

// user role
//   1   M
//   M   1 ==> M:M