package org.usfirst.frc.team2976.robot.commands;
import org.usfirst.frc.team2976.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *@author NeilHazra
 */
public class LiftGear extends Command {
	double power = 0;
    public LiftGear(double power) {
    	requires(Robot.gear);
    	power = this.power;
    }
    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.gear.setGearPivot(power);
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }
    // Called once after isFinished returns true
    protected void end() {
    	Robot.gear.setGearPivot(0);
    }
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
