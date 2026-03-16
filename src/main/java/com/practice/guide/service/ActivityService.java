package com.practice.guide.service;

import com.practice.guide.model.ActivityRequest;
import com.practice.guide.model.ActivityResponse;
import com.practice.guide.model.UpdateActivityRequest;

import java.util.List;

public interface ActivityService {

    void createActivity(ActivityRequest request);

    void updateActivity(Long id, ActivityRequest request);

    ActivityResponse updateActivityPriceAndSpecialOffer(Long id, UpdateActivityRequest request);

    void deleteActivity(Long id);

    List<ActivityResponse> getAllActivities();

    ActivityResponse getActivityById(Long id);

    List<ActivityResponse> searchActivity(String currency);


}
