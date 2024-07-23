package com.tanay.projectmanagementsystem.service;

import com.tanay.projectmanagementsystem.model.PlanType;
import com.tanay.projectmanagementsystem.model.Subscription;
import com.tanay.projectmanagementsystem.model.User;

public interface SubscriptionService
{
    Subscription createSubscription(User user);

    Subscription getUserSubscription(Long userId) throws Exception;

    Subscription upgradeSubscription(Long userId, PlanType planType);

    boolean isValid(Subscription subscription);
}
