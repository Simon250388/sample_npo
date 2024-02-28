package org.simon.npo.cucumber.config

import io.cucumber.java.After
import io.cucumber.java.Before
import org.simon.npo.db.inmemory.InMemoryUserNpoRepository
import org.springframework.beans.BeansException
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.BeanFactoryAware
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionStatus


class SpringTransactionHooks : BeanFactoryAware {
    private var beanFactory: BeanFactory? = null
    private val transactionStatus: TransactionStatus? = null

    @Throws(BeansException::class)
    override fun setBeanFactory(beanFactory: BeanFactory) {
        this.beanFactory = beanFactory
    }

    @Before(value = "@txn", order = 100)
    fun startTransaction() {
        val userNpoRepository = beanFactory!!.getBean(
            InMemoryUserNpoRepository::class.java
        )
        userNpoRepository.clear()
        //        transactionStatus = obtainPlatformTransactionManager()
//                .getTransaction(new DefaultTransactionDefinition());
    }

    fun obtainPlatformTransactionManager(): PlatformTransactionManager {
        return beanFactory!!.getBean(PlatformTransactionManager::class.java)
    }

    @After(value = "@txn", order = 100)
    fun rollBackTransaction() {
//        obtainPlatformTransactionManager()
//                .rollback(transactionStatus);
    }
}
