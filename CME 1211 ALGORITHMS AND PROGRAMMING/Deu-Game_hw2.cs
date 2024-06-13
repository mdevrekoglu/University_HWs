using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Trial_1
{
    class Program
    {
        static void Main(string[] args)
        {
            //These are the variables
            char[] a1 = new char[15];
            char[] a2 = new char[15];
            char[] a3 = new char[15];
            int k1 = -1, k2 = -1, k3 = -1;
            int random_1 = 0, random_2 = 0;
            int player_1 = 120, player_2 = 120;
            int counter = 0, mod;
            bool stop = false;

            Random rnd = new Random();
            Console.WriteLine();

            //The most important loop in this program.
            while (!stop)
            {
                //Console.Readline(); //If we want to see progress step by step we can delete the first two side line.
                if (k1 != 14 && k2 != 14 && k3 != 14)
                {
                    random_1 = rnd.Next(1, 4);
                    random_2 = rnd.Next(1, 4);
                }
                else if (k1 == 14 && k2 != 14 && k3 != 14)
                {
                    do
                    {
                        random_1 = rnd.Next(1, 4);
                    } while (random_1 == 1);
                    random_2 = rnd.Next(1, 4);
                }
                else if (k1 != 14 && k2 == 14 && k3 != 14)
                {
                    do
                    {
                        random_1 = rnd.Next(1, 4);
                    } while (random_1 == 2);
                    random_2 = rnd.Next(1, 4);
                }
                else if (k1 != 14 && k2 != 14 && k3 == 14)
                {
                    do
                    {
                        random_1 = rnd.Next(1, 4);
                    } while (random_1 == 3);
                    random_2 = rnd.Next(1, 4);
                }
                else if (k1 != 14 && k2 == 14 && k3 == 14)
                {
                    random_1 = 1;
                    random_2 = rnd.Next(1, 4);
                }
                else if (k1 == 14 && k2 != 14 && k3 == 14)
                {
                    random_1 = 2;
                    random_2 = rnd.Next(1, 4);
                }
                else if (k1 == 14 && k2 == 14 && k3 != 14)
                {
                    random_1 = 3;
                    random_2 = rnd.Next(1, 4);
                }
                else if (k1 == 14 && k2 == 14 && k3 == 14)
                {
                    Console.WriteLine();
                    Console.WriteLine("TIE");
                    Console.WriteLine();
                    score_table_writer(0, 0, 0);
                    stop = true;
                    continue;
                }

                //Thanks to mod we can select which player is playing at the moment.
                counter++;
                mod = counter % 2;
                if (mod == 1)
                    player_1 -= 5;
                else
                    player_2 -= 5;
                if (mod == 0)
                    mod = 2;
                Console.WriteLine();
                Console.WriteLine("Player{0}:             (P1-{1} P2-{2})", mod, player_1, player_2);

                //
                if (random_1 == 1)
                {
                    k1++;
                    a1[k1] = assign(random_2);
                    writer(random_1, random_2, k1, k2, k3, a1, a2, a3);
                }
                else if (random_1 == 2)
                {
                    k2++;
                    a2[k2] = assign(random_2);
                    writer(random_1, random_2, k1, k2, k3, a1, a2, a3);
                }
                else if (random_1 == 3)
                {
                    k3++;
                    a3[k3] = assign(random_2);
                    writer(random_1, random_2, k1, k2, k3, a1, a2, a3);
                }

                stop = checker(k1, k2, k3, a1, a2, a3);
                if(stop == true)
                {
                    score_table_writer(player_1, player_2, mod);
                }
            }
            Console.ReadLine();
        }

        //It writes hole line.
        public static void writer(int random_1, int random_2, int k1, int k2, int k3, char[] a1, char[] a2, char[] a3)
        {
            Console.Write("A1:");
            for (int i = 0; i <= k1; i++)
            {
                Console.Write(" ");
                Console.Write(a1[i]);
            }
            Console.WriteLine();
            Console.Write("A2:");
            for (int i = 0; i <= k2; i++)
            {
                Console.Write(" ");
                Console.Write(a2[i]);
            }
            Console.WriteLine();
            Console.Write("A3:");
            for (int i = 0; i <= k3; i++)
            {
                Console.Write(" ");
                Console.Write(a3[i]);
            }
            Console.WriteLine();
        }

        //It checks if the has been finished.
        public static bool checker(int k1, int k2, int k3, char[] a1, char[] a2, char[] a3)
        {
            bool stop = false;
            int a, b, c;
            int countery = -1;

            while (k1 > countery && k2 > countery && k3 > countery)
            {
                countery++;//ortak y uzunluğu
            }
            for (int i = 0; i <= countery; i++)//vertical check
            {
                a = a1[i];
                b = a2[i];
                c = a3[i];
                if ((a == 68 && b == 69 && c == 85) || (a == 85 && b == 69 && c == 68))
                {
                    stop = true;
                    break;
                }
            }
            if(k1 >= 2)
            {
                for (int i = 0; i <= k1; i++)
                {
                    if (i + 2 > 14)
                        break;
                    a = a1[i];
                    b = a1[i + 1];
                    c = a1[i + 2];
                    if ((a == 68 && b == 69 && c == 85) || (a == 85 && b == 69 && c == 68))
                    {
                        stop = true;
                        break;
                    }
                    if (i + 2 == k1)
                        break;
                }
            }
            if (k2 >= 2)
            {
                for (int i = 0; i <= k2; i++)
                {
                    if (i + 2 > 14)
                        break;
                    a = a2[i];
                    b = a2[i + 1];
                    c = a2[i + 2];
                    if ((a == 68 && b == 69 && c == 85) || (a == 85 && b == 69 && c == 68))
                    {
                        stop = true;
                        break;
                    }
                }
            }
            if (k3 >= 2)
            {
                for (int i = 0; i <= k3; i++)
                {
                    if (i + 2 > 14)
                        break;
                    a = a3[i];
                    b = a3[i + 1];
                    c = a3[i + 2];
                    if ((a == 68 && b == 69 && c == 85) || (a == 85 && b == 69 && c == 68))
                    {
                        stop = true;
                        break;
                    }
                }
            }
            for (int i = 0; i <= k3 - 2; i++)
            {
                if (i + 2 > 14)
                    break;
                a = a1[i];
                b = a2[i + 1];
                c = a3[i + 2];
                if ((a == 68 && b == 69 && c == 85) || (a == 85 && b == 69 && c == 68))
                {
                    stop = true;
                    break;
                }
            }
            for (int i = 0; i <= k1 - 2; i++)
            {
                if (i + 2 > 14)
                    break;
                a = a1[i + 2];
                b = a2[i + 1];
                c = a3[i];
                if ((a == 68 && b == 69 && c == 85) || (a == 85 && b == 69 && c == 68))
                {
                    stop = true;
                    break;
                }
            }
            return stop;
        }

        //It creates char.
        public static char assign(int random_2)
        {
            char a = ' ';
            switch (random_2)
            {
                case 1:
                    {
                        a = 'D';
                        break;
                    }

                case 2:
                    {
                        a = 'E';
                        break;
                    }

                case 3:
                    {
                        a = 'U';
                        break;
                    }
            }
            return a;
        }

        //It writes the high score table.
        public static void score_table_writer(int point_1, int point_2, int winner)
        {
            //winner 1 2 0
            string[] name = { "Derya", "Elife", "Fatih", "Ali", "Azra", "Sibel", "Cem", "Nazan", "Mehmet", "Nil", "Can", "Tarkan" };
            int[] score = { 100, 100, 95, 90, 85, 80, 80, 70, 55, 50, 30, 30 };
            int point = 0;
            if (winner == 1)
                point = point_1;
            else if (winner == 2)
                point = point_2;

            Console.WriteLine();
            Console.WriteLine("Name           |Score");
            Console.WriteLine("---------------------");
            

            if (point > 100)
            {
                winner = 0;
                Console.WriteLine("Player1        |{0}", point);
            }
            if (winner == 0)
            {
                for (int u = 0; u < 12; u++)
                {                  
                    Console.Write(name[u]);
                    for (int o = 0; o < 15 - name[u].Length; o++)
                    {
                        Console.Write(" ");
                    }
                    Console.WriteLine("|" + score[u]);
                }
            }
            else
            {
                for (int u = 0; u < 12; u++)
                {
                    Console.Write(name[u]);
                    for (int o = 0; o < 15 - name[u].Length; o++)
                    {
                        Console.Write(" ");
                    }
                    Console.WriteLine("|" + score[u]);
                    if ((point <= 30 && u == 11) || (point <= score [u] && point > score[u + 1]))
                    {                        
                        Console.WriteLine("Player{0}        |{1}", winner, point);
                    }                    
                }
            }
        }
    }
}
