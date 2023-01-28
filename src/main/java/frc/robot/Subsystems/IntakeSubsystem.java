// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new IntakeSubsystem. */
  CANSparkMax intake;
  DoubleSolenoid intakePiston;
  Value state;
  public IntakeSubsystem() {
    intake = new CANSparkMax(RobotMap.INTAKE_PORT, MotorType.kBrushless);
    intakePiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,6,7);
    state = Value.kOff;
    intakePiston.set(state);
  
  }
  public void toggleOn(){
    intakePiston.set(Value.kForward);
  }
  public void toggleOff(){
    intakePiston.set(Value.kReverse);

  }

  public void takeIn(){
    intake.set(0.5);
  }

  public void stop(){
    intake.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
