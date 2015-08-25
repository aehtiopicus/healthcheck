//package com.nuskin.ebiz.business;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.concurrent.CyclicBarrier;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import com.nse.common.CDLObject;
//import com.nuskin.ebiz.business.view.soundConcepts.OPSEmailData;
//import com.nuskin.ebiz.business.view.soundConcepts.OPSSocialNetworks;
//import com.nuskin.ebiz.model.HealthCheck;
//import com.nuskin.ebiz.model.HealthCheckerData;
//import com.nuskin.ebiz.model.SystemHealth;
//
///**
// * Business Class for checking this service systems status
// */
//@Component
//@Scope("prototype")
//public class HealthCheckBiz {
//
//	private static final String USER_EMAIL_TEMPLATES_LIST = "UserEmailTemplatesList";
//	private static final String GET_USER_EMAIL_TEMPLATES = "getUserEmailTemplates";
//	private static final String GET_URL_INNER_METHOD = "getUrl";
//	private static final String USER_ENABLED_SOCIAL_NETWORKS = "UserEnabledSocialNetworks";
//	private static final String GET_USER_ENABLED_SOCIAL_NETWORKS = "getUserEnabledSocialNetworks";
//	private static final String GET_ASSETS_INNER_METHOD = "getAssets";
//	private static final String GET_OPS_CONTACT_CONTAINER_INNER_METHOD = "getOpsContactContainer";
//	private static final String VIDEO_PARAMETER = "Video";
//	private static final String DEFAULT_COUNTRY_EN = "en";
//	private static final String USER_EMAIL_GENERAL_DATA = "UserEmailGeneralData";
//	private static final String GET_USER_EMAIL_GENERAL_DATA = "getUserEmailGeneralData";
//	private static final String USER_MANAGE_SITE_REDIRECT_URL = "UserManageSiteRedirectUrl";
//	private static final String GET_COMPLETE_MANAGE_SITE_REDIRECT_URL = "getCompleteManageSiteRedirectUrl";
//	private static final String USER_ID = "UserId";
//	private static final String GET_USER_OPS_USER_ID = "getUserOPSUserId";
//	private static final String USER_NAME = "UserName";
//	private static final String GET_USER_OPS_USER_NAME = "getUserOPSUserName";
//	private static final String USER_CONTACTS_LIST = "UserContactsList";
//	private static final String GET_USER_CONTACTS = "getUserContacts";
//	private static final String USER_CATEGORIES_LIST = "UserCategoriesList";
//	private static final String GET_USER_CATEGORIES = "getUserCategories";
//	private static final String USER_ASSETS_LIST = "UserAssetsList";
//	private static final String GET_USER_ASSETS = "getUserAssets";
//	private static final String USER_DRIPS_LIST = "UserDripsList";
//	private static final String GET_USER_DRIPS = "getUserDrips";
//	private static final String ACTIONS_COUNT = "ActionsCount";
//	private static final String GET_USER_ACTIONS_COUNT = "getUserActionsCount";
//	private static final String USER_ENABLED_SOCIAL_NETWORKS_WITH_OPS_AUTH = "UserEnabledSocialNetworksWithOPSAuth";
//	private static final String GET_USER_ENABLED_SOCIAL_NETWORKS_WITH_OPS_AUTH = "getUserEnabledSocialNetworksWithOPSAuth";
//	private static final CDLObject LOG = new CDLObject();
//	private static final String SOUND_CONCEPTS_API = "SoundConceptsAPI-";
//
//	@Autowired
//	private SoundConceptsBiz soundConceptsBiz;
//
//	// For manage threads
//	private CyclicBarrier cyclicBarrier;
//
//	/**
//	 * This method verifies health of systems / services consumed by this
//	 * service
//	 * 
//	 * @param sapId
//	 * @return {@link HealthCheck} object with information about system health.
//	 * @see com.nuskin.ebiz.model.HealthCheck
//	 */
//	public HealthCheck getHealthCheck(String sapId) {
//		Boolean healthy = Boolean.TRUE;
//
//		List<SystemHealth> systems = Collections
//				.synchronizedList(new ArrayList<SystemHealth>());
//
//		if (sapId != null) {
//			HealthCheckBiz.LOG.info("Deep health check process started");
//
//			cyclicBarrier = new CyclicBarrier(13);
//
//			List<HealthChecker> hcList = new ArrayList<HealthChecker>();
//			hcList.add(checkActionsCount(sapId, systems));
//			hcList.add(checkGetUserDrips(sapId, systems));
//			hcList.add(checkGetUserAssets(sapId, systems));
//			hcList.add(checkGetUserCategories(sapId, systems));
//			hcList.add(checkGetUserContacts(sapId, systems));
//			hcList.add(checkGetUserName(sapId, systems));
//			hcList.add(checkGetUserId(sapId, systems));
//			hcList.add(checkGetCompleteManageSiteRedirectUrl(sapId, systems));
//			hcList.add(checkGetUserEmailGeneralData(sapId, systems));
//			hcList.add(checkGetUserEnabledSocialNetworks(sapId, systems));
//			hcList.add(checkGetUserEmailTemplates(sapId, systems));
//			hcList.add(checkGetUserEnabledSocialNetworksWithOPSAuth(sapId,
//					systems));
//
//			// Let's fire up our threads
//			for (HealthChecker hc : hcList) {
//				new Thread(hc).start();
//			}
//
//			try {
//				// waiting for all threads to complete
//				// TODO: should we implement timeouts on our worker threads?
//				cyclicBarrier.await();
//			} catch (Exception e) {
//				LOG.error("Something went horribly worng", e);
//			}
//
//			for (SystemHealth system : systems) {
//				if (!system.isHealthy()) {
//					healthy = Boolean.FALSE;
//					break;
//				}
//			}
//			HealthCheckBiz.LOG.info("Deeper health check process completed");
//		}
//		return new HealthCheck(healthy, systems);
//	}
//
//	/**
//	 * Collects necessary data to build up the HealthChecker object
//	 * @param method name of the method that is going to be invoke
//	 * @param endPointName soundConcept endpoint
//	 * @param systems 
//	 * @return
//	 */
//	private HealthCheckerData assembleHealthCheckGeneralData(String method,
//			String endPointName, List<SystemHealth> systems) {
//
//		HealthCheckerData hc = new HealthCheckerData();
//		hc.setCyclicBarrier(cyclicBarrier);
//		hc.setMethodName(method);
//		hc.setService(soundConceptsBiz);
//		hc.setSystemName(HealthCheckBiz.SOUND_CONCEPTS_API);
//		hc.setEndPointName(endPointName);
//		hc.setSystemHealth(systems);
//
//		return hc;
//	}
//
//	private HealthChecker assembleHealthCheck(HealthCheckerData hcd) {
//		HealthChecker hc = new HealthChecker();
//		hc.setHealthCheckerData(hcd);
//		return hc;
//	}
//
//	private HealthChecker checkGetUserEnabledSocialNetworksWithOPSAuth(
//			String sapId, List<SystemHealth> systems) {
//
//		HealthCheckerData hcd = assembleHealthCheckGeneralData(
//				GET_USER_ENABLED_SOCIAL_NETWORKS_WITH_OPS_AUTH,
//				USER_ENABLED_SOCIAL_NETWORKS_WITH_OPS_AUTH, systems);
//		hcd.setParameters(sapId, String.class);
//		return assembleHealthCheck(hcd);
//	}
//
//	private HealthChecker checkActionsCount(String sapId,
//			List<SystemHealth> systems) {
//		HealthCheckerData hcd = assembleHealthCheckGeneralData(
//				GET_USER_ACTIONS_COUNT, ACTIONS_COUNT, systems);
//		hcd.setParameters(sapId, String.class);
//		return assembleHealthCheck(hcd);
//	}
//
//	private HealthChecker checkGetUserDrips(String sapId,
//			List<SystemHealth> systems) {
//		HealthCheckerData hcd = assembleHealthCheckGeneralData(GET_USER_DRIPS,
//				USER_DRIPS_LIST, systems);
//		hcd.setParameters(sapId, String.class);
//		return assembleHealthCheck(hcd);
//	}
//
//	private HealthChecker checkGetUserAssets(String sapId,
//			List<SystemHealth> systems) {
//		HealthCheckerData hcd = assembleHealthCheckGeneralData(GET_USER_ASSETS,
//				USER_ASSETS_LIST, systems);
//		hcd.setParameters(sapId, String.class);
//		hcd.setParameters(VIDEO_PARAMETER, String.class);
//		return assembleHealthCheck(hcd);
//	}
//
//	private HealthChecker checkGetUserCategories(String sapId,
//			List<SystemHealth> systems) {
//		HealthCheckerData hcd = assembleHealthCheckGeneralData(
//				GET_USER_CATEGORIES, USER_CATEGORIES_LIST, systems);
//		hcd.setParameters(sapId, String.class);
//		hcd.setParameters(null, String.class);
//		return assembleHealthCheck(hcd);
//	}
//
//	private HealthChecker checkGetUserContacts(String sapId,
//			List<SystemHealth> systems) {
//		HealthCheckerData hcd = assembleHealthCheckGeneralData(
//				GET_USER_CONTACTS, USER_CONTACTS_LIST, systems);
//		hcd.setParameters(sapId, String.class);
//		return assembleHealthCheck(hcd);
//	}
//
//	private HealthChecker checkGetUserName(String sapId,
//			List<SystemHealth> systems) {
//		HealthCheckerData hcd = assembleHealthCheckGeneralData(
//				GET_USER_OPS_USER_NAME, USER_NAME, systems);
//		hcd.setParameters(sapId, String.class);
//		return assembleHealthCheck(hcd);
//	}
//
//	private HealthChecker checkGetUserId(String sapId,
//			List<SystemHealth> systems) {
//		HealthCheckerData hcd = assembleHealthCheckGeneralData(
//				GET_USER_OPS_USER_ID, USER_ID, systems);
//		hcd.setParameters(sapId, String.class);
//		return assembleHealthCheck(hcd);
//	}
//
//	private HealthChecker checkGetCompleteManageSiteRedirectUrl(String sapId,
//			List<SystemHealth> systems) {
//		HealthCheckerData hcd = assembleHealthCheckGeneralData(
//				GET_COMPLETE_MANAGE_SITE_REDIRECT_URL,
//				USER_MANAGE_SITE_REDIRECT_URL, systems);
//		hcd.setParameters(sapId, String.class);
//		hcd.setParameters(Boolean.FALSE, Boolean.class);
//		hcd.setResultClass(String.class);
//		hcd.setResultSizeLowerLimit(10);
//
//		return assembleHealthCheck(hcd);
//	}
//
//	private HealthChecker checkGetUserEmailGeneralData(String sapId,
//			List<SystemHealth> systems) {
//		HealthCheckerData hcd = assembleHealthCheckGeneralData(
//				GET_USER_EMAIL_GENERAL_DATA, USER_EMAIL_GENERAL_DATA, systems);
//		hcd.setParameters(sapId, String.class);
//		hcd.setParameters(DEFAULT_COUNTRY_EN, String.class);
//		hcd.setParameters(VIDEO_PARAMETER, String.class);
//		hcd.setResultClass(OPSEmailData.class);
//		hcd.setResultObjectNoParametersMethods(GET_OPS_CONTACT_CONTAINER_INNER_METHOD,
//				GET_ASSETS_INNER_METHOD);
//
//		return assembleHealthCheck(hcd);
//	}
//
//	private HealthChecker checkGetUserEnabledSocialNetworks(String sapId,
//			List<SystemHealth> systems) {
//		HealthCheckerData hcd = assembleHealthCheckGeneralData(
//				GET_USER_ENABLED_SOCIAL_NETWORKS, USER_ENABLED_SOCIAL_NETWORKS,
//				systems);
//		hcd.setParameters(sapId, String.class);
//		hcd.setResultClass(OPSSocialNetworks.class);
//		hcd.setResultObjectNoParametersMethods(GET_URL_INNER_METHOD);
//
//		return assembleHealthCheck(hcd);
//	}
//
//	private HealthChecker checkGetUserEmailTemplates(String sapId,
//			List<SystemHealth> systems) {
//		HealthCheckerData hcd = assembleHealthCheckGeneralData(
//				GET_USER_EMAIL_TEMPLATES, USER_EMAIL_TEMPLATES_LIST, systems);
//		hcd.setParameters(sapId, String.class);
//		hcd.setParameters(DEFAULT_COUNTRY_EN, String.class);
//
//		return assembleHealthCheck(hcd);
//	}
//
//}
