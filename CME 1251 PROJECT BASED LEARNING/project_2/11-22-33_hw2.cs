using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;

namespace Trial_1
{
    class Program
    {
        static void Main(string[] args)
        {
            int menu, difficult = 1;
            int s1, stop_counter;
            int move_counter = 0;
            ConsoleKeyInfo keyInfo;
            int[] x = new int[30];
            int[] y = new int[30];
            int[] k = new int[30];

            int sx = 5, sy = 3;
            int counternumber = 0, counter = 0, score = 0, counterrepointnumber1 = 0, counterrepointnumber2 = 0;
            bool point = false, canmove, repoint = false;


            //To use random library.                   
            Random rnd = new Random();

            while (true)
            {
                Console.Clear();
                Console.WriteLine(" Welcome to '11 22 33' please select an option!");
                Console.WriteLine("");
                Console.WriteLine("              +~~~~~~OPTIONS~~~~~~~+");
                Console.WriteLine("              |1-Rules             |");
                Console.WriteLine("              |2-Difficult         |");
                Console.WriteLine("              |3-Console Color     |");
                Console.WriteLine("              |4-Play              |");
                Console.WriteLine("              |5-Exit              |");
                Console.WriteLine("              +~~~~~~~~~~~~~~~~~~~~+");
                Console.WriteLine("");
                Console.Write(" Option:");
                do
                {
                    Console.SetCursorPosition(8, 10);
                    Console.Write("      ");
                    Console.SetCursorPosition(8, 10);
                    menu = Convert.ToInt32(Console.ReadLine());
                } while (menu != 1 && menu != 2 && menu != 3 && menu != 4 && menu != 5);

                if (menu == 1)
                {
                    Console.Clear();
                    Console.WriteLine();
                    Console.WriteLine("             ~~~~~~~RULES~~~~~~~");
                    Console.WriteLine(" 1-Player can select an difficulty before play");
                    Console.WriteLine("  *Easy is default difficult. There is no move limit.");
                    Console.WriteLine("  *Normal limits the move. Player can move cursor 20 times by using arrow keys");
                    Console.WriteLine("  *Hard limits the move. Player can move cursor 15 times by using arrow keys");
                    Console.WriteLine("  *Insane limits the move. Player can move cursor 10 times by using arrow keys");
                    Console.WriteLine(" 2-Player can only move the numbers which do not have another number next to itself");//need helllp
                    Console.WriteLine(" 3-If two same number comes together these two nombers will be deleted and will be created on game area");
                    Console.WriteLine(" 4- Player can get point(10) in every case.(Randomly or by player move)");
                    Console.WriteLine(" 5-Player can use only 9 key:");
                    Console.WriteLine("  *Up Arrow: To move up cursor");
                    Console.WriteLine("  *Down Arrow: To move down cursor");
                    Console.WriteLine("  *Right Arrow: To move right cursor");
                    Console.WriteLine("  *Left Arrow: To move left cursor");
                    Console.WriteLine("  *W: To move up number one time");
                    Console.WriteLine("  *S: To move down number one time");
                    Console.WriteLine("  *D: To move number right as far as it can");
                    Console.WriteLine("  *A: To move number left as far as it can");
                    Console.WriteLine("  *Esc: To return menu");
                    Console.WriteLine();
                    Console.WriteLine();
                    Console.WriteLine(" Please press ESC to return menu!");
                    do
                    {
                        keyInfo = Console.ReadKey(true);
                    } while (keyInfo.Key != ConsoleKey.Escape);
                }
                else if (menu == 2)
                {
                    Console.Clear();
                    Console.WriteLine("             +~~~~~~DIFFICULT~~~~~~+");
                    Console.WriteLine("             |1-Easy (Recommended) |");
                    Console.WriteLine("             |2-Normal             |");
                    Console.WriteLine("             |3-Hard               |");
                    Console.WriteLine("             |4-Insane             |");
                    Console.WriteLine("             +~~~~~~~~~~~~~~~~~~~~~+");
                    Console.WriteLine();
                    Console.Write(" Difficult:");
                    do
                    {
                        Console.SetCursorPosition(11, 7);
                        Console.Write("          ");
                        Console.SetCursorPosition(11, 7);
                        difficult = Convert.ToInt32(Console.ReadLine());
                    } while (difficult != 1 && difficult != 2 && difficult != 3 && difficult != 4);
                    Console.WriteLine(" Difficult have been selected. Please press ESC to return menu!");
                    do
                    {
                        keyInfo = Console.ReadKey(true);
                    } while (keyInfo.Key != ConsoleKey.Escape);
                }
                else if (menu == 3)
                {
                    Console.Clear();
                    Console.WriteLine("             +~~~~~~~Colors~~~~~~~+");
                    Console.WriteLine("             |1-Black-White       |");
                    Console.WriteLine("             |2-Dark Green-Yellow |");
                    Console.WriteLine("             |3-White-Black       |");
                    Console.WriteLine("             |4-Dark Blue-Yellow  |");
                    Console.WriteLine("             |5-Dark Magenta-White|");
                    Console.WriteLine("             |6-Gray-Black        |");
                    Console.WriteLine("             +~~~~~~~~~~~~~~~~~~~~+");
                    Console.WriteLine();
                    Console.Write(" Color:");
                    Console.WriteLine();
                    do
                    {
                        Console.SetCursorPosition(7, 9);
                        Console.Write("          ");
                        Console.SetCursorPosition(7, 9);
                        s1 = Convert.ToInt32(Console.ReadLine());
                    } while (s1 != 1 && s1 != 2 && s1 != 3 && s1 != 4 && s1 != 5 && s1 != 6);
                    if (s1 == 1)
                    {
                        Console.BackgroundColor = ConsoleColor.Black;
                        Console.Clear();
                        Console.ForegroundColor = ConsoleColor.White;
                    }
                    else if (s1 == 2)
                    {
                        Console.BackgroundColor = ConsoleColor.DarkGreen;
                        Console.Clear();
                        Console.ForegroundColor = ConsoleColor.Yellow;
                    }
                    else if (s1 == 3)
                    {
                        Console.BackgroundColor = ConsoleColor.White;
                        Console.Clear();
                        Console.ForegroundColor = ConsoleColor.Black;
                    }
                    else if (s1 == 4)
                    {
                        Console.BackgroundColor = ConsoleColor.DarkBlue;
                        Console.Clear();
                        Console.ForegroundColor = ConsoleColor.Yellow;
                    }
                    else if (s1 == 5)
                    {
                        Console.BackgroundColor = ConsoleColor.DarkMagenta;
                        Console.Clear();
                        Console.ForegroundColor = ConsoleColor.White;
                    }
                    else if (s1 == 6)
                    {
                        Console.BackgroundColor = ConsoleColor.Gray;
                        Console.Clear();
                        Console.ForegroundColor = ConsoleColor.Black;
                    }
                    Console.WriteLine(" Settings have been selected. Please press ESC to return menu!");
                    do
                    {
                        keyInfo = Console.ReadKey(true);
                    } while (keyInfo.Key != ConsoleKey.Escape);
                }
                else if (menu == 4)
                {
                    Console.Clear();

                    //
                    for (int i = 0; i < 30; i++)
                    {
                        x[i] = 0;
                        y[i] = 0;
                        k[i] = 0;
                    }

                    //bug var
                    for (int u = 0; u < 30; u++)
                    {
                        x[u] = rnd.Next(5, 35);
                        y[u] = rnd.Next(3, 6);
                        k[u] = rnd.Next(1, 4);
                        counter++;
                        for (int o = 1; o < counter; o++)
                        {
                            if ((x[o - 1] == x[u]) && (y[o - 1] == y[u]))
                            {
                                counter--;
                                u--;
                            }
                        }
                    }

                    //
                    Console.WriteLine("");
                    Console.WriteLine("");
                    Console.WriteLine("    +------------------------------+");
                    Console.WriteLine("    |                              |");
                    Console.WriteLine("    |                              |");
                    Console.WriteLine("    |                              |");
                    Console.WriteLine("    +------------------------------+");

                    //
                    for (int i = 0; i < 30; i++)
                    {
                        Console.SetCursorPosition(x[i], y[i]);
                        Console.Write(k[i]);
                    }

                    //
                    do
                    {
                        counternumber = 0;
                        point = false;
                        canmove = true;
                        stop_counter = 0;

                        //
                        for (int u = 0; u < 30; u++)
                        {
                            for (int o = 0; o < 30; o++)
                            {
                                if ((x[u] == x[o] + 1 && y[u] == y[o]) || (x[u] == x[o] - 1 && y[u] == y[o]) || (x[u] == 5 || x[u] == 34))
                                {
                                    stop_counter++;
                                    break;
                                }
                            }
                        }                       
                        if(stop_counter == 30)
                        {
                            Console.SetCursorPosition(40, 6);
                            Console.Write("Game is over!   Total score is: " + score);
                            Console.SetCursorPosition(1, 10);
                            Console.WriteLine("Please press ESC to return menu!");
                            do
                            {
                                keyInfo = Console.ReadKey(true);
                            } while (keyInfo.Key != ConsoleKey.Escape);
                            break;
                        }

                        //
                        do
                        {
                            for (int u = 0; u < 30; u++)
                            {
                                for (int o = 0; o < 30; o++)
                                {
                                    if ((x[o] == x[u] + 1 && y[o] == y[u] && k[o] == k[u]) || (x[o] == x[u] - 1 && y[o] == y[u] && k[o] == k[u]))
                                    {
                                        score += 10;
                                        Console.Beep(350, 200);
                                        move_counter = 0;
                                        counterrepointnumber1 = u;
                                        counterrepointnumber2 = o;
                                        Console.SetCursorPosition(x[counterrepointnumber1], y[counterrepointnumber1]);
                                        Console.Write(" ");
                                        Console.SetCursorPosition(x[counterrepointnumber2], y[counterrepointnumber2]);
                                        Console.Write(" ");
                                        Console.SetCursorPosition(sx, sy);
                                        repoint = true;
                                        break;
                                    }
                                }
                                if (repoint == true)
                                    break;
                            }
                            if (repoint == true)
                            {
                                while (repoint)
                                {
                                    x[counterrepointnumber1] = rnd.Next(5, 35);
                                    y[counterrepointnumber1] = rnd.Next(3, 6);
                                    k[counterrepointnumber1] = rnd.Next(1, 4);

                                    for (int o = 0; o < 30; o++)
                                    {
                                        if (x[counterrepointnumber1] == x[o] && y[counterrepointnumber1] == y[o] && counterrepointnumber1 != o)
                                        {
                                            repoint = true;
                                            break;
                                        }
                                        else
                                        {
                                            repoint = false;
                                        }
                                    }
                                }
                                Console.SetCursorPosition(x[counterrepointnumber1], y[counterrepointnumber1]);
                                Console.Write(k[counterrepointnumber1]);
                                repoint = true;
                                while (repoint)
                                {
                                    x[counterrepointnumber2] = rnd.Next(5, 35);
                                    y[counterrepointnumber2] = rnd.Next(3, 6);
                                    k[counterrepointnumber2] = rnd.Next(1, 4);

                                    for (int o = 0; o < 30; o++)
                                    {
                                        if (x[counterrepointnumber2] == x[o] && y[counterrepointnumber2] == y[o] && counterrepointnumber2 != o)
                                        {
                                            repoint = true;
                                            break;
                                        }
                                        else
                                        {
                                            repoint = false;
                                        }
                                    }
                                }
                                Console.SetCursorPosition(x[counterrepointnumber2], y[counterrepointnumber2]);
                                Console.Write(k[counterrepointnumber2]);
                                Console.SetCursorPosition(sx, sy);
                            }
                            for (int u = 0; u < 30; u++)
                            {
                                for (int o = 0; o < 30; o++)
                                {
                                    if ((x[o] == x[u] + 1 && y[o] == y[u] && k[o] == k[u]) || (x[o] == x[u] - 1 && y[o] == y[u] && k[o] == k[u]))
                                    {
                                        score += 10;
                                        Console.Beep(350, 200);
                                        move_counter = 0;
                                        counterrepointnumber1 = u;
                                        counterrepointnumber2 = o;
                                        Console.SetCursorPosition(x[counterrepointnumber1], y[counterrepointnumber1]);
                                        Console.Write(" ");
                                        Console.SetCursorPosition(x[counterrepointnumber2], y[counterrepointnumber2]);
                                        Console.Write(" ");
                                        Console.SetCursorPosition(sx, sy);
                                        repoint = true;
                                        break;
                                    }
                                }
                                if (repoint == true)
                                    break;
                            }
                        } while (repoint == true);

                        //
                        Console.SetCursorPosition(40, 6);
                        Console.Write("Total score is: " + score);
                        Console.SetCursorPosition(sx, sy);
                        if (difficult != 1)
                        {
                            Console.SetCursorPosition(40, 3);
                            Console.Write("Number of moves:" + move_counter);
                            Console.SetCursorPosition(sx, sy);
                        }

                        //
                        keyInfo = Console.ReadKey(true);
                        switch (keyInfo.Key)
                        {
                            case ConsoleKey.UpArrow:
                                if (sy == 3)
                                    sy++;
                                sy--;
                                Console.SetCursorPosition(sx, sy);
                                move_counter++;
                                break;

                            case ConsoleKey.DownArrow:
                                if (sy == 5)
                                    sy--;
                                sy++;
                                Console.SetCursorPosition(sx, sy);
                                move_counter++;
                                break;

                            case ConsoleKey.RightArrow:
                                if (sx == 34)
                                    sx--;
                                sx++;
                                Console.SetCursorPosition(sx, sy);
                                move_counter++;
                                break;

                            case ConsoleKey.LeftArrow:
                                if (sx == 5)
                                    sx++;
                                sx--;
                                Console.SetCursorPosition(sx, sy);
                                move_counter++;
                                break;

                            case ConsoleKey.W:
                                for (int u = 0; u < 30; u++)
                                {
                                    if (x[u] == sx && y[u] == sy)
                                    {
                                        counternumber = u;
                                        point = true;
                                        break;
                                    }
                                }
                                if (point == true)
                                {
                                    for (int u = 0; u < 30; u++)
                                    {
                                        if ((x[u] == sx + 1 && y[u] == sy) || (x[u] == sx - 1 && y[u] == sy) || (sx == 5 || sx == 34))
                                        {
                                            canmove = false;
                                            break;
                                        }
                                    }
                                    if (canmove == false)
                                        break;

                                    for (int u = 0; u < 30; u++)
                                    {
                                        if (x[u] == sx && y[u] == sy - 1)
                                        {
                                            canmove = false;
                                            break;
                                        }
                                    }
                                    if (canmove == false)
                                        break;
                                    if (y[counternumber] - 1 > 5 || y[counternumber] - 1 < 3)
                                        break;

                                    y[counternumber] -= 1;
                                    Console.WriteLine(" ");
                                    Console.SetCursorPosition(x[counternumber], y[counternumber]);
                                    Console.WriteLine(k[counternumber]);
                                    Console.SetCursorPosition(sx, sy);
                                }
                                break;

                            case ConsoleKey.S:
                                for (int u = 0; u < 30; u++)
                                {
                                    if (x[u] == sx && y[u] == sy)
                                    {
                                        counternumber = u;
                                        point = true;
                                        break;
                                    }
                                }
                                if (point == true)
                                {
                                    for (int u = 0; u < 30; u++)
                                    {
                                        if ((x[u] == sx + 1 && y[u] == sy) || (x[u] == sx - 1 && y[u] == sy) || (sx == 5 || sx == 34))
                                        {
                                            canmove = false;
                                            break;
                                        }
                                    }
                                    if (canmove == false)
                                        break;

                                    for (int u = 0; u < 30; u++)
                                    {
                                        if (x[u] == sx && y[u] == sy + 1)
                                        {
                                            canmove = false;
                                            break;
                                        }
                                    }
                                    if (canmove == false)
                                        break;
                                    if (y[counternumber] + 1 > 5 || y[counternumber] + 1 < 3)
                                        break;

                                    y[counternumber] += 1;
                                    Console.WriteLine(" ");
                                    Console.SetCursorPosition(x[counternumber], y[counternumber]);
                                    Console.WriteLine(k[counternumber]);
                                    Console.SetCursorPosition(sx, sy);
                                }
                                break;

                            case ConsoleKey.D:
                                for (int u = 0; u < 30; u++)
                                {
                                    if (x[u] == sx && y[u] == sy)
                                    {
                                        counternumber = u;
                                        point = true;
                                        break;
                                    }
                                }
                                if (point == true)
                                {
                                    for (int u = 0; u < 30; u++)
                                    {
                                        if ((x[u] == sx + 1 && y[u] == sy) || (x[u] == sx - 1 && y[u] == sy) || (sx == 5 || sx == 34))
                                        {
                                            canmove = false;
                                            break;
                                        }
                                    }
                                    if (canmove == false)
                                        break;

                                    for (int u = 0; u < 30; u++)
                                    {
                                        x[counternumber] += 1;
                                        if (x[counternumber] < 5 || x[counternumber] > 34)
                                        {
                                            x[counternumber] -= 1;
                                            canmove = false;
                                            break;
                                        }
                                        for (int o = 0; o < 30; o++)
                                        {
                                            if (x[o] == x[counternumber] + 1 && y[o] == y[counternumber] || x[o] == x[counternumber] - 1 && y[o] == y[counternumber])
                                            {
                                                canmove = false;
                                                break;
                                            }
                                        }
                                        if (canmove == false)
                                            break;
                                    }

                                    Console.WriteLine(" ");
                                    Console.SetCursorPosition(x[counternumber], y[counternumber]);
                                    Console.WriteLine(k[counternumber]);
                                    Console.SetCursorPosition(sx, sy);
                                }
                                break;

                            case ConsoleKey.A:
                                for (int u = 0; u < 30; u++)
                                {
                                    if (x[u] == sx && y[u] == sy)
                                    {
                                        counternumber = u;
                                        point = true;
                                        break;
                                    }
                                }
                                if (point == true)
                                {
                                    for (int u = 0; u < 30; u++)
                                    {
                                        if ((x[u] == sx + 1 && y[u] == sy) || (x[u] == sx - 1 && y[u] == sy) || (sx == 5 || sx == 34))
                                        {
                                            canmove = false;
                                            break;
                                        }
                                    }
                                    if (canmove == false)
                                        break;

                                    for (int u = 0; u < 30; u++)
                                    {
                                        x[counternumber] -= 1;
                                        if (x[counternumber] < 5 || x[counternumber] > 34)
                                        {
                                            x[counternumber] += 1;
                                            canmove = false;
                                            break;
                                        }
                                        for (int o = 0; o < 30; o++)
                                        {
                                            if (x[o] == x[counternumber] + 1 && y[o] == y[counternumber] || x[o] == x[counternumber] - 1 && y[o] == y[counternumber])
                                            {
                                                canmove = false;
                                                break;
                                            }
                                        }
                                        if (canmove == false)
                                            break;
                                    }

                                    Console.WriteLine(" ");
                                    Console.SetCursorPosition(x[counternumber], y[counternumber]);
                                    Console.WriteLine(k[counternumber]);
                                    Console.SetCursorPosition(sx, sy);
                                }
                                break;
                        }
                        
                        //
                        if (difficult == 2 && move_counter == 20)
                        {
                            Console.SetCursorPosition(40, 6);
                            Console.Write("Game is over!   Total score is: " + score);
                            Console.SetCursorPosition(1, 10);
                            Console.WriteLine("Please press ESC to return menu!");
                            do
                            {
                                keyInfo = Console.ReadKey(true);
                            } while (keyInfo.Key != ConsoleKey.Escape);
                            break;
                        }
                        else if (difficult == 3 && move_counter == 15)
                        {
                            Console.SetCursorPosition(40, 6);
                            Console.Write("Game is over!   Total score is: " + score);
                            Console.SetCursorPosition(1, 10);
                            Console.WriteLine("Please press ESC to return menu!");
                            do
                            {
                                keyInfo = Console.ReadKey(true);
                            } while (keyInfo.Key != ConsoleKey.Escape);
                            break;
                        }
                        else if (difficult == 4 && move_counter == 10)
                        {
                            Console.SetCursorPosition(40, 6);
                            Console.Write("Game is over!   Total score is: " + score);
                            Console.SetCursorPosition(1, 10);
                            Console.WriteLine("Please press ESC to return menu!");
                            do
                            {
                                keyInfo = Console.ReadKey(true);
                            } while (keyInfo.Key != ConsoleKey.Escape);
                            break;
                        }
                        else if (keyInfo.Key == ConsoleKey.Escape)
                        {
                            Console.SetCursorPosition(40, 6);
                            Console.Write("Game is over!   Total score is: " + score);
                            Console.SetCursorPosition(1, 10);
                            Console.WriteLine("Please press ESC to return menu!");
                            do
                            {
                                keyInfo = Console.ReadKey(true);
                            } while (keyInfo.Key != ConsoleKey.Escape);
                            break;
                        }
                    } while (keyInfo.Key != ConsoleKey.Escape);
                    score = 0;
                    counternumber = 0;
                    difficult = 0;
                    counterrepointnumber1 = counterrepointnumber2 = 0;
                }
                else if (menu == 5)
                {
                    Console.Clear();
                    Console.WriteLine("Are you sure to leave? (Y or N)");
                    do
                    {
                        keyInfo = Console.ReadKey(true);
                    } while (keyInfo.Key != ConsoleKey.Y && keyInfo.Key != ConsoleKey.N);
                    if (keyInfo.Key == ConsoleKey.Y)
                        return;
                }
            }
        }
    }
}