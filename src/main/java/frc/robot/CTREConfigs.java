package frc.robot;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.ctre.phoenix.sensors.SensorTimeBase;

public final class CTREConfigs {

    public TalonFXConfiguration drivetrainFXConfig;

    public TalonFXConfiguration lowJointFXConfig;
    public TalonFXConfiguration midJointFXConfig;

    public TalonFXConfiguration sawFXConfig;

    public CTREConfigs(){

        drivetrainFXConfig = new TalonFXConfiguration();


        lowJointFXConfig = new TalonFXConfiguration();
        sawFXConfig = new TalonFXConfiguration();

        /* Drivetrain Motor Configuration */
        SupplyCurrentLimitConfiguration driveSupplyLimit = new SupplyCurrentLimitConfiguration(
            Constants.Drivetrain.motorEnableCurrentLimit, 
            Constants.Drivetrain.motorContinuousCurrentLimit, 
            Constants.Drivetrain.motorPeakCurrentLimit, 
            Constants.Drivetrain.motorPeakCurrentDuration);
        
        drivetrainFXConfig.supplyCurrLimit = driveSupplyLimit;


        /* Arm Motor Configuration */
        SupplyCurrentLimitConfiguration armSupplyLimit = new SupplyCurrentLimitConfiguration(
            Constants.Arm.motorEnableCurrentLimit, 
            Constants.Arm.motorContinuousCurrentLimit, 
            Constants.Arm.motorPeakCurrentLimit, 
            Constants.Arm.motorPeakCurrentDuration);        
        
        /* Mid joint Motor Configuration */
            midJointFXConfig.slot0.kP = Constants.Arm.kP;
            midJointFXConfig.slot0.kI = Constants.Arm.kI;
            midJointFXConfig.slot0.kD = Constants.Arm.kD;
            midJointFXConfig.slot0.kF = Constants.Arm.kF;
            midJointFXConfig.supplyCurrLimit = armSupplyLimit;
            midJointFXConfig.motionAcceleration = 0;
            midJointFXConfig.motionCruiseVelocity = 0;
            midJointFXConfig.peakOutputForward = Constants.Arm.midpeakOutputForward;
            midJointFXConfig.peakOutputReverse = Constants.Arm.midpeakOutputReverse;
            midJointFXConfig.forwardSoftLimitThreshold = Constants.Arm.lowfwdHardLimit;
            midJointFXConfig.reverseSoftLimitThreshold = Constants.Arm.lowrevHardLimit;
            midJointFXConfig.forwardSoftLimitEnable = false;
            midJointFXConfig.reverseSoftLimitEnable = false;

        /* Low Joint Motor Configuration */
            lowJointFXConfig.slot0.kP = Constants.Arm.kP;
            lowJointFXConfig.slot0.kI = Constants.Arm.kI;
            lowJointFXConfig.slot0.kD = Constants.Arm.kD;
            lowJointFXConfig.slot0.kF = Constants.Arm.kF;
            lowJointFXConfig.supplyCurrLimit = armSupplyLimit;
            // lowJointFXConfig.peakOutputForward = Constants.Arm.peakOutputForward;
            // lowJointFXConfig.peakOutputReverse = Constants.Arm.peakOutputReverse;
            // lowJointFXConfig.forwardSoftLimitThreshold = Constants.Arm.fwdHardLimit;
            // lowJointFXConfig.reverseSoftLimitThreshold = Constants.Arm.revHardLimit;
            lowJointFXConfig.forwardSoftLimitEnable = false;
            lowJointFXConfig.reverseSoftLimitEnable = false;



        
        /* Saw Motor Configuration */
        SupplyCurrentLimitConfiguration sawSupplyLimit = new SupplyCurrentLimitConfiguration(
            Constants.Saw.motorEnableCurrentLimit, 
            Constants.Saw.motorContinuousCurrentLimit, 
            Constants.Saw.motorPeakCurrentLimit, 
            Constants.Saw.motorPeakCurrentDuration);        
        
        sawFXConfig.slot0.kP = Constants.Saw.kP;
        sawFXConfig.slot0.kI = Constants.Saw.kI;
        sawFXConfig.slot0.kD = Constants.Saw.kD;
        sawFXConfig.slot0.kF = Constants.Saw.kF;
        sawFXConfig.supplyCurrLimit = sawSupplyLimit;
        
    }
}