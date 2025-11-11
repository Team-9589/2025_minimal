package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.*;

public class shoot extends Command {

    private final big_ball_thingy_subsystem bigBallThingy;

    public shoot(big_ball_thingy_subsystem bigBallThingy) {
        this.bigBallThingy = bigBallThingy;
        addRequirements(bigBallThingy);
    }

    @Override
    public void execute() {

        System.out.println("COMMAND HAS BEEN ACTIVATED");        
        bigBallThingy.shoot();
        
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
