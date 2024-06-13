public class quickSort {
    
    // Constructor
    quickSort() {
        super();// To initialize the super class
    }

    // Quick sort algorithm that takes an array of integer and three integers which are the low and high index and the type of pivot
    // It sorts the array in ascending order and it is a recursive method
    // Type 1: Select the first element as the pivot
    // Type 2: Select a random element as the pivot and swap it with the first element
    // Type 3: Select the middle element as the pivot (middle of three low-mid-high)
    public void quickSortF(int[] arr, int low, int high, int type) {
        
        if (low >= high){ // If the low index is greater than or equal to the high index, return
            return;
        }
        
        if(type == 1){ // Select the first element as the pivot

            int pivot = arr[low]; // Set the pivot to the first element
            int i = high + 1; // Set the i index to the high index + 1

            for (int j = high; j > low; j--) { // Loop to check all the elements
                if (arr[j] > pivot) { // If the element is greater than the pivot, swap it with the element at the i index
                    i--;
                    swap(arr, i, j);
                }
            }

            i--; // Decrement the i index because it is currently pointing to the next element
            
            // Swap the pivot with the element at the i index
            swap(arr, i, low); // Now new pivot index is i

            // Recursively call the method on the left side of the pivot and the right side of the pivot
            // Pivot is not included in the left and right side
            // Therefore, the low index is low and the high index is i - 1
            // The low index is i + 1 and the high index is high
            quickSortF(arr, low, i - 1, type); // Recursively call the method on the left side of the pivot
            quickSortF(arr, i + 1, high, type); // Recursively call the method on the right side of the pivot
        }else if(type == 2){ // Select a random element as the pivot and swap it with the first element
            
            // Create a random number between the low and high index
            int pivot = (int) (Math.random() * (high - low + 1) + low);


            swap(arr, low, pivot); // Swap the pivot with the first element
            pivot = arr[low]; // Set the pivot to the first element
            int i = high + 1; // Set the i index to the high index + 1

            for (int j = high; j > low; j--) { // Loop to check all the elements
                if (arr[j] > pivot) {
                    i--;
                    swap(arr, i, j);
                }
            }

            i--; // Decrement the i index because it is currently pointing to the next element
            
            // Swap the pivot with the element at the i index
            swap(arr, i, low); // Now new pivot index is i

            // Recursively call the method on the left side of the pivot and the right side of the pivot
            // Pivot is not included in the left and right side
            // Therefore, the low index is low and the high index is i - 1
            // The low index is i + 1 and the high index is high
            quickSortF(arr, low, i - 1, type);
            quickSortF(arr, i + 1, high, type);

        }else if(type == 3){ // Select the middle element as the pivot (middle of three low-mid-high)
                    
            // If the low index is medium
            if((arr[low] < arr[high] && arr[low] > arr[(low + high) / 2]) || (arr[low] > arr[high] && arr[low] < arr[(low + high) / 2])){

                int pivot = arr[low]; // Set the pivot to the first element
                int i = high + 1; // Set the i index to the high index + 1

                for (int j = high; j > low; j--) { // Loop to check all the elements
                    if (arr[j] > pivot) { // If the element is greater than the pivot, swap it with the element at the i index
                        i--;
                        swap(arr, i, j);
                    }
                }

                i--; // Decrement the i index because it is currently pointing to the next element
                
                // Swap the pivot with the element at the i index
                swap(arr, i, low); // Now new pivot index is i

                // Recursively call the method on the left side of the pivot and the right side of the pivot
                // Pivot is not included in the left and right side
                // Therefore, the low index is low and the high index is i - 1
                // The low index is i + 1 and the high index is high
                quickSortF(arr, low, i - 1, type); // Recursively call the method on the left side of the pivot
                quickSortF(arr, i + 1, high, type); // Recursively call the method on the right side of the pivot
            }
            // If the high index is medium
            else if((arr[high] > arr[low] && arr[high] < arr[(high + low) / 2]) || (arr[high] < arr[low] && arr[high] > arr[(high + low) / 2])){ 

                int pivot = arr[high]; // Set the pivot to the last element
                int i = low - 1; // Set the i index to the low index - 1

                for (int j = low; j < high; j++) { // Loop to check all the elements
                    if (arr[j] < pivot) { // If the element is less than the pivot, swap it with the element at the i index
                        i++;
                        swap(arr, i, j);
                    }
                }

                i++; // Increment the i index because it is currently pointing to the previous element
                
                // Swap the pivot with the element at the i index
                swap(arr, i, high); // Now new pivot index is i

                // Recursively call the method on the left side of the pivot and the right side of the pivot
                // Pivot is not included in the left and right side
                // Therefore, the low index is low and the high index is i - 1
                // The low index is i + 1 and the high index is high
                quickSortF(arr, low, i - 1, type); // Recursively call the method on the left side of the pivot
                quickSortF(arr, i + 1, high, type); // Recursively call the method on the right side of the pivot
            }else{

                // We are creating two integers, because in this part we are not swapping the pivot with the first element or last element
                // And we are not using for loop to check all the elements
                // Instead, we are using two indexes, i and j to check all the elements
                // Set the i index to the low index and the j index to the high index

                int i = low, j = high;
                // Set the pivot to the middle element
                int pivot = arr[(low + high) / 2];

                while (i <= j) { // Loop to check all the elements

                    while (arr[i] < pivot){ // If the element is less than the pivot, increment the i index
                        i++;
                    }
                    while (arr[j] > pivot){ // If the element is greater than the pivot, decrement the j index
                        j--;
                    }
                    
                    if (i > j){ // If the i index is greater than the j index, break out of the loop
                        break; // Because we have already checked all the elements
                    }
                        
                    swap(arr, i, j); // Swap the elements at the i and j index
                    i++; // Increment the i index to check the next element
                    j--; // Decrement the j index to check the next element
                }

                // Recursively call the method on the left side of the pivot and the right side of the pivot
                // Pivot is not included in the left and right side
                // Therefore, the low index is low and the high index is i - 1
                // The low index is i + 1 and the high index is high
                quickSortF(arr, low, j, type); // Recursively call the method on the left side of the pivot
                quickSortF(arr, i, high, type); // Recursively call the method on the right side of the pivot
            }
        }
    }

    // This method swaps two elements in an array
    public void swap(int[] arr, int x, int y) {
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }
}