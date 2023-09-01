package com.detillens.commands;

import com.detillens.OutputPrinter;
import com.detillens.exception.NoFreeSlotAvailableException;
import com.detillens.model.Car;
import com.detillens.model.Command;
import com.detillens.service.ParkingLotService;

import java.util.ArrayList;
import java.util.List;

/**
 * Executor to handle command of parking a car into the parking lot. This outputs the alloted slot
 * in which the car is parked.
 */
public class ParkCommandExecutor extends CommandExecutor {
  public static String COMMAND_NAME = "park";

  public ParkCommandExecutor(
      final ParkingLotService parkingLotService, final OutputPrinter outputPrinter) {
    super(parkingLotService, outputPrinter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean validate(final Command command) {
    return command.getParams().size() >= 2 && command.getParams().size() % 2 == 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(final Command command) {
    final List<Car> cars = new ArrayList<>();
    for (int i = 0; i < command.getParams().size(); i++) {
      final Car car = new Car(command.getParams().get(i), command.getParams().get(i+1));
      cars.add(car);
      i++;
    }

    try {
      final Integer slot = parkingLotService.park(cars);
      outputPrinter.printWithNewLine("Allocated slot number: " + slot);
    } catch (NoFreeSlotAvailableException exception) {
      outputPrinter.parkingLotFull();
    }
  }
}
