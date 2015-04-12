package org.example.appdirect.service;

import org.example.appdirect.domain.User;
import org.example.appdirect.repository.UserRepository;
import org.example.appdirect.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getUserWithAuthorities() {

        User currentUser = userRepository.findOneByLogin(SecurityUtils.getCurrentLogin());
        currentUser.getAuthorities().size(); // eagerly load the association
        return currentUser;
    }
}
