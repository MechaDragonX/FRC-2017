package org.usfirst.frc.team2976.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team2976.robot.RobotMap;

import com.ctre.CANTalon;
/**
 *@author NeilHazra
 */
public class Hopper extends Subsystem {
	private CANTalon hopperMotor;
	private DigitalInput beamBreak;
	private Servo hopperServoRight;
	private Servo hopperServoLeft;
    
	public Hopper()	{
    	hopperMotor = new CANTalon(RobotMap.hopperMotor);
    	beamBreak = new DigitalInput(RobotMap.limitSwitchHopper);
    	hopperServoRight = new Servo(RobotMap.hopperServoRight);
    	hopperServoLeft = new Servo(RobotMap.hopperServoLeft);
	}
    
    public void raiseHopper(double power)	{
    	hopperMotor.set(power);
    }
    
    public void lowerHopper(double power)	{
    	hopperMotor.set(power);
    }
    
    /**
     * @param scaledPosition is from 0 to 1
     */
    public void setHopperServos(double scaledPosition) {
    	hopperServoRight.set(scaledPosition);
        hopperServoLeft.set(1.0-scaledPosition);	
    	
    }
    
    public boolean isRaised() {
    	// return beamBreak.get(); //might need to negate this value depending on wiring of sensor
    	return false;
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}