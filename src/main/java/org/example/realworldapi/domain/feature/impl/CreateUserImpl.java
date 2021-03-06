package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.CreateUser;
import org.example.realworldapi.domain.model.exception.EmailAlreadyExistsException;
import org.example.realworldapi.domain.model.exception.UsernameAlreadyExistsException;
import org.example.realworldapi.domain.model.provider.HashProvider;
import org.example.realworldapi.domain.model.repository.NewUserRepository;
import org.example.realworldapi.domain.model.user.CreateUserInput;
import org.example.realworldapi.domain.model.user.User;
import org.example.realworldapi.domain.model.user.UserModelBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@AllArgsConstructor
public class CreateUserImpl implements CreateUser {

  private final NewUserRepository userRepository;
  private final HashProvider hashProvider;
  private final UserModelBuilder userBuilder;

  @Override
  @Transactional
  public User handle(CreateUserInput createUserInput) {
    final var user =
        userBuilder.build(
            createUserInput.getUsername(),
            createUserInput.getEmail(),
            createUserInput.getPassword());
    checkExistingUsername(user.getUsername());
    checkExistingEmail(user.getEmail());
    user.setPassword(hashProvider.hashPassword(user.getPassword()));
    userRepository.save(user);
    return user;
  }

  private void checkExistingUsername(String username) {
    if (userRepository.existsBy("username", username)) {
      throw new UsernameAlreadyExistsException();
    }
  }

  private void checkExistingEmail(String email) {
    if (userRepository.existsBy("email", email)) {
      throw new EmailAlreadyExistsException();
    }
  }
}
