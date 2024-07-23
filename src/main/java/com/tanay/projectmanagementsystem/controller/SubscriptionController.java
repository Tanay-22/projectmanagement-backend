package com.tanay.projectmanagementsystem.controller;

import com.tanay.projectmanagementsystem.config.JwtConstant;
import com.tanay.projectmanagementsystem.model.PlanType;
import com.tanay.projectmanagementsystem.model.Subscription;
import com.tanay.projectmanagementsystem.model.User;
import com.tanay.projectmanagementsystem.repository.SubscriptionRepository;
import com.tanay.projectmanagementsystem.repository.UserService;
import com.tanay.projectmanagementsystem.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController
{
    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<Subscription> getUserSubscription(@RequestHeader(JwtConstant.JWT_HEADER) String jwt)
            throws Exception
    {
        User user = userService.findUserProfileByJwt(jwt);

        Subscription subscription = subscriptionService.getUserSubscription(user.getId());

        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    @PatchMapping("/upgrade")
    public ResponseEntity<Subscription> upgradeSubscription(@RequestHeader(JwtConstant.JWT_HEADER) String jwt,
                                                            @RequestParam PlanType planType)
            throws Exception
    {
        User user = userService.findUserProfileByJwt(jwt);

        Subscription subscription = subscriptionService.upgradeSubscription(user.getId(), planType);

        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }
}
