// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class Shoot extends CommandBase {
  /** Creates a new Shoot. */
  long startTime;
  long time;
  double maxSpeed = 5680;
  public Shoot() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.MAGAZINE_SUBSYSTEM);
    addRequirements(Robot.SHOOTER_SUBSYSTEM);
    startTime = 0;
    time = 0;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTime = System.currentTimeMillis();
    time = System.currentTimeMillis();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.SHOOTER_SUBSYSTEM.shoot(-1);
    SmartDashboard.putNumber("velocity", Robot.SHOOTER_SUBSYSTEM.getShooterVelocity());
    if(Math.abs(Robot.SHOOTER_SUBSYSTEM.getShooterVelocity()) >= maxSpeed){
      Robot.MAGAZINE_SUBSYSTEM.moveUp();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.SHOOTER_SUBSYSTEM.shoot(0);
    Robot.MAGAZINE_SUBSYSTEM.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
} 