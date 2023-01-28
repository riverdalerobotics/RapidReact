// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class pickUp extends CommandBase {
  /** Creates a new pickUp. */
  long timer = 0;

  public pickUp() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.chassis);
    addRequirements(Robot.INTAKE_SUBSYSTEM);
    addRequirements(Robot.MAGAZINE_SUBSYSTEM);
    addRequirements(Robot.SHOOTER_SUBSYSTEM);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
    //Robot.INTAKE_SUBSYSTEM.toggleOff();
    timer = System.currentTimeMillis();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    double hOffset = Robot.lime.getTX();
    double allowanceForError = 8; 
    if(!(hOffset <=allowanceForError && hOffset>=-allowanceForError)){
      if(hOffset < 0){
        Robot.SHOOTER_SUBSYSTEM.rotateRight();
        //Robot.chassis.move(0, -turnSpeed);
      } else {
        Robot.SHOOTER_SUBSYSTEM.rotateLeft();
        //Robot.chassis.move(0, turnSpeed);
      }
    }
    if(System.currentTimeMillis() - timer < 2000){
      Robot.chassis.move(-0.6, 0);
      //Robot.INTAKE_SUBSYSTEM.takeIn();
      //Robot.MAGAZINE_SUBSYSTEM.moveUp();
    }
    if(System.currentTimeMillis() - timer < 6000 && System.currentTimeMillis() - timer > 2000){
      Robot.chassis.move(0, 0);
      Robot.INTAKE_SUBSYSTEM.stop();
      Robot.MAGAZINE_SUBSYSTEM.stop();

      Robot.SHOOTER_SUBSYSTEM.shoot(-0.75);
    }
    if(System.currentTimeMillis() - timer > 6000){
      Robot.MAGAZINE_SUBSYSTEM.moveUp();
      Robot.SHOOTER_SUBSYSTEM.shoot(-0.75);
    }

    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.MAGAZINE_SUBSYSTEM.stop();
    Robot.SHOOTER_SUBSYSTEM.shoot(0);


  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(System.currentTimeMillis() - timer > 8000){
      return true;
    }
    return false;
  }
}
