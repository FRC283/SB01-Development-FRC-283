package org.usfirst.frc.team283.robot;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

/**
 * The purpose of this class is to read annotations and generate a schema image based on those annotations
 * @author Benjamin
 */
public class JoystickSchema
{
	//Logitech Ports (Default)
		//Digital
			public static final int A = 0;
			public static final int B = 1;
			public static final int X = 2;
			public static final int Y = 3;
			public static final int LEFT_BUMPER = 4;
			public static final int RIGHT_BUMPER = 5;
			public static final int BACK = 6;
			public static final int START = 7;
			public static final int LEFT_STICK_BUTTON = 8;
			public static final int RIGHT_STICK_BUTTON = 9;
		//Analog
			public static final int LEFT_X = 0;
			public static final int LEFT_Y = 1;
			public static final int LEFT_TRIGGER = 2;
			public static final int RIGHT_TRIGGER = 3;
			public static final int RIGHT_X = 4;
			public static final int RIGHT_Y = 5;
	//Xbox Ports
		//Digital
			public static final int XBOX_A = 0;
			public static final int XBOX_B = 1;
			public static final int XBOX_X = 2;
			public static final int XBOX_Y = 3;
			public static final int XBOX_LEFT_BUMPER = 4;
			public static final int XBOX_RIGHT_BUMPER = 5;
			public static final int XBOX_BACK = 6;
			public static final int XBOX_START = 7;
			public static final int XBOX_LEFT_STICK_BUTTON = 8;
			public static final int XBOX_RIGHT_STICK_BUTTON = 9;
		//Analog
			public static final int XBOX_LEFT_X = 0;
			public static final int XBOX_LEFT_Y = 1;
			public static final int XBOX_LEFT_TRIGGER = 2;
			public static final int XBOX_RIGHT_TRIGGER = 3;
			public static final int XBOX_RIGHT_X = 4;
			public static final int XBOX_RIGHT_Y = 5;	
	//Physical Joystick
		//Digital
		//Analog
	
	@Retention(RetentionPolicy.RUNTIME)
	@interface Schema
	{
		/**
		 * Stores the button/axis that the function is assigned to 
		 * Possible Values: are above
		 */
		int value(); //Since this is named value, you can simply input it next to the annotation
		//String desc(); Possibily consider including this in a later version. Purpose would be to have better naming
	}
	
	/** Our stored references to all classes in this project. Holds a max of 20 for now */
	Class<?>[] classes = new Class[20];
	
	/**
	 * Takes a list of class names and fetches classes based on that
	 * @param classNames - List of class names
	 */
	JoystickSchema(String... classNames)
	{
		for (int i = 0; i < classNames.length; i++)
		{
			try 
			{
				classes[i] = Class.forName(classNames[i]);
			} 
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Takes a list of instantiated classes and stores them
	 * @deprecated
	 * @param classInstances - List of objects of desired classes
	 */
	JoystickSchema(Object... classInstances)
	{
		for (int i = 0; i < classInstances.length; i++)
		{
			classes[i] = classInstances[i].getClass();
		}
	}
	
	public static void main(String[] args)
	{
		JoystickSchema js = new JoystickSchema("org.usfirst.frc.team283.robot.CannonSubsystem", "org.usfirst.frc.team283.robot.DriveSubsystem", "org.usfirst.frc.team283.robot.ElevatorSubsystem");
		js.generate();
	}
	
	/**
	 * Creates an updated control schema/image based on stored class annotations
	 */
	public void generate()
	{
		for (Class<?> c : classes)
		{
			if (c !=null)
			{
				Method[] methods = c.getDeclaredMethods();
		
				for (Method m : methods)
				{
					Schema schemaAnnotation = m.getAnnotation(Schema.class);
					if (schemaAnnotation != null)
					{
						System.out.println("Function: " + m.getName() + ", Input Number: " + schemaAnnotation.value());
					}
				}
			}
			else
			{
				System.out.println("Class is null");
			}
		}
	}
	
}
