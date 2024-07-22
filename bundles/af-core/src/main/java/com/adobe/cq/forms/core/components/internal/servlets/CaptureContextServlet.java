// package com.adobe.cq.forms.core.components.internal.servlets;
//
// import java.io.IOException;
//
// import javax.servlet.Servlet;
//
// import org.apache.sling.api.SlingHttpServletRequest;
// import org.apache.sling.api.SlingHttpServletResponse;
// import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
// import org.json.JSONObject;
// import org.osgi.service.component.annotations.Component;
// import org.osgi.service.component.annotations.Reference;
//
// import Model.GenerateCaptureContextRequest;
// import com.adobe.cq.forms.core.components.internal.cybersource.service.JwtProcessorService;
// import com.adobe.cq.forms.core.components.internal.cybersource.service.MicroformService;
// import com.adobe.cq.forms.core.components.internal.cybersource.service.PaymentsService;
// import com.fasterxml.jackson.databind.ObjectMapper;
//
// @Component(
// service = { Servlet.class },
// property = {
// "sling.servlet.paths=" + "/api/capture-context",
// "sling.servlet.methods=POST"
// })
// public class CaptureContextServlet extends SlingSafeMethodsServlet {
//
// @Reference
// private MicroformService microformService;
//
// @Reference
// private JwtProcessorService jwtProcessorService;
//
// @Reference
// private PaymentsService paymentsService;
//
// protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
//
// try {
// // Read request body
// String requestBody = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
// ObjectMapper objectMapper = new ObjectMapper();
// GenerateCaptureContextRequest mappedRequest = objectMapper.readValue(requestBody, GenerateCaptureContextRequest.class);
//
// // Call service methods
// String captureContextJwt = microformService.generateCaptureContext(mappedRequest);
// String decodedBody = jwtProcessorService.verifyJwtAndGetDecodedBody(captureContextJwt);
// String clientVersion = jwtProcessorService.getClientVersionFromDecodedBody(decodedBody);
//
// // Create JSON response
// JSONObject jsonResponse = new JSONObject();
// jsonResponse.put("captureContextJwt", captureContextJwt);
// jsonResponse.put("decodedBody", decodedBody);
// jsonResponse.put("clientVersion", clientVersion);
//
// // Send response
// response.setContentType("application/json");
// response.getWriter().write(jsonResponse.toString());
// } catch (Exception e) {
// response.sendError(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
// }
// }
// }
