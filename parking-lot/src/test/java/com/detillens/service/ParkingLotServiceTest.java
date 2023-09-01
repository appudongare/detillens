package com.detillens.service;

import com.detillens.exception.ParkingLotException;
import com.detillens.model.Car;
import com.detillens.model.ParkingLot;
import com.detillens.model.Slot;
import com.detillens.model.parking.strategy.NaturalOrderingParkingStrategy;
import com.detillens.model.parking.strategy.ParkingStrategy;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ParkingLotServiceTest {
  private ParkingLotService parkingLotService = new ParkingLotService();
  private ParkingStrategy parkingStrategy;
  private ParkingLot parkingLot;

  @Before
  public void setUp() throws Exception {
    parkingStrategy = mock(ParkingStrategy.class);
    ;
    parkingLot = mock(ParkingLot.class);
    parkingLotService.createParkingLot(parkingLot, parkingStrategy);
  }

  @Test(expected = ParkingLotException.class)
  public void testCreatingParkingLotWhenAlreadyExists() {
    final ParkingLotService parkingLotService = new ParkingLotService();
    parkingLotService.createParkingLot(new ParkingLot(10), new NaturalOrderingParkingStrategy());
    parkingLotService.createParkingLot(new ParkingLot(20), new NaturalOrderingParkingStrategy());
  }

  @Test
  public void testSlotNumberIsRemovedFromStrategyAfterParking() {
    final Car testCar = new Car("test-car-no", "white");
    List<Car> cars = new ArrayList<>();
    cars.add(testCar);
    when(parkingStrategy.getNextSlot()).thenReturn(1);
    parkingLotService.park(cars);
    verify(parkingStrategy).removeSlot(1);
  }

  @Test
  public void testParkingIsDoneInTheParkingLot() {
    final Car testCar = new Car("test-car-no", "white");
    List<Car> cars = new ArrayList<>();
    cars.add(testCar);
    when(parkingStrategy.getNextSlot()).thenReturn(1);
    parkingLotService.park(cars);
    verify(parkingLot).park(testCar, 1);
  }

  @Test(expected = ParkingLotException.class)
  public void testParkingCarWithoutCreatingParkingLot() {
    final ParkingLotService parkingLotService = new ParkingLotService();
    final Car testCar = new Car("test-car-no", "white");
    List<Car> cars = new ArrayList<>();
    cars.add(testCar);
    parkingLotService.park(cars);
  }

  @Test
  public void testFreeingSlot() {
    parkingLotService.makeSlotFree(1);
    verify(parkingStrategy).addSlot(1);
    verify(parkingLot).makeSlotFree(1);
  }

  @Test(expected = ParkingLotException.class)
  public void testFreeingSlotWithoutCreatingParkingLot() {
    final ParkingLotService parkingLotService = new ParkingLotService();
    parkingLotService.makeSlotFree(1);
  }

  @Test
  public void testOccupiedSlots() {
    final Map<Integer, Slot> allSlots = new HashMap<>();
    final Slot slot1 = new Slot(1);
    final Slot slot2 = new Slot(2);
    slot2.assignCar(new Car("test-car-no1", "white"));
    final Slot slot3 = new Slot(3);
    final Slot slot4 = new Slot(4);
    slot4.assignCar(new Car("test-car-no2", "white"));

    allSlots.put(1, slot1);
    allSlots.put(2, slot2);
    allSlots.put(3, slot3);
    allSlots.put(4, slot4);

    when(parkingLot.getSlots()).thenReturn(allSlots);
    when(parkingLot.getCapacity()).thenReturn(10);

    final List<Slot> occupiedSlots = parkingLotService.getOccupiedSlots();
    assertEquals(2, occupiedSlots.size());
    assertEquals(slot2, occupiedSlots.get(0));
    assertEquals(slot4, occupiedSlots.get(1));
  }

}
