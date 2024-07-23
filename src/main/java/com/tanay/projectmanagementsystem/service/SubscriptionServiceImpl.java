package com.tanay.projectmanagementsystem.service;

import com.tanay.projectmanagementsystem.model.PlanType;
import com.tanay.projectmanagementsystem.model.Subscription;
import com.tanay.projectmanagementsystem.model.User;
import com.tanay.projectmanagementsystem.repository.SubscriptionRepository;
import com.tanay.projectmanagementsystem.repository.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class SubscriptionServiceImpl implements SubscriptionService
{
    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription createSubscription(User user)
    {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusYears(1));
        subscription.setValid(true);
        subscription.setPlanType(PlanType.FREE);

        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription getUserSubscription(Long userId) throws Exception
    {
        Subscription subscription = subscriptionRepository.findByUserId(userId);

        if(!isValid(subscription))
        {
            subscription.setPlanType(PlanType.FREE);
            subscription.setStartDate(LocalDate.now());
            subscription.setEndDate(LocalDate.now().plusYears(1));
        }
        return subscription;
    }

    @Override
    public Subscription upgradeSubscription(Long userId, PlanType planType)
    {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        subscription.setPlanType(planType);
        subscription.setStartDate(LocalDate.now());

        if(planType.equals(PlanType.ANNUALLY))
            subscription.setEndDate(LocalDate.now().plusYears(1));
        else
            subscription.setEndDate(LocalDate.now().plusMonths(1));

        return subscriptionRepository.save(subscription);
    }

    @Override
    public boolean isValid(Subscription subscription)
    {
        if(subscription.getPlanType().equals(PlanType.FREE))
            return true;

        LocalDate endDate = subscription.getEndDate();
        LocalDate currentDate = LocalDate.now();

        return endDate.isAfter(currentDate) || endDate.isEqual(currentDate);
    }
}
