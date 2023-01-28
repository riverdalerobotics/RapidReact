// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class AutoWithAccelerometer extends CommandBase {
  /** Creates a new AutoWithAccelerometer. */
  double time;
  double previousVelocity;
  double totalDistance;
  double distanceTarget = 3;
  double shootingTimer;
  public AutoWithAccelerometer() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.chassis);
    addRequirements(Robot.INTAKE_SUBSYSTEM);
    addRequirements(Robot.MAGAZINE_SUBSYSTEM);
    addRequirements(Robot.SHOOTER_SUBSYSTEM);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    time = System.currentTimeMillis();
    previousVelocity = 0;
    totalDistance = 0;
    shootingTimer = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double timeElapsed = (double) System.currentTimeMillis() - time;
    if(timeElapsed >= 500){
      double t = timeElapsed/1000;
      double accel = Robot.chassis.getAccelerationX()/9.8;
      totalDistance += Robot.chassis.getDisplacement(accel, previousVelocity, t);
      previousVelocity = Robot.chassis.updatePrevVelocity(accel, t, previousVelocity);
      time = System.currentTimeMillis();
    }
    if(totalDistance <= distanceTarget){
      Robot.chassis.move(-0.6, 0);
    }
    else{
      if(shootingTimer == 0) shootingTimer = System.currentTimeMillis();
      Robot.chassis.move(0, 0);
      if(System.currentTimeMillis() - shootingTimer <= 3000){
        Robot.SHOOTER_SUBSYSTEM.shoot(-1);
      }
      else{
        Robot.SHOOTER_SUBSYSTEM.shoot(-1);
        Robot.MAGAZINE_SUBSYSTEM.moveUp();
      }

    }
    


  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.chassis.move(0,0);
    Robot.SHOOTER_SUBSYSTEM.shoot(0);
    Robot.MAGAZINE_SUBSYSTEM.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return shootingTimer >= 6000;
  }
}
