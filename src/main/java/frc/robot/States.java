package frc.robot;

public class States {
    


    public static enum DriveStates {
        openLoop, openLoopDampened
    }

    public static DriveStates driveState = DriveStates.openLoop;
}
