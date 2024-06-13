import java.io.*;

/**
 * insertion_sort
 */
public class insertion_sort {

    public static class data{
        
        private String studentName;
        private int studentNumber;

        data(String name, int number){
            this.studentName = name;
            this.studentNumber = number;
        }

        public String getStudentName() {
            return studentName;
        }

        public int getStudentNumber() {
            return studentNumber;
        }
    }

    public static void main(String[] args) {
        
        // Also can be written as path
        String fileName = "student.txt";

        try {

            // Read the files
            File file = new File(fileName);
            BufferedReader count = new BufferedReader(new FileReader(file));

            // Count the number of lines in the file
            // First line is meaningless
            int lines = -1;
            while (count.readLine() != null) lines++;

            // Close the file
            count.close();

            // Create an array of the size of the number of lines, data
            data[] students = new data[lines];

            // Read the file again
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // First line is meaningless so skip it
            String line = reader.readLine();
            int counter = 0;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts.length == 3) {            
                    students[counter] = new data(parts[0] + " " + parts[1], Integer.parseInt(parts[2]));
                    counter++;
                }
                else{
                    System.out.println("Error: Invalid line format.");
                    System.exit(1);
                }
            }

            // Close the file
            reader.close();

            // Print the unsorted arrays
            System.out.println("\nUnsorted arrays:");
            for (int i = 0; i < lines; i++) {
                System.out.println(students[i].getStudentName() + " " + students[i].getStudentNumber());
            }

            // Sort the arrays by student number
            for (int i = 0; i < lines; i++) {
                int val = students[i].getStudentNumber();
                String name = students[i].getStudentName();
                int index = i - 1;

                while(index >= 0 && students[index].getStudentNumber() > val){
                    students[index + 1] = students[index];
                    index--;
                }
                students[index + 1] = new data(name, val);
            }

            // Print the sorted arrays
            System.out.println("\nSorted arrays:");
            for (int i = 0; i < lines; i++) {
                System.out.println(students[i].getStudentName() + " " + students[i].getStudentNumber());
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("An error occured.");
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}