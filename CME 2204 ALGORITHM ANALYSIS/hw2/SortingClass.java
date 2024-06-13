/**
 * SortingClass
 */
public class SortingClass {

    public static int[] arr;
    public static long startTime, endTime;
    public static String str;

    public static void main(String[] args) {

        // Create an instance of the mergeSort and quickSort class
        mergeSort mergeS = new mergeSort();
        quickSort quickS = new quickSort();
        System.out.println("\n");

        for (int i = 1; i <= 4; i++) { // There are 4 types of array type 1,2,3,4 equal,random,increasing,decreasing
            for (int j = 3; j <= 5; j++) { // There are 3 types of array size 1000,10000,100000
                
                resetVariables(); // Reset the variables
                str = "Array size: " + (int)Math.pow(10, j) + " Array type: "; // Set the string to the array size and array type
                str += i == 1 ? "Equal" : i == 2 ? "Random" : i == 3 ? "Increasing" : "Decreasing"; // Set the string to the array type
 
                // Merge Sort Split into 2
                arr = randomArrayCreator((int)Math.pow(10, j), i, (int)Math.pow(10, j)); // Create an array
                System.out.println(i + "-) Merge Sort Split into 2 " + str);
                isSorted(arr);
                startTime = System.nanoTime(); // Start the timer
                mergeS.mergeSort2F(arr, 0, arr.length - 1); // Call the merge sort method
                endTime = System.nanoTime(); // End the timer
                isSorted(arr); // Check if the array is sorted
                System.out.println("Time: " + (double)(endTime - startTime) / (double)1000000 + " milliseconds\n\n");
                //printArray(arr);

                // Merge Sort Split into 3
                arr = randomArrayCreator((int)Math.pow(10, j), i, (int)Math.pow(10, j)); // Create an array
                System.out.println(i + "-) Merge Sort Split into 3 " + str);
                isSorted(arr);
                startTime = System.nanoTime(); // Start the timer
                mergeS.mergeSort3F(arr, 0, arr.length - 1); // Call the merge sort method
                endTime = System.nanoTime(); // End the timer
                isSorted(arr); // Check if the array is sorted
                System.out.println("Time: " + (double)(endTime - startTime) / (double)1000000 + " milliseconds\n\n");
                //printArray(arr);

                // Quick Sort Pivot is the first element
                arr = randomArrayCreator((int)Math.pow(10, j), i, (int)Math.pow(10, j)); // Create an array
                System.out.println(i + "-) Quick Sort Pivot is the first element " + str);
                isSorted(arr);
                startTime = System.nanoTime(); // Start the timer
                quickS.quickSortF(arr, 0, arr.length - 1, 1);
                endTime = System.nanoTime(); // End the timer
                isSorted(arr); // Check if the array is sorted
                System.out.println("Time: " + (double)(endTime - startTime) / (double)1000000 + " milliseconds\n\n");
                //printArray(arr);

                // Quick Sort Pivot is random element
                arr = randomArrayCreator((int)Math.pow(10, j), i, (int)Math.pow(10, j)); // Create an array
                System.out.println(i + "-) Quick Sort Pivot is random element " + str);
                isSorted(arr);
                startTime = System.nanoTime(); // Start the timer
                quickS.quickSortF(arr, 0, arr.length - 1, 2);
                endTime = System.nanoTime(); // End the timer
                isSorted(arr); // Check if the array is sorted
                System.out.println("Time: " + (double)(endTime - startTime) / (double)1000000 + " milliseconds\n\n");
                //printArray(arr);

                // Quick Sort Pivot is the median of the first, middle and last element (median of three method)
                arr = randomArrayCreator((int)Math.pow(10, j), i, (int)Math.pow(10, j)); // Create an array
                System.out.println(i + "-) Quick Sort Pivot is median of three " + str);
                isSorted(arr);
                startTime = System.nanoTime(); // Start the timer
                quickS.quickSortF(arr, 0, arr.length - 1, 3);
                endTime = System.nanoTime(); // End the timer
                isSorted(arr); // Check if the array is sorted
                System.out.println("Time: " + (double)(endTime - startTime) / (double)1000000 + " milliseconds\n\n");
                //printArray(arr);
            }
        }
    }

    // This funtion creates a random array
    // Type1-> equal array, Type2-> random array, Type3-> increasing array, Type4-> decreasing array
    public static int[] randomArrayCreator(int size, int type, int interval) {

        // Create an array of the given size
        int[] arr = new int[size];

        if (type == 1) { // If the user wants a equal array
            int num = (int) (Math.random() * interval);
            for (int i = 0; i < size; i++) {
                arr[i] = num;
            }
        } else if (type == 2) { // If the user wants an random array
            for (int i = 0; i < size; i++) {
                arr[i] = 1 + (int) (Math.random() * interval);
            }
        } else if (type == 3) { // If the user wants an increasing array
            for (int i = 0; i < size; i++) {
                arr[i] = i;
            }
        } else { // If the user wants a decreasing array
            for (int i = 0; i < size; i++) {
                arr[i] = size - i;
            }
        }

        return arr;
    }

    // This method checks whether the array is sorted or not
    public static void isSorted(int[] arr) {

        // A for loop to check every element in the array
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) { // If the current element is greater than the next element
                System.out.println("Array is not sorted"); // The array is not sorted
                return; // Exit the method
            }
        }

        // If the array is sorted
        System.out.println("Array is sorted");
    }
    
    // This method prints the array
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "-->");
        }
        System.out.println();
    }

    // This method resets the variables
    public static void resetVariables() {
        arr = null;
        startTime = 0;
        endTime = 0;
        str = "";
    }
}