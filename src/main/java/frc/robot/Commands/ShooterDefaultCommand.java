// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ShooterDefaultCommand extends CommandBase {
  /** Creates a new ShooterDefaultCommand. */
  public ShooterDefaultCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.SHOOTER_SUBSYSTEM);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Robot.oi.getXButton()) {
      Robot.SHOOTER_SUBSYSTEM.shoot(-0.8);
    }
    else if(Robot.oi.getShootMax()){
      Robot.SHOOTER_SUBSYSTEM.shoot(-1);
    }
    else{
      Robot.SHOOTER_SUBSYSTEM.shoot(0);
    }

    System.out.println(Robot.SHOOTER_SUBSYSTEM.getTurretRotations());

      if(Robot.oi.getRightBumper() && Robot.oi.getLeftBumper()){
        if(Robot.SHOOTER_SUBSYSTEM.getTurretRotations() > 1000){
          Robot.SHOOTER_SUBSYSTEM.rotateRight();
        }
        else if(Robot.SHOOTER_SUBSYSTEM.getTurretRotations() < -1000){
          Robot.SHOOTER_SUBSYSTEM.rotateLeft();
        }
        else{
          Robot.SHOOTER_SUBSYSTEM.rotateStop();
        }
      }
      else if(Robot.oi.getRightBumper() && Robot.SHOOTER_SUBSYSTEM.getTurretRotations() < 325000){
        Robot.SHOOTER_SUBSYSTEM.rotateLeft();

      }
      else if(Robot.oi.getLeftBumper() && Robot.SHOOTER_SUBSYSTEM.getTurretRotations() > -325000){
        Robot.SHOOTER_SUBSYSTEM.rotateRight();

      }
      else{
        Robot.SHOOTER_SUBSYSTEM.rotateStop();
      }

      SmartDashboard.putNumber("velocity", Robot.SHOOTER_SUBSYSTEM.getShooterVelocity());
      SmartDashboard.putNumber("position", Robot.SHOOTER_SUBSYSTEM.getShooterEncoderPosition());

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
