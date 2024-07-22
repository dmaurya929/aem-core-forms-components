// package com.adobe.cq.forms.core.components.internal.cybersource.service;
//
// import org.osgi.service.component.annotations.Component;
// import org.osgi.service.component.annotations.Reference;
//
// import Api.PaymentsApi;
// import Invokers.ApiClient;
// import Invokers.ApiException;
// import Model.*;
// import com.adobe.cq.forms.core.components.internal.cybersource.config.MerchantCredentials;
// import com.adobe.cq.forms.core.components.internal.cybersource.dto.MakePaymentRequest;
// import com.cybersource.authsdk.core.ConfigException;
// import com.cybersource.authsdk.core.MerchantConfig;
// import lombok.RequiredArgsConstructor;
//
// @Component
// @RequiredArgsConstructor
// public class PaymentsService {
//
// @Reference
// private MerchantCredentials merchantCredentials;
//
// public String makePaymentWithTransientToken(MakePaymentRequest request) throws ConfigException, ApiException {
// final CreatePaymentRequest paymentRequest = createPaymentRequest(request);
// final ApiClient apiClient = new ApiClient(new MerchantConfig(merchantCredentials.getAsJavaProps()));
// final PaymentsApi paymentApi = new PaymentsApi(apiClient);
//
// final PtsV2PaymentsPost201Response response = paymentApi.createPayment(paymentRequest);
//
// System.out.println("ResponseCode: " + apiClient.responseCode);
// System.out.println("Status: " + apiClient.status);
// System.out.println(response);
//
// return response.toString();
// }
//
// private static CreatePaymentRequest createPaymentRequest(MakePaymentRequest request) {
//
// final Ptsv2paymentsClientReferenceInformation client = new Ptsv2paymentsClientReferenceInformation()
// .code(request.getClientReferenceCode());
//
// // Hardcoding address field for now, this should be taken in AF itself
// final Ptsv2paymentsOrderInformationBillTo billTo = new Ptsv2paymentsOrderInformationBillTo()
// .country("US")
// .firstName("ABC")
// .lastName("DEF")
// .address1("2 sector 111")
// .postalCode("94105")
// .locality("san francisco")
// .administrativeArea("CA")
// .email("test@adb.com");
//
// final Ptsv2paymentsOrderInformationAmountDetails amountDetails = new Ptsv2paymentsOrderInformationAmountDetails()
// .totalAmount(request.getAmount())
// .currency(request.getCurrency());
//
// final Ptsv2paymentsOrderInformation orderInformation = new Ptsv2paymentsOrderInformation()
// .billTo(billTo)
// .amountDetails(amountDetails);
//
// final Ptsv2paymentsTokenInformation tokenInformation = new Ptsv2paymentsTokenInformation().transientTokenJwt(request
// .getTransientToken());
//
// return new CreatePaymentRequest()
// .clientReferenceInformation(client)
// .orderInformation(orderInformation)
// .tokenInformation(tokenInformation);
// }
// }
