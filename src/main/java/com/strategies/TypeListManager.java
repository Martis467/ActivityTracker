package com.strategies;

import com.enumerations.ActivityDuration;
import com.enumerations.ActivityType;
import com.exception.UIException;
import com.models.Activity;
import com.models.ActivityLog;
import com.utilities.Extensions;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.TextAlignment;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TypeListManager {

    private List<Button> workout;
    private List<Button> read;
    private List<Button> program;
    private List<Button> game;
    private List<Button> music;
    private List<Button> educate;

    private List<Button> selected;

    private double width;
    private double height;

    public TypeListManager(List<Activity> activityList, double width, double height) {
        this.width = width;
        this.height = height;

        this.workout = this.parseActivities(activityList, ActivityType.Workout);
        this.read = this.parseActivities(activityList, ActivityType.Read);
        this.program = this.parseActivities(activityList, ActivityType.Program);
        this.game = this.parseActivities(activityList, ActivityType.Game);
        this.music = this.parseActivities(activityList, ActivityType.Music);
        this.educate = this.parseActivities(activityList, ActivityType.Educate);

        this.selected = new LinkedList<>();
    }

    public List<ActivityLog> getLogs() throws UIException {
        if (selected.isEmpty())
            throw new UIException("No activity logs have been selected, because the selected list is empty", "Unable to create");

        return selected.stream().map(b -> (ActivityLog)b.getUserData()).collect(Collectors.toList());
    }

    private List<Button> parseActivities(List<Activity> activityList, ActivityType type){
        var buttons = new LinkedList<Button>();
         activityList.stream().filter(a -> a.type == type).forEach(a -> buttons.addAll(makeButtons(a)));

         return buttons;
    }

    /**
     * Create a button list containing an activity and it's duration
     * Returns multiple buttons for activities
     * @param activity
     * @return
     */
    private List<Button> makeButtons(Activity activity){
        var buttons = new LinkedList<Button>();

        activity.expectedDurations.forEach(ed -> {
            var button = new Button(activity.name + " - " + ed.getDuration() + " min.");
            var activityLog = createLog(activity, ed);

            button.setPrefWidth(this.width);
            button.setMinWidth(this.width);

            button.setPrefHeight(this.height);
            button.setMinHeight(this.height);

            button.textAlignmentProperty().setValue(TextAlignment.CENTER);
            button.setUserData(activityLog);

            this.addButtonOnClickEvent(button);
        });

        return buttons;
    }

    private ActivityLog createLog(Activity activity, ActivityDuration duration){
        return new ActivityLog(
                0,
                duration.getDuration(),
                0,
                Extensions.getNowInUnixTime(),
                0,
                activity.id,
                activity);
    }

    private void addButtonOnClickEvent(Button button){
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(selected.contains(button)){
                    selected.remove(button);
                    editTypeList(button, true);
                } else {
                    selected.add(button);
                    editTypeList(button, false);
                }
            }
        });
    }

    private void editTypeList(Button button, boolean add){
        var activity = (ActivityLog)button.getUserData();

        switch (activity.activity.type){
            case Workout:
                if(add) this.workout.add(button);
                else this.workout.remove(button);
                break;

            case Read:
                if(add) this.read.add(button);
                else this.read.remove(button);
                break;

            case Program:
                if(add) this.program.add(button);
                else this.program.remove(button);
                break;

            case Game:
                if(add) this.game.add(button);
                else this.game.remove(button);
                break;

            case Music:
                if(add) this.music.add(button);
                else this.music.remove(button);
                break;

            case Educate:
                if(add) this.educate.add(button);
                else this.educate.remove(button);
                break;
        }
    }
}
