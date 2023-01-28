// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Commands.AutonomousDistanceBasedCommand;
import frc.robot.Commands.ChassisDefaultCommand;
import frc.robot.Commands.ClimbDefaultCommand;
import frc.robot.Commands.InTakeDefaultCommand;
import frc.robot.Commands.MagazineDefaultCommand;
import frc.robot.Commands.ShooterDefaultCommand;
import frc.robot.Subsystems.ChassisSubsystem;
import frc.robot.Subsystems.ClimbSubsystem;
import frc.robot.Subsystems.MagazineSubsystem;
import frc.robot.Subsystems.IntakeSubsystem;
import frc.robot.Subsystems.ShooterSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  public static final ShooterSubsystem SHOOTER_SUBSYSTEM = new ShooterSubsystem();
  public static final IntakeSubsystem INTAKE_SUBSYSTEM = new IntakeSubsystem();
  public static final Limelight lime = new Limelight();
  public static final ClimbSubsystem CLIMB_SUBSYSTEM = new ClimbSubsystem();
  public static final ChassisSubsystem chassis = new ChassisSubsystem();
  public static final MagazineSubsystem MAGAZINE_SUBSYSTEM = new MagazineSubsystem();
  public static final OperatorInput oi = new OperatorInput();

  public static boolean slowMode = false;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() { 
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    CommandScheduler.getInstance().setDefaultCommand(CLIMB_SUBSYSTEM, new ClimbDefaultCommand());
    CommandScheduler.getInstance().setDefaultCommand(chassis, new ChassisDefaultCommand());
    CommandScheduler.getInstance().setDefaultCommand(SHOOTER_SUBSYSTEM, new ShooterDefaultCommand());
    CommandScheduler.getInstance().setDefaultCommand(INTAKE_SUBSYSTEM, new InTakeDefaultCommand());
    CommandScheduler.getInstance().setDefaultCommand(MAGAZINE_SUBSYSTEM, new MagazineDefaultCommand());
    
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("TX", lime.getTX());
    SmartDashboard.putNumber("TY", lime.getTY());
    SmartDashboard.putNumber("Distance", lime.calcDistance());
    SmartDashboard.putNumber("ENCL", chassis.getLeftDistance());
    SmartDashboard.putNumber("ENCR", chassis.getRightDistance());
    SmartDashboard.putNumber("DistEnc", chassis.getAvgDistance());
    CommandScheduler.getInstance().run();
    
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    CommandScheduler.getInstance().cancelAll();
    CommandScheduler.getInstance().schedule(new AutonomousDistanceBasedCommand());

  
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    

    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    CommandScheduler.getInstance().cancelAll();

  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    
    if(oi.getBackButton()){
      //kill all commands
      CommandScheduler.getInstance().cancelAll();
    } 
    if(oi.rescheduleCommands()){
      CommandScheduler.getInstance().setDefaultCommand(chassis, new ChassisDefaultCommand());
      CommandScheduler.getInstance().setDefaultCommand(SHOOTER_SUBSYSTEM, new ShooterDefaultCommand());
      CommandScheduler.getInstance().setDefaultCommand(INTAKE_SUBSYSTEM, new InTakeDefaultCommand());
      CommandScheduler.getInstance().setDefaultCommand(MAGAZINE_SUBSYSTEM, new MagazineDefaultCommand());        
    }

    if(Robot.oi.switchMode()){
      slowMode = slowMode ? false : true;
    }
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
