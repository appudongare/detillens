package com.detillens.commands;

import com.detillens.OutputPrinter;
import com.detillens.exception.ParkingLotException;
import com.detillens.model.Command;
import com.detillens.service.ParkingLotService;
import com.detillens.validator.IntegerValidator;
import java.util.List;

/**
 * Executor to handle command of freeing of slot from a car.
 */
public class LeaveCommandExecutor extends CommandExecutor {
  public static String COMMAND_NAME = "leave";

  public LeaveCommandExecutor(final ParkingLotService parkingLotService,
      final OutputPrinter outputPrinter) {
    super(parkingLotService, outputPrinter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean validate(final Command command) {
    final List<String> params = command.getParams();
    if (params.size() != 1) {
      return false;
    }
    return IntegerValidator.isInteger(params.get(0));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(final Command command) {
    final int slotNum = Integer.parseInt(command.getParams().get(0));
    try {
      int charges = parkingLotService.makeSlotFree(slotNum);
      outputPrinter.printWithNewLine("Slot number " + slotNum + " is free & charges are " + charges);
    } catch(ParkingLotException e) {
      outputPrinter.printWithNewLine("Slot number " + slotNum + " is already empty");
    }

  }
}
