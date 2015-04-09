package org.example.appdirect.repository;

import org.example.appdirect.domain.User;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(DateTime dateTime);

    User findOneByLogin(String login);

    User findOneByEmail(String email);

}
