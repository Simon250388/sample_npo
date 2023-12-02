package org.simon.npo.helper;

import java.time.Instant;

public interface Interval {
    Instant intervalStart();
    Instant intervalEnd();
}
