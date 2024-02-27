package org.simon.npo.service;

import lombok.RequiredArgsConstructor;
import org.simon.npo.core.entity.npoDictionary.BaseNpoValue;
import org.simon.npo.core.reposity.NpoDictionaryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NpoDictionaryService {
    private final NpoDictionaryRepository npoDictionaryRepository;
    public BaseNpoValue getByName(String activity) {
        return npoDictionaryRepository.findByName(activity);
    }
}
