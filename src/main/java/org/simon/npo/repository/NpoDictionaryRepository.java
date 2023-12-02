package org.simon.npo.repository;

import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.simon.npo.entity.npoDictionary.NpoDictionary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NpoDictionaryRepository implements CrudRepository<NpoDictionary, String> {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public <S extends NpoDictionary> S save(@NotNull S entity) {
        return entity;
    }

    @Override
    public <S extends NpoDictionary> @NotNull Iterable<S> saveAll(@NotNull Iterable<S> entities) {
        return entities;
    }

    @Override
    public @NotNull Optional<NpoDictionary> findById(@NotNull String name) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(@NotNull String name) {
        return false;
    }

    @Override
    public @NotNull Iterable<NpoDictionary> findAll() {
        return Collections.emptyList();
    }

    @Override
    public @NotNull Iterable<NpoDictionary> findAllById(@NotNull Iterable<String> names) {
        return Collections.emptyList();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(@NotNull String name) {

    }

    @Override
    public void delete(@NotNull NpoDictionary entity) {

    }

    @Override
    public void deleteAllById(@NotNull Iterable<? extends String> names) {

    }

    @Override
    public void deleteAll(@NotNull Iterable<? extends NpoDictionary> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
