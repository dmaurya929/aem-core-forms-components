//
// package com.adobe.cq.forms.core.components.internal.cybersource.service;
//
// import org.osgi.service.component.annotations.Component;
// import org.osgi.service.component.annotations.Reference;
//
// import Api.MicroformIntegrationApi;
// import Invokers.ApiClient;
// import Invokers.ApiException;
// import Model.GenerateCaptureContextRequest;
// import com.adobe.cq.forms.core.components.internal.cybersource.config.MerchantCredentials;
// import com.cybersource.authsdk.core.ConfigException;
// import com.cybersource.authsdk.core.MerchantConfig;
// import lombok.RequiredArgsConstructor;
//
// @Component
// @RequiredArgsConstructor
// public class MicroformService {
//
// @Reference
// private MerchantCredentials merchantCredentials;
//
// public String generateCaptureContext(final GenerateCaptureContextRequest captureContextRequest) throws ConfigException, ApiException {
//
// final ApiClient apiClient = new ApiClient(new MerchantConfig(merchantCredentials.getAsJavaProps()));
//
// final MicroformIntegrationApi microformApi = new MicroformIntegrationApi(apiClient);
//
// final String response = microformApi.generateCaptureContext(captureContextRequest);
//
// System.out.println("Response Code: " + apiClient.responseCode);
// System.out.println("Response Message: " + apiClient.status);
// System.out.println("Response Body: " + response);
// return response;
// }
// }
