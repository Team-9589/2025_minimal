package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.*;

public class intake extends Command {

    private final big_ball_thingy_subsystem bigBallThingy;

    public intake(big_ball_thingy_subsystem bigBallThingy) {
        this.bigBallThingy = bigBallThingy;
        addRequirements(bigBallThingy);
    }

    @Override
    public void execute() {

                
        bigBallThingy.intake();
        
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
