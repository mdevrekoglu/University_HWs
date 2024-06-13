public class mergeSort {
    
    // Constructor
    mergeSort() {
        super(); // To initialize the super class
    }

    // Merge sort algorithm that takes an array of integer and two integers which are the low and high index
    // The method is void and it returns a sorted array
    // This method splits the array in half
    // Uses more memory than the other quick sort methods
    public void mergeSort2F(int[] arr, int low, int high) {
        if (low < high) {
            int middle = (low + high) / 2;
            mergeSort2F(arr, low, middle);
            mergeSort2F(arr, middle + 1, high);
            merge2(arr, low, middle, high);
        }
    }

    // Merge method that takes an array of integer and three integers which are the low, middle and high index
    // This method merges two arrays
    public void merge2(int[] arr, int low, int middle, int high) {

        // Get the number of elements in the two arrays
        int n1 = middle - low + 1;
        int n2 = high - middle;

        // Create two arrays to store the elements
        int[] left = new int[n1];
        int[] right = new int[n2];

        // Copy the elements into the two arrays
        for (int i = 0; i < n1; i++) {
            left[i] = arr[low + i];
        }
        for (int i = 0; i < n2; i++) {
            right[i] = arr[middle + 1 + i];
        }

        // Variables to keep track of the index of the arrays
        int i = 0;
        int j = 0;
        int k = low;

        // Merge the two arrays
        while (i < n1 && j < n2) {
            if (left[i] <= right[j]) {
                arr[k] = left[i];
                i++;
            } else {
                arr[k] = right[j];
                j++;
            }
            k++;
        }

        // If there are any remaining elements in the left array, add them to the
        while (i < n1) {
            arr[k] = left[i];
            i++;
            k++;
        }

        // If there are any remaining elements in the right array, add them to the
        while (j < n2) {
            arr[k] = right[j];
            j++;
            k++;
        }
    }

    // Merge sort algorithm that takes an array of integer and two integers which are the low and high index
    // The method is void and it returns a sorted array
    // This method splits the array in thirds
    public void mergeSort3F(int[] arr, int low, int high) {

        if (low < high) {
            // Find the middle indexses
            int middle1 = low + (high - low) / 3;
            int middle2 = low + 2 * (high - low) / 3;

            mergeSort3F(arr, low, middle1);
            mergeSort3F(arr, middle1 + 1, middle2);
            mergeSort3F(arr, middle2 + 1, high);
            merge3(arr, low, middle1, middle2, high);
        }
    }

    // Merge method that takes an array of integer and four integers which are the
    // low, middle1, middle2 and high index
    // This method merges three arrays
    public void merge3(int[] arr, int low, int middle1, int middle2, int high) {

        // Get the number of elements in the two arrays
        int n1 = middle1 - low + 1;
        int n2 = middle2 - middle1;
        int n3 = high - middle2;

        // Create two arrays to store the elements
        int[] left = new int[n1];
        int[] middle = new int[n2];
        int[] right = new int[n3];

        // Copy the elements into the two arrays
        for (int i = 0; i < n1; i++) {
            left[i] = arr[low + i];
        }
        for (int i = 0; i < n2; i++) {
            middle[i] = arr[middle1 + 1 + i];
        }
        for (int i = 0; i < n3; i++) {
            right[i] = arr[middle2 + 1 + i];
        }

        // Variables to keep track of the index of the arrays
        int i = 0;
        int j = 0;
        int k = 0;
        int l = low;

        // Merge the two arrays
        while (i < n1 && j < n2 && k < n3) {
            if (left[i] <= middle[j] && left[i] <= right[k]) {
                arr[l] = left[i];
                i++;
            } else if (middle[j] <= left[i] && middle[j] <= right[k]) {
                arr[l] = middle[j];
                j++;
            } else {
                arr[l] = right[k];
                k++;
            }
            l++;
        }

        // If there is not any element in the left array
        while (j < n2 && k < n3) {
            if (middle[j] <= right[k]) {
                arr[l] = middle[j];
                j++;
            } else {
                arr[l] = right[k];
                k++;
            }
            l++;
        }

        // If there is not any element in the middle array
        while (i < n1 && k < n3) {
            if (left[i] <= right[k]) {
                arr[l] = left[i];
                i++;
            } else {
                arr[l] = right[k];
                k++;
            }
            l++;
        }

        // If there is not any element in the right array
        while (i < n1 && j < n2) {
            if (left[i] <= middle[j]) {
                arr[l] = left[i];
                i++;
            } else {
                arr[l] = middle[j];
                j++;
            }
            l++;
        }

        // If there are some remaining elements in the left array
        while (i < n1) {
            arr[l] = left[i];
            i++;
            l++;
        }

        // If there are some remaining elements in the middle array
        while (j < n2) {
            arr[l] = middle[j];
            j++;
            l++;
        }

        // If there are some remaining elements in the right array
        while (k < n3) {
            arr[l] = right[k];
            k++;
            l++;
        }
    }

    // This method swaps two elements in an array
    public void swap(int[] arr, int x, int y) {
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }
}
