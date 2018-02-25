package org.usfirst.frc.team3555.robot.Autonomous;

import java.util.ArrayDeque;

/**
 * This is a queue that holds a set of {@link Action} to be done.
 * Adding actions will add to the queue, but only one action will be checked (the first will execute, when it has completed, the next will move up and execute)
 * However, an action can store more than 1 action, allowing for these to be completed in sets in succession 
 * 
 * @author Sam S.
 */
public class ActionQueue {
	private ArrayDeque<Action> queue;
	
	/**
	 * Creates a new Action Queue.
	 */
	public ActionQueue() {
		queue = new ArrayDeque<>();
	}
	
	/**
	 * Updates the first Action.
	 * If this Action hasn't been started, then it will call its start method.
	 * If this Action is complete then it will be removed from the list and its clean up method will be called
	 * 
	 * @see Action
	 */
	public void update() {
		Action currentAction = queue.peek();
		
		if(currentAction != null) {
			if(!currentAction.isStarted())//Check if this has already started
				currentAction.start();
			
			if(currentAction.update())//Update the action, removes it if it is complete
				remove();
		}
	}
	
	/**
	 * Adds this action to the end of the queue
	 * NOTE: These actions are completed one by one in succession. 
	 * This means that if an action is added to the end, the rest that are in the front all need to execute before this one does
	 * 
	 * @param a - Action to be added
	 */
	public void add(Action a) { queue.addLast(a); }

	/**
	 * This will remove the first action, and call its clean up method
	 */
	public void remove() {
		Action a = queue.pop();
		a.cleanUp();
	}
	
	public void clear() {
		queue.clear();
	}
}
