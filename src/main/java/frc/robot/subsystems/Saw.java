package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class Saw extends SubsystemBase {
    
    public TalonFX saw;
    public TalonFX sawSlave;

    public Saw(){
        saw = new TalonFX(Constants.Saw.motorID);
        sawSlave = new TalonFX(Constants.Saw.slaveMotorID);

        configMaster();
        configSlave();
    }
     
    private void configMaster(){
        saw.configFactoryDefault();
        saw.configAllSettings(Robot.ctreConfigs.sawFXConfig);
        saw.setNeutralMode(Constants.Saw.neutralMode);
        saw.setInverted(Constants.Saw.motorInvert);
    }

    private void configSlave(){
        sawSlave.configFactoryDefault();
        sawSlave.configAllSettings(Robot.ctreConfigs.sawFXConfig);
        sawSlave.setNeutralMode(Constants.Saw.neutralMode);
        sawSlave.setStatusFramePeriod(StatusFrame.Status_1_General, Constants.Saw.slaveMotorStatus1, Constants.ctreTimeout);
        sawSlave.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, Constants.Saw.slaveMotorStatus2, Constants.ctreTimeout);
        sawSlave.setInverted(Constants.Saw.slaveMotorInvert);
        sawSlave.follow(saw);
    }

    @Override
    public void periodic(){
        
    }

}
