package frc.robot;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.*;
import frc.robot.commands.*;

public class robot_container {

  private final drive_subsystem drive;
  private final elevator_subsystem elevator;
  private final big_ball_thingy_subsystem ball_thing_subsystem;

  private final CommandXboxController driver_controller = new CommandXboxController(0);
  private final CommandXboxController operator_controller = new CommandXboxController(1);

  public robot_container() {

    this.drive = new drive_subsystem();
    this.ball_thing_subsystem = new big_ball_thingy_subsystem();
    this.elevator = new elevator_subsystem(); 

    configure_bindings();
    autos.AutoModeSelector(autos.get_auto_chooser(), drive, elevator, ball_thing_subsystem);

  }

  public void configure_bindings() {

    System.out.println("CONFIGURING BINDINGS");

    Trigger left_bumper = new Trigger(operator_controller.leftBumper());
    Trigger left_trigger = new Trigger(driver_controller.leftTrigger());
    Trigger right_trigger = new Trigger(driver_controller.rightTrigger());
    Trigger a = new Trigger(driver_controller.a());
    Trigger b = new Trigger(driver_controller.b());
    Trigger x = new Trigger(driver_controller.x());
    Trigger y = new Trigger(driver_controller.y());
    Trigger right_joystick_push = new Trigger(driver_controller.rightStick());
    Trigger left_joystick_push = new Trigger(driver_controller.leftStick());
    Trigger pov_up = new Trigger(driver_controller.povUp());

    Trigger driver_a = new Trigger(driver_controller.a());
    Trigger driver_left_bumper = new Trigger(driver_controller.leftBumper());
    
    pov_up.onTrue(new arm_reset(ball_thing_subsystem));
    x.whileTrue(new intake(ball_thing_subsystem).andThen(new stop_all(drive, elevator, ball_thing_subsystem)));
    y.whileTrue(new shoot(ball_thing_subsystem));
    left_bumper.onTrue(new stop_all(drive, elevator, ball_thing_subsystem));
    left_trigger.onTrue(new put_ball_in_hole_thingy(elevator, ball_thing_subsystem).withTimeout(1).andThen(new stop_all(drive, elevator, ball_thing_subsystem)));
    right_trigger.onTrue(new lebron(elevator, ball_thing_subsystem).withTimeout(1).andThen(new stop_all(drive, elevator, ball_thing_subsystem)));
    a.onTrue(new get_ball_from_low_level(elevator, ball_thing_subsystem).withTimeout(1).andThen(new stop_all(drive, elevator, ball_thing_subsystem)));
    b.onTrue(new get_ball_from_high_level(elevator, ball_thing_subsystem).withTimeout(1).andThen(new stop_all(drive, elevator, ball_thing_subsystem)));
    right_joystick_push.onTrue(new get_ball_from_ground(elevator, ball_thing_subsystem).withTimeout(1).andThen(new stop_all(drive, elevator, ball_thing_subsystem)));
    left_joystick_push.onTrue(new reset(elevator, ball_thing_subsystem));

    driver_left_bumper.onTrue(new stop_all(drive, elevator, ball_thing_subsystem));
    driver_a.whileTrue(new live_and_learn(drive));

  }

  public static SequentialCommandGroup get_autonomous_command() {

    return autos.get_auto_chooser().getSelected();

  }

}
