package org.example.realworldapi.infrastructure.repository.hibernate.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class UsersFollowedEntityKey implements Serializable {

  @ManyToOne private UserEntity user;

  @ManyToOne private UserEntity followed;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    UsersFollowedEntityKey that = (UsersFollowedEntityKey) o;
    return Objects.equals(user, that.user) && Objects.equals(followed, that.followed);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, followed);
  }
}
