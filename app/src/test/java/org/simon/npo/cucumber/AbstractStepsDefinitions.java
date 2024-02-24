package org.simon.npo.cucumber;

public class AbstractStepsDefinitions {
    private static final ThreadLocal<NpoTestContext> context = ThreadLocal.withInitial(NpoTestContext::new);

    public NpoTestContext getContext() {
        return context.get();
    }
}
