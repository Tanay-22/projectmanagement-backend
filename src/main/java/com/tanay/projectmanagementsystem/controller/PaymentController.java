package com.tanay.projectmanagementsystem.controller;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.tanay.projectmanagementsystem.config.JwtConstant;
import com.tanay.projectmanagementsystem.model.PlanType;
import com.tanay.projectmanagementsystem.model.User;
import com.tanay.projectmanagementsystem.repository.UserService;
import com.tanay.projectmanagementsystem.response.PaymentLinkResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController
{
    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecret;

    @Autowired
    private UserService userService;

    @PostMapping("/{planType}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable PlanType planType,
                                                         @RequestHeader(JwtConstant.JWT_HEADER) String jwt)
            throws Exception
    {
        User user = userService.findUserProfileByJwt(jwt);
        int amount = 799 * 100;

        if(planType.equals(PlanType.ANNUALLY))
            amount = (int)(amount * 12 * 0.7);

        RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);

        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", amount);
        paymentLinkRequest.put("currency", "INR");

        JSONObject customer = new JSONObject();
        customer.put("name", user.getFullName());
        customer.put("email", user.getEmail());
        paymentLinkRequest.put("customer", customer);

        JSONObject notify = new JSONObject();
        notify.put("email", true);
        paymentLinkRequest.put("notify", notify);

        paymentLinkRequest.put("callback_url", "http://localhost:5173/upgrade_plan/success?planType=" + planType);

        PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

        String paymentLinkId = payment.get("id");
        String paymentLinkUrl = payment.get("short_url");

        PaymentLinkResponse res = new PaymentLinkResponse();
        res.setPaymentLinkId(paymentLinkId);
        res.setPaymentLinkUrl(paymentLinkUrl);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
