// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;


import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.ADXL362;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class ChassisSubsystem extends SubsystemBase {
  /** Creates a new ChassisSubsystem. */
  
  WPI_TalonSRX leftLead;
  WPI_TalonSRX leftMid;
  CANSparkMax leftFollow;
  //WPI_TalonSRX leftFollow;
  //WPI_TalonSRX rightLead;
  CANSparkMax rightLead;
  WPI_TalonSRX rightMid;
  WPI_TalonSRX rightFollow;
  
  ADXRS450_Gyro gyro;
  ADXL362 accelerometer;
  

  MotorControllerGroup left;
  MotorControllerGroup right;

  DifferentialDrive drive;

  RelativeEncoder leftEnc;
  RelativeEncoder rightEnc;
  
  public ChassisSubsystem() {
    // 5 50
    leftLead = new WPI_TalonSRX(RobotMap.CHASSIS_LEFT_FRONT);
    leftMid = new WPI_TalonSRX(RobotMap.CHASSIS_LEFT_MIDDLE);
    leftFollow = new CANSparkMax(RobotMap.CHASSIS_LEFT_BACK, MotorType.kBrushless);
    
    rightLead = new CANSparkMax(RobotMap.CHASSIS_RIGHT_FRONT, MotorType.kBrushless);
    rightMid = new WPI_TalonSRX(RobotMap.CHASSIS_RIGHT_MIDDLE);
    rightFollow = new WPI_TalonSRX(RobotMap.CHASSIS_RIGHT_BACK);
    left = new MotorControllerGroup(leftLead, leftMid, leftFollow);
    right = new MotorControllerGroup(rightLead, rightMid, rightFollow);
    
    right.setInverted(true);

    drive = new DifferentialDrive(left, right);
    
    gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
    accelerometer = new ADXL362(SPI.Port.kMXP, Accelerometer.Range.k8G);

    leftEnc = leftFollow.getEncoder();
    rightEnc = rightLead.getEncoder();
    
    
  }
  public void calibrateGyro(){
    gyro.calibrate();
  }
  public double getAccelerationX(){
    return accelerometer.getX();
  }
  public double getAccelerationY(){
    return accelerometer.getY();
  }
  public double getAccelerationZ(){
    return accelerometer.getZ();
  }
  public double getDisplacement(double acceleration, double v1, double deltatime){
    return (v1*deltatime) + 0.5*acceleration*(Math.pow(deltatime, 2));
  }
  public double updatePrevVelocity(double acceleration, double time, double v1){
    return v1 + acceleration*time;
  }

  public double getAngle(){
    return gyro.getAngle();
  }
  public void move(double speed, double turn){
    //drive.arcadeDrive(speed, turn);
    arcade_drive(speed, turn);
  }
  public void setRight(double speed){
    right.set(speed);
  }
  public void setLeft(double speed){
    left.set(speed);
  }

  public double getAvgDistance(){
    return (Math.abs(rightEnc.getPosition()) + Math.abs(leftEnc.getPosition()))/2;
  }
  public double getRightDistance(){
    return rightEnc.getPosition();
  } 
  public double getLeftDistance(){
    return leftEnc.getPosition();
  }  

  public void arcade_drive(double speed, double turn) {
    //Adapted from Sean Sun's Frc Zero to Autonomous YT series
    //https://www.youtube.com/watch?v=ihO-mw_4Qpo
    //double POWERRCTRL = 0.6 - (speed*0.3); //right side runs twice as fast as left side when told to run at same "speed"
    double POWERRCTRL = 0.6;
    double lPower = speed + turn;
    double rPower = speed - turn;
    rPower *= POWERRCTRL;
    
    if(Math.abs(lPower) < 0.07 && Math.abs(rPower) < 0.07){
      left.set(0);
      right.set(0);
    }
    else{
      left.set(lPower);
      right.set(rPower);

    }
    SmartDashboard.putNumber("OI speed", speed);
    SmartDashboard.putNumber("OI turn", turn);
    SmartDashboard.putNumber("Lp", lPower);
    SmartDashboard.putNumber("Rp", rPower);
    SmartDashboard.putNumber("right velo", rightEnc.getVelocity());
    SmartDashboard.putNumber("left velo", leftEnc.getVelocity());

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
