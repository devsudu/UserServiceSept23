package dev.sudu.userservicesept23.models;

import dev.sudu.userservicesept23.exceptions.InvalidTokenException;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Role extends BaseModel {
    private String name;

    public Role(){}

    public Role(String name) throws InvalidTokenException {
        if(name.isBlank()){
            throw new InvalidTokenException("Please enter role");
        }
        this.name = name;
    }

    public static RoleBuilder getBuilder(){
        return new RoleBuilder();
    }

    public static class RoleBuilder{
        private String name;

        public RoleBuilder () {};

        public String getName() {
            return name;
        }

        public RoleBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public Role build() throws InvalidTokenException {
            return new Role(this.name);
        }
    }
}
