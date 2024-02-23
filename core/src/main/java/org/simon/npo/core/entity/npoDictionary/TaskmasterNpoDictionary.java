package org.simon.npo.core.entity.npoDictionary;

import java.time.Duration;
import org.jetbrains.annotations.Nullable;

public class TaskmasterNpoDictionary extends BaseNpoDictionary {
    public TaskmasterNpoDictionary(String name, boolean disable) {
        super(name, disable, false, false, null);
    }

    public TaskmasterNpoDictionary(String name, boolean disable, boolean hasChildren) {
        super(name, disable, false, false, null);
    }

    @Override
    public @Nullable Duration duration() {
        return null;
    }
}
