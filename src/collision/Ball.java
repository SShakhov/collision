package collision;

public class Ball
{
	public double x, y;
	public double vx, vy;
	private double m, r;
	
	public Ball(double x, double y, double vx, double vy, double m, double r)
			//throws IllegalArgumentException
	{
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		setMass(m);
		setRadius(r);
	}
	
	public void setMass(double m)
	{
		if (m <= 0)
			throw new IllegalArgumentException("Mass must be positive");
		else
			this.m = m;
	}
	
	public void setRadius(double r)
	{
		if (r <= 0)
			throw new IllegalArgumentException("Radius must be positive");
		else
			this.r = r;
	}
	
	public double getMass()
	{
		return m;
	}
	
	public double getRadius()
	{
		return r;
	}
}