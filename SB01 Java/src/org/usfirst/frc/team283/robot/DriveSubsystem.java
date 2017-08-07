package org.usfirst.frc.team283.robot;

import org.usfirst.frc.team283.robot.JoystickSchema.Schema;

import edu.wpi.first.wpilibj.Talon;

public class DriveSubsystem 
{
	private static final double DEADZONE = 0.1;
	//The following two constants act as alias for bool values
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
	
	@Schema(JoystickSchema.LEFT_Y)
	@Schema(JoystickSchema.LEFT_X)
	@Schema(JoystickSchema.RIGHT_Y)
	@Schema(JoystickSchema.RIGHT_X)
	@Schema(value = JoystickSchema.X, desc = "slow speed")
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
		double lym = Rescaler.rescale(DEADZONE, 0, 1, 0, leftYMagnitude);
		double rym = Rescaler.rescale(DEADZONE, 0, 1, 0, rightYMagnitude);
		double lxm = Rescaler.rescale(DEADZONE, 0, 1, 0, leftXMagnitude);
		double rxm = Rescaler.rescale(DEADZONE, 0, 1, 0, rightXMagnitude);
		
		if (driveMode == TANK) //X Magnitudes can be ignored
		{
			//The front and back motors of both sides are set to the same values
			frontLeftController.set(lym);
			frontRightController.set(rym);
			backLeftController.set(lym);
			backRightController.set(rym);
		}
		else if (driveMode == MECHANUM) //X magnitudes are used to control robot
		{
			//Will be coded later
			/* The assumed layout:
			 * \\  //
			 *   []
			 * //  \\
			 */
			frontLeftController.set((lym + lxm)  * (slow ? SLOWSPEED : 1));
			frontRightController.set((rym - rxm) * (slow ? SLOWSPEED : 1));
			backLeftController.set((lym - lxm)  * (slow ? SLOWSPEED : 1));
			backRightController.set((rym + rxm)  * (slow ? SLOWSPEED : 1));
		}
	}
	
	@Schema(value = JoystickSchema.BACK, desc = "tank mode")
	@Schema(value = JoystickSchema.START, desc = "mechanum mode")
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
