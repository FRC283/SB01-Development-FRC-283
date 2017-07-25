package org.usfirst.frc.team283.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SB01 extends IterativeRobot 
{
	private static final double TRIGGER_THRESHOLD = 0.5;
	
	DriveSubsystem driveSubsystem;
	ElevatorSubsystem elevatorSubsystem;
	CannonSubsystem cannonSubsystem;
	
	Joystick logitech;
	
	@Override
	public void robotInit() 
	{
		DriveSubsystem driveSubsystem = new DriveSubsystem();
		ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem();
		CannonSubsystem cannonSubsystem = new CannonSubsystem();
		
		logitech = new Joystick(Constants.LOGITECH_PORT);
	}
	
	@Override
	public void teleopPeriodic()
	{
		driveSubsystem.drive(logitech.getRawAxis(Constants.LEFT_Y), logitech.getRawAxis(Constants.RIGHT_Y), logitech.getRawAxis(Constants.LEFT_X), logitech.getRawAxis(Constants.RIGHT_X), logitech.getRawButton(Constants.X));
		driveSubsystem.driveMode(logitech.getRawButton(Constants.BACK), logitech.getRawButton(Constants.START));
		elevatorSubsystem.raise(logitech.getRawButton(Constants.LEFT_BUMPER), (logitech.getRawAxis(Constants.LEFT_TRIGGER) >= TRIGGER_THRESHOLD));
		if (logitech.getRawButton(Constants.RIGHT_BUMPER)) 
			cannonSubsystem.fillInit();
		if (logitech.getRawAxis(Constants.RIGHT_TRIGGER) >= TRIGGER_THRESHOLD)
			cannonSubsystem.fireInit(logitech.getRawButton(Constants.Y));
	
		cannonSubsystem.fillPeriodic();
		cannonSubsystem.firePeriodic();
		
		System.out.println("==============================");
	}
}

