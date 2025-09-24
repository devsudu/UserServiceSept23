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
}
