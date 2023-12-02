package org.simon.npo.repository;

import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.simon.npo.entity.userNpo.UserNpo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserNpoRepository implements CrudRepository<UserNpo, Long> {
  private final NamedParameterJdbcTemplate jdbcTemplate;

  @Override
  public <S extends UserNpo> @NotNull S save(@NotNull S entity) {
    return entity;
  }

  @Override
  public <S extends UserNpo> @NotNull Iterable<S> saveAll(@NotNull Iterable<S> entities) {
    return entities;
  }

  @Override
  public @NotNull Optional<UserNpo> findById(@NotNull Long id) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(@NotNull Long id) {
    return false;
  }

  @Override
  public @NotNull Iterable<UserNpo> findAll() {
    return Collections.emptyList();
  }

  @Override
  public @NotNull Iterable<UserNpo> findAllById(@NotNull Iterable<Long> Ids) {
    return Collections.emptyList();
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void deleteById(@NotNull Long id) {}

  @Override
  public void delete(@NotNull UserNpo entity) {}

  @Override
  public void deleteAllById(@NotNull Iterable<? extends Long> ids) {}

  @Override
  public void deleteAll(@NotNull Iterable<? extends UserNpo> entities) {}

  @Override
  public void deleteAll() {
    throw new UnsupportedOperationException("Delete all fo user npo not supported");
  }
}
