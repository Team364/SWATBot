package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    private boolean waitingOnMidJoint;
    private boolean waitingOnLowJoint;

    private enum ControlStates {
        notCalibrated, manual
    }
    private ControlStates controlState;

    public Arm(Joystick controller){
        lowJointFX = new TalonFX(Constants.Arm.lowJointID);
        midJointFX = new TalonFX(Constants.Arm.midJointID);
        this.controller = controller;
        controlState = ControlStates.notCalibrated;
        configMidJoint();
        configLowJoint();
        resetInit();
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

    private void resetInit(){
        waitingOnMidJoint = false;
        waitingOnLowJoint = false;
    }

    public double getMidAngle(){
        return falconToRadians(midJointFX.getSelectedSensorPosition());
    }

    public double getLowAngle(){
        return falconToRadians(lowJointFX.getSelectedSensorPosition());
    }

    private boolean resetMidJoint(){
        if(midJointFX.getSupplyCurrent() < 6){
            midJointFX.set(ControlMode.PercentOutput, -0.2);
            return false;
        } else{
            midJointFX.set(ControlMode.PercentOutput, 0);
            midJointFX.setSelectedSensorPosition(0);
            midJointFX.configForwardSoftLimitEnable(true, Constants.ctreTimeout);
            midJointFX.configReverseSoftLimitEnable(true, Constants.ctreTimeout);
            return true;
        }
    }

    private boolean resetLowJoint(){
        if(lowJointFX.getSupplyCurrent() < 6){
            lowJointFX.set(ControlMode.PercentOutput, -0.2);
            return false;
        } else{
            lowJointFX.set(ControlMode.PercentOutput, 0);
            lowJointFX.setSelectedSensorPosition(0);
            lowJointFX.configForwardSoftLimitEnable(true, Constants.ctreTimeout);
            lowJointFX.configReverseSoftLimitEnable(true, Constants.ctreTimeout);
            return true;
        }
    }

    @Override
    public void periodic(){

        switch(controlState){
            case notCalibrated:
                if(waitingOnMidJoint && resetMidJoint()){
                    controlState = ControlStates.manual;
                }else if(waitingOnLowJoint && resetLowJoint()){    
                    controlState = ControlStates.manual;
                }else{
                    boolean mid = resetMidJoint();
                    boolean low = resetLowJoint();
                    if(mid && low){
                        controlState = ControlStates.manual;
                    }else if(mid){
                        waitingOnLowJoint = true;
                    }else if(low){
                        waitingOnMidJoint = true;
                    }
                }
                break;
            case manual:
                if(controller.getRawButton(0)){
                    resetInit();
                    controlState = ControlStates.notCalibrated;
                    break;
                }
                double dampen = 0.05;
                double c_x = controller.getRawAxis(0);
                double c_y = -controller.getRawAxis(1);
                c_x = Math.abs(c_x) > 0.05 ? c_x*dampen : 0;
                c_y = Math.abs(c_y) > 0.05 ? c_y*dampen : 0;
                x += c_x;
                y += c_y;
                if(Math.sqrt(x*x + y*y) >= maxHeight){  
                    x -= c_x;
                    y -= c_y;
                }


                midAngle = Math.acos((x*x + y*y - lowArmLength*lowArmLength - midArmLength*midArmLength)/(2*lowArmLength*midArmLength));
                lowAngle = Math.atan2(y,x)-Math.atan((midArmLength*Math.sin(midAngle))/(lowArmLength + midArmLength*Math.cos(midAngle)));
                //midJointFX.set(ControlMode.Position, radiansToFalcon(midAngle)*120.0);
                //lowJointFX.set(ControlMode.Position, radiansToFalcon(lowAngle)*120.0);
                break;
        }
        
        SmartDashboard.putNumber("target ", midAngle );
        SmartDashboard.putNumber("x ", x);
        SmartDashboard.putNumber("y ", y);
        SmartDashboard.putNumber("midAngle ", Math.toDegrees(midAngle));
        SmartDashboard.putNumber("lowAngle ", Math.toDegrees(lowAngle));
        SmartDashboard.putNumber("position ", (falconToRadians(midJointFX.getSelectedSensorPosition())));


    }


    public double falconToRadians(double units){
        return units * ((2*Math.PI) / 2048.0);
    }

    public double radiansToFalcon(double units){
        return units * (2048.0 / (2*Math.PI));
    }
    
}
