package org.usfirst.frc.team2976.robot.subsystems;

import org.usfirst.frc.team2976.robot.Robot;
import org.usfirst.frc.team2976.robot.RobotMap;
import org.usfirst.frc.team2976.robot.commands.DriveWithJoystick;
import com.ctre.*;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import util.PIDMain;
import util.PIDSource;
import util.RPS;
/**
 *
 */
//this class works, lets not touch it
public class DriveTrain extends Subsystem {
	private SpeedController rightFrontMotor, leftFrontMotor;
	private SpeedController rightBackMotor, leftBackMotor;

	public RobotDrive m_drive;
	public PIDMain rotationLock;
	public PIDSource gyroSource;
	//public PIDMain XLock;
	//public PIDSource XEncoderSource;
	//public PIDMain YLock;
	//public PIDSource YEncoderSource;
	
	//public Encoder XEncoder,YEncoder;
	
	public boolean xBox;

	public DriveTrain() {
		xBox = true;
		//YEncoder = new Encoder(5,6,false, Encoder.EncodingType.k1X);
		//XEncoder = new Encoder(7,8,false, Encoder.EncodingType.k1X);
		
		//XEncoder.setDistancePerPulse(0.003875);
		//YEncoder.setDistancePerPulse(0.003875);
		Robot.rps.reset();
		Timer.delay(1); 
		
		gyroSource = new PIDSource() {
			public double getInput() {
				return getHeading();
			}
		};
	/*	XEncoderSource = new PIDSource()	{
			public double getInput(){
				return XEncoder.get(); //nothing fancy needed
			}
		};
	 */
	/*
		YEncoderSource = new PIDSource()	{
			public double getInput(){
				return YEncoder.get(); //nothing fancy needed
			}
		};
	*/
		rotationLock = new PIDMain(gyroSource, (int) getHeading(), 100, 0, 0, 0);	
		//rotationLock = new PIDMain(gyroSource, (int) getHeading(), 100, -0.014, -0.0003	, 0);	
		//rotationLock = new PIDMain(gyroSource, (int) getHeading(), 100, -0.017, -0.0006	, 0);	
		//rotationLock = new PIDMain(gyroSource, (int) getHeading(), 100, -0.017, -0.0014	, 0);	
		
		//XLock = new PIDMain(XEncoderSource, XEncoder.get(), 100, 0.001, 0.0001	, 0);	//TODO tune PID
		//YLock = new PIDMain(YEncoderSource, YEncoder.get(), 100, 0.001, 0.0001	, 0);	//TODO tune PID
		
		rightFrontMotor = new CANTalon(RobotMap.RightFrontDriveMotor);
		leftFrontMotor = new CANTalon(RobotMap.LeftFrontDriveMotor);
		rightBackMotor = new CANTalon(RobotMap.RightBackDriveMotor);
		leftBackMotor = new CANTalon(RobotMap.LeftBackDriveMotor);

		m_drive = new RobotDrive(leftFrontMotor, leftBackMotor, rightFrontMotor, rightBackMotor);
		m_drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
		m_drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick());
	}
/*
	public double getDistanceX()	{
		return XEncoder.getDistance();
	}
	public double getDistanceY()	{
		return YEncoder.getDistance();
	}
*/
	
	public void rotationLockDrive(double x, double y) {
		m_drive.mecanumDrive_Cartesian(x, y, rotationLock.getOutput(), 0);
	}
	public void xLockDrive(double y) {
	//	m_drive.mecanumDrive_Cartesian(XLock.getOutput(), y, rotationLock.getOutput(), 0);
	}
	public void yLockDrive(double x) {
	//	m_drive.mecanumDrive_Cartesian(x, YLock.getOutputb(), rotationLock.getOutput(), 0);
	}
	
	public double getHeading() {
		return Robot.rps.getAngle();
	}

	// Rounds numbers to 2 decimal places to make SmartDashboard nicer. Could
	// also be used to prevents OPs things
	public double round(double input) {
		double roundOff = Math.round(input * 100.0) / 100.0;
		return roundOff;
	}
	  public double driveCurve(double input, boolean invert, boolean slowMode){
	    	double slider = 1;	
	    	if (slowMode){
	    		slider = 0.4;
	    	}
	    	return driveCurve(input, invert, slider);
	}    
	 //Returns curved drive values with Slider (which indicates sensitivity)    
	public double driveCurve(double input, boolean invert, double slider) {
		double value = 0.0;
		slider = (slider + 1) / 2;
		//This code was written by Jasmine Cheng, please talk to her if you are concerned with redundancy, overcomplication, and nested if's
		//It works tho :)
		if (invert == false) {
			if (input > 0) {
				if (input < 0.1) {
					input = 0;
				} else {
					input = slider * ((0.09574 * Math.pow(10, input * 1.059)) - 0.09574);
				}
			} else {
				if (input > -0.1) {
					input = 0;
				} else {
					input = -1 * input;
					input = -slider * ((0.09574 * Math.pow(10, input * 1.059)) - 0.09574);
				}
			}
		} else {
			if (input > 0) {
				if (input < 0.1) {
					input = 0;
				} else {
					input = slider * ((0.09574 * Math.pow(10, input * 1.059)) - 0.09574);
				}
			} else {
				if (input > -0.1) {
					input = 0;
				} else {
					input = -1 * input;
					input = -slider * ((0.09574 * Math.pow(10, input * 1.059) - 0.09574));
				}
			}
		}
		value = input;
		return value;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param rotation
	 */
	public void drive(double x, double y, double rotation) {
		m_drive.mecanumDrive_Cartesian(x, y, rotation, 0);
	}
}
	