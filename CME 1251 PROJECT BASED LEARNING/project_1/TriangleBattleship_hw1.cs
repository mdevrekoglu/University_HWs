using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;

namespace Menu_Trail
{
    class Program
    {
        static void Main(string[] args)
        {

            //All of these are variables that we use to store some mathematical results.
            int ax = 0, bx = 0, cx = 0, ay = 0, by = 0, cy = 0;
            int intarea = 0;
            double ax_length = 0, bx_length = 0, cx_length = 0;
            double ay_length = 0, by_length = 0, cy_length = 0;
            double a_lengthd = 0, b_lengthd = 0, c_lengthd = 0;
            double a_length = 0, b_length = 0, c_length = 0;
            double abc_length2 = 0;
            double inscribedr = 0, inscribedrarea = 0;
            double circumscribedr = 0, circumscribedarea = 0;
            double area = 0;
            double A_cos = 0, B_cos = 0, C_cos = 0;
            double A_radian = 0, B_radian = 0, C_radian = 0;
            double A_degree = 0, B_degree = 0, C_degree = 0;
            int intA_degree = 0, intB_degree = 0, intC_degree = 0;
            double A_med_x = 0, B_med_x = 0, C_med_x = 0;
            double A_med_y = 0, B_med_y = 0, C_med_y = 0;
            double x_centroid = 0, y_centroid = 0;
            double BA_degree = 0, BA_length = 0, BA_radian = 0;
            int ship_created = 0, ship_win_counter = 0, ship_lose_counter = 0;
            int pointNK = 60, pointAK = 30, pointSA = 10, pointPR = 0;
            string namePR, namePR_v2;
            string typeofship1 = "", typeofship2 = "";
            int sx = 0, sy = 0;
            double AS_xlength = 0, AS_ylength = 0, AS_length = 0;
            double BS_xlength = 0, BS_ylength = 0, BS_length = 0;
            double CS_xlength = 0, CS_ylength = 0, CS_length = 0;
            double AS_cos = 0, AS_degree = 0;
            double BS_cos = 0, BS_degree = 0;
            double CS_cos = 0, CS_degree = 0;
            int S_degree = 0;

            //These are the codes that change console color and create a sound when program start.
            Console.BackgroundColor = ConsoleColor.White;
            Console.Clear();
            Console.ForegroundColor = ConsoleColor.Black;
            Console.Beep();

            //These are the codes that takes player name.
            Console.Write("Please enter your name: ");
            namePR = Console.ReadLine();

            //These are the codes that prepare player name for score table.
            namePR_v2 = namePR;
            for (int i = 0; i < 25 - namePR.Length; i++)
            {
                namePR_v2 = namePR_v2 + " ";
            }

            //To use random library.
            Random rnd = new Random();

            //Infinite loop.
            while (true)
            {

                //To clear console.
                Console.Clear();

                //Menu.
                Console.ForegroundColor = ConsoleColor.DarkBlue;
                Console.WriteLine("~~Welcome " + namePR + "!~~");
                Console.ForegroundColor = ConsoleColor.Black;
                Console.WriteLine("");
                Console.WriteLine("              - MENU -");
                Console.WriteLine("PLEASE SELECT FROM THE OPTIONS BELOW:");
                Console.WriteLine("1 – Enter ship location");
                Console.WriteLine("2 – Ship info");
                Console.WriteLine("3 – Shoot at the ship");
                Console.WriteLine("4 – Show high score table");
                Console.WriteLine("5 – Change name");
                Console.WriteLine("6 – Exit");

                //Input part.
                Console.WriteLine("");
                Console.ForegroundColor = ConsoleColor.DarkMagenta;
                Console.Write("Option:");
                int menu = Convert.ToInt32(Console.ReadLine());
                Console.ForegroundColor = ConsoleColor.Black;

                //Entering ship location.
                if (menu == 1)
                {
                    // This one clears the console.
                    Console.Clear();

                    //Resets old score and counters.
                    ship_win_counter = 0;
                    ship_lose_counter = 0;
                    pointPR = 0;

                    //If player enters something wrong. Thanks to these codes the program will contunie to work.
                    try
                    {
                        //The place that we take the coordinates of the ship from player. And we convert all variables from string to Int.
                        Console.Write("Please enter the x coordinate of point A:");
                        ax = Convert.ToInt32(Console.ReadLine());
                        Console.Write("Please enter the y coordinate of point A:");
                        ay = Convert.ToInt32(Console.ReadLine());
                        Console.Write("Please enter the x coordinate of point B:");
                        bx = Convert.ToInt32(Console.ReadLine());
                        Console.Write("Please enter the y coordinate of point B:");
                        by = Convert.ToInt32(Console.ReadLine());
                        Console.Write("Please enter the x coordinate of point C:");
                        cx = Convert.ToInt32(Console.ReadLine());
                        Console.Write("Please enter the y coordinate of point C:");
                        cy = Convert.ToInt32(Console.ReadLine());
                    }
                    catch
                    {
                        Console.WriteLine("");
                        Console.WriteLine("Coordinates you enter must be intiger. Please try again!");
                        Console.WriteLine("Please type anything to return menu!");
                        Console.ReadLine();
                        continue;
                    }

                    //These are the codes which we use to calculate the length of a edge by using Pythagorean Theorem and some math functions.
                    ax_length = Math.Pow(bx - cx, 2);
                    ay_length = Math.Pow(by - cy, 2);
                    a_lengthd = Math.Sqrt(ay_length + ax_length);
                    a_length = Math.Round(a_lengthd, 2);

                    //These are the codes which we use to calculate the length of b edge by using Pythagorean Theorem and some math functions.
                    bx_length = Math.Pow(ax - cx, 2);
                    by_length = Math.Pow(ay - cy, 2);
                    b_lengthd = Math.Sqrt(by_length + bx_length);
                    b_length = Math.Round(b_lengthd, 2);

                    //These are the codes which we use to calculate the length of c edge by using Pythagorean Theorem and some math functions.
                    cx_length = Math.Pow(ax - bx, 2);
                    cy_length = Math.Pow(ay - by, 2);
                    c_lengthd = Math.Sqrt(cy_length + cx_length);
                    c_length = Math.Round(c_lengthd, 2);

                    //These are the codes which we use to calculate the degree of a by using Law of Cosines and "Acos()" function.
                    A_cos = (Math.Pow(b_lengthd, 2) + Math.Pow(c_lengthd, 2) - Math.Pow(a_lengthd, 2)) / (b_lengthd * c_lengthd * 2);
                    A_radian = Math.Acos(A_cos);
                    A_degree = (180 / Math.PI) * A_radian;
                    A_degree = Math.Round(A_degree, 2);

                    //These are the codes which we use to calculate the degree of b by using Law of Cosines and "Acos()" function.
                    B_cos = (Math.Pow(a_lengthd, 2) + Math.Pow(c_lengthd, 2) - Math.Pow(b_lengthd, 2)) / (a_lengthd * c_lengthd * 2);
                    B_radian = Math.Acos(B_cos);
                    B_degree = (180 / Math.PI) * B_radian;
                    B_degree = Math.Round(B_degree, 2);

                    //These are the codes which we use to calculate the degree of c by using Law of Cosines and "Acos()" function.
                    C_cos = (Math.Pow(a_lengthd, 2) + Math.Pow(b_lengthd, 2) - Math.Pow(c_lengthd, 2)) / (a_lengthd * b_lengthd * 2);
                    C_radian = Math.Acos(C_cos);
                    C_degree = (180 / Math.PI) * C_radian;
                    C_degree = Math.Round(C_degree, 2);


                    if (ax > 30 || ax <= 0 || bx > 30 || bx <= 0 || cx > 30 || cx <= 0)//This one checks If we choose x coordinates correctly.
                    {
                        Console.WriteLine("");
                        ship_created = 0;
                        Console.WriteLine("Your points are not according to the rules of game!");
                        Console.WriteLine("Please type anything to return menu!");
                        Console.ReadLine();
                    }
                    else if (ay > 12 || ay <= 0 || by > 12 || by <= 0 || cy > 12 || cy <= 0)//This one checks If we choose y coordinates correctly.
                    {
                        Console.WriteLine("");
                        ship_created = 0;
                        Console.WriteLine("Your points are not according to the rules of game!");
                        Console.WriteLine("Please type anything to return menu!");
                        Console.ReadLine();
                    }
                    else if (A_degree == 180 || B_degree == 180 || C_degree == 180 || A_degree == 0 || B_degree == 0 || C_degree == 0)//This one checks If we create line.
                    {
                        Console.WriteLine("");
                        ship_created = 0;
                        Console.WriteLine("The points you selected do not form a triangle!");
                        Console.WriteLine("Please type anything to return menu!");
                        Console.ReadLine();
                    }
                    else if (Math.Abs(a_lengthd - b_lengthd) >= c_lengthd || a_lengthd + b_lengthd <= c_lengthd)//This one checks If the coordinates create a ship.
                    {
                        Console.WriteLine("");
                        ship_created = 0;
                        Console.WriteLine("The points you selected do not form a triangle!");
                        Console.WriteLine("Please type anything to return menu!");
                        Console.ReadLine();
                    }
                    else if (Math.Abs(c_lengthd - b_lengthd) >= a_lengthd || c_lengthd + b_lengthd <= a_lengthd)//This one checks If the coordinates create a ship.
                    {
                        Console.WriteLine("");
                        ship_created = 0;
                        Console.WriteLine("The points you selected do not form a triangle!");
                        Console.WriteLine("Please type anything to return menu!");
                        Console.ReadLine();
                    }
                    else if (Math.Abs(a_lengthd - c_lengthd) >= b_lengthd || a_lengthd + c_lengthd <= b_lengthd)//This one checks If the coordinates create a ship.
                    {
                        Console.WriteLine("");
                        ship_created = 0;
                        Console.WriteLine("The points you selected do not form a triangle!");
                        Console.WriteLine("Please type anything to return menu!");
                        Console.ReadLine();
                    }
                    else//These are the codes that inform the user.
                    {
                        //This code allows us to reach second and third option
                        ship_created = 1;

                        //These are the codes which we use to calculate the ship's area by using some math functions.
                        abc_length2 = (c_length + b_length + a_length) / 2;
                        area = Math.Sqrt((abc_length2 - a_length) * (abc_length2 - b_length) * (abc_length2 - c_length) * abc_length2);
                        intarea = Convert.ToInt32(area);

                        //These are the codes which we use to calculate median points of the ship. We used 2d as a divisive to get more accurate result.
                        A_med_x = (bx + cx) / 2d;
                        A_med_y = (by + cy) / 2d;
                        B_med_x = (ax + cx) / 2d;
                        B_med_y = (ay + cy) / 2d;
                        C_med_x = (ax + bx) / 2d;
                        C_med_y = (ay + by) / 2d;

                        //These are the codes which we use to calculate the centroid of the ship. 
                        x_centroid = (ax + bx + cx) / 3d;
                        y_centroid = (ay + by + cy) / 3d;
                        x_centroid = Math.Round(x_centroid, 2);
                        y_centroid = Math.Round(y_centroid, 2);

                        //These are the codes that calculates length of the bisector of the point A
                        BA_degree = (180 - B_degree) - (A_degree / 2);
                        BA_radian = (Math.PI * BA_degree) / 180;
                        BA_length = (c_length * Math.Sin(B_radian)) / Math.Sin(BA_radian);
                        BA_length = Math.Round(BA_length, 2);

                        //This converts the degrees to integer.
                        intA_degree = Convert.ToInt32(A_degree);
                        intB_degree = Convert.ToInt32(B_degree);
                        intC_degree = Convert.ToInt32(C_degree);

                        //These are the codes that calculates length of the inscribedr circle's area
                        inscribedr = area / abc_length2;
                        inscribedrarea = Math.Round((Math.PI * (inscribedr * inscribedr)), 2);

                        //These are the codes that calculate the area of circumsricbed circle.
                        circumscribedr = (a_length * b_length * c_length) / (4 * area);
                        circumscribedarea = Math.Round((Math.PI * (circumscribedr * circumscribedr)), 2);


                        //These are the conditions which calculates properties of Triangle.
                        if (a_length == b_length && b_length == c_length)
                        {
                            typeofship1 = "Equilateral";
                        }
                        else if ((a_length == b_length && b_length != c_length) || (a_length == c_length && a_length != b_length) || (b_length == c_length && a_length != c_length))
                        {
                            typeofship1 = "Isosceles";
                        }
                        else if (a_length != b_length && a_length != c_length && b_length != c_length)
                        {
                            typeofship1 = "Scalene";
                        }

                        //These are the conditions which calculates properties of Triangle.
                        if (intA_degree > 90 || intB_degree > 90 || intC_degree > 90)
                        {
                            typeofship2 = "Obtuse-Angled";
                        }
                        else if (intA_degree == 90 || intB_degree == 90 || intC_degree == 90)
                        {
                            typeofship2 = "Right-Angled";
                        }
                        else if (intA_degree < 90 && intB_degree < 90 && intC_degree < 90)
                        {
                            typeofship2 = "Acute-Angled";
                        }

                        //This one informs the player that the ship has created.
                        Console.WriteLine("");
                        Console.WriteLine("Ship has been created. Please type anything to return menu!");
                        Console.ReadLine();
                    }                    
                }

                //If user selects menu 2 without entering ship location, this one will work and will worn user.
                if (menu == 2 && ship_created == 0)
                {
                    //EXİT
                    Console.Clear();
                    Console.WriteLine("Player have to enter ship's location. Please type anything to return menu!");
                    Console.ReadLine();
                    Console.Clear();
                }

                //These are the codes that create coordinate system and properties of the ship. This one works if user enters ship location.
                if (menu == 2 && ship_created == 1)
                {
                    Console.Clear();

                    Console.ForegroundColor = ConsoleColor.DarkMagenta;
                    Console.WriteLine("~~Ship Info~~");
                    Console.ForegroundColor = ConsoleColor.Black;
                    Console.WriteLine("");

                    //These are the codes which create the coordinate's y system.
                    int lengthy = 12;
                    int numbery = 12;
                    for (int u = 0; u < lengthy; u++)
                    {
                        if (numbery > 9)
                        {
                            Console.Write("  " + numbery + "|");
                        }
                        else
                        {
                            Console.Write("   " + numbery + "|");
                        }
                        numbery--;
                        Console.WriteLine("");
                    }

                    //These are the codes which create the coordinate's x system.
                    Console.Write("    +");
                    int lengthx1 = 31;
                    for (int o = 0; o < lengthx1 - 1; o++)
                    {
                        Console.Write("-");
                    }

                    Console.WriteLine("");
                    Console.Write("     ");

                    int lengthx2 = 30;
                    int numberx = 1;
                    for (int i = 0; i < lengthx2; i++)
                    {
                        if (numberx >= 10)
                        {
                            numberx -= 10;
                            Console.Write(numberx);
                            numberx++;
                        }
                        else
                        {
                            Console.Write(numberx);
                            numberx++;
                        }
                    }
                    //These are the codes which place the points.
                    Console.ForegroundColor = ConsoleColor.DarkBlue;
                    Console.SetCursorPosition(ax + 4, 14 - ay);
                    Console.WriteLine("A");
                    Console.SetCursorPosition(bx + 4, 14 - by);
                    Console.WriteLine("B");
                    Console.SetCursorPosition(cx + 4, 14 - cy);
                    Console.WriteLine("C");
                    Console.ForegroundColor = ConsoleColor.Black;

                    //This one sets cursor positions under the coordinate system.
                    Console.SetCursorPosition(0, 16);

                    //These are the codes which shows us properties of the ship.
                    Console.WriteLine("");
                    Console.WriteLine("1-The size of the ship: a=" + a_length + " b=" + b_length + " c=" + c_length);
                    Console.WriteLine("2-The perimeter of the ship: " + (a_length + b_length + c_length));
                    Console.WriteLine("3-The area of the ship: " + intarea);
                    Console.WriteLine("4-The angles of the ship: A=" + A_degree + "° B=" + B_degree + "° C=" + C_degree + "°");
                    Console.WriteLine("5-The median points: ('" + A_med_x + "','" + A_med_y + "') " + "('" + B_med_x + "','" + B_med_y + "') ('" + C_med_x + "','" + C_med_y + "')");
                    Console.WriteLine("6-The centroid of the ship: ('" + x_centroid + "','" + y_centroid + "')");
                    Console.WriteLine("7-The length of the bisector of the point A: " + BA_length);
                    Console.WriteLine("8-The area of the inscribed circle: " + inscribedrarea);
                    Console.WriteLine("9-The area of circumscribed circle: " + circumscribedarea);
                    Console.WriteLine("10-The type of the ship: " + typeofship1 + " (" + typeofship2 + ")");
                    Console.WriteLine("");
                    Console.WriteLine("");

                    //This one informs the player that the properties of the ship has listed.
                    Console.WriteLine("Properties of the ship has been shown. Please type anything to return menu!");
                    Console.ReadLine();
                    Console.Clear();
                }

                //If user selects menu 3 without entering ship location, this one will work and will worn user.
                if (menu == 3 && ship_created == 0)
                {
                    //EXİT
                    Console.Clear();
                    Console.WriteLine("Player have to enter ship's location. Please type anything to return menu!");
                    Console.ReadLine();
                }

                //These are the game codes. This one works if user enters ship location.
                if (menu == 3 && ship_created == 1)
                {
                    Console.Clear();

                    //These are the codes which creates random shoot.
                    sx = rnd.Next(1, 31);
                    sy = rnd.Next(1, 13);

                    Console.ForegroundColor = ConsoleColor.DarkMagenta;
                    Console.WriteLine("SHOOT(" + sx + "," + sy + ")");
                    Console.WriteLine("");
                    Console.ForegroundColor = ConsoleColor.Black;

                    //These are the codes which create the coordinate's y system.
                    int lengthy = 12;
                    int numbery = 12;
                    for (int u = 0; u < lengthy; u++)
                    {
                        if (numbery > 9)
                        {
                            Console.Write("  " + numbery + "|");
                        }
                        else
                        {
                            Console.Write("   " + numbery + "|");
                        }
                        numbery--;
                        Console.WriteLine("");
                    }

                    //These are the codes which create the coordinate's x system.
                    Console.Write("    +");
                    int lengthx1 = 31;
                    for (int o = 0; o < lengthx1 - 1; o++)
                    {
                        Console.Write("-");
                    }

                    Console.WriteLine("");
                    Console.Write("     ");

                    int lengthx2 = 30;
                    int numberx = 1;
                    for (int i = 0; i < lengthx2; i++)
                    {
                        if (numberx >= 10)
                        {
                            numberx -= 10;
                            Console.Write(numberx);
                            numberx++;
                        }
                        else
                        {
                            Console.Write(numberx);
                            numberx++;
                        }
                    }
                    //These are the codes which place the points.
                    Console.ForegroundColor = ConsoleColor.DarkBlue;
                    Console.SetCursorPosition(ax + 4, 14 - ay);
                    Console.WriteLine("A");
                    Console.SetCursorPosition(bx + 4, 14 - by);
                    Console.WriteLine("B");
                    Console.SetCursorPosition(cx + 4, 14 - cy);
                    Console.WriteLine("C");
                    Console.ForegroundColor = ConsoleColor.Red;
                    Console.SetCursorPosition(sx + 4, 14 - sy);
                    Console.WriteLine("X");
                    Console.ForegroundColor = ConsoleColor.Black;

                    //This one sets cursor positions under the coordinate system.
                    Console.SetCursorPosition(0, 16);
                    Console.WriteLine("");


                    if ((ax == sx && ay == sy) || (bx == sx && by == sy) || (cx == sx && cy == sy))//This code works if the ship is hit from a corner.
                    {
                        pointPR = 0;
                        ship_lose_counter++;
                        ship_win_counter = 0;
                        Console.WriteLine("Your ship sank! Total score is: 0");
                        Console.WriteLine("Lose streak: " + ship_lose_counter);
                        Console.WriteLine("");
                        Console.WriteLine("");
                        Console.ForegroundColor = ConsoleColor.DarkMagenta;
                        Console.WriteLine("HIGH SCORE TABLE");
                        Console.ForegroundColor = ConsoleColor.Black;
                        Console.WriteLine("");
                        Console.WriteLine("NAME                     SCORE");
                        Console.WriteLine("Nazan Kaya               " + pointNK);
                        Console.WriteLine("Ali Kurt                 " + pointAK);
                        Console.WriteLine("Sibel Arslan             " + pointSA);
                        //EXİT
                        Console.WriteLine("");
                        Console.WriteLine("The HİGH SCORE TABLE has been shown. Please type anything to return menu!");
                        Console.ReadLine();
                    }
                    else//Else.
                    {
                        //These are the codes that calculates length between A point and X point  AS_length
                        AS_xlength = Math.Pow(ax - sx, 2);
                        AS_ylength = Math.Pow(ay - sy, 2);
                        AS_length = Math.Sqrt(AS_xlength + AS_ylength);

                        //These are the codes that calculates length between B point and X point  BS_length
                        BS_xlength = Math.Pow(bx - sx, 2);
                        BS_ylength = Math.Pow(by - sy, 2);
                        BS_length = Math.Sqrt(BS_xlength + BS_ylength);

                        //These are the codes that calculates length between C point and X point  CS_length
                        CS_xlength = Math.Pow(cx - sx, 2);
                        CS_ylength = Math.Pow(cy - sy, 2);
                        CS_length = Math.Sqrt(CS_xlength + CS_ylength);

                        //These are the codes which we use to calculate the degree of a by using Law of Cosines and "AScos()" function.
                        AS_cos = (Math.Pow(CS_length, 2) + Math.Pow(BS_length, 2) - Math.Pow(a_lengthd, 2)) / (BS_length * CS_length * 2);
                        AS_degree = (180 / Math.PI) * Math.Acos(AS_cos);

                        //These are the codes which we use to calculate the degree of a by using Law of Cosines and "BScos()" function.
                        BS_cos = (Math.Pow(AS_length, 2) + Math.Pow(CS_length, 2) - Math.Pow(b_lengthd, 2)) / (AS_length * CS_length * 2);
                        BS_degree = (180 / Math.PI) * Math.Acos(BS_cos);

                        //These are the codes which we use to calculate the degree of a by using Law of Cosines and "CScos()" function.
                        CS_cos = (Math.Pow(AS_length, 2) + Math.Pow(BS_length, 2) - Math.Pow(c_lengthd, 2)) / (AS_length * BS_length * 2);
                        CS_degree = (180 / Math.PI) * Math.Acos(CS_cos);

                        //This code sum up all degrees.
                        S_degree = Convert.ToInt32(AS_degree + BS_degree + CS_degree);


                        if (S_degree == 360)//This one checks if ship is shooted.
                        {
                            pointPR = 0;
                            ship_lose_counter++;
                            ship_win_counter = 0;
                            Console.Beep();
                            Console.WriteLine("Your ship sank! Total score is: 0");
                            Console.WriteLine("Lose streak: " + ship_lose_counter);
                            Console.WriteLine("");
                            Console.WriteLine("");
                            Console.ForegroundColor = ConsoleColor.DarkMagenta;
                            Console.WriteLine("~~HIGH SCORE TABLE~~");
                            Console.ForegroundColor = ConsoleColor.Black;
                            Console.WriteLine("");
                            Console.WriteLine("NAME                     SCORE");
                            Console.WriteLine("Nazan Kaya               " + pointNK);
                            Console.WriteLine("Ali Kurt                 " + pointAK);
                            Console.WriteLine("Sibel Arslan             " + pointSA);

                        }
                        else//This says "Your ship survived!" and creates high score table.
                        {
                            pointPR = intarea + (ship_win_counter) * 10;
                            ship_win_counter++;
                            ship_lose_counter = 0;
                            Console.WriteLine("Your ship survived! Total score is: " + pointPR);
                            Console.WriteLine("Win streak: " + ship_win_counter);
                            Console.WriteLine("");
                            Console.ForegroundColor = ConsoleColor.DarkMagenta;
                            Console.WriteLine("~~HIGH SCORE TABLE~~");
                            Console.ForegroundColor = ConsoleColor.Black;
                            Console.WriteLine("");
                            Console.WriteLine("NAME                     SCORE");

                            //These are the cases might happen for all situations.
                            if (pointPR > pointNK)
                            {
                                Console.WriteLine(namePR_v2 + pointPR);
                                Console.WriteLine("Nazan Kaya               " + pointNK);
                                Console.WriteLine("Ali Kurt                 " + pointAK);
                            }
                            else if ((pointPR > pointAK && pointPR < pointNK) || (pointPR == pointNK))
                            {
                                Console.WriteLine("Nazan Kaya               " + pointNK);
                                Console.WriteLine(namePR_v2 + pointPR);
                                Console.WriteLine("Ali Kurt                 " + pointAK);
                            }
                            else if ((pointPR > pointSA && pointPR < pointAK) || (pointPR == pointAK))
                            {
                                Console.WriteLine("Nazan Kaya               " + pointNK);
                                Console.WriteLine("Ali Kurt                 " + pointAK);
                                Console.WriteLine(namePR_v2 + pointPR);
                            }
                            else if (pointPR <= pointSA)
                            {
                                Console.WriteLine("Nazan Kaya               " + pointNK);
                                Console.WriteLine("Ali Kurt                 " + pointAK);
                                Console.WriteLine("Sibel Arslan             " + pointSA);
                            }

                            if(ship_win_counter == 5)//Music for player
                            {
                                Console.WriteLine();
                                Console.WriteLine();
                                Console.WriteLine("Congratulations you won five times in a row!!");
                                
                                //Mario game music.
                                Console.Beep(658, 125); Console.Beep(1320, 500); Console.Beep(990, 250); Console.Beep(1056, 250); Console.Beep(1188, 250); Console.Beep(1320, 125); Console.Beep(1188, 125); Console.Beep(1056, 250); Console.Beep(990, 250); Console.Beep(880, 500); Console.Beep(880, 250); Console.Beep(1056, 250); Console.Beep(1320, 500); Console.Beep(1188, 250); Console.Beep(1056, 250); Console.Beep(990, 750); Console.Beep(1056, 250); Console.Beep(1188, 500); Console.Beep(1320, 500); Console.Beep(1056, 500); Console.Beep(880, 500); Console.Beep(880, 500); Console.Beep(1188, 500); Console.Beep(1408, 250); Console.Beep(1760, 500); Console.Beep(1584, 250); Console.Beep(1408, 250); Console.Beep(1320, 750); Console.Beep(1056, 250); Console.Beep(1320, 500); Console.Beep(1188, 250); Console.Beep(1056, 250); Console.Beep(990, 500); Console.Beep(990, 250); Console.Beep(1056, 250); Console.Beep(1188, 500); Console.Beep(1320, 500); Console.Beep(1056, 500); Console.Beep(880, 500); Console.Beep(880, 500); Console.Beep(1320, 500); Console.Beep(990, 250); Console.Beep(1056, 250); Console.Beep(1188, 250); Console.Beep(1320, 125); Console.Beep(1188, 125); Console.Beep(1056, 250); Console.Beep(990, 250); Console.Beep(880, 500); Console.Beep(880, 250); Console.Beep(1056, 250); Console.Beep(1320, 500); Console.Beep(1188, 250); Console.Beep(1056, 250); Console.Beep(990, 750); Console.Beep(1056, 250); Console.Beep(1188, 500); Console.Beep(1320, 500); Console.Beep(1056, 500); Console.Beep(880, 500); Console.Beep(880, 500); Console.Beep(1188, 500); Console.Beep(1408, 250); Console.Beep(1760, 500); Console.Beep(1584, 250); Console.Beep(1408, 250); Console.Beep(1320, 750); Console.Beep(1056, 250); Console.Beep(1320, 500); Console.Beep(1188, 250); Console.Beep(1056, 250); Console.Beep(990, 500); Console.Beep(990, 250); Console.Beep(1056, 250); Console.Beep(1188, 500); Console.Beep(1320, 500); Console.Beep(1056, 500); Console.Beep(880, 500); Console.Beep(880, 500); ; Console.Beep(660, 1000); Console.Beep(528, 1000); Console.Beep(594, 1000); Console.Beep(495, 1000); Console.Beep(528, 1000); Console.Beep(440, 1000); Console.Beep(419, 1000); Console.Beep(495, 1000); Console.Beep(660, 1000); Console.Beep(528, 1000); Console.Beep(594, 1000); Console.Beep(495, 1000); Console.Beep(528, 500); Console.Beep(660, 500); Console.Beep(880, 1000); Console.Beep(838, 2000); Console.Beep(660, 1000); Console.Beep(528, 1000); Console.Beep(594, 1000); Console.Beep(495, 1000); Console.Beep(528, 1000); Console.Beep(440, 1000); Console.Beep(419, 1000); Console.Beep(495, 1000); Console.Beep(660, 1000); Console.Beep(528, 1000); Console.Beep(594, 1000); Console.Beep(495, 1000); Console.Beep(528, 500); Console.Beep(660, 500); Console.Beep(880, 1000); Console.Beep(838, 2000);
                            }
                        }
                        //EXİT
                        Console.WriteLine("");
                        Console.WriteLine("The HİGH SCORE TABLE has been shown. Please type anything to return menu!");
                        Console.ReadLine();
                    }
                }

                //This is the high score table. User can select it when you want.
                if (menu == 4)
                {
                    Console.Clear();

                    Console.ForegroundColor = ConsoleColor.DarkMagenta;
                    Console.WriteLine("~~HIGH SCORE TABLE~~");
                    Console.ForegroundColor = ConsoleColor.Black;
                    if (ship_win_counter == 0 && ship_lose_counter == 0)
                    {

                    }
                    else if (ship_win_counter != 0 && ship_lose_counter == 0)
                    {
                        Console.WriteLine("Win streak: " + ship_win_counter);
                    }
                    else if (ship_win_counter == 0 && ship_lose_counter != 0)
                    {
                        Console.WriteLine("Lose streak: " + ship_lose_counter);
                    }
                    Console.WriteLine("");
                    Console.WriteLine("NAME                     SCORE");
                    if (pointPR > pointNK)
                    {
                        Console.WriteLine(namePR_v2 + pointPR);
                        Console.WriteLine("Nazan Kaya               " + pointNK);
                        Console.WriteLine("Ali Kurt                 " + pointAK);
                    }
                    else if ((pointPR > pointAK && pointPR < pointNK) || (pointPR == pointNK))
                    {
                        Console.WriteLine("Nazan Kaya               " + pointNK);
                        Console.WriteLine(namePR_v2 + pointPR);
                        Console.WriteLine("Ali Kurt                 " + pointAK);
                    }
                    else if ((pointPR > pointSA && pointPR < pointAK) || (pointPR == pointAK))
                    {
                        Console.WriteLine("Nazan Kaya               " + pointNK);
                        Console.WriteLine("Ali Kurt                 " + pointAK);
                        Console.WriteLine(namePR_v2 + pointPR);
                    }
                    else if (pointPR <= pointSA)
                    {
                        Console.WriteLine("Nazan Kaya               " + pointNK);
                        Console.WriteLine("Ali Kurt                 " + pointAK);
                        Console.WriteLine("Sibel Arslan             " + pointSA);
                    }
                    //EXİT
                    Console.WriteLine("");
                    Console.WriteLine("The HİGH SCORE TABLE has been shown. Please type anything to return menu!");
                    Console.ReadLine();
                    Console.Clear();
                }

                //This menu is being used for change player name.
                if (menu == 5)
                {
                    Console.Clear();
                    //These are the codes that takes player name.
                    Console.Write("Please enter your name: ");
                    namePR = Console.ReadLine();

                    //These are the codes that prepare player name for score table.
                    namePR_v2 = namePR;
                    for (int i = 0; i < 25 - namePR.Length; i++)
                    {
                        namePR_v2 = namePR_v2 + " ";
                    }
                    Console.WriteLine("New name have been created. Please type anything to return menu!");
                    Console.Clear();
                }

                //EXİT
                if (menu == 6)
                {
                    Console.Beep();
                    return;
                }
            }
        }
    }
}