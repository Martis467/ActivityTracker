package com.views;

import com.models.Activity;
import com.models.Goal;
import com.models.GoalActivityRelation;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GoalTabView {
    private List<Goal> goals;
    private Map<Goal, List<Activity>> goalActivityMap;
    private int cur; // Current goal index
    private int max; // Max goal index

    public GoalTabView(List<Goal> goals, List<GoalActivityRelation> goalActivityRelations){
        this.goals = goals;
        this.cur = 0;
        this.max = goals.size();
        this.goalActivityMap = new HashMap<>();

        goals.forEach(g -> {
            var activities = new LinkedList<Activity>();
            goalActivityRelations.stream()
                    .filter(ga -> ga.goalId == g.id)
                    .forEach(ga -> activities.add(ga.activity));

            goalActivityMap.put(g, activities);
        });
    }

    /**
     * Increase view index by one
     */
    public void next(){
        cur++;
        if(cur == max)
            cur = 0;
    }

    /**
     * Decrease view index by one
     */
    public void previous(){
        cur--;
        if(cur == -1)
            cur = max-1;
    }

    /**
     * Get current goal
     * @return
     */
    public Goal getGoal(){return goals.get(cur);}

    /**
     * Get current goal activities
     * @return
     */
    public List<Activity> getGoalActivities() {return goalActivityMap.get(goals.get(cur));}
}
