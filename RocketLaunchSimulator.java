import java.util.Scanner;

public class RocketLaunchSimulator {

    public static void main(String[] args) {
        Rocket rocket = new Rocket();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter command (start_checks, launch, fast_forward X, or exit):");
            String command = scanner.nextLine();
            if (command.startsWith("fast_forward")) {
                try {
                    int seconds = Integer.parseInt(command.split(" ")[1]);
                    rocket.fastForward(seconds);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid fast_forward command. Please enter a valid number.");
                }
            } else if (command.equals("start_checks")) {
                rocket.startChecks();
            } else if (command.equals("launch")) {
                rocket.launch();
            } else if (command.equals("exit")) {
                break;
            } else {
                System.out.println("Invalid command. Please enter start_checks, launch, fast_forward X, or exit.");
            }
        }

        scanner.close();
    }
}

class Rocket {
    private String stage = "Pre-Launch";
    private int fuel = 100;
    private int altitude = 0;
    private int speed = 0;
    private boolean checksCompleted = false;
    private boolean launched = false;

    public void startChecks() {
        if (stage.equals("Pre-Launch")) {
            checksCompleted = true;
            System.out.println("All systems are 'Go' for launch.");
        } else {
            System.out.println("Checks can only be started in the Pre-Launch stage.");
        }
    }

    public void launch() {
        if (checksCompleted) {
            launched = true;
            System.out.println("Launch initiated!");
            while (launched) {
                simulateSecond();
                try {
                    Thread.sleep(1000); // Simulate real-time by waiting 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Please complete pre-launch checks first.");
        }
    }

    public void fastForward(int seconds) {
        for (int i = 0; i < seconds; i++) {
            if (launched) {
                simulateSecond();
            } else {
                break;
            }
        }
    }

    private void simulateSecond() {
        // Check if there's still fuel
        if (fuel > 0) {
            // Transition from Pre-Launch to Stage 1
            if (stage.equals("Pre-Launch")) {
                stage = "Stage 1";
            } 
            // Transition from Stage 1 to Stage 2 if altitude reaches 100 km
            else if (stage.equals("Stage 1")  && altitude >= 100) {
                stage = "Stage 2";
                System.out.println("Stage 1 complete. Separating stage. Entering Stage 2.");
            }
    
            // Consume fuel, increase altitude, and increase speed
            fuel -= 10;
            altitude += 10;
            speed += 100;
    
            // Print current status
            System.out.println("Stage: " + stage + ", Fuel: " + fuel + "%, Altitude: " + altitude + " km, Speed: " + speed + " km/h");
    
            // Check if the rocket has achieved orbit
            if (altitude >= 200) {
                stage = "Orbit";
                launched = false;
                System.out.println("Orbit achieved! Mission Successful.");
            }
        } else {
            // If fuel is depleted, mark the mission as failed
            launched = false;
            System.out.println("Mission Failed due to insufficient fuel.");
        }
    }
    
}
