# ChocAn System – Project 4 (Team 5)

A Java version of the Chocoholics Anonymous (ChocAn) data processing system.
It runs in the **command line**.
All data is **saved to text files**, so it is still there after you restart the program.

## Team

| Team member | What they built |
|---|---|
| Isaac Kim | Program start-up, login and role menus, the database with file saving, and the `Terminal` class that handles all input and output |
| Sarah Rechkemmer | Operator menu: add, edit, delete, suspend and reinstate members; add, edit and delete providers |
| Jackson Turner | Provider menu: validate member cards, bill ChocAn for a service, provider directory |
| Ross Craig | Manager menu: main accounting procedure, EFT file, summary, member and provider reports |

## What you need

- Java JDK 17 or newer (check with `java -version` and `javac -version`)

## How to compile and run

Open a terminal **in this folder** (the one with `src` in it).

**Compile:**

```
javac -d out src/*.java
```

**Run:**

```
java -cp out Main
```

On Windows you can also double-click `run.bat`, which compiles and runs the program in one step.

## Test logins

| Role | Number |
|---|---|
| Operator | `900000001` |
| Manager | `800000001` |
| Provider | `200000001` (Healthy Habits Clinic), `200000002` (Dr. Sam Patel), `200000003` (FitLife Gym) |

**Sample members:** `100000001` to `100000004` are Active. `100000005` (Emma Brown) is **Suspended**.

**Service codes:**

| Code | Service | Fee |
|---|---|---|
| 883948 | Aerobics Exercise Session | 50.00 |
| 123456 | Chocolate Addiction Counseling | 120.00 |
| 598470 | Dietitian Session | 75.00 |
| 234567 | Group Therapy Session | 60.00 |
| 345678 | Nutrition Workshop | 40.00 |
| 456789 | Wellness Check-up | 90.00 |

## Where files are saved

These folders are created next to `src` the first time they are needed:

| Folder | What is in it |
|---|---|
| `data/` | Members, providers, services and service records. The program loads these each time it starts. |
| `reports/` | Member, provider and summary reports, and the provider directory |
| `eft/` | The EFT (payment) file made by the main accounting procedure |

The first time the program runs it creates sample data, including a few services from this week so the reports are not empty.
**To start over with fresh sample data, delete the `data` folder.**

## Main classes

| Class | Job |
|---|---|
| `Main` | Starts the program |
| `MainMenu` | Login and opening the right menu |
| `Terminal` | All input and output |
| `ChocAnDatabase` | Stores everything and saves and loads the text files |
| `VerificationController` | Checks login numbers |
| `OperatorTerminal`, `MemberController`, `ProviderController` | Managing members and providers |
| `ProviderTerminal`, `BillServiceController`, `ProviderDirectory`, `ProviderDirectoryController` | Validating members, recording services, the directory |
| `ManagerTerminal`, `ReportController`, `AccountingController`, `Report` (+ `MemberReport`, `ProviderReport`, `SummaryReport`), `ReportDetails`, `EFTRecord` | Reports and accounting |
| `Person` (+ `Member`, `Provider`, `Operator`, `Manager`), `Service`, `ServiceRecord`, `DateHelper` | Data classes and date helpers |
