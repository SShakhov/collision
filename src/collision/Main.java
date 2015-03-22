package collision;

import java.io.*;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Ball[] balls = new Ball[2];
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("src/collision/input.txt"));
			for(int i = 0; i < 2; i++)
			{
				String line = br.readLine();
				Scanner s = new Scanner(line);
				double x = s.nextDouble();
				double y = s.nextDouble();
				double vx = s.nextDouble();
				double vy = s.nextDouble();
				double m = s.nextDouble();
				double r = s.nextDouble();
				
				balls[i] = new Ball(x, y, vx, vy, m, r);
				
				s.close();
			}
			br.close();
			
			BallCollider bc = new BallCollider(balls[0], balls[1]);

			System.out.println(bc.getCollisionType());
			System.out.println("Ball #1: Vx = " + bc.getBall1VelocityX() + ", Vy = " + 
					bc.getBall1VelocityY());
			System.out.println("Ball #2: Vx = " + bc.getBall2VelocityX() + ", Vy = " + 
					bc.getBall2VelocityY());
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
			return;
		}
		catch(IOException e)
		{
			
		}
		
		catch(IllegalArgumentException e)
		{
			System.out.println(e);
		}
	}
}