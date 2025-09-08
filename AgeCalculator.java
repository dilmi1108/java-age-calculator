import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class AgeCalculator {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Age Calculator ===");
        System.out.println("Enter your birth date in format: YYYY-MM-DD");
        System.out.print("Birth Date: ");
        
        try {
            String birthDateInput = scanner.nextLine();
            LocalDate birthDate = LocalDate.parse(birthDateInput);
            LocalDate currentDate = LocalDate.now();
            
            // Calculate age
            AgeResult age = calculateAge(birthDate, currentDate);
            
            // Display results
            displayAge(age, birthDate, currentDate);
            
        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid date format. Please use YYYY-MM-DD format.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
    
    /**
     * Calculates age between birth date and current date
     * @param birthDate The birth date
     * @param currentDate The current date
     * @return AgeResult object containing years, months, days, and total days
     * @throws IllegalArgumentException if birth date is in the future
     */
    public static AgeResult calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if (birthDate.isAfter(currentDate)) {
            throw new IllegalArgumentException("Birth date cannot be in the future!");
        }
        
        Period period = Period.between(birthDate, currentDate);
        // Fix: Use ChronoUnit to get accurate total days between dates
        long totalDays = java.time.temporal.ChronoUnit.DAYS.between(birthDate, currentDate);
        
        return new AgeResult(
            period.getYears(),
            period.getMonths(),
            period.getDays(),
            totalDays
        );
    }
    
    /**
     * Displays the calculated age in a formatted way
     * @param age The calculated age result
     * @param birthDate The birth date
     * @param currentDate The current date
     */
    public static void displayAge(AgeResult age, LocalDate birthDate, LocalDate currentDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        
        System.out.println("\n=== Age Calculation Results ===");
        System.out.println("Birth Date: " + birthDate.format(formatter));
        System.out.println("Current Date: " + currentDate.format(formatter));
        System.out.println();
        
        System.out.println("Your age is:");
        System.out.println("• " + age.getYears() + " years");
        System.out.println("• " + age.getMonths() + " months");
        System.out.println("• " + age.getDays() + " days");
        System.out.println();
        
        System.out.println("Total days lived: " + age.getTotalDays() + " days");
        
        // Additional fun facts
        System.out.println("\n=== Fun Facts ===");
        System.out.println("Total hours lived: " + (age.getTotalDays() * 24) + " hours");
        System.out.println("Total minutes lived: " + (age.getTotalDays() * 24 * 60) + " minutes");
        
        // Next birthday calculation
        LocalDate nextBirthday = getNextBirthday(birthDate, currentDate);
        long daysUntilBirthday = java.time.temporal.ChronoUnit.DAYS.between(currentDate, nextBirthday);
        System.out.println("Days until next birthday: " + daysUntilBirthday + " days");
    }
    
    /**
     * Calculates the next birthday date
     * @param birthDate The birth date
     * @param currentDate The current date
     * @return The date of the next birthday
     */
    public static LocalDate getNextBirthday(LocalDate birthDate, LocalDate currentDate) {
        LocalDate nextBirthday = LocalDate.of(currentDate.getYear(), birthDate.getMonth(), birthDate.getDayOfMonth());
        
        // If birthday has already passed this year, get next year's birthday
        if (nextBirthday.isBefore(currentDate) || nextBirthday.isEqual(currentDate)) {
            nextBirthday = nextBirthday.plusYears(1);
        }
        
        return nextBirthday;
    }
}

/**
 * Class to hold age calculation results
 */
class AgeResult {
    private final int years;
    private final int months;
    private final int days;
    private final long totalDays;
    
    public AgeResult(int years, int months, int days, long totalDays) {
        this.years = years;
        this.months = months;
        this.days = days;
        this.totalDays = totalDays;
    }
    
    public int getYears() { return years; }
    public int getMonths() { return months; }
    public int getDays() { return days; }
    public long getTotalDays() { return totalDays; }
    
    @Override
    public String toString() {
        return String.format("%d years, %d months, %d days", years, months, days);
    }
}