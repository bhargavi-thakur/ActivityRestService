package com.practice.guide.controller;

import com.practice.guide.model.ActivityRequest;
import com.practice.guide.model.ActivityResponse;
import com.practice.guide.model.UpdateActivityRequest;
import com.practice.guide.service.ActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ActivitiesController {

    private final ActivityService activityService;

    @PostMapping("/activities")
    public ResponseEntity<Void> createActivity(@Valid @RequestBody ActivityRequest activity){
        this.activityService.createActivity(activity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/activities")
    public ResponseEntity<List<ActivityResponse>> getAllActivities(){
        return ResponseEntity.ok(this.activityService.getAllActivities());
    }

    @GetMapping("/activities/id/{id}")
    public ResponseEntity<ActivityResponse> getActivity(@PathVariable Long id){
        return ResponseEntity.ok(this.activityService.getActivityById(id));
    }

    @GetMapping("/activities/search")
    public ResponseEntity<List<ActivityResponse>> getActivitiesByCurrency(@RequestParam(name = "currency",
            required = false,defaultValue = "$") String currency){
        return ResponseEntity.ok(this.activityService.searchActivity(currency));
    }

    @PutMapping("/activities/id/{id}")
    public ResponseEntity<?> updateActivity(@PathVariable Long id,
                                                                @RequestBody ActivityRequest request){
        this.activityService.updateActivity(id,request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/activities/id/{id}")
    public ResponseEntity<ActivityResponse> updateActivityPartially(@PathVariable Long id,
                                            @RequestBody UpdateActivityRequest request){
        ActivityResponse activityResponse = this.activityService.updateActivityPriceAndSpecialOffer(id, request);
        return ResponseEntity.ok(activityResponse);
    }

    @DeleteMapping("/activities/id/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id){
        this.activityService.deleteActivity(id);
        return ResponseEntity.ok().build();

    }
}
