/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.SerialPort;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  public TalonFX left;
  public TalonFX slaveLeft;
  public TalonFX right;
  public TalonFX slaveRight;
  // public TalonFX saw;
  // public TalonFX sawSlave;
  public static Joystick controller = new Joystick(0);
  public static boolean toggleDS = true;

  public double frontThrottle;
  public double backThrottle;
  public double steer;
  public double leftPower;
  public double rightPower;
  public double throttle;
  public boolean damper = true;

  private RobotContainer m_robotContainer;


  public static MockDS ds;
  private boolean haveIStartedFakeDS = false;

  public AnalogPotentiometer pot = new AnalogPotentiometer(0);

  public SerialPort radio = new SerialPort(9600, Port.kUSB);

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    right = new TalonFX(1);
    slaveRight = new TalonFX(2);
    left = new TalonFX(3);
    slaveLeft = new TalonFX(4);
    slaveLeft.follow(left);
    slaveRight.follow(right);
    right.setInverted(true);
    left.setInverted(false);
    slaveLeft.setInverted(InvertType.FollowMaster);
    slaveRight.setInverted(InvertType.FollowMaster);
    // saw = new TalonFX(1);
    // sawSlave = new TalonFX(2);
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    ds = new MockDS();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    System.out.println(radio.readString());
    CommandScheduler.getInstance().run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    if(!haveIStartedFakeDS){
      ds.start();
      haveIStartedFakeDS = true;
    }
    //   haveIStartedFakeDS = true;
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    frontThrottle = controller.getRawAxis(2);
    backThrottle = controller.getRawAxis(3);
    steer = controller.getRawAxis(0);
    throttle = backThrottle - frontThrottle;
    leftPower = throttle + steer;
    rightPower = throttle - steer;
    if(controller.getRawButton(3)){
      damper = true;
    } else if(controller.getRawButton(4)){
      damper = false;
    }
    if(damper){
      left.set(ControlMode.PercentOutput, leftPower * 0.25);
      right.set(ControlMode.PercentOutput, rightPower * 0.25);
    } else if(!damper){
      left.set(ControlMode.PercentOutput, leftPower * 0.5);
      right.set(ControlMode.PercentOutput, rightPower * 0.5);
    }


    left.set(ControlMode.PercentOutput, deadband(pot.get()) * 0.5);
    right.set(ControlMode.PercentOutput, deadband(pot.get()) * 0.5);
    SmartDashboard.putNumber("pot", deadband(pot.get()));
    // double input = controller.getRawAxis(1);
    // input *= input;
    // saw.set(ControlMode.PercentOutput, input);
    // sawSlave.set(ControlMode.PercentOutput, input);
  }

  public double deadband(double input){
    return Math.abs(input) > 0.1 ? input : 0;
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  // public void toggle(){
  //   if(controller.getRawButton(1)){
  //     toggleDS = !toggleDS;
  //   }
  // }
}
