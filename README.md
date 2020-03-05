# ActivityTracker

Activity tracker is a simple application to set up what activities that one might want to and relate them to goals

## Project purpose

I'd like to deepen my knowledge in javaFX, so I've decided create a UI application for tracking your own time
## Model Structure
### Activity
| Field | Type | Explanation |
| ------|------|------------ |
| Name | String | Explain here |


Doing an activity has an impact on the overall completion of a goal, some activities directly impact goals
and some only partially. That's why there is a certain weight associated with each goal-activity relation. The
weight is used to multiply the amount of time spent on doing the activity. 
