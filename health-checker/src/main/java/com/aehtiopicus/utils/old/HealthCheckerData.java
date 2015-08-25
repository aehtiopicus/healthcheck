//package com.nuskin.ebiz.model;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map; 
//import java.util.concurrent.CyclicBarrier;
//
//public class HealthCheckerData {
//
//	// property used by the thread.-
//	private CyclicBarrier cyclicBarrier;
//
//	private String systemName;
//
//	private String endPointName;
//
//	// parameter that will be used to invoke the method.
//	private String methodName;
//
//	private Object service;
//
//	private List<SystemHealth> systemHealth;
//
//	private Map<Integer, Map<Class<?>, Object>> parameterData = new HashMap<Integer, Map<Class<?>, Object>>();
//
//	private int resultSizeLowerLimit = -1;
//
//	private Class<?> resultClass;
//
//	private List<String> resultObjectNoParametersMethods;
//
//	public CyclicBarrier getCyclicBarrier() {
//		return cyclicBarrier;
//	}
//
//	public void setCyclicBarrier(CyclicBarrier cyclicBarrier) {
//		this.cyclicBarrier = cyclicBarrier;
//	}
//
//	public String getSystemName() {
//		return systemName;
//	}
//
//	public void setSystemName(String systemName) {
//		this.systemName = systemName;
//	}
//
//	public String getEndPointName() {
//		return endPointName;
//	}
//
//	public void setEndPointName(String endPointName) {
//		this.endPointName = endPointName;
//	}
//
//	public String getMethodName() {
//		return methodName;
//	}
//
//	public void setMethodName(String methodName) {
//		this.methodName = methodName;
//	}
//
//	public Object getService() {
//		return service;
//	}
//
//	public void setService(Object service) {
//		this.service = service;
//	}
//
//	public List<SystemHealth> getSystemHealth() {
//		return systemHealth;
//	}
//
//	public void setSystemHealth(List<SystemHealth> systemHealth) {
//		this.systemHealth = systemHealth;
//	}
//
//	public void setParameters(Object value, Class<?> c) {
//		int nextElement = parameterData.size();
//		Map<Class<?>, Object> element = new HashMap<Class<?>, Object>();
//		element.put(c, value);
//		parameterData.put(nextElement, element);
//
//	}
//
//	public Map<Integer, Map<Class<?>, Object>> getParameters() {
//		return parameterData;
//	}
//
//	public int getResultSizeLowerLimit() {
//		return resultSizeLowerLimit;
//	}
//
//	public void setResultSizeLowerLimit(int resultSizeLowerLimit) {
//		this.resultSizeLowerLimit = resultSizeLowerLimit;
//	}
//
//	public Class<?> getResultClass() {
//		return resultClass;
//	}
//
//	public void setResultClass(Class<?> resultClass) {
//		this.resultClass = resultClass;
//	}
//
//	public List<String> getResultObjectNoParametersMethods() {
//		return resultObjectNoParametersMethods;
//	}
//
//	public void setResultObjectNoParametersMethods(
//			String... resultObjectVoidMethodsName) {
//		this.resultObjectNoParametersMethods = Arrays
//				.asList(resultObjectVoidMethodsName);
//	}
//}
