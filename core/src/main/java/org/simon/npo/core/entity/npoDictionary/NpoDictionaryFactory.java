package org.simon.npo.core.entity.npoDictionary;

public class NpoDictionaryFactory {
  public static NpoDictionary create(BaseNpoValue value) {
    if (value.isShowTsd()) {
      return new ShowTsdNpoDictionary(value);
    } else if (value.isHasChildren()) {
      return new WithChildNpoDictionary(value);
    }
    return new TaskmasterNpoDictionary(value);
  }
}
