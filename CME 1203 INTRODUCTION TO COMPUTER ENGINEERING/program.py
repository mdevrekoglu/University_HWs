def Word_Order_Frequency_One_Book(book, word_order, file_output):    
    
    #The part that we try to open given book
    try:
        book_1 = open(book, 'r', encoding = "utf-8")
        content = book_1.read()
        book_1.close()
    except FileNotFoundError:
        print("There is not a file such as", book)
        return 0    
    try:
        stop_words = open("stop_words_english.txt", 'r', encoding = "utf-8")
        stop_words_content = stop_words.read()
        stop_words.close()
    except FileNotFoundError:
        print("There is not a file such as stop_words_english.txt please add it.")
        return 0
    
    #If word order is greater than 2 or less than 1 it works and stops the program
    if (not(word_order == 1 or word_order == 2)):
        print("Invalid word_order number. It can be 1 or 2.")
        return 0
    
    #To prapare content to process
    content = content.lower()
    content = content.replace('\n', ' ')
    
    #To prapare stop_words to process
    stop_words_content = stop_words_content.lower()
    stop_words_content = stop_words_content.replace('\n', ' ')
    
    #To create a list which includes all ascii characters except small letters
    characters = []
    for i in range(0,97):       
        characters.append(chr(i))
    for i in range(122, 256):
        characters.append(chr(i))
    characters.remove(' ')
    characters.append('”')
    characters.append('“')
    characters.append('’')
    characters.append('‘')
    characters.append('—')
    
    #To delete banned characters from content
    for key in characters:
        content = content.replace(key, '')    
    while '  ' in content:
        content = content.replace('  ', ' ')
    book_1_list = list(content.split(' '))
    
    #To delete banned characters from stop_words
    for key in characters:
        stop_words_content = stop_words_content.replace(key, '')  
    while '  ' in stop_words_content:
        stop_words_content = stop_words_content.replace('  ', ' ')               
    stop_words_list = list(stop_words_content.split(' '))  
    stop_words_set = set(stop_words_list)
      
    #To delete stop_words from book_1_list and book_2_list
    book_1_list = [key for key in book_1_list if key not in stop_words_set]
     
    #If order is two it works
    if(word_order == 2):
        for i in range(len(book_1_list)):
            if(i != len(book_1_list) - 1):
                book_1_list[i] += ' ' + book_1_list[i + 1]
            elif (i == len(book_1_list) - 1):
                del book_1_list[len(book_1_list) - 1]
        
    #To create a dictionary for storage numbers and strings
    order_frequency = {}   
    for key in book_1_list:
        if(key in order_frequency.keys()):
            order_frequency[key] += 1
        else:
            order_frequency[key] = 1     

    #To sort dictionary
    sorted_dict = {}
    sorted_keys = sorted(order_frequency, key=order_frequency.get, reverse = True)
    for x in sorted_keys:
        sorted_dict[x] = order_frequency[x]
        
    
    #To print only the top 100 frequncy     
    while(len(sorted_dict) > 100):
        sorted_dict.popitem()
    
    #To create result           
    result = ""
   
    #To print result in order
    for key in sorted_dict:       
        result += '|{:>10} | {:>25}|\n'.format(str(sorted_dict[key]), str(key))
  
    #To print result to the file
    output_file = open(file_output, 'w', encoding = 'utf-8')
    output_file.write("+-----------+--------------------------+\n")
    output_file.write("| WORD      |          WORD            |\n")
    output_file.write("| ORDER     |          ORDER           |\n")
    output_file.write("| FREQUENCY |          SEQUENCE        |\n")
    output_file.write("+-----------+--------------------------+\n")
    output_file.write(result)
    output_file.write("+-----------+--------------------------+\n")
    output_file.close()
        
def Word_Order_Frequency_Two_Books (book_1_name, book_2_name, word_order, file_output):
    
    #The part that we try to open given book
    try:
        book_1 = open(book_1_name, 'r', encoding = "utf-8")
        content_1 = book_1.read()
        book_1.close()
    except FileNotFoundError:
        print("There is not a file such as", book_1_name)
        return 0
    try:
        book_2 = open(book_2_name, 'r', encoding = "utf-8")
        content_2 = book_2.read()
        book_2.close()
    except FileNotFoundError:
        print("There is not a file such as", book_2_name)
        return 0        
    try:
        stop_words = open("stop_words_english.txt", 'r', encoding = "utf-8")
        stop_words_content = stop_words.read()
        stop_words.close()
    except FileNotFoundError:
        print("There is not a file such as stop_words_english.txt please add it")
        return 0
    
    #If word order is greater than 2 or less than 1 it works and stops the program
    if (not(word_order == 1 or word_order == 2)):
        print("Invalid word_order number. It can be 1 or 2.")
        return 0
    
    #To create a list which includes all ascii characters except small letters
    characters = []
    for i in range(0,97):       
        characters.append(chr(i))
    for i in range(122, 256):
        characters.append(chr(i))
    characters.remove(' ')
    characters.append('”')
    characters.append('“')
    characters.append('’')
    characters.append('‘')
    characters.append('—')
    
    #To prepare stop_words for process
    stop_words_content = stop_words_content.lower()
    stop_words_content = stop_words_content.replace('\n', ' ')
    for key in characters:
        stop_words_content = stop_words_content.replace(key, '')  
    while '  ' in stop_words_content:
        stop_words_content = stop_words_content.replace('  ', ' ')               
    stop_words_list = list(stop_words_content.split(' '))
    stop_words_set = set(stop_words_list)
    
    #To prapare content_1 to process
    content_1 = content_1.lower()
    content_1 = content_1.replace('\n', ' ')
    for key in characters:
        content_1 = content_1.replace(key, '')    
    while '  ' in content_1:
        content_1 = content_1.replace('  ', ' ')
    book_1_list = list(content_1.split(' '))
    
    #To prapare content_2 to process    
    content_2 = content_2.lower()
    content_2 = content_2.replace('\n', ' ')
    for key in characters:
        content_2 = content_2.replace(key, '')    
    while '  ' in content_2:
        content_2 = content_2.replace('  ', ' ')
    book_2_list = list(content_2.split(' '))
       
    #To delete stop_words from book_1_list and book_2_list
    book_1_list = [key for key in book_1_list if key not in stop_words_set]
    book_2_list = [key for key in book_2_list if key not in stop_words_set]
    
    #If order is two it works
    if(word_order == 2):
        for i in range(len(book_1_list)):
            if(i != len(book_1_list) - 1):
                book_1_list[i] += ' ' + book_1_list[i + 1]
            elif (i == len(book_1_list) - 1):
                del book_1_list[len(book_1_list) - 1]  
    if(word_order == 2):
        for i in range(len(book_2_list)):
            if(i != len(book_2_list) - 1):
                book_2_list[i] += ' ' + book_2_list[i + 1]
            elif (i == len(book_2_list) - 1):
                del book_2_list[len(book_2_list) - 1]
                           
    #To create final dictionary 
    book_result = []
    book_result.extend(book_1_list)
    book_result.extend(book_2_list)       
          
    #To create a dictionary for storage numbers and strings
    order_frequency = {}  
    for key in book_result:
        if(key in order_frequency.keys()):
            order_frequency[key] += 1
        else:
            order_frequency[key] = 1    
     
    #To sort dictionary        
    sorted_dict = {}
    sorted_keys = sorted(order_frequency, key=order_frequency.get, reverse = True)
    for x in sorted_keys:
        sorted_dict[x] = order_frequency[x]
    
    #To print only the top 100 frequncy     
    while(len(sorted_dict) > 100):
        sorted_dict.popitem()
    
    #To print result in order
    result = ""
    for key in sorted_dict:
        result += '|{:>10} | {:>9} | {:>9} | {:>25}|\n'.format(str(sorted_dict[key]), str(book_1_list.count(key)), str(book_2_list.count(key)), str(key))
      
    #To print result to the file
    output_file = open(file_output, 'w', encoding = 'utf-8')
    output_file.write("+-----------+-----------+-----------+--------------------------+\n")
    output_file.write("| TOTAL     | BOOK 1    | BOOK 2    |          WORD            |\n")
    output_file.write("| ORDER     | ORDER     | ORDER     |          ORDER           |\n")
    output_file.write("| FREQUENCY | FREQUENCY | FREQUENCY |          SEQUENCE        |\n")
    output_file.write("+-----------+-----------+-----------+--------------------------+\n")      
    output_file.write(result)  
    output_file.write("+-----------+-----------+-----------+--------------------------+\n") 
    output_file.close()
