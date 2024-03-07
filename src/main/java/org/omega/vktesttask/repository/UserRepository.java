package org.omega.vktesttask.repository;

import jakarta.transaction.Transactional;
import org.omega.vktesttask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByLogin(String login);
    void deleteUserByLogin(String login);
    boolean existsByLogin(String login);

}
