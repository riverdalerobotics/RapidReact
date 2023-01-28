// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
//import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class ShooterSubsystem extends SubsystemBase {
  /** Creates a new Shooter. */
  
  //Instances for turret
  Encoder turretEncoder;
  WPI_TalonSRX turret;
  final double GEAR_ROTATIONS_PER_PULSE_TURRET = 1d/(1024*197.5d);
  final double TURRET_SPEED = 0.1; //Need to change this
  CANSparkMax shooterOne;
  CANSparkMax shooterTwo;
  
  
  RelativeEncoder shooterEncoderOne;
  RelativeEncoder shooterEncoderTwo;

  public ShooterSubsystem() {
    turret = new WPI_TalonSRX(RobotMap.TURRET_PORT);
    turretEncoder = new Encoder(0, 1);

    shooterOne = new CANSparkMax(RobotMap.SHOOTER_ONE, MotorType.kBrushless);
    shooterTwo = new CANSparkMax(RobotMap.SHOOTER_TWO, MotorType.kBrushless);
    shooterOne.setInverted(false);
    shooterEncoderOne = shooterOne.getEncoder();
    shooterEncoderTwo = shooterTwo.getEncoder();
  }
  
  public double getShooterEncoderPosition(){
    return shooterEncoderOne.getPosition();
  }
  public double getShooterVelocity(){
    return shooterEncoderOne.getVelocity();
  }
  public double getTurretRotations(){
    return turretEncoder.getDistance();
  }

  public void shoot(double speed){
    shooterOne.set(-speed);
    shooterTwo.set(-speed);
  }

  /*public double getShooterOneVelocity() {
    return shooterEncoderOne.getVelocity();
    
  }
  public double getShooterTwoVelocity() {
    return shooterEncoderTwo.getVelocity();
  }*/
  public void rotateRight(){
    turret.set(0.4);
  }

  public void rotateLeft(){
    turret.set(-0.4);
  }

  public void rotateStop(){
    turret.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
