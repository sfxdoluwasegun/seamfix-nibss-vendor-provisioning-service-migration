package com.seamfix.bioweb.microservices.vendor.provision.utils;

import java.util.concurrent.Future;
import java.util.function.Supplier;

import javax.ejb.ApplicationException;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * @author DAWUZI
 * 
 * <br/><br/>
 * So it was noticed in some scenarios that the container does not open a new transaction when a method annotated 
 * with the {@link TransactionAttribute} {@link TransactionAttributeType} REQUIRES_NEW is invoked locally within 
 * the same EJB. This is because the container does not intercept the method call and thus does not prepare the 
 * environment accurately before the method is invoked. This class is to be used to ensure that method calls that 
 * require the environment to be properly set can do so via any of the appropriate methods below 
 * 
 * <br/><br/>
 * <strong>NB</strong>: note that if an exception is thrown while executing the method of a functional argument, 
 * the container wraps the exception with an {@link EJBException} unless it is marked as 
 * application exception in the ejb-jar.xml or via the {@link ApplicationException} annotation
 * 
 */

@Stateless
public class ProxyUtil {

	/**
	 * 
	 * @param supplier
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public <T> T executeWithNewTransaction(Supplier<T> supplier){
		return supplier.get();
	}

	/**
	 *
	 * @param runnableTask
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void executeWithNewTransaction(RunnableTask runnableTask){
		runnableTask.run();
	}

	/**
	 * 
	 * @param <T>
	 * @param supplier
	 * @return
	 */
	@Asynchronous
	public <T> Future<T> executeAsync(Supplier<T> supplier){
		return new AsyncResult<T>(supplier.get());
	}

	/**
	 *
	 * @param runnableTask
	 */
	@Asynchronous
	public void executeAsync(RunnableTask runnableTask){
		runnableTask.run();
	}

	/**
	 * This functional interface was declared and used in the methods above in order to create a {@link FunctionalInterface} equivalent
	 * of {@link Runnable} to avoid any confusion since {@link Runnable} often connotes a {@link Thread} context which is not really the
	 * case for the scenarios above
	 */
	@FunctionalInterface
	public interface RunnableTask{
		void run();
	}

	
}
