package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.*;



public class SawArm extends SubsystemBase{
    public TalonFX mSawArm;
    public TalonFX mSlave;
    public Joystick controller;
    public SawArm(Joystick controller){
        this.controller = controller;
        mSawArm = new TalonFX(Constants.SAWARM);
        mSlave = new TalonFX(Constants.SLAVE);
        //configure mSawArm
        mSawArm.configFactoryDefault();
        TalonFXConfiguration mSawArmConfiguration = new TalonFXConfiguration();
        mSlave.configFactoryDefault();
        TalonFXConfiguration mSlaveConfiguration = new TalonFXConfiguration();

        SupplyCurrentLimitConfiguration mSawArmSupplyLimit = new SupplyCurrentLimitConfiguration(Constants.SAWARMENABLECURRENTLIMIT, Constants.SAWARMCONTINUOUSCURRENTLIMIT, Constants.SAWARMPEAKCURRENT, Constants.SAWARMPEAKCURRENTDURATION);
        mSawArmConfiguration.supplyCurrLimit = mSawArmSupplyLimit;
        SupplyCurrentLimitConfiguration mSlaveSupplyLimit = new SupplyCurrentLimitConfiguration(Constants.SAWARMENABLECURRENTLIMIT, Constants.SAWARMCONTINUOUSCURRENTLIMIT, Constants.SAWARMPEAKCURRENT, Constants.SAWARMPEAKCURRENTDURATION);
        mSlaveConfiguration.supplyCurrLimit = mSlaveSupplyLimit;
    }

    public void setSpeed(double speed) {
        mSawArm.set(ControlMode.PercentOutput, speed);
        mSlave.set(ControlMode.PercentOutput, speed);
    }
}