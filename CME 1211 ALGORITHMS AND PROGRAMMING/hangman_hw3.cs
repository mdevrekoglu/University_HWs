using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Homework_3_Trial_1
{
    class Program
    {
        static void Main()
        {
            bool stop = true, next_word = false;
            string sentence, input, temp_input;
            int word_counter, star_counter = 0;
            int type = 0;

            //
            do
            {
                Console.Clear();
                Console.WriteLine("Welcome to word game!");
                Console.Write("Please enter a sentence: ");
                sentence = Console.ReadLine();
                for (int i = 0; i < sentence.Length; i++)
                {
                    if (!(sentence[i] == 44 || sentence[i] == 46 || (sentence[i] >= 46 && sentence[i] <= 90) || (sentence[i] >= 97 && sentence[i] <= 122) || sentence[i] == 32))
                    {
                        stop = false;
                        break;
                    }
                    else
                    {
                        stop = true;
                    }
                }
            } while (!stop);

            //
            sentence = sentence.Trim().Replace(",", " ");
            sentence = sentence.Trim().Replace(".", " ");
            while (sentence.Contains("  "))
            {
                sentence = sentence.Trim().Replace("  ", " ");
            }
            string[] words_1 = sentence.Split(' ');
            words_1 = words_1.Distinct().ToArray();
            string[] words_1_lower = new string[words_1.Length];
            for (int i = 0; i < words_1.Length; i++)
            {
                words_1_lower[i] = words_1[i].ToLower();
            }

            //            
            do
            {
                Console.Clear();
                Console.Write("Please enter a coded word: ");
                input = Console.ReadLine();
                input = input.Trim().Replace(" ", "");
                input = input.ToLower();
                for (int i = 0; i < input.Length; i++)
                {
                    //
                    if (!(input[i] == 42 || input[i] == 45 || (input[i] >= 97 && input[i] <= 122)))
                    {
                        stop = false;
                        break;
                    }
                    else
                    {
                        stop = true;
                    }

                    //
                    if (type == 0 && input[i] == 42)//*
                        type = 1;
                    else if (type == 0 && input[i] == 45)//-
                        type = 2;
                    else if (type == 1 && input[i] == 45)
                    {
                        stop = false;
                        break;
                    }
                    else if (type == 2 && input[i] == 42)
                    {
                        stop = false;
                        break;
                    }
                }
                if (type == 0)
                    stop = false;
            } while (!stop);

            //
            if (type == 1)//Input: h*al*re   Output: healthcare
            {
                //
                while (input.Contains("**"))
                {
                    input = input.Trim().Replace("**", "*");
                }

                //
                for (int i = 0; i < input.Length; i++)
                {
                    if (input[i] == 42)
                    {
                        star_counter++;
                    }
                }
                //
                int[] inputarr = new int[star_counter + 2];

                //
                for (int u = 0; u < words_1_lower.Length; u++)
                {
                    for (int i = 0; i < inputarr.Length; i++)
                        inputarr[i] = 0;
                    inputarr[0] = -1;
                    star_counter = 1;
                    temp_input = "";
                    for (int o = 0; o < input.Length; o++)
                    {
                        if (input[o] != 42)
                        {
                            temp_input += input[o];
                        }

                        if (input[o] == 42 || o == input.Length - 1)
                        {
                            //
                            if (star_counter == 1 && words_1_lower[u].IndexOf(temp_input, inputarr[1]) != 0)
                            {
                                break;
                            }
                            else if (input[o] != 42 && o == input.Length - 1 && words_1_lower[u].IndexOf(temp_input, inputarr[star_counter - 1] + 1) != words_1_lower[u].Length - temp_input.Length)
                            {
                                break;
                            }

                            //


                            if (words_1_lower[u].IndexOf(temp_input, inputarr[star_counter]) != -1)
                                inputarr[star_counter] = words_1_lower[u].IndexOf(temp_input, inputarr[star_counter - 1] + 1) + temp_input.Length - 1;
                            else
                                break;

                            temp_input = "";

                            //
                            for (int i = 1; i < star_counter; i++)
                            {
                                if (inputarr[star_counter] < inputarr[i])
                                {
                                    next_word = true;
                                    break;
                                }

                            }

                            //
                            if (o == input.Length - 1 && next_word == false)
                                Console.WriteLine(words_1[u]);

                            //
                            if (next_word == true)
                            {
                                next_word = false;
                                break;
                            }

                            //
                            star_counter++;
                        }
                    }
                }
            }
            else if (type == 2)//
            {
                word_counter = input.Length;
                for (int u = 0; u < words_1_lower.Length; u++)
                {
                    if (words_1[u].Length == word_counter)
                    {
                        for (int o = 0; o < word_counter; o++)
                        {
                            if ((input[o] != 45) && (input[o] != words_1_lower[u][o]))
                            {
                                break;
                            }
                            if (word_counter - 1 == o)
                                Console.WriteLine(words_1[u]);
                        }
                    }
                }
            }
            Console.ReadLine();
        }
    }
}