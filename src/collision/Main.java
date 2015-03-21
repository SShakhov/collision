package collision;

import java.awt.geom.Point2D;

public class Main
{
	public static void main(String[] args)
	{
		Ball ball1 = new Ball(0, 0, .5, 0, 1, 1);
		Ball ball2 = new Ball(-5, 0, 1, 0, 1, 1);
		
		//System.out.println(BallCollider.checkCollision(ball1, ball2));
		Point2D[] vels = BallCollider.getVelocities(ball1, ball2);
		System.out.println("Ball #1: Vx = " + vels[0].getX() + ", Vy = " + 
						vels[0].getY() + "\nBall #2: Vx = " + vels[1].getX() + ", Vy = " + 
						vels[1].getY());
	}
}