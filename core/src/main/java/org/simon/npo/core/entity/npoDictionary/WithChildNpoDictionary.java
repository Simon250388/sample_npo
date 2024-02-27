package org.simon.npo.core.entity.npoDictionary;

public class WithChildNpoDictionary extends TaskmasterNpoDictionary implements NpoDictionary {
  WithChildNpoDictionary(String name, boolean disable) {
    super(name, disable);
    this.value.setHasChildren(true);
  }

  WithChildNpoDictionary(BaseNpoValue value) {
    super(value);
  }
}
