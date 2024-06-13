import java.io.*;

/**
 * merge_sort
 */
public class merge_sort {

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
        String file_Name = "student.txt";

        try {

            // Read the files
            File file = new File(file_Name);
            BufferedReader count = new BufferedReader(new FileReader(file));

            // Count the number of lines in the file
            // First line is meaningless
            int lines = -1;
            while (count.readLine() != null) lines++;

            // Close the file
            count.close();

            // Create an array of the size of the number of lines
            data[] students = new data[lines];

            // Read the file again
            BufferedReader reader = new BufferedReader(new FileReader(file));

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

            // Sort the arrays by student id
            mergeSort(students, 0, lines - 1);

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

    // Merge sort algorithm that takes an array of data and two integers to find the middle of the array
    // Returns a sorted array, sorted by student id and it is an void method
    public static void mergeSort(data[] students, int low, int high){
        if(low < high){
            int middle = (low + high) / 2;
            mergeSort(students, low, middle);
            mergeSort(students, middle + 1, high);
            merge(students, low, middle, high);
        }
    }

    // Merge method that takes an array of data and three integers to find the middle of the array
    // Returns a sorted array, sorted by student id and it is an void method
    public static void merge(data[] students, int low, int middle, int high){
        
        // Get the number of elements in the two arrays
        int n1 = middle - low + 1;
        int n2 = high - middle;

        // Create two arrays to store the elements
        data[] left = new data[n1];
        data[] right = new data[n2];

        // Copy the elements into the two arrays
        for (int i = 0; i < n1; i++) {
            left[i] = students[low + i];
        }
        for (int i = 0; i < n2; i++) {
            right[i] = students[middle + 1 + i];
        }

        // Variables to keep track of the index of the arrays
        int i = 0;
        int j = 0;
        int k = low;

        // Merge the two arrays
        while(i < n1 && j < n2){
            if(left[i].getStudentNumber() <= right[j].getStudentNumber()){
                students[k] = left[i];
                i++;
            }
            else{
                students[k] = right[j];
                j++;
            }
            k++;
        }

        // If there are any remaining elements in the left array, add them to the students array
        while(i < n1){
            students[k] = left[i];
            i++;
            k++;
        }

        // If there are any remaining elements in the right array, add them to the students array
        while(j < n2){
            students[k] = right[j];
            j++;
            k++;
        }
    }
}