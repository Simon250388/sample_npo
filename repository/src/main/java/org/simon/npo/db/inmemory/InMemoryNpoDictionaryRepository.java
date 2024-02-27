package org.simon.npo.db.inmemory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.simon.npo.core.entity.npoDictionary.BaseNpoValue;
import org.simon.npo.core.reposity.NpoDictionaryRepository;

public class InMemoryNpoDictionaryRepository implements NpoDictionaryRepository {
  private final Map<String, BaseNpoValue> storage;

  public InMemoryNpoDictionaryRepository() {
    this.storage = new HashMap<>();
    this.storage.put("LUNCH", new BaseNpoValue("LUNCH", Duration.ofMinutes(30)));
    this.storage.put("PROCHIE_RABOTY_DRUGOE", new BaseNpoValue("Prochie_raboty_drugoe"));
    this.storage.put("PROCHIE_RABOTY_OTGRUZKA", new BaseNpoValue("Prochie_raboty_otgruzka"));
  }

  @Override
  public BaseNpoValue findByName(String activity) {
    return storage.get(activity.toUpperCase());
  }
}
