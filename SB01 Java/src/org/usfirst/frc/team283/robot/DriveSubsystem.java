package org.usfirst.frc.team283.robot;

import edu.wpi.first.wpilibj.Talon;

public class DriveSubsystem 
{
	private static final double DEADZONE = 0.1;
	//The following two constants act as alternate names for bool values
	private static final boolean MECHANUM = false;
	private static final boolean TANK = true;
	/** Speed multiplier for slow speed */
	private static final double SLOWSPEED = 0.5;
	
	/** Controls which drive mode we're in. True is tank, false is mechanum */
	private boolean driveMode = TANK;
	
	Talon frontLeftController;
	Talon frontRightController;
	Talon backLeftController;
	Talon backRightController;
	
	DriveSubsystem()
	{
		frontLeftController = new Talon(Constants.FRONT_LEFT_PORT);
		frontRightController = new Talon(Constants.FRONT_RIGHT_PORT);
		backLeftController = new Talon(Constants.BACK_LEFT_PORT);
		backRightController = new Talon(Constants.BACK_RIGHT_PORT);
	}
	
	/**
	 * Controls the drive of the robot
	 * @param leftYMagnitude - Main control for left
	 * @param rightYMagnitude - Main control for right
	 * @param leftXMagnitude - Used in mechanum drive
	 * @param rightXMagnitude - Used in mechanum drive
	 * @param slow - the state of the button assigned to control slow speed
	 */
	public void drive(double leftYMagnitude, double rightYMagnitude, double leftXMagnitude, double rightXMagnitude, boolean slow)
	{
		if (driveMode == TANK) //X Magnitudes can be ignored
		{
			//The front and back motors of both sides are set to the same values
			frontLeftController.set(leftYMagnitude);
			frontRightController.set(rightYMagnitude);
			backLeftController.set(leftYMagnitude);
			backRightController.set(rightYMagnitude);
		}
		else if (driveMode == MECHANUM) //X magnitudes are used to control robot
		{
			//Will be coded later
		}
	}
	
	/**
	 * This function controls the drive mode
	 * @param tankButton - The state of button to switch to tank mode
	 * @param mechButton - The state of button to switch to mechanum mode
	 */
	public void driveMode(boolean tankButton, boolean mechButton)
	{
		//tankMode = (tankButton && !mechButton);
		if (tankButton && !mechButton) //If only the one button is pressed
		{
			driveMode = TANK; //Switch to that mode
		}
		else if (!tankButton && mechButton) //If only this one button is pressed
		{
			driveMode = MECHANUM; //Switch to this mode (mechanum)
		}
		//If both are true, the state does not change
		//If both are false, the state does not change
	}
}
