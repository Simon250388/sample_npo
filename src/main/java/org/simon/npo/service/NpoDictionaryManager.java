package org.simon.npo.service;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;
import org.simon.npo.entity.npoDictionary.NpoDictionary;
import org.simon.npo.entity.npoDictionary.ShowTsdNpoDictionary;
import org.simon.npo.entity.npoDictionary.TaskmasterNpoDictionary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NpoDictionaryManager {

  private final CrudRepository<NpoDictionary, String> npoDictionaryRepository;

  public Iterable<NpoDictionary> getForTaskMaster() {
    return StreamSupport.stream(npoDictionaryRepository.findAll().spliterator(), false)
        .filter(item -> item instanceof TaskmasterNpoDictionary)
        .toList();
  }

  public Iterable<NpoDictionary> getForTsd() {
    return StreamSupport.stream(npoDictionaryRepository.findAll().spliterator(), false)
        .filter(item -> item instanceof ShowTsdNpoDictionary)
        .toList();
  }

  public NpoDictionary getByName(String name) {
    return npoDictionaryRepository.findById(name).orElseThrow();
  }
}
