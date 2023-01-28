// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.RobotMap;

public class MagazineSubsystem extends SubsystemBase {
  /** Creates a new magazineSubsystem. */
  TalonSRX magazineMotor;

  public MagazineSubsystem() {
    magazineMotor = new TalonSRX(RobotMap.MAGAZINE_PORT);
    magazineMotor.setInverted(true);
  }

  private void setmagazineMotor(double speed) {
    magazineMotor.set(ControlMode.PercentOutput, speed);
  }

  public void moveDown() {
    setmagazineMotor(1);
  }

  public void moveUp() {
    setmagazineMotor(-1);
  }

  public void stop() {
    setmagazineMotor(0);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
