package dev.sudu.userservicesept23.repositories;

import dev.sudu.userservicesept23.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
