package com.nuskin.ebiz.business;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BrokenBarrierException;

import org.springframework.util.CollectionUtils;

import com.nse.common.CDLObject;
import com.nuskin.ebiz.business.exception.HealthCheckInformationCatchingException;
import com.nuskin.ebiz.model.HealthCheckerData;
import com.nuskin.ebiz.model.SystemHealth;

public class HealthChecker implements Runnable {

	private static final String WS_CALL_RESULT_WITH_UNEXPECTED_INFORMATION = "WS call result with unexpected information";

	private static final String UNEXPECTED_ERROR = "Unexpected error";

	private static final String UNEXPECTED_RESULT_INFORMATION_ERROR = "Unexpected result information error N ";

	private static final String OBJECT_WAS_SET_WITHOUT_INNER_REQUIRED_ATTRIBUTES = "Object was set without inner required attributes";

	private static final String NO_RESULTS = "No results";

	private static final String COLLECTION_INFORMATION_WAS_EXPECTED = "Collection information was expected";

	private static final String CURRENT_RESPONSE = "Current response ";

	private static final String CURRENT_LOWER_LENGTH_LIMIT = "Current lower length limit ";

	private static final String STRING_RESULT_HAS_LESS_INFORMATION_THAT_EXPECTED = "String result has less information that expected";

	protected static CDLObject LOG = new CDLObject();

	private HealthCheckerData healthCheckerData;

	public HealthCheckerData getHealthCheckerData() {
		return healthCheckerData;
	}

	public void setHealthCheckerData(HealthCheckerData healthCheckerData) {
		this.healthCheckerData = healthCheckerData;
	}

	@Override
	public void run() {
		try {
			this.executeHealthCheck();
			this.healthCheckerData.getCyclicBarrier().await();
		} catch (Exception e) {
			LOG.error("Health Check Exception ERROR", e);
			try {
				LOG.debug("kill thread");
				this.healthCheckerData.getCyclicBarrier().await();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} catch (BrokenBarrierException e1) {
				e1.printStackTrace();
			}
		}
	}

	private static <T> T convertInstanceOfObject(Object o, Class<T> clazz) {
		try {
			return clazz.cast(o);
		} catch (ClassCastException e) {
			return null;
		}
	}

	private void executeHealthCheck() {
		SystemHealth actionsCountHealth = new SystemHealth();

		actionsCountHealth.setSystemName(this.healthCheckerData.getSystemName()
				+ this.healthCheckerData.getEndPointName());
		try {
			Object results = prepareMethod();
			if (this.healthCheckerData.getResultClass() == null) {
				if (results == null) {
					setHealthCheckStatus(Boolean.FALSE, actionsCountHealth);
				} else {
					setHealthCheckStatus(Boolean.TRUE, actionsCountHealth);
				}
			} else {
				resultClassHandler(results, actionsCountHealth);
			}
		} catch (InvocationTargetException e) {
			LOG.debug("Health check caught errors", e);
			if (e.getTargetException() instanceof HealthCheckInformationCatchingException) {
				handleInvocationException(
						(HealthCheckInformationCatchingException) e
								.getTargetException(),
						actionsCountHealth);
			} else if (e.getTargetException().getCause() instanceof HealthCheckInformationCatchingException) {
				handleInvocationException(
						(HealthCheckInformationCatchingException) e
								.getTargetException().getCause(),
						actionsCountHealth);
			} else {
				setHealthCheckStatus(Boolean.FALSE, actionsCountHealth);
			}
		} catch (Exception e) {
			LOG.debug("Healthy check internal error", e);
			setHealthCheckStatus(Boolean.FALSE, actionsCountHealth);
		}
		this.healthCheckerData.getSystemHealth().add(actionsCountHealth);

	}

	private void handleInvocationException(
			HealthCheckInformationCatchingException e,
			SystemHealth actionsCountHealth) {
		setHealthCheckStatus(Boolean.FALSE, actionsCountHealth);
		HealthCheckInformationCatchingException hcException = e;
		actionsCountHealth.setSystemCall(hcException.getSystemCall());
		actionsCountHealth.setDetailedComments(hcException.getError());
	}

	private void resultClassHandler(Object results,
			SystemHealth actionsCountHealth) throws Exception {
		if (results != null) {
			if (this.healthCheckerData.getResultClass().isAssignableFrom(
					results.getClass())) {

				if (String.class
						.equals(this.healthCheckerData.getResultClass())) {
					String resultAsString = (String) convertInstanceOfObject(
							results, this.healthCheckerData.getResultClass());

					if (resultAsString.length() < this.healthCheckerData
							.getResultSizeLowerLimit()) {				
						setDetailedCommentsOnUnexpectedResults(actionsCountHealth,STRING_RESULT_HAS_LESS_INFORMATION_THAT_EXPECTED,CURRENT_LOWER_LENGTH_LIMIT+this.healthCheckerData.getResultSizeLowerLimit(),CURRENT_RESPONSE+resultAsString);
						setHealthCheckStatus(Boolean.FALSE, actionsCountHealth);
					} else {
						setHealthCheckStatus(Boolean.TRUE, actionsCountHealth);
					}
				} else if (Collection.class
						.isAssignableFrom(results.getClass())) {
					if (CollectionUtils.isEmpty(((Collection<?>) results))) {
						setDetailedCommentsOnUnexpectedResults(actionsCountHealth,COLLECTION_INFORMATION_WAS_EXPECTED,NO_RESULTS);
						setHealthCheckStatus(Boolean.FALSE, actionsCountHealth);
					} else {
						setHealthCheckStatus(Boolean.TRUE, actionsCountHealth);
					}
				} else {

					boolean error = false;
					for (String method : this.healthCheckerData
							.getResultObjectNoParametersMethods()) {
						Method m = results.getClass().getDeclaredMethod(method);
						m.setAccessible(true);
						Object mr = m.invoke(results);

						if (mr == null) {
							error = true;
							break;
						} else if (mr instanceof String) {
							if (((String) mr).isEmpty()) {
								error = true;
								break;
							}
						} else if (Collection.class.isAssignableFrom(mr
								.getClass())) {
							if (CollectionUtils.isEmpty(((Collection<?>) mr))) {
								error = true;
								break;
							}
						}
					}
					if (!error) {
						setHealthCheckStatus(Boolean.TRUE, actionsCountHealth);
					} else {
						setDetailedCommentsOnUnexpectedResults(actionsCountHealth,OBJECT_WAS_SET_WITHOUT_INNER_REQUIRED_ATTRIBUTES);
						setHealthCheckStatus(Boolean.FALSE, actionsCountHealth);
					}

				}

			}
		} else {
			setHealthCheckStatus(Boolean.FALSE, actionsCountHealth);
		}
	}

	private void setDetailedCommentsOnUnexpectedResults(SystemHealth actionsCountHealth, String ...comments){
		
		if(comments!=null){
			int i = 1;
			Map<String,String> commentsData = new HashMap<>();			
			for(String comment : comments){
				commentsData.put(UNEXPECTED_RESULT_INFORMATION_ERROR+i, comment);
				i++;
			}
			commentsData.put(UNEXPECTED_ERROR, WS_CALL_RESULT_WITH_UNEXPECTED_INFORMATION);
			actionsCountHealth.setDetailedComments(commentsData);
		}
	}
	private void setHealthCheckStatus(boolean status,
			SystemHealth actionsCountHealth) {
		actionsCountHealth.setHealthy(status);
		actionsCountHealth.setComment(MessageFormat.format(
				this.healthCheckerData.getEndPointName() + " is "
						+ (status ? "" : "not ") + "healthy {0}", new Date()));
	}

	private Object prepareMethod() throws Exception {

		Class<?> parameterClassObject[] = new Class<?>[this.healthCheckerData
				.getParameters().size()];
		Object parameterObject[] = new Object[this.healthCheckerData
				.getParameters().size()];
		for (int i = 0; i < parameterClassObject.length; i++) {
			for (Entry<Class<?>, Object> entry : this.healthCheckerData
					.getParameters().get(i).entrySet()) {
				parameterClassObject[i] = entry.getKey();
				parameterObject[i] = entry.getValue();
			}

		}

		Method methodToInvoke = this.healthCheckerData
				.getService()
				.getClass()
				.getDeclaredMethod(this.healthCheckerData.getMethodName(),
						parameterClassObject);
		methodToInvoke.setAccessible(true);
		Object results = methodToInvoke.invoke(
				this.healthCheckerData.getService(), parameterObject);
		return results;
	}

}
