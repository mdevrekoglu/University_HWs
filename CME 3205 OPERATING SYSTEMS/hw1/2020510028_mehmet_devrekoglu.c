#include<stdio.h> // for printf
#include<string.h> // for strlen
#include<stdlib.h> // for atoi
#include<sys/socket.h> // for sockets
#include<arpa/inet.h> // for inet_addr
#include<unistd.h>    // for write
#include <sys/types.h> // for socket types
#include <pthread.h> // for thread funcs

// Can not make them global variable, so i defined them
#define DATA_SIZE 100
#define PORT_NUMBER 60000

// Global variables
// To see if there is a problem with input size, create string with one more character
char INPUT_STRING [DATA_SIZE + 1];
int FIRST_ARRAY [DATA_SIZE];
int SECOND_ARRAY [DATA_SIZE];
int CARRY_ARRAY [DATA_SIZE];
int RESULT_ARRAY [DATA_SIZE];

// To send message to user
char *message;

// Socket variables
int socket_desc , new_socket , c;
int numCounter1, numCounter2;
struct sockaddr_in server , client;

// Functions
int checkInputAndAssignIntArray(int bytes_received, int array[]);
void *SumArrays(void *t_id);

// gcc 2020510028_mehmet_devrekoglu.c -o test.o -pthread
// ./test.o
// telnet localhost 60000

// Example inputs and outputs
// 105 449 445 842 292 655 959 6 404 149
// 999 601 78 502 156 482 805 670 834 27
// 1 105 50 524 344 449 138 764 677 238 176

// Main
int main(int argc, char *argv[])
{
    // Create socket
    socket_desc = socket(AF_INET, SOCK_STREAM, 0);
    //Address Family - AF_INET (this is IP version 4)
    //Type - SOCK_STREAM (this means connection oriented TCP protocol)
    //Protocol - 0 (Specifies a particular protocol to be used with the socket...
    //Specifying a protocol of 0 causes socket() to use an unspecified default protocol)
    //[ or IPPROTO_IP This is IP protocol]

    // Failed to create socket
    if (socket_desc == -1)
    {
        puts("Could not create socket");
        return 1;
    }

    server.sin_family = AF_INET;  //IPv4 Internet protocols
    server.sin_addr.s_addr = INADDR_ANY; //IPv4 local host address
    server.sin_port = htons(PORT_NUMBER); // server will listen to PORT_NUMBER port

    // Bind
    if(bind(socket_desc, (struct sockaddr *)&server, sizeof(server)) < 0)
    {
        puts("Binding failed");
        return 1;
    }
    puts("Socket is binded");

    // Listen
    listen(socket_desc, 3);

    // Accept and incoming connection
    puts("Waiting for incoming connections...");

    c = sizeof(struct sockaddr_in);
    new_socket= accept(socket_desc,(struct sockaddr *)&client,(socklen_t*)&c);
    if (new_socket<0)
    {
        puts("Accept failed");
        return 1;
    }

    puts("Connection accepted");

    // Reply to the client
    memset(&message, 0, sizeof(message));
    message = "\n\nHello, this is Array Addition Server!\n\n";
    write(new_socket, message, strlen(message));

    //Get FIRST_ARRAY
    memset(&message, 0, sizeof(message));
    message = "Please enter the first array for addition:\n";
    write(new_socket, message, strlen(message));

    // Retrieve an additional character to determine if the input length exceeds 100 characters.
    memset(&INPUT_STRING, 0, sizeof(INPUT_STRING));
    ssize_t bytes_received_1 = recv(new_socket, INPUT_STRING, sizeof(INPUT_STRING) + 2, 0);

    // Check input and assign to FIRST_ARRAY
    memset(&FIRST_ARRAY, 0, sizeof(FIRST_ARRAY));
    int result_1 = checkInputAndAssignIntArray(bytes_received_1, FIRST_ARRAY); // Check result if result is -1, there is an error
    if(result_1 == -1){
        return 1;
    }

    // Get SECOND_ARRAY
    memset(&message, 0, sizeof(message));
    message = "Please enter the second array for addition:\n";
    write(new_socket, message, strlen(message));

    // Retrieve an additional character to determine if the input length exceeds 100 characters.
    memset(&INPUT_STRING, 0, sizeof(INPUT_STRING));
    ssize_t bytes_received_2 = recv(new_socket, INPUT_STRING, sizeof(INPUT_STRING) + 1, 0);

    // Check input and assign to SECOND_ARRAY
    memset(&SECOND_ARRAY, 0, sizeof(SECOND_ARRAY));
    int result_2 = checkInputAndAssignIntArray(bytes_received_2, SECOND_ARRAY);
    if(result_2 == -1){
        return 1;
    }

    // If no errors occurred up to this point, proceed to check if the numbers are equal
    if(result_1 != result_2){
        puts("ERROR: The number of integers are different for both arrays. You must send equal number of integers for both arrays\n");
        memset(&message, 0, sizeof(message));
        message = "ERROR: The number of integers are different for both arrays. You must send equal number of integers for both arrays\n";
        write(new_socket, message, strlen(message));
        return 1;
    }

    memset(&CARRY_ARRAY, 0, sizeof(CARRY_ARRAY));
    memset(&RESULT_ARRAY, 0, sizeof(RESULT_ARRAY));

    // Create THREAD_ARRAY
    int THREAD_COUNT = result_1;
    pthread_t THREAD_ARRAY [THREAD_COUNT];

    // Creating threads
    int tcr;
    int* t_id;
    for (int i = 0; i < THREAD_COUNT; i++) {
        // Create a thread id and assign it to the current thread id
        t_id = (int*)malloc(sizeof(int));
        *t_id = i;

        printf("Creating thread %d\n", (i));
        tcr = pthread_create(&THREAD_ARRAY[i], NULL, SumArrays, (void*)t_id);
        if (tcr != 0) {
            printf("Thread creation error\n");
        }
    }

    // Wait for all threads to finish
    for (int i = 0; i < THREAD_COUNT; i++) {
        pthread_join(THREAD_ARRAY[i], NULL);
    }

    // Add carry values to the result
    // Type is LSB because it is an addition operation
    for(int i = THREAD_COUNT - 1; i > 0; i--){
        if(CARRY_ARRAY[i] != 0){
            RESULT_ARRAY[i - 1] += CARRY_ARRAY[i];
            if(RESULT_ARRAY[i - 1] > 999){
                RESULT_ARRAY[i - 1] -= 1000;
                CARRY_ARRAY[i -1]++;
            }
        }
    }

    // Send Result to the user
    memset(&message, 0, sizeof(message));
    message = "\nThe result of array addition are given below:\n";
    write(new_socket, message, strlen(message));

    // Send result to the user
    // Create a char array to store the result which is Data Size + 2, and initialize the index to 0
    char result[DATA_SIZE + 2];
    int result_index = 0;

    // Iterate through the result array and store the integers in the result char array
    // But if there is a carry value in the MSB, we need to add it to the result char array
    if(CARRY_ARRAY[0] != 0){
        result[result_index] = CARRY_ARRAY[0] + '0';
        result_index++;
        result[result_index] = ' ';
        result_index++;
    }

    // Iterate through the result array and store the integers in the result char array
    for (int i = 0; i < THREAD_COUNT; i++) {
        // Convert the current integer to a string and store it in the result char array
        int len = sprintf(&result[result_index], "%d", RESULT_ARRAY[i]);
        // Increment the index to move to the next index in the result char array
        result_index += len;
        // Add a space to the result char array
        result[result_index] = ' ';
        // Increment the index to move to the next index in the result char array
        result_index++;
    }

    // Add a null terminator to the end of the result char array
    result[result_index] = '\0';

    // Send the result to the user
    write(new_socket, result, strlen(result));

    // Send 'Goodby Message'
    memset(&message, 0, sizeof(message));
    message = "\n\nThank you for Array Addition Server! Good Bye!\n\n";
    write(new_socket, message, strlen(message));

    // Close the sockets
    close(socket_desc);
    close(new_socket);

    return 0;
}

// Thread function
void *SumArrays(void *param) {
    int t_id;
    t_id = *((int*)param);

    //printf("Hello! I'm thread number %d! Work in progress\n", t_id);
    int sum = FIRST_ARRAY[t_id] + SECOND_ARRAY[t_id];
    if(sum > 999){
        sum -= 1000;
        CARRY_ARRAY[t_id] = 1;
    }

    RESULT_ARRAY[t_id] = sum;

    printf("I'm thread number %d! Work done\n", t_id);

    pthread_exit(NULL);
}

// Check input and assign to array
int checkInputAndAssignIntArray(int bytes_received, int array[]) {

    int numCounter = 0;
    int digitCounter = 0;

    if (bytes_received < 0) { // If an error occurs during receiving data from the socket
        puts("Error: An error occurred during receiving data from the socket\n");

        // Send error message to the user
        memset(&message, 0, sizeof(message));
        message = "Error: An error occurred during receiving data from the socket\n";
        write(new_socket, message, strlen(message));

        return -1;
    } else if (INPUT_STRING[DATA_SIZE] != '\0' && INPUT_STRING[DATA_SIZE] != -35 + '0' && INPUT_STRING[DATA_SIZE] != -38 + '0') { // If there is a problem about size
        puts("Error: Input data is very big. Please use less than 100 characters\n");

        // Send error message to the user
        memset(&message, 0, sizeof(message));
        message = "Error: Input data is very big. Please use less than 100 characters\n";
        write(new_socket, message, strlen(message));

        return -1;
    } else { // If everything is okay, check whether the input contains forbidden characters

        for (int i = 0; i < DATA_SIZE; i++) {

            if (INPUT_STRING[i] == ' ') {
                digitCounter = 0;
            }else if (INPUT_STRING[i] == -35 + '0' || INPUT_STRING[i] == -38 + '0') { //10-New Line 13-Carriage Return in ascii
                INPUT_STRING[i] = '\0';
            }else if(INPUT_STRING[i] <= '9' && INPUT_STRING[i] >= '0'){
                digitCounter++;
                if(INPUT_STRING[i - 1] == ' ' || i == 0){
                    numCounter++;
                }
            }else if(INPUT_STRING[i] == '\0'){
                break;
            }else{
                puts("ERROR: The inputted data contains non-integer characters. You must input only integers and empty spaces to separate inputted integers\n");
                printf("Error %d,%c,%d\n", i, INPUT_STRING[i], INPUT_STRING[i]);

                // Send error message to the user
                memset(&message, 0, sizeof(message));
                message = "ERROR: The inputted data contains non-integer characters. You must input only integers and empty spaces to separate inputted integers\n";
                write(new_socket, message, strlen(message));

                return -1;
            }

            if(digitCounter > 3){
                puts("ERROR: Each number in the input must have a maximum of 3 digits.\n");

                // Send error message to the user
                memset(&message, 0, sizeof(message));
                message = "ERROR: Each number in the input must have a maximum of 3 digits.\n";
                write(new_socket, message, strlen(message));

                return -1;
            }
        }
    }

    // For any case adding stop point end of the array
    INPUT_STRING[DATA_SIZE] = '\0';

    int left_index = 0;
    int right_index = 0;
    for(int i = 0; i < DATA_SIZE; i++){
        if(INPUT_STRING[i] == ' ' || INPUT_STRING[i] == '\0'){
            array[right_index] = atoi(&INPUT_STRING[left_index]);
            left_index = i + 1;
            right_index++;
        }
    }

    return numCounter; // Success
}
