package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.*;

public class get_ball_from_low_level extends Command {

    private final elevator_subsystem elevator_subsystem;
    private final big_ball_thingy_subsystem bigBallThingy;

    public get_ball_from_low_level(elevator_subsystem elevator, big_ball_thingy_subsystem bigBallThingy) {
        this.elevator_subsystem = elevator;
        this.bigBallThingy = bigBallThingy;
        addRequirements(elevator_subsystem, bigBallThingy);
    }

    @Override
    public void execute() {

        System.out.println("COMMAND HAS BEEN ACTIVATED");        
        elevator_subsystem.move_to_low_intake();
        bigBallThingy.move_arm_to_low_intake();
        // bigBallThingy.intake();
        
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
