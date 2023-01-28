// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;



public class InTakeDefaultCommand extends CommandBase {
  /** Creates a new InTakeDefaultCommand. */
  public InTakeDefaultCommand() {
    addRequirements(Robot.INTAKE_SUBSYSTEM);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Robot.oi.getBButton()) {
      Robot.INTAKE_SUBSYSTEM.takeIn();
    }
    else{
      Robot.INTAKE_SUBSYSTEM.stop();
    }
    if(Robot.oi.getUp()){
      Robot.INTAKE_SUBSYSTEM.toggleOn();
    }
    else if(Robot.oi.getDown()){
      Robot.INTAKE_SUBSYSTEM.toggleOff();
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
