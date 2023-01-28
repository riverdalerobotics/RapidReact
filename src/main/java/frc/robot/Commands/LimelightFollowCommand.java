// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
/*
DO NOT USE UNLESSS IN A WIDE OPEN SPACE AND YOU ARE SURE OF WHAT YOU ARE DOING

*/ 
// Some code is adapted from Limelight docs
//https://docs.limelightvision.io/en/latest/

public class LimelightFollowCommand extends CommandBase {
  /** Creates a new LimelightFollowCommand. */
  
  public LimelightFollowCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.chassis);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //code adapted from limelight docs
    final double Kp = 0.03;
    final double min = 0.3;
    double error = Robot.lime.getTX();
    final double KpDistance = 0.0035f; 
    final double distanceTarget = 700;
    double adjust = 0.0;
    
    double distance = Robot.lime.calcDistance();
    double distance_error = distanceTarget - distance;
    double driving_adjust = KpDistance * distance_error;
    if (distance <= distanceTarget+125 && distance >= distanceTarget-10) {
      Robot.SHOOTER_SUBSYSTEM.shoot(-1);
    } else {
      Robot.SHOOTER_SUBSYSTEM.shoot(0);
    }

    if (distance <= distanceTarget+70 && distance >= distanceTarget-10) {
      Robot.MAGAZINE_SUBSYSTEM.moveUp();
    } else {
      Robot.MAGAZINE_SUBSYSTEM.stop();
    }
    if (Robot.lime.getTX() > 1) {
      adjust = Kp * error - min;
    } 
    else if (Robot.lime.getTX() < 1) {
      adjust = Kp * error + min;
    }
    
    
    Robot.chassis.move(-driving_adjust, adjust);
    
      
    /*if(distance > distanceTarget+50){
      Robot.chassis.move(-driving_adjust, adjust);
      if(driving_adjust <= 0.3){
        Robot.chassis.move(-minSpeed, 0);
      }
    }
    else if(distance < distanceTarget+50 && driving_adjust < 0.2){
      if(distance>distanceTarget){
        Robot.chassis.move(-minSpeed, 0);
      }
      else{
        Robot.chassis.move(minSpeed,0);
      } 
    } */
      
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(Robot.lime.getTX()==0 && Robot.lime.getTY()==0){
      return true;
    }
    return false;
  }
}
