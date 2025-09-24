package dev.sudu.userservicesept23.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Token extends BaseModel {
    private String token;
    private Long expiryAt;
    private Boolean isActive;
    @ManyToOne
    private User user;

    public Token(TokenBuilder tokenBuilder) {
        this.token = tokenBuilder.getToken();
        this.expiryAt = tokenBuilder.getExpiryAt();
        this.isActive = tokenBuilder.getIsActive();
        this.user = tokenBuilder.getUser();
    }

    public Token() {}

    public static TokenBuilder getBuilder(){
        return new TokenBuilder();
    }

    public static class TokenBuilder{
        private String token;
        private Long expiryAt;
        private Boolean isActive;
        private User user;

        public String getToken() {
            return token;
        }

        public TokenBuilder setToken(String token) {
            this.token = token;
            return this;
        }

        public Long getExpiryAt() {
            return expiryAt;
        }

        public TokenBuilder setExpiryAt(Long expiryAt) {
            this.expiryAt = expiryAt;
            return this;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public TokenBuilder setIsActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public User getUser() {
            return user;
        }

        public TokenBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public Token build(){
            return new Token(this);
        }
    }
}
