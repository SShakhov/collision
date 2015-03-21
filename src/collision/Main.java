package collision;

public class Main
{
	public static void main(String[] args)
	{
		Ball ball1 = new Ball(0, 0, .5, 0, 1, 1);
		Ball ball2 = new Ball(-5, 0, 1, 0, 1, 1);
		
		BallCollider bc = new BallCollider(ball1, ball2);
		
		System.out.println("Ball #1: Vx = " + bc.getBall1VelocityX() + ", Vy = " + 
						bc.getBall1VelocityY());
		System.out.println("Ball #2: Vx = " + bc.getBall2VelocityX() + ", Vy = " + 
				bc.getBall2VelocityY());
	}
}