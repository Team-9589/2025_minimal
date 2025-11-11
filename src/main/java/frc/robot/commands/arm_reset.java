package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.*;

public class arm_reset extends Command {

    private final big_ball_thingy_subsystem big_ball_thingy;

    public arm_reset(big_ball_thingy_subsystem big_ball_thingy_subsystem){

        this.big_ball_thingy = big_ball_thingy_subsystem;

        addRequirements(big_ball_thingy);

    }

    @Override
    public void execute() {

        System.out.println("COMMAND HAS BEEN ACTIVATED");
        big_ball_thingy.reset();

    }

    @Override
    public boolean isFinished(){

        return false;

    }
}