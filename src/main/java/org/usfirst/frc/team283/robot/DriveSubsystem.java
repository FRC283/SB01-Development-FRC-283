package org.usfirst.frc.team283.robot;

import org.usfirst.frc.team283.robot.Scheme.Schema;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveSubsystem 
{
	private static final double DEADZONE = 0.1;
	/** Speed multiplier for slow speed */
	private static final double SLOWSPEED = 0.3;
	private static boolean nextHeld = false;
	private static boolean prevHeld = false;
	double lym;
	double rym;
	double lxm;
	double rxm;
	/** Controls which drive mode we're in. True is tank, false is mechanum */
	public String driveMode = "Tank - Normal";
	
	Talon frontLeftController;
	Talon frontRightController;
	Spark backLeftController;
	Talon backRightController;
	
	DriveSubsystem()
	{
		frontLeftController = new Talon(Constants.FRONT_LEFT_PORT);
		frontRightController = new Talon(Constants.FRONT_RIGHT_PORT);
		backLeftController = new Spark(Constants.BACK_LEFT_PORT);
		backRightController = new Talon(Constants.BACK_RIGHT_PORT);
	}
	
	/** Called once per cycle to update information */
	void periodic()
	{
		SmartDashboard.putString("Drive Mode", driveMode); //Since MECHANUM = false, !driveMode is true when in mechanum
	}
	
	@Schema(value = Scheme.LEFT_Y, desc = "drive / push in for slow")
	@Schema(Scheme.LEFT_X)
	@Schema(value = Scheme.RIGHT_Y, desc = "drive / push in for slow")
	@Schema(Scheme.RIGHT_X)
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
		lym = Rescaler.rescale(DEADZONE, 1.0, 0.0, 1.0, leftYMagnitude);
		rym = Rescaler.rescale(DEADZONE, 1.0, 0.0, 1.0, rightYMagnitude) * -1;
		lxm = Rescaler.rescale(DEADZONE, 1.0, 0.0, 1.0, leftXMagnitude) * -1;
		rxm = Rescaler.rescale(DEADZONE, 1.0, 0.0, 1.0, rightXMagnitude);
		
		/*slowState = false;
		if(slow)
		{
			slowState = !slowState;
		}*/
		if(driveMode.equals("Tank - Normal"))
		{
			//The front and back motors of both sides are set to the same values
			frontLeftController.set(lym  * (slow ? SLOWSPEED : 1));
			frontRightController.set(rym  * (slow ? SLOWSPEED : 1));
			backLeftController.set(lym  * (slow ? SLOWSPEED : 1));
			backRightController.set(rym * (slow ? SLOWSPEED : 1));
		}
		if(driveMode.equals("Tank - Mecanum"))
		{
			//Will be coded later
			/* The layout:
			 * \\  //
			 *   []
			 * //  \\
			 */
			
			frontLeftController.set((lym + lxm)  * (slow ? SLOWSPEED : 1));
			frontRightController.set((rym - rxm) * (slow ? SLOWSPEED : 1));
			backLeftController.set((lym - lxm)  * (slow ? SLOWSPEED : 1));
			backRightController.set((rym + rxm)  * (slow ? SLOWSPEED : 1));

			System.out.println("Left X : " + lxm);
			System.out.println("Right X : " + rxm);
		}
		if(driveMode.equals("Split Arcade"))
		{
				//Will be coded later
				/* The layout:
					* \\  //
					*   []+
					* //  \\
					*/
				
				frontLeftController.set((( -rxm) + lxm + lym)  * (slow ? SLOWSPEED : 1));
				frontRightController.set(((-rxm) + lxm - lym) * (slow ? SLOWSPEED : 1));
				backLeftController.set(  ( -rxm  - lxm +(lym))  * (slow ? SLOWSPEED : 1));
				backRightController.set( ((-rxm) - lxm - lym)  * (slow ? SLOWSPEED : 1));		
		}
		System.out.println("===========================================");
		System.out.println("Left Y : " + lym);
		System.out.println("Right Y : " + rym);
		System.out.println("===========================================");
		System.out.println("Front Left : " + frontLeftController.get());
		System.out.println("Back Left : " + backLeftController.get());
		System.out.println("Front Right : " + frontRightController.get());
		System.out.println("Back Right : " + backRightController.get());
	}
	
	
	@Schema(value = Scheme.BACK, desc = "tank mode")
	@Schema(value = Scheme.START, desc = "mechanum mode")
	/**
	 * This function controls the drive mode
	 * @param tankButton - The state of button to switch to tank mode
	 * @param mechButton - The state of button to switch to mechanum mode
	 */
	public void driveMode(boolean nextButton, boolean backButton)
	{
		//tankMode = (tankButton && !mechButton);
		if (nextButton && !nextHeld) //If button is pressed
		{
			if(driveMode.equals("Tank - Mecanum"))
					driveMode = "Split Arcade";
			else if(driveMode.equals("Tank - Normal"))
					driveMode = "Tank - Mecanum";
			else if(driveMode.equals("Split Arcade"))
				driveMode = "Tank - Normal";

		}
		else if (backButton && !prevHeld) //If button is pressed
		{
			if(driveMode.equals("Tank - Normal"))
				driveMode = "Split Arcade";
			else if(driveMode.equals("Tank - Mecanum"))
				driveMode = "Tank - Normal";
			else if(driveMode.equals("Split Arcade"))
				driveMode = "Tank - Mecanum";
		}
		nextHeld = nextButton;
		prevHeld = backButton;

		System.out.println("Drive Mode: " + driveMode);
	}
}
