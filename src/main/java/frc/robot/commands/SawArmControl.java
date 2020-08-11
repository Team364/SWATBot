package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.*;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SawArm;

public class SawArmControl extends CommandBase {

    private double input;

    private SawArm SawArm;

    /**
     * Driver control
     */
    public SawArmControl(SawArm SawArm) {
        this.SawArm = SawArm;
        addRequirements(SawArm);
    }

    @Override 
    public void execute(){
        input = SawArm.controller.getRawAxis(1);
        SawArm.setSpeed(input * input);
    }

}