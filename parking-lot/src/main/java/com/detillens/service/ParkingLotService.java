package com.detillens.service;

import com.detillens.exception.ParkingLotException;
import com.detillens.model.Car;
import com.detillens.model.ParkingLot;
import com.detillens.model.Slot;
import com.detillens.model.parking.strategy.ParkingStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service for enable the functioning of a parking lot. This will have all the business logic of
 * how the parking service will operate.
 */
public class ParkingLotService {
  private ParkingLot parkingLot;
  private ParkingStrategy parkingStrategy;

  /**
   * Allots a parking lot into the parking service. Throwns {@link ParkingLotException} if there is
   * already a parking lot allotted to the service previously.
   *
   * @param parkingLot Parking lot to be allotted.
   * @param parkingStrategy Strategy to be used while parking.
   */
  public void createParkingLot(final ParkingLot parkingLot, final ParkingStrategy parkingStrategy) {
    if (this.parkingLot != null) {
      throw new ParkingLotException("Parking lot already exists.");
    }
    this.parkingLot = parkingLot;
    this.parkingStrategy = parkingStrategy;
    for (int i = 1; i <= parkingLot.getCapacity(); i++) {
      parkingStrategy.addSlot(i);
    }
  }

  /**
   * Parks a {@link Car} into the parking lot. {@link ParkingStrategy} is used to decide the slot
   * number and then the car is parked into the {@link ParkingLot} into that slot number.
   *
   * @param car Car to be parked.
   * @return Slot number in which the car is parked.
   */
  public Integer park(final List<Car> cars) {
    Integer nextFreeSlot = null;
    validateParkingLotExists();
    for(Car car : cars) {
      nextFreeSlot = parkingStrategy.getNextSlot();
      parkingLot.park(car, nextFreeSlot);
      parkingStrategy.removeSlot(nextFreeSlot);
    }

    return nextFreeSlot;
  }

  /**
   * Unparks a car from a slot. Freed slot number is given back to the parking strategy so that it
   * becomes available for future parkings.
   *
   * @param slotNumber Slot number to be freed.
   */
  public int makeSlotFree(final Integer slotNumber) {
    validateParkingLotExists();
    long totalTimeInMinutes = parkingLot.makeSlotFree(slotNumber);
    parkingStrategy.addSlot(slotNumber);
    return calculateCharges(totalTimeInMinutes);
  }

  /**
   * Calculate parking charges when slot is getting free
   *
   * @param totalTimeInMinutes total time in minutes car being parked.
   */
  public int calculateCharges(final long totalTimeInMinutes) {
    int hours = (int) totalTimeInMinutes / 60;
    double additionalMinutes = (double) totalTimeInMinutes % 60;
    return (additionalMinutes > 0 ? hours + 1 : hours) * 2;
  }

  /**
   * Gets the list of all the slots which are occupied.
   */
  public List<Slot> getOccupiedSlots() {
    validateParkingLotExists();
    final List<Slot> occupiedSlotsList = new ArrayList<>();
    final Map<Integer, Slot> allSlots = parkingLot.getSlots();

    for (int i = 1; i <= parkingLot.getCapacity(); i++) {
      if (allSlots.containsKey(i)) {
        final Slot slot = allSlots.get(i);
        if (!slot.isSlotFree()) {
          occupiedSlotsList.add(slot);
        }
      }
    }

    return occupiedSlotsList;
  }

  /**
   * Helper method to validate whether the parking lot exists or not. This is used to validate the
   * existence of parking lot before doing any operation on it.
   */
  private void validateParkingLotExists() {
    if (parkingLot == null) {
      throw new ParkingLotException("Parking lot does not exists to park.");
    }
  }

}
