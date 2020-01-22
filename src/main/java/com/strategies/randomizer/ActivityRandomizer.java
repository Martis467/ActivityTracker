package com.strategies.randomizer;

import com.enumerations.ActivityDuration;
import com.enumerations.ActivityType;
import com.enumerations.FilteringPriority;
import com.enumerations.GoalType;
import com.models.Activity;
import com.models.ActivityLog;
import com.models.GoalActivityRelation;
import com.sun.marlin.FloatMath;
import com.utilities.Extensions;
import jdk.jshell.spi.ExecutionControl;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class ActivityRandomizer {
    // Filters
    private int totalTime;
    private Collection<ActivityType> selectedActivityTypes;
    private Collection<GoalType> selectedGoalTypes;
    private FilteringPriority priority;
    private boolean overtime;

    // Data objects
    private Vector<GoalActivityRelation> goalActivities;
    private Vector<ActivityLog> activityLogs;
    private KnapsackSolver solver;


    public ActivityRandomizer(int totalTime, Collection<ActivityType> selectedActivityTypes,
                              Collection<GoalType> selectedGoalTypes, FilteringPriority priority,
                              boolean overtime, List<GoalActivityRelation> goalActivities) {
        this.totalTime = totalTime;
        this.selectedActivityTypes = selectedActivityTypes;
        this.selectedGoalTypes = selectedGoalTypes;
        this.priority = priority;
        this.overtime = overtime;
        this.goalActivities = new Vector<>(goalActivities);
        this.activityLogs = new Vector<>();
    }

    /**
     * From the selected parameters get random activities that match the selected filters
     * It uses the Knapsack algorithm, that pick elements without duplicates.
     * @return random activity logs
     * @throws ExecutionControl.NotImplementedException
     */
    public List<ActivityLog> getRandomActivities() throws ExecutionControl.NotImplementedException {
        // TODO: CAN BE OPTIMIZED
        var activityWeights = getActivityWeights(); // Filtered values

        var weights = new Vector<Integer>();
        var values = new Vector<Integer>();

        for (var aw :
                activityWeights.entrySet()) {
            // Find the activity object by the activity id
            var activity = goalActivities.stream().filter(ga -> ga.activityId == aw.getKey()).findFirst().get().activity;

            weights.addAll(activity.expectedDurations.stream()
                    .map(ActivityDuration::getDuration).collect(Collectors.toList()));

            values.addAll(activity.expectedDurations.stream()
                    .map(ed -> this.adjustValueByDuration(ed.getDuration()) * aw.getValue()).collect(Collectors.toList()));

            activity.expectedDurations.forEach(ed -> this.activityLogs.add(new ActivityLog(activity,ed)));
        }

        this.solver = new KnapsackSolver(this.totalTime, weights.size(),
                values.stream().mapToInt(i -> i).toArray(),
                weights.stream().mapToInt(i -> i).toArray());

        this.solver.solveWithDuplicates();
        var backpack = this.solver.getBackpack();
        var randomLogs = backpack.stream().map(index -> this.activityLogs.get(index)).collect(Collectors.toList());

        if(this.overtime && this.totalTime != randomLogs.stream().mapToInt(al -> al.duration).sum())
            adjustForTotalTime(randomLogs);

        return backpack.stream().map(index -> this.activityLogs.get(index)).collect(Collectors.toList());
    }

    /**
     * Applies the filters and returns the adjusted values for each goal activity relation
     * @return
     */
    private Map<Integer, Integer> getActivityWeights() throws ExecutionControl.NotImplementedException {
        this.goalActivities.removeIf(x -> !this.selectedActivityTypes.contains(x.activity.type)
                && !this.selectedGoalTypes.contains(x.goal.type));

        var activityWeightMap = new HashMap<Integer, Integer>();

        this.goalActivities.forEach(ga -> activityWeightMap.put(ga.activity.id, 0));

        switch (priority){
            case None:
                this.goalActivities.forEach(ga -> {
                    var i = activityWeightMap.get(ga.activity.id);
                    activityWeightMap.put(ga.activity.id, i + ga.weight);
                });
                break;
            case GoalCloseToCompletion:
                this.goalActivities.forEach(ga -> {
                    var i = activityWeightMap.get(ga.activity.id);
                    activityWeightMap.put(ga.activity.id, i + (int)(ga.weight * ga.goal.percentage));
                });
                break;
            case GoalsCloseToDeadline:
                var currentDate = Extensions.getNowInUnixTime();
                this.goalActivities.forEach(ga -> {
                    var i = activityWeightMap.get(ga.activity.id);
                    activityWeightMap.put(ga.activity.id, i + adjustValueByDate(ga, currentDate));
                });
                break;
            case GoalsFarFromCompletion:
                this.goalActivities.forEach(ga -> {
                    var i = activityWeightMap.get(ga.activity.id);
                    activityWeightMap.put(ga.activity.id, i + (int)(ga.weight * (100 - ga.goal.percentage)));
                });
                break;
            case SeldomGoals:
                throw new ExecutionControl.NotImplementedException("This priority has not been implemented");
            case SeldomActivities:
                throw new ExecutionControl.NotImplementedException("This priority has not been implemented");
            default:
                break;
        }

        return activityWeightMap;
    }

    /**
     * Increases the goal activity value by 2x/1.5x/1.25x depending on how close is expected finish
     * @param ga
     * @param now
     * @return
     */
    private int adjustValueByDate(@NotNull GoalActivityRelation ga, long now) {
        // s -> min -> hour -> days
        var daysDiff = (ga.goal.expectedFinish - now) / 1000 / 60 / 60 / 24;

        if(daysDiff <= 7) return 2 * ga.weight;
        if(daysDiff <= 14) return FloatMath.floor_int(1.5 * ga.weight);
        if(daysDiff <= 21) return FloatMath.floor_int(1.25 * ga.weight);

        return ga.weight;
    }

    /**
     * Increases the value of the activity based on it's duration
     * @param duration
     * @return
     */
    private int adjustValueByDuration(int duration){
        if (duration <= 25) return duration;
        if (duration <= 60) return (int)(duration * 1.1);
        if (duration <= 100) return (int)(duration * 1.2);

        return (int)(duration * 1.3);
    }

    /**
     * If overflow was selected and the backpack was not full we can add the other smallest element
     * to "overflow" the backpack with activities
     * @param randomLogs
     */
    private void adjustForTotalTime(List<ActivityLog> randomLogs) {
        var activities = randomLogs.stream().map(al -> al.activity).collect(Collectors.toList());
        this.activityLogs.removeIf(al -> activities.contains(al.activity));
        var activityLog = this.activityLogs.stream().min((al1, al2) -> al2.duration - al1.duration).get();
        randomLogs.add(activityLog);
    }
}
