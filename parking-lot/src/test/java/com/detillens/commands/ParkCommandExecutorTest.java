package com.detillens.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.detillens.OutputPrinter;
import com.detillens.exception.NoFreeSlotAvailableException;
import com.detillens.model.Car;
import com.detillens.model.Command;
import com.detillens.service.ParkingLotService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

public class ParkCommandExecutorTest {
  private ParkingLotService parkingLotService;
  private OutputPrinter outputPrinter;
  private ParkCommandExecutor parkCommandExecutor;

  @Before
  public void setUp() throws Exception {
    parkingLotService = mock(ParkingLotService.class);
    outputPrinter = mock(OutputPrinter.class);
    parkCommandExecutor = new ParkCommandExecutor(parkingLotService, outputPrinter);
  }

  @Test
  public void testValidCommand() {
    assertTrue(parkCommandExecutor.validate(new Command("park test-command-number white")));
  }

  @Test
  public void testInvalidCommand() {
    assertFalse(parkCommandExecutor.validate(new Command("park")));
    assertFalse(parkCommandExecutor.validate(new Command("park test-car-number")));
    assertFalse(parkCommandExecutor.validate(new Command("park test-car-number white abcd")));
  }

  @Test
  public void testCommandExecutionWhenParkingSucceeds() {
    when(parkingLotService.park(anyListOf(Car.class))).thenReturn(1);
    parkCommandExecutor.execute(new Command("park test-car-number white"));
    final ArgumentCaptor<List> argument = ArgumentCaptor.forClass(List.class);
    verify(parkingLotService).park(argument.capture());
    List<Car> list = argument.getValue();
    assertEquals("test-car-number", list.get(0).getRegistrationNumber());
    assertEquals("white", list.get(0).getColor());

    verify(outputPrinter).printWithNewLine("Allocated slot number: 1");
  }

  @Test
  public void testCommandExecutionWhenParkingIsFull() {
    when(parkingLotService.park(anyListOf(Car.class))).thenThrow(new NoFreeSlotAvailableException());
    parkCommandExecutor.execute(new Command("park test-car-number white"));
    final ArgumentCaptor<List> argument = ArgumentCaptor.forClass(List.class);
    verify(parkingLotService).park(argument.capture());
    List<Car> list = argument.getValue();
    assertEquals("test-car-number", list.get(0).getRegistrationNumber());
    assertEquals("white", list.get(0).getColor());

    verify(outputPrinter).parkingLotFull();
  }
}
