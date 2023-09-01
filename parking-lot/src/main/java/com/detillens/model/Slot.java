package com.detillens.model;

import com.detillens.exception.ParkingLotException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Slot {
  private Car parkedCar;
  private Integer slotNumber;
  private LocalDateTime assignedDateTime;

  public Slot(final Integer slotNumber) {
    this.slotNumber = slotNumber;
  }

  public Integer getSlotNumber() {
    return slotNumber;
  }

  public Car getParkedCar() {
    return parkedCar;
  }

  public boolean isSlotFree() {
    return parkedCar == null;
  }

  public void assignCar(Car car) {
    assignedDateTime = LocalDateTime.now();
    this.parkedCar = car;
  }

  public long unassignCar() {
    if (this.parkedCar != null) {
      long totalTimeInMinutes = assignedDateTime.until(LocalDateTime.now(), ChronoUnit.MINUTES);
      this.parkedCar = null;
      return totalTimeInMinutes;
    } else {
      throw new ParkingLotException("This slot is already empty");
    }
  }
}
