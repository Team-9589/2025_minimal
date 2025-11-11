package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.autos;

public class robot extends TimedRobot {

  private Command autonomousCommand;
  public robot_container container; 

  public robot() {
    
    container =  new robot_container();
  
  }

  @Override
  public void robotInit() {

    autos.Shuffleboard();
    autos.get_auto_chooser();

  }

  @Override
  public void robotPeriodic() {

    CommandScheduler.getInstance().run();
    
  }
  
  @Override
  public void disabledInit() {
    
    CommandScheduler.getInstance().cancelAll();
    
  }
  
  @Override
  public void disabledPeriodic() {}
  
  @Override
  public void autonomousInit() {
    autonomousCommand = robot_container.get_autonomous_command();
    if (autonomousCommand != null) {

      autonomousCommand.schedule();
      
    }
  }
  
  @Override
  public void autonomousPeriodic() {}
  
  @Override
  public void teleopInit() {
    
    if (autonomousCommand != null) {
      
      autonomousCommand.cancel();
      
    }
    
  }
  
  @Override
  public void teleopPeriodic() {
    
    // CommandScheduler.getInstance().cancelAll();
    
  }

  @Override
  public void testInit() {

    CommandScheduler.getInstance().cancelAll();

  }

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {

    autos.get_auto_chooser();

  }

  @Override
  public void simulationPeriodic() {}

}
