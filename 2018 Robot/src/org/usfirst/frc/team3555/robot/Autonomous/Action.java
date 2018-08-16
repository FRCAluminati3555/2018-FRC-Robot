package org.usfirst.frc.team3555.robot.Autonomous;

import org.usfirst.frc.team3555.robot.Autonomous.InnerType.ActionCleanUp;
import org.usfirst.frc.team3555.robot.Autonomous.InnerType.ActionStart;
import org.usfirst.frc.team3555.robot.Autonomous.InnerType.ActionUpdate;

/**
 * This class will store an action for the robot to complete during autonomous. 
 * Actions can be a simple singular action that will be completed, or an action can be given a set of other actions that can be executed simultaneously.
 * NOTE: If this is a set of actions, then it will not be completed until ALL of the set's actions have been completed.
 * ALSO: DO NOT conflict actions (i.e. give one action to drive forward, and one to drive back). If done so, then the robot will attempt both at the same time, and will cause problems 
 * 
 * @author Sam S.
 */
public class Action {
	
	/**
	 * Lambda Expressions that dictate code to be executed at certain states
	 * This means that this is a single action object that has three states
	 */
	private ActionStart actionStart;
	private ActionUpdate actionUpdate;
	private ActionCleanUp actionCleanUp;
	
	/**
	 * These store a list of other actions to execute
	 * Depending on "simultaneous" these will either all be done one by one, or all at the same time
	 * BUT This action as a whole will not be "complete" until all actions in this list are done
	 */
	private Action[] actions;
	private boolean simultaneous;
	
	/**
	 * Variables to keep track of the state that this action is in
	 */
	private long startTime;
	private boolean started;
	private boolean complete;
	
	/**
	 * Denotes a single action that will execute the actionUpdate code to make the robot do something
	 * 
	 * @param actionStart - Inner type that will be executed to start an action
	 * @param actionUpdate - Inner type that will be executed to perform an action
	 * @param actionCleanUp - Inner type that will be executed to end an action
	 */
	public Action(ActionStart actionStart, ActionUpdate actionUpdate, ActionCleanUp actionCleanUp) {
		this.actionStart = actionStart;
		this.actionUpdate = actionUpdate;
		this.actionCleanUp = actionCleanUp;
	}
	
	/**
	 * This denotes this action to be a set of actions that will all be completed simultaneously or one at a time by the robot
	 * 
	 * @param simultaneous - Denotes whether or not this set of actions will compete in succession or all at the same time 
	 * @param actions - The set of actions to be completed (The subsystems have static generators for actions)
	 */
	public Action(boolean simultaneous, Action... actions) {
		this.actions = actions;
		this.simultaneous = simultaneous;
	}
	
	/**
	 * Calls this action's start inner type
	 * This will automatically set the start time to the current time, and the started state will now be true. 
	 * NOTE: This will not run if this has already been started (i.e. the started state is already true)
	 */
	public void start() {
		if(!started) {
			startTime = System.currentTimeMillis();
			started = true;
			
			if(actionStart != null) 
				actionStart.start(); 
		}
	}
	
	/**
	 * This will update the robot for its designated action(s).
	 * If it is a set of actions, then they will be done one by one, or all at the same time depending on what was given in the constructor.
	 * The set will not be considered complete until all actions are done.
	 * 
	 * @return - The state of the action, ongoing or complete
	 */
	public boolean update() {
		if(!complete) {//Checks if this action is already done
			if(actions != null) {//This will check if this is a set of actions, or just a singular action
				if(simultaneous) {//Checks to see if the actions are all at once, or one at a time
					boolean temp = true;
					for(int i = 0; i < actions.length; i++) {//Loop through all the actions in this list and update them, start them, and FINISH HIM!
						Action a = actions[i];
						
						if(a != null) {
							temp = false;
							
							if(!a.isStarted())
								a.start();
							else if(!a.isComplete()) {
								a.update();
							} else if(a.isComplete()) {
								a.cleanUp();
								actions[i] = null;
							}
						}
					}
					complete = temp;
				} else {
					for(int i = 0; i < actions.length; i++) {//Loop through the action list and update the first action that is not complete
						Action a = actions[i];
						
						if(a != null) {
							if(!a.isStarted()) {
								a.start();
							} else if(!a.isComplete()) {
								a.update();
							} else if(a.isComplete()) {
								a.cleanUp();
								actions[i] = null;
							}
							break;
						}
					}
					
					complete = actions[actions.length - 1] == null;//Check to see if all the actions are done, since this is done 1 by 1 then if the last is done, then they are all done
				}
			} else {//Singular action, just check the update method inner type
				complete = actionUpdate.update(startTime);
			}
		}
		return complete;
	}
	
	/**
	 * Cleans up the action -> Sets any motors to 0, change their modes etc...
	 * Only called once when they finish
	 */
	public void cleanUp() {
		if(actionCleanUp != null)
			actionCleanUp.cleanUp();
	}
	
	/**
	 * Checks the state of this action is in
	 * 
	 * @return - The state that this action is in, ongoing or complete
	 */
	public boolean isComplete() { return complete; }

	/**
	 * Checks if this action has started
	 * 
	 * @return - Whether or not this action has started
	 */
	public boolean isStarted() { return started; }
	
	/**
	 * This will stop this action by stating that it is complete, and therefore will be taken out by its action queue when it gets priority
	 */
	public void interupt() { complete = true; }
	
	/**
	 * Checks the time that this action started at
	 * 
	 * @return - The time that this action started at
	 */
	public long getStartTime() { return startTime; }
}
