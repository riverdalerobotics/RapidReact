// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;



import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.motorcontrol.PWMTalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimbSubsystem extends SubsystemBase {
  /** Creates a new ClimbSubsystem. */
  DoubleSolenoid climbPistons;
  PWMTalonSRX winch;
  
  Value state;
  public ClimbSubsystem() {
    climbPistons = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 4, 5);
    state = Value.kOff;
    winch = new PWMTalonSRX(2) ;
    
  }
  public void toggle(){
    state = (state == Value.kForward) ? Value.kReverse : Value.kForward; 
    climbPistons.set(state);
    
  }
  public void turnOff(){
    state = Value.kReverse;
    climbPistons.set(state);

  }
  private void setWinchMotor(double speed) {
    winch.set(speed);
    
  }
  public void winch(){
    setWinchMotor(-1);
  }
  public void slowWinch(){
    setWinchMotor(-0.3);
  }
  public void unwinch(){
    setWinchMotor(0.3);
  }
  public void stop(){
    setWinchMotor(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
