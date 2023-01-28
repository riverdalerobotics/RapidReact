// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;



import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class LimelightSafeAim extends CommandBase {
  /** Creates a new LimelightSafeAim. */
  double turnSpeed = 0.8;
  double allowanceForError = 7; // how many degrees off is it allowed to be from 0
  double hOffset;
  public LimelightSafeAim() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.chassis);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    hOffset = Robot.lime.getTX();
    if(Math.abs(hOffset) >= allowanceForError){
      if(hOffset < 0){
        Robot.SHOOTER_SUBSYSTEM.rotateRight();
        //Robot.chassis.move(0, -turnSpeed);
      } else {
        Robot.SHOOTER_SUBSYSTEM.rotateLeft();
        //Robot.chassis.move(0, turnSpeed);
      }
    } 
    else{
      Robot.SHOOTER_SUBSYSTEM.rotateStop();
    }
    Robot.SHOOTER_SUBSYSTEM.shoot(-1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(Robot.lime.getTX()== 0 && Robot.lime.getTY()==0) return true;
    if((hOffset <=allowanceForError && hOffset>=-allowanceForError)) return true;
    return false;
  }
}
