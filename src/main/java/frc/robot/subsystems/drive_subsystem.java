package frc.robot.subsystems;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.constants.*;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class drive_subsystem extends SubsystemBase {

  SparkMax front_left;
  SparkMax back_left;
  SparkMax front_right;
  SparkMax back_right;

  SparkClosedLoopController back_left_closed_loop_controller;
  SparkClosedLoopController front_left_closed_loop_controller;
  SparkClosedLoopController front_right_closed_loop_controller;
  SparkClosedLoopController back_right_closed_loop_controller;

  DifferentialDrive drive;
  
  XboxController driver_controller;
  XboxController testing_controller;

  public drive_subsystem() {

    front_left = new SparkMax(drive_constants.front_left, MotorType.kBrushless);
    front_right = new SparkMax(drive_constants.front_right, MotorType.kBrushless);
    back_left = new SparkMax(drive_constants.back_left, MotorType.kBrushless);
    back_right = new SparkMax(drive_constants.back_right, MotorType.kBrushless);

    back_left_closed_loop_controller = back_left.getClosedLoopController();
    back_right_closed_loop_controller = back_right.getClosedLoopController();
    front_left_closed_loop_controller = front_left.getClosedLoopController();
    front_right_closed_loop_controller = front_right.getClosedLoopController();

    SparkMaxConfig front_left_config = new SparkMaxConfig(); 
    SparkMaxConfig front_right_config = new SparkMaxConfig(); 
    SparkMaxConfig back_left_config = new SparkMaxConfig(); 
    SparkMaxConfig back_right_config = new SparkMaxConfig(); 

    front_left_config.openLoopRampRate(0.5).idleMode(IdleMode.kBrake).inverted(false).closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder).maxMotion.allowedClosedLoopError(1).maxAcceleration(1000).maxVelocity(2000);
    back_left_config.openLoopRampRate(0.5).idleMode(IdleMode.kBrake).inverted(false).follow(front_left).closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder).maxMotion.maxAcceleration(1000).maxVelocity(2000);
    front_right_config.openLoopRampRate(0.5).idleMode(IdleMode.kBrake).inverted(false).closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder).maxMotion.allowedClosedLoopError(1).maxAcceleration(1000).maxVelocity(2000);
    back_right_config.openLoopRampRate(0.5).idleMode(IdleMode.kBrake).inverted(false).follow(front_right).closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder).maxMotion.maxAcceleration(1000).maxVelocity(2000);

    front_left.configure(front_right_config, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
    back_left.configure(back_left_config, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
    front_right.configure(front_right_config, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
    back_right.configure(back_right_config, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

    drive = new DifferentialDrive(front_left::set, front_right::set);

    driver_controller = new XboxController(0);
    testing_controller = new XboxController(2);
    
    
  }
  
  public void start_drive(){
    
    boolean shift = driver_controller.getRightBumperButton();
    
    if(!shift){

      drive.arcadeDrive(driver_controller.getRightX(), driver_controller.getLeftY(), true);
    
    } else {

      System.out.println("Shift is pressed");
      drive.arcadeDrive(driver_controller.getRightX() / 3.5, driver_controller.getLeftY() / 3, true);

    }

  }

  public void drive_straight(float distance){

    front_left_closed_loop_controller.setReference(auto_constants.drive_distance, ControlType.kPosition);
    front_right_closed_loop_controller.setReference(-auto_constants.drive_distance, ControlType.kPosition);
    back_left_closed_loop_controller.setReference(auto_constants.drive_distance, ControlType.kPosition);
    back_right_closed_loop_controller.setReference(auto_constants.drive_distance, ControlType.kPosition);

  }

  public void stop(){

    front_left_closed_loop_controller.setReference(0, ControlType.kVelocity);
    front_right_closed_loop_controller.setReference(0, ControlType.kVelocity);
    back_left_closed_loop_controller.setReference(0, ControlType.kVelocity);
    back_right_closed_loop_controller.setReference(0, ControlType.kVelocity);

  }

  public void live_and_learn(){

    front_left_closed_loop_controller.setReference(1000000, ControlType.kVelocity);
    front_right_closed_loop_controller.setReference(1000000, ControlType.kVelocity);
    back_left_closed_loop_controller.setReference(1000000, ControlType.kVelocity);
    back_right_closed_loop_controller.setReference(1000000, ControlType.kVelocity);

  }

  public void turn(float degrees){

    front_left_closed_loop_controller.setReference(degrees * (auto_constants.left_motor_turn_360 / 360), ControlType.kPosition);
    front_right_closed_loop_controller.setReference(degrees * (auto_constants.right_motor_turn_360 / 360), ControlType.kPosition);

  } 

  @Override
  public void periodic() {

    start_drive();

  }

}
