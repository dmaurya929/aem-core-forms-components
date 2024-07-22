
package com.adobe.cq.forms.core.components.internal.cybersource.config;

import java.util.Properties;

import org.osgi.service.component.annotations.Component;

import lombok.Data;

@Component
@Data
public class MerchantCredentials {

    // app.merchantID=aem_forms_1720077330
    // app.merchantKeyId=a43912f8-be1f-42cc-830d-a59700daaa9c
    // app.merchantSecretKey=KigQfIEVbabKCrrPCjDO8XK5vVdXNjs+df9TxjIuJ/8=
    //
    // app.requestHost=apitest.cybersource.com
    // app.userAgent=Mozilla/5.0
    // app.runEnvironment=apitest.cybersource.com
    // app.authenticationType=http_signature

    String merchantID = "aem_forms_1720077330";
    String requestHost = "apitest.cybersource.com";
    String merchantKeyId = "a43912f8-be1f-42cc-830d-a59700daaa9c";
    String merchantSecretKey = "KigQfIEVbabKCrrPCjDO8XK5vVdXNjs+df9TxjIuJ/8=";
    String userAgent = "Mozilla/5.0";
    String runEnvironment = "apitest.cybersource.com";
    String authenticationType = "http_signature";

    public Properties getAsJavaProps() {
        Properties props = new Properties();
        props.setProperty("merchantID", merchantID);
        props.setProperty("merchantKeyId", merchantKeyId);
        // Take care, not true camel case here
        props.setProperty("merchantsecretKey", merchantSecretKey);
        props.setProperty("userAgent", userAgent);
        props.setProperty("requestHost", requestHost);
        props.setProperty("runEnvironment", runEnvironment);
        props.setProperty("authenticationType", authenticationType);

        return props;
    }

}
