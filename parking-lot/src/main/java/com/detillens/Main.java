package com.detillens;

import com.detillens.commands.CommandExecutorFactory;
import com.detillens.exception.InvalidModeException;
import com.detillens.mode.FileMode;
import com.detillens.mode.InteractiveMode;
import com.detillens.service.ParkingLotService;
import java.io.IOException;

public class Main {
  public static void main(final String[] args) throws IOException {
    final OutputPrinter outputPrinter = new OutputPrinter();
    final ParkingLotService parkingLotService = new ParkingLotService();
    final CommandExecutorFactory commandExecutorFactory =
        new CommandExecutorFactory(parkingLotService);

    if (isInteractiveMode(args)) {
      new InteractiveMode(commandExecutorFactory, outputPrinter).process();
    } else if (isFileInputMode(args)) {
      new FileMode(commandExecutorFactory, outputPrinter, args[0]).process();
    } else {
      throw new InvalidModeException();
    }
  }

  /**
   * Checks whether the program is running using file input mode.
   *
   * @param args Command line arguments.
   * @return Boolean indicating whether in file input mode.
   */
  private static boolean isFileInputMode(final String[] args) {
    return args.length == 1;
  }

  /**
   * Checks whether the program is running using interactive shell mode.
   *
   * @param args Command line arguments.
   * @return Boolean indicating whether in interactive shell mode.
   */
  private static boolean isInteractiveMode(final String[] args) {
    return args.length == 0;
  }
}
