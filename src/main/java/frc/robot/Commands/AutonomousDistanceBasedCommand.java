// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class AutonomousDistanceBasedCommand extends CommandBase {
  /** Creates a new AutonomousDistanceBasedCommand. */
  final double distanceTarget = 35; // encoder units
  long timer;
  boolean foundTarget;
  double initPos;
  public AutonomousDistanceBasedCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.chassis);
    
    addRequirements(Robot.MAGAZINE_SUBSYSTEM);
    addRequirements(Robot.SHOOTER_SUBSYSTEM);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer = 0;
    foundTarget = false;
    initPos = Robot.chassis.getAvgDistance();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    
    if (Math.abs(Math.abs(Robot.chassis.getAvgDistance()) - Math.abs(initPos)) <= distanceTarget) {
      
      Robot.chassis.move(0.3, 0);
    } else {
      if(foundTarget){
        double hOffset = Robot.lime.getTX();
        double allowanceForError = 10; 
        if(Math.abs(hOffset)>=allowanceForError){
          if(hOffset < 0){
            Robot.SHOOTER_SUBSYSTEM.rotateRight();
            //Robot.chassis.move(0, -turnSpeed);
          } else if(hOffset >= 0){
            Robot.SHOOTER_SUBSYSTEM.rotateLeft();
            //Robot.chassis.move(0, turnSpeed);
          }
        }
        else{
          Robot.SHOOTER_SUBSYSTEM.rotateStop();
        }
        

        Robot.SHOOTER_SUBSYSTEM.shoot(-0.85);
        if (timer == 0) {
          timer = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - timer >= 3000) {
          Robot.MAGAZINE_SUBSYSTEM.moveUp();
        }
      
      } else{
        Robot.chassis.move(0, -0.2);
        if(Robot.lime.getTX()!=0.0 && Robot.lime.getTY()!=0.0) foundTarget = true;
      }

    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.MAGAZINE_SUBSYSTEM.stop();
    Robot.INTAKE_SUBSYSTEM.stop();
    Robot.SHOOTER_SUBSYSTEM.shoot(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (timer != 0) {
      if (System.currentTimeMillis() - timer >= 9000) {
        return true;
      }
    }

    return false;
  }
}