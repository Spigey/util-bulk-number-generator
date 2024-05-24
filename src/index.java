import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class index {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        File inputFile = null;
        long startTime = 0;
        long endTime = 0;
        while (inputFile == null || !inputFile.exists()) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.print("Choose a function:\n1. Generate random numbers\n2. Read numbers from file\n3. Count numbers from file\n");
            int choice = scanner.nextInt();
            scanner.nextLine();
            System.out.print("\033[H\033[2J");
            System.out.flush();
            if (choice == 1) {
                System.out.print("Enter the amount of random numbers to generate: ");
                int numNumbers = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Select a file to write to (including file extension): ");
                String filePath = scanner.nextLine();
                System.out.print("Enter the range of numbers to generate (e.g., 100 for numbers between 0 and 100): ");
                int range = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enable Super Fast Mode? (y/n): ");
                boolean superFastMode = scanner.nextLine().equalsIgnoreCase("y");
                if(superFastMode)
                    System.out.print("Enable output? (y/n): ");
                    boolean output = scanner.nextLine().equalsIgnoreCase("y");
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("Generating " + numNumbers + " random numbers...");
                try (FileWriter writer = new FileWriter(filePath)) {
                    StringBuilder all = new StringBuilder();
                    if(superFastMode){
                        if(output){
                            startTime = System.currentTimeMillis();
                            for (int i = 0; i < numNumbers; i++) {
                                all.append((int) (Math.random() * 100)).append('\n');
                                if (i % (int) (numNumbers * 0.0001) == 0 || i == numNumbers - 1) {
                                    System.out.printf("Progress: %.2f%%\r", (double) i / (numNumbers - 1) * 100);
                                }
                            }
                            writer.write(all.toString());
                            endTime = System.currentTimeMillis();
                        } else {
                            startTime = System.currentTimeMillis();
                            for (int i = 0; i < numNumbers; i++) {
                                all.append((int) (Math.random() * 100)).append('\n');
                            }
                            writer.write(all.toString());
                            endTime = System.currentTimeMillis();
                        }
                    }else {
                        startTime = System.currentTimeMillis();
                        for (int i = 0; i < numNumbers; i++) {
                        /* if ((i + 1) % 100 == 0) {
                            System.out.println("Generated " + (i + 1) + " out of " + numNumbers + " random numbers.");
                            // ▓▒▒▒▒▒▒▒▒▒ 10%
                            // ▓▓▓▓▒▒▒▒▒▒ 40%
                            // ▓▓▓▓▓▓▓▓▓▓ 100%
                        }*/
                            final int barLength = 30;
                            all.append((int) (Math.random() * range)).append("\n");
                            if ((i + 1) % Math.max(numNumbers / barLength, 1) == 0 || i == numNumbers - 1) {
                                // Thread.sleep(10);
                                double percent = (double) (i + 1) / numNumbers * 100;
                                int progress = (int) (percent / (100.0 / barLength));

                                System.out.print("\r[");
                                for (int j = 0; j < progress; j++) {
                                    System.out.print("▓"); // Filled block
                                }
                                for (int j = progress; j < barLength; j++) {
                                    System.out.print("▒"); // Empty block
                                }
                                System.out.printf("] %.1f%%", percent);
                            }
                        }
                        writer.write(all.toString());
                        System.out.println();
                        endTime = System.currentTimeMillis();
                    }
                    System.out.println("Finished generating numbers in " + (endTime - startTime) + "ms. Check " + filePath + " for the results.");
                    scanner.nextLine();
                } catch (Exception e) {
                    System.err.println("Error writing to file: " + e);
                    scanner.nextLine();
                }
            } else if (choice == 2) {
                System.out.print("Enter the file path (or type 'quit' to exit): ");
                String filePath = scanner.nextLine();
                if (filePath.equalsIgnoreCase("quit")) {
                    System.exit(0);
                }

                inputFile = new File(filePath);
                if (!inputFile.exists()) {
                    System.err.println("Error: File not found. Please try again.");
                }
                int amount = 0;
                try (Scanner fileScanner = new Scanner(inputFile)) {
                    startTime = System.currentTimeMillis();
                    while (fileScanner.hasNextLine()) {
                        try {
                            amount++;
                            System.out.println(fileScanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.err.println("Error parsing line: " + e.getMessage());
                            break;
                        }
                    }
                    endTime = System.currentTimeMillis();
                    System.out.println("Done! In total " + amount + " numbers were read from the file. (" + (endTime - startTime) + "ms)");
                } catch (IOException e) {
                    System.err.println("Error reading file: " + e.getMessage());
                }
            } else if (choice == 3){
                System.out.print("Enter the file path (or type 'quit' to exit): ");
                String filePath = scanner.nextLine();
                if (filePath.equalsIgnoreCase("quit")) {
                    System.exit(0);
                }
                inputFile = new File(filePath);
                if (!inputFile.exists()) {
                    System.err.println("Error: File not found. Please try again.");
                }
                int amount = 0;
                try (Scanner fileScanner = new Scanner(inputFile)) {
                    System.out.println("Scanning file...");
                    startTime = System.currentTimeMillis();
                    while (fileScanner.hasNextLine()) {
                        try {
                            amount++;
                            fileScanner.nextLine();
                        } catch (NumberFormatException e) {
                            System.err.println("Error parsing line: " + e.getMessage());
                            break;
                        }
                    }
                    endTime = System.currentTimeMillis();
                    System.out.println("Done! In total " + amount + " numbers were read from the file. (" + (endTime - startTime) + "ms)");
                } catch (IOException e) {
                    System.err.println("Error reading file: " + e.getMessage());
                }
            } else {
                System.err.println("Invalid choice. Please enter 1, 2 or 3.");
            }
        }
        /* Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", "start run"});
        Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", "exit"}); */
    }
}
