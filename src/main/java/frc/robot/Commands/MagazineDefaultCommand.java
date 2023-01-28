// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;



public class MagazineDefaultCommand extends CommandBase {
  /** Creates a new magazineDefaultCommand. */
  
  public MagazineDefaultCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.MAGAZINE_SUBSYSTEM);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
    public void execute() {
      boolean intake = Robot.oi.magazineForward();
      boolean outtake = Robot.oi.reverseMagazine();
      if(intake || outtake){
        if(intake && outtake){
          Robot.MAGAZINE_SUBSYSTEM.stop();
        }
        else if (intake) {
          Robot.MAGAZINE_SUBSYSTEM.moveDown();
        }
        else{ 
          Robot.MAGAZINE_SUBSYSTEM.moveUp();
        }

      }
      else {
        Robot.MAGAZINE_SUBSYSTEM.stop();
      }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
   return false; 
  }
}
