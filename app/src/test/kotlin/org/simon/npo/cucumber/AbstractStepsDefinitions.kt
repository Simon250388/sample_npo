package org.simon.npo.cucumber

open class AbstractStepsDefinitions {
    fun getContext(): NpoTestContext = context.get()

    companion object {
        private val context: ThreadLocal<NpoTestContext> = ThreadLocal.withInitial { NpoTestContext() }
    }
}
