/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final int ctreTimeout = 30;

    public final static class Saw {
        public static final int motorID = 7;
        public static final int slaveMotorID = 8;
        public static final boolean motorInvert = false;
        public static final InvertType slaveMotorInvert = InvertType.OpposeMaster;
        public static final NeutralMode neutralMode = NeutralMode.Coast;
        
        public static final int slaveMotorStatus1 = 255;
        public static final int slaveMotorStatus2 = 255;
        
        public static final double gearRatio = 1;

        /* Saw PID Constants */
        public static final double kP = 0.6;
        public static final double kI = 0.0;
        public static final double kD = 0.0;
        public static final double kF = 0.0; 


        /* Saw Current Limiting */
        public static final int motorContinuousCurrentLimit = 35;
        public static final int motorPeakCurrentLimit = 60;
        public static final double motorPeakCurrentDuration = 0.1;
        public static final boolean motorEnableCurrentLimit = true;

    }

    public final static class Arm {
        public static final int lowJointID = 5;
        public static final int midJointID = 0;
        public static final boolean lowJointInvert = false;
        public static final boolean midJointInvert = false;
        public static final NeutralMode neutralMode = NeutralMode.Brake;

        /* Arm Physical Constrants*/
        public static final double lowJointMin = 0;
        public static final double lowJointMax = 0;
        public static final double midJointMin = 0;
        public static final double midJointMax = 0;
        public static final double maxHeight = 2;
        public static final double lowArmLength = 1;
        public static final double midArmLength = 1;

        public static final int motorStatus1 = 255;
        public static final int motorStatus2 = 255;
        
        public static final double gearRatio = 1;

        /* Arm PID Constants */
        public static final double kP = 0.1;
        public static final double kI = 0.0;
        public static final double kD = 0.0;
        public static final double kF = 0.0; 


        /* Arm Current Limiting */
        public static final int motorContinuousCurrentLimit = 35;
        public static final int motorPeakCurrentLimit = 60;
        public static final double motorPeakCurrentDuration = 0.1;
        public static final boolean motorEnableCurrentLimit = true;
		public static final double midpeakOutputForward = 0.2;
		public static final double midpeakOutputReverse = -0.2;
		public static final int midfwdHardLimit = 0;
        public static final int midrevHardLimit = 0;
        public static final double lowJointpeakOutputForward = 0;
		public static final double lowpeakOutputReverse = 0;
		public static final int lowfwdHardLimit = 0;
		public static final int lowrevHardLimit = 0;

        
    }



    public final static class Drivetrain {
        public static final int frontLeftID = 1;
        public static final int frontRightID = 2;
        public static final int backLeftID = 3;
        public static final int backRightID = 4;
        public static final boolean frontLeftInvert = false;
        public static final boolean frontRightInvert = false;
        public static final boolean backLeftInvert = false;
        public static final boolean backRightInvert = false;
        
        public static final int motorStatus1 = 255;
        public static final int motorStatus2 = 255;
        

        /* Drivetrain Current Limiting */
        public static final int motorContinuousCurrentLimit = 35;
        public static final int motorPeakCurrentLimit = 60;
        public static final double motorPeakCurrentDuration = 0.1;
        public static final boolean motorEnableCurrentLimit = true;
    }

}
