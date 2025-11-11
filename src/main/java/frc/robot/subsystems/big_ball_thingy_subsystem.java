package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import frc.robot.constants.*;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

@SuppressWarnings("unused")
public class big_ball_thingy_subsystem extends SubsystemBase {

  private static SparkMax arm_motor;
  private static SparkMax claw_motor;
  private static PIDController arm_pid_controller;
  private static PIDController claw_pid_controller;
  private static SparkClosedLoopController arm_closed_loop_controller;



  /** Creates a new big_ball_thingy_subsystem. */
  public big_ball_thingy_subsystem() {

    arm_motor = new SparkMax(ball_thingy_constants.arm_motor, MotorType.kBrushless);
    claw_motor = new SparkMax(ball_thingy_constants.claw_motor, MotorType.kBrushless);
    arm_pid_controller = new PIDController(elevator_constants.p, elevator_constants.i, elevator_constants.d);
    claw_pid_controller = new PIDController(elevator_constants.p, elevator_constants.i, elevator_constants.d);
    arm_closed_loop_controller = arm_motor.getClosedLoopController();
 
    SparkMaxConfig arm_config = new SparkMaxConfig();
    SparkMaxConfig claw_config = new SparkMaxConfig();

    arm_config.openLoopRampRate(0.5).idleMode(IdleMode.kBrake).inverted(false).closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder).maxMotion.maxAcceleration(1000).maxVelocity(2000).allowedClosedLoopError(1.0);
    claw_config.openLoopRampRate(0.5).idleMode(IdleMode.kBrake).inverted(false).closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder).maxMotion.maxAcceleration(1000).maxVelocity(2000).allowedClosedLoopError(1.0);

    arm_motor.configure(arm_config, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
    claw_motor.configure(claw_config, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

    System.out.println("P-Constant: ");

  }

  public void stop(){

    arm_motor.disable();
    claw_motor.disable();

  } 

  public void arm_test(){

    XboxController test_controller = new XboxController(2);
    boolean button3 = test_controller.getRawButton(1);
    boolean button4 = test_controller.getRawButton(2);

    if(button3){

      arm_motor.set(0.11);
      System.out.println("button3 pressed -- speed = " + arm_motor.get());

    } else if (button4){

      arm_motor.set(-0.11);
      System.out.println("button4 pressed -- speed = " + arm_motor.get());

    } else {

      arm_motor.set(0);

    }

  }

  public void intake(){

    stop();
    claw_motor.set(ball_thingy_constants.intake_speed);

  }

  public void move_arm_to_ground_intake(){

    stop();
    arm_closed_loop_controller.setReference(ball_thingy_constants.ground_intake, ControlType.kPosition, ClosedLoopSlot.kSlot0);

  }

  public void move_arm_to_score(){

    stop();
    arm_closed_loop_controller.setReference(ball_thingy_constants.score_tube_thing, ControlType.kPosition, ClosedLoopSlot.kSlot0);

  }

  public void reset(){

    stop();
    arm_closed_loop_controller.setReference(0, ControlType.kPosition, ClosedLoopSlot.kSlot0);

  }

  public void shoot(){

    stop();
    claw_motor.set(ball_thingy_constants.hole_speed);

  }

  public void score_in_net_thing(){

    stop();
    claw_motor.set(ball_thingy_constants.net_speed);
  }

  public void move_arm_to_high_intake(){

    stop();
    System.out.println("Moving arm to high intake");
    arm_closed_loop_controller.setReference(ball_thingy_constants.high_intake, ControlType.kPosition, ClosedLoopSlot.kSlot0);

  }

  public void move_arm_to_low_intake(){

    stop();
    arm_closed_loop_controller.setReference(ball_thingy_constants.low_intake, ControlType.kPosition, ClosedLoopSlot.kSlot0);

  }
  
  public void move_arm_to_hole_thing(){

    stop();
    arm_closed_loop_controller.setReference(ball_thingy_constants.hole_thing, ControlType.kPosition, ClosedLoopSlot.kSlot0);

  }

  public void move_arm_to_net_thing(){

    stop();
    arm_closed_loop_controller.setReference(ball_thingy_constants.net_thing, ControlType.kPosition, ClosedLoopSlot.kSlot0);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
