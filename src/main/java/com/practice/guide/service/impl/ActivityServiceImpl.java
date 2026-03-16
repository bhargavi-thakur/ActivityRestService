package com.practice.guide.service.impl;

import com.practice.guide.entity.Activity;
import com.practice.guide.entity.Supplier;
import com.practice.guide.exception.DataNotFoundException;
import com.practice.guide.model.ActivityRequest;
import com.practice.guide.model.ActivityResponse;
import com.practice.guide.model.UpdateActivityRequest;
import com.practice.guide.repository.ActivityRepository;
import com.practice.guide.repository.SupplierRepository;
import com.practice.guide.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    public static final String ACTIVITY_ID_NOT_FOUND = "Activity not found with id: %d";
    public static final String SUPPLIER_ID_NOT_FOUND = "Supplier not found with id: %d";
    private final ActivityRepository activityRepository;
    private final SupplierRepository supplierRepository;

    @Override
    @Transactional
    public void createActivity(ActivityRequest request) {
        Supplier supplier = this.supplierRepository.findById(request.supplierId()).orElseThrow(() ->
                new DataNotFoundException(String.format(SUPPLIER_ID_NOT_FOUND, request.supplierId())));

        this.activityRepository.save(toActivityEntity(request, supplier));
    }

    @Override
    @Transactional
    public void updateActivity(Long id, ActivityRequest request) {
        Supplier supplier = this.supplierRepository.findById(request.supplierId()).orElseThrow(() ->
                new DataNotFoundException(String.format(SUPPLIER_ID_NOT_FOUND, request.supplierId())));

        Activity activity = getActivityOrThrow(id);
        setActivityForUpdate(request, activity, supplier);
        this.activityRepository.save(activity);
    }

    private static void setActivityForUpdate(ActivityRequest request, Activity activity, Supplier supplier) {
        activity.setTitle(request.title());
        activity.setPrice(request.price());
        activity.setCurrency(request.currency());
        activity.setRating(request.rating());
        activity.setSpecialOffer(request.specialOffer());
        activity.setSupplier(supplier);
    }

    @Override
    @Transactional
    public ActivityResponse updateActivityPriceAndSpecialOffer(Long id, UpdateActivityRequest request) {
        Activity activity = getActivityOrThrow(id);
        activity.setPrice(request.price());
        activity.setSpecialOffer(request.specialOffer());
        return toActivityResponse(this.activityRepository.save(activity));
    }

    @Override
    @Transactional
    public void deleteActivity(Long id) {
        Activity activity = getActivityOrThrow(id);
        this.activityRepository.delete(activity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityResponse> getAllActivities() {
        List<ActivityResponse> activityResponseList = new ArrayList<>();
        List<Activity> activities = this.activityRepository.findAll();
        activities.forEach(activity -> {
            ActivityResponse activityResponse = toActivityResponse(activity);
            activityResponseList.add(activityResponse);
        });
        return activityResponseList;
    }

    @Override
    @Transactional(readOnly = true)
    public ActivityResponse getActivityById(Long id) {
        return toActivityResponse(getActivityOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityResponse> searchActivity(String currency) {
        return this.activityRepository.findByCurrency(currency)
                .stream()
                .map(this::toActivityResponse)
                .toList();
    }

    private Activity getActivityOrThrow(Long id) {
        return this.activityRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ACTIVITY_ID_NOT_FOUND.formatted(id)));
    }


    private Activity toActivityEntity(ActivityRequest request, Supplier supplier) {
        Activity activity = new Activity();
        setActivityForUpdate(request, activity, supplier);
        return activity;
    }

    private ActivityResponse toActivityResponse(Activity activity) {
        return ActivityResponse.builder()
                .id(activity.getId())
                .title(activity.getTitle())
                .price(activity.getPrice())
                .currency(activity.getCurrency())
                .rating(activity.getRating())
                .specialOffer(activity.getSpecialOffer())
                //when getSupplier() is invoked then only it will be loaded from DB due LAZY FETCH
                .supplierName(activity.getSupplier().getName())
                .build();
    }
}
