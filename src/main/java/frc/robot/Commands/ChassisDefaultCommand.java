// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ChassisDefaultCommand extends CommandBase {
  /** Creates a new ChassisDefaultCommand. */
  public ChassisDefaultCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.chassis); 
  }

  public double maxSpeed;

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (Robot.slowMode) {
      maxSpeed = 0.3;
    } else {
      maxSpeed = 0.6; 
    }
    double turnSpeed = Robot.oi.getTurn();
    double speed = Robot.oi.getSpeed();
    
    if (Math.abs(speed) <= maxSpeed) {
      Robot.chassis.move(speed, turnSpeed);

    } else {
      if (speed < 0) {
        Robot.chassis.move(-maxSpeed, turnSpeed);
      } else {
        Robot.chassis.move(maxSpeed, turnSpeed);
      }
    }
    SmartDashboard.putNumber("EncR", Robot.chassis.getRightDistance());
    SmartDashboard.putNumber("EncL", Robot.chassis.getLeftDistance());

    

    // Robot.chassis.move(Robot.oi.getSpeed(), Robot.oi.getTurn());

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
