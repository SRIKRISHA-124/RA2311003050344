# Vehicle Maintenance Scheduler

## Overview
This project solves the Vehicle Maintenance Scheduler problem using a knapsack-style optimization.
- Depot API provides MechanicHours.
- Vehicles API provides tasks with Duration and Impact.
- Scheduler selects tasks to maximize Impact within available MechanicHours.

## Dry Run Example
- DepotID: 1 → MechanicHours = 60
- Tasks fetched:
  - TaskID: 72a91bc4 → Duration 5, Impact 9
  - TaskID: 8a7ff5b1 → Duration 6, Impact 10
- Selected Tasks: [72a91bc4, 8a7ff5b1]
- Total Duration = 11 (≤ 60)
- Total Impact = 19

## Logging
- All API calls and scheduler logic are logged via custom LoggingMiddleware.
- No console.log or inbuilt loggers used.
