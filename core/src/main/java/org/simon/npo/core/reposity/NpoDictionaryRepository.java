package org.simon.npo.core.reposity;

import org.simon.npo.core.entity.npoDictionary.BaseNpoValue;

public interface NpoDictionaryRepository  {

    BaseNpoValue findByName(String activity);
}
