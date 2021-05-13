package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import static frc.robot.Constants.Arm.*;
import frc.robot.Robot;
import static frc.robot.States.*;

public class Arm extends SubsystemBase {
    public TalonFX lowJointFX;
    public TalonFX midJointFX;
    public Joystick controller;
    private double x = 0;
    private double y = 0;
    private double midAngle;
    private double lowAngle;
    private double robot_midAngle;
    private double robot_lowAngle;

    private enum ControlStates {
        inRange, outRange, still
    }
    private ControlStates controlState;

    public Arm(Joystick controller){
        lowJointFX = new TalonFX(Constants.Arm.lowJoinID);
        midJointFX = new TalonFX(Constants.Arm.lowJoinID);
        this.controller = controller;
        controlState = ControlStates.still;
        configMidJoint();

        configLowJoint();
    }

    private void configMidJoint(){
        midJointFX.configFactoryDefault();
        midJointFX.configAllSettings(Robot.ctreConfigs.midJointFXConfig);
        midJointFX.setInverted(Constants.Arm.midJointInvert);
        midJointFX.setNeutralMode(Constants.Arm.neutralMode);
        midJointFX.setSelectedSensorPosition(0);
    }
    private void configLowJoint(){
        lowJointFX.configFactoryDefault();
        lowJointFX.configAllSettings(Robot.ctreConfigs.lowJointFXConfig);
        lowJointFX.setInverted(Constants.Arm.lowJointInvert);
        lowJointFX.setNeutralMode(Constants.Arm.neutralMode);
        lowJointFX.setSelectedSensorPosition(0);
    }



    public double getLowAngle(){
        return 0;
    }
    public double getMidAngle(){
        return 0;
    }

    @Override
    public void periodic(){
        x += controller.getRawAxis(0);
        y += -controller.getRawAxis(1);
        if(Math.sqrt(x*x + y*y) >= maxHeight){
            x -= controller.getRawAxis(0);
            y -= controller.getRawAxis(1);
        }

        midAngle = Math.acos((x*x + y*y - lowArmLength*lowArmLength - midArmLength*midArmLength)/(2*lowArmLength*midArmLength));
        lowAngle = Math.atan2(y,x)-Math.atan((midArmLength*Math.sin(midAngle))/(lowArmLength + midArmLength*Math.cos(midAngle)));
        
        midAngle = midAngle >= midJointMax ? midJointMax : midAngle;
        midAngle = midAngle <= midJointMin ? midJointMin : midAngle;
        
        midJointFX.set(ControlMode.MotionMagic, radiansToFalcon(midAngle));
        lowJointFX.set(ControlMode.MotionMagic, radiansToFalcon(lowAngle));

        robot_midAngle = falconToRadians(midJointFX.getSelectedSensorPosition());
        robot_lowAngle = falconToRadians(lowJointFX.getSelectedSensorPosition());
    }


    public double falconToRadians(double units){
        return 0;
    }

    public double radiansToFalcon(double units){
        return 0;
    }
    
}
