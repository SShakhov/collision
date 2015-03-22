package collision;

import java.awt.geom.Point2D;
import java.lang.Math;

public class BallCollider
{
	private Ball ball1, ball2;
	private double vxRel, vyRel;
	private int collisionType;
	private Point2D[] vels;
	
	public BallCollider(Ball ball1, Ball ball2)
	{
		double d = Math.sqrt(Math.pow(ball1.x - ball2.x, 2) + Math.pow(ball1.y - ball2.y, 2));
		if (d < ball1.getRadius() + ball2.getRadius())
			throw new IllegalArgumentException("Invalid initial position");
		
		this.ball1 = ball1;
		this.ball2 = ball2;
		
		//2nd relative to the 1st
		vxRel = ball2.vx - ball1.vx;
		vyRel = ball2.vy - ball1.vy;
		
		collisionType = checkCollision();
		
		if (collisionType >= 0)
			vels = getVelocities();
	}
	
	private Point2D getRelativeClosestPoint()
	{
		double solX, solY;
		
		//if moving away from ball - already closest
		if ((ball1.x - ball2.x) * vxRel + (ball1.y - ball2.y) * vyRel <= 0)
		{
			solX = ball2.x;
			solY = ball2.y;
		}
		//special case - (only) one relative velocity is zero
		else if (Math.pow(vxRel, 2) + Math.pow(vyRel, 2) != 0 && vxRel * vyRel == 0)
		{
			solX = (vxRel == 0) ? ball2.x : 0;
			solY = (vyRel == 0) ? ball2.y : 0;
		}
		else
		{
			//tangential to trajectory
			double k1 = vyRel / vxRel;
			//perpendicular to trajectory
			double k2 = - (vxRel / vyRel);
			
			//setting up system of linear equations
			double a11 = k1;
			double a12 = -1;
			double a21 = k2;
			double a22 = -1;
			
			double b1 = k1 * ball2.x - ball2.y;
			double b2 = k2 * ball1.x - ball1.y;
			
			//solution - closest point in relative coordinates
			solX = (b1*a22 - b2*a12) / (a11*a22 - a21*a12);
			solY = (a11*b2 - a21*b1) / (a11*a22 - a21*a12);
		}
		
		return new Point2D.Double(solX, solY);
	}
	
	private int checkCollision()
	{
		//-1 - no collision, 0 - central collision, 1 - non-central collision
		
		double eps = 0.0001;
		double criticalDistance = ball1.getRadius() + ball2.getRadius();
		Point2D relPoint = getRelativeClosestPoint();
		
		double minDistance = relPoint.distance(ball1.x, ball1.y);
		
		if (minDistance < eps)
			return 0;
		
		if (minDistance < criticalDistance)
			return 1;
		else
			return -1;
	}
	
	private Point2D getRelativeCollisionPoint()
	{
		if (checkCollision() < 0)
		{
			System.out.println("Error: no collision");
			return null;
		}
		
		Point2D relPoint = getRelativeClosestPoint();
		
		double h = Math.sqrt(Math.pow(ball1.getRadius() + ball2.getRadius(), 2) - 
				Math.pow(relPoint.distance(ball1.x, ball2.y), 2));
		
		double absV = Math.sqrt(Math.pow(vxRel, 2) + Math.pow(vyRel, 2));
		
		double colX = relPoint.getX() - h * (vxRel / absV);
		double colY = relPoint.getY() - h * (vyRel / absV);
		
		return new Point2D.Double(colX, colY);
	}
	
	private Point2D[] getVelocities()
	{
		Point2D colPoint = getRelativeCollisionPoint();
		Point2D[] vels = new Point2D[2];
		
		double h = Math.sqrt(Math.pow(colPoint.getX() - ball1.x, 2) + 
				Math.pow(colPoint.getY() - ball1.y, 2));
		
		//get velocities in new (rotated) coordinates
		double rotVx = ((ball1.x - colPoint.getX()) / h) * vxRel + 
				((ball1.y - colPoint.getY()) / h) * vyRel;
		double rotVy = ((ball1.x - colPoint.getX()) / h) * vyRel - 
				((ball1.y - colPoint.getY()) / h) * vxRel;
		
		double rotVx1 = 2 * ball2.getMass() / (ball1.getMass() + ball2.getMass()) * rotVx;
		double rotVx2 = (ball2.getMass() - ball1.getMass()) / (ball1.getMass() + ball2.getMass()) * rotVx;
		
		//back to original coordinates
		double Vx1 = ((ball1.x - colPoint.getX()) / h) * rotVx1;
		double Vy1 = ((ball1.y - colPoint.getY()) / h) * rotVx1;
		double Vx2 = ((ball1.x - colPoint.getX()) / h) * rotVx2 - 
				((ball1.y - colPoint.getY()) / h) * rotVy;
		double Vy2 = ((ball1.y - colPoint.getY()) / h) * rotVx2 + 
				((ball1.x - colPoint.getX()) / h) * rotVy;
		
		//back to even more original coordinates
		vels[0] = new Point2D.Double(Vx1 + ball1.vx, Vy1 + ball1.vy);
		vels[1] = new Point2D.Double(Vx2 + ball1.vx, Vy2 + ball1.vy);
		
		return vels;
	}
	
	public String getCollisionType()
	{
		switch(collisionType)
		{
			case -1:
				return "No collision";
			case 0:
				return "Central collision";
			default:
				return "Non-central collision";
		}
	}
	
	public double getBall1VelocityX()
	{
		if (collisionType < 0)
		{
			return ball1.vx;
		}
		return vels[0].getX();
	}
	
	public double getBall1VelocityY()
	{
		if (collisionType < 0)
		{
			return ball1.vy;
		}
		return vels[0].getY();
	}
	
	public double getBall2VelocityX()
	{
		if (collisionType < 0)
		{
			return ball2.vx;
		}
		return vels[1].getX();
	}
	
	public double getBall2VelocityY()
	{
		if (collisionType < 0)
		{
			return ball2.vy;
		}
		return vels[1].getY();
	}
}