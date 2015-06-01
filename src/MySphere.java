import org.jtrace.Constants;
import org.jtrace.Hit;
import org.jtrace.Jay;
import org.jtrace.Material;
import org.jtrace.NotHit;
import org.jtrace.geometry.Sphere;
import org.jtrace.primitives.Point3D;
import org.jtrace.primitives.Vector3D;

public class MySphere extends Sphere{
	
    private Point3D center;
    private double radius;
    private double height;

    public MySphere(Point3D center, double radius, double height, Material material) {
    	super(center, radius, material);
        this.center = center;
        this.radius = radius;
        this.height = height;
	}

	@Override
    public Hit hit(final Jay jay) {
        final Vector3D temp = new Vector3D(jay.getOrigin().subtract(center));

        final double a = jay.getDirection().dot();
        final double b = temp.multiply(2).dot(jay.getDirection());
        final double c = temp.dot() - radius * radius;

        final double delta = b * b - 4 * a * c;

        double t;

        if (delta < 0.0) {
            return new NotHit();
        } else {
        	final double deltaRoot = Math.sqrt(delta);

        	//smaller root
            t = (-b - deltaRoot) / 2*a;
            if (t > Constants.epsilon) {
                final Vector3D normal = temp.add(jay.getDirection().multiply(t)).divide(t);
                if(Math.abs(t) > this.height/2)
                	return new Hit(t, normal.normal());
                else return new NotHit();
                //return new Hit(t, normal.normal());
            }

            //larger root
            t = (-b + deltaRoot) / 2*a;
            if (t > Constants.epsilon) {
                final Vector3D normal = temp.add(jay.getDirection().multiply(t)).divide(t);
                if(Math.abs(t) > this.height/2)
                	return new Hit(t, normal.normal());
            }

            return new NotHit();
        }
    }
	
}
