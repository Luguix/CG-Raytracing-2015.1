import java.util.List;

import org.jtrace.Hit;
import org.jtrace.Jay;
import org.jtrace.Material;
import org.jtrace.NotHit;
import org.jtrace.Section;
import org.jtrace.geometry.GeometricObject;
import org.jtrace.geometry.Sphere;
import org.jtrace.primitives.Point3D;
import org.jtrace.primitives.Vector3D;

public class Cylinder extends GeometricObject{
	
	private double radius;
	private double height;
	private Point3D center;
	private Point3D floorCenter;
	private Point3D ceilingCenter;
	private Vector3D axis;
	

	public Cylinder(final Point3D center, final Point3D floorCenter, final Point3D ceilingCenter, final double radius, Material material) {
		super(material);
		this.center = center;
		this.floorCenter = floorCenter;
		this.ceilingCenter = ceilingCenter;
        this.radius = radius;
        this.axis = ceilingCenter.subtract(floorCenter);
        this.height = this.axis.module();
	}

	
	@Override
	public Hit hit(final Jay jay) {
		
		final Vector3D normalizedAxis = axis.normal();
		final Vector3D jayDirection = jay.getDirection().normal();
		
		Vector3D C = new Vector3D(this.center.getX(), this.center.getY(), this.center.getZ());
		Vector3D Cpar = normalizedAxis.multiply(C.dot(normalizedAxis)/normalizedAxis.dot());
		Vector3D Cperp = C.subtract(Cpar);
		
		Point3D Cpoint = new Point3D(Cperp.getX(), Cperp.getY(), Cperp.getZ());
		
		Vector3D P = new Vector3D(jay.getOrigin().getX(), jay.getOrigin().getY(), jay.getOrigin().getZ());
		Vector3D Ppar = normalizedAxis.multiply(P.dot(normalizedAxis)/normalizedAxis.dot());
		Vector3D Pperp = P.subtract(Ppar);
		
		Vector3D Dpar = normalizedAxis.multiply(jayDirection.dot(normalizedAxis)/normalizedAxis.dot());
		Vector3D Dperp = jayDirection.subtract(Dpar);
		
		Jay newJay = new Jay(new Point3D(Pperp.getX(), Pperp.getY(), Pperp.getZ()), Dperp);
		
		MySphere temp = new MySphere(Cpoint, this.radius, this.height, this.getMaterial());
				
		return temp.hit(newJay);
		
	}

	/* Abaixo temos funções que falharam.
	
	@Override
	public Hit hit(final Jay jay) {
		double t, t0, t1, y0, y1;
		
		double sqradius = this.radius*this.radius;
		
		final Vector3D normalizedAxis = axis.normal();
		final Vector3D origin = new Vector3D(jay.getOrigin().subtract(this.center));
		Point3D hitPoint;
		Vector3D normal;
		
		double a = jay.getDirection().getX()*jay.getDirection().getX() +
				jay.getDirection().getZ() + jay.getDirection().getZ();
		
		double b = 2*origin.getX()*jay.getDirection().getX() +
				2*origin.getZ()*jay.getDirection().getZ();
		
		double c = origin.getX()*origin.getX() +
				origin.getZ()*origin.getZ() - sqradius;
		
		//a.t2 + b.t + c = 0
		//t = (-b +/- sqrt(b*b - 4*a*c)) / 2*a
		
		double delta = b*b - 4*a*c;
		
		if (delta < 0)
			return new NotHit();
		
		double sqrtDelta = Math.sqrt(delta);
		
		t0 = (-b + sqrtDelta) / (2 * a);
		t1 = (-b - sqrtDelta) / (2 * a);
		
		if(t0 > t1){
			double swap = t1;
			t1 = t0;
			t0 = swap;
		}
		
		
		y0 = origin.getY() + t0*jay.getDirection().getY();
		y1 = origin.getY() + t1*jay.getDirection().getY();
		
		
		if(y0 < -sqradius){
			if(y1 < -sqradius){
				return new NotHit();
			}
			else {
				double th = t0+(t1-t0)*(y0+1)/(y0-y1);
				if(th <= 0)
					return new NotHit();
				
				return new Hit(th, normalizedAxis.multiply(-1));
			}
		}
		else if(y0 >= -sqradius && y0 <= sqradius){
			if(t0 <= 0)
				return new NotHit();
			hitPoint = jay.getOrigin().add(jay.getDirection().multiply(t0));
			Vector3D v1 = hitPoint.subtract(this.floorCenter);
			Vector3D v2 = this.axis.multiply(v1.dot(this.axis));
			normal = v2.multiply(-1).add(v1);
			normal = normal.normal();
			System.out.println("SOMETHING SHOULD SHOW UP");
			return new Hit(t0, normal);
		}
		else if(y0 > sqradius){
			if(y1 > sqradius)
				return new NotHit();
			else{
				double th = t0+(t1-t0)*(y0-1)/(y0-y1);
				if(th <= 0)
					return new NotHit();
					
				return new Hit(th, normalizedAxis.multiply(-1));
			}
		}
		
		return new NotHit();

	}
	
	
	//If something goes wrong with the new hit, use this one as backup.
	//It's buggy but it's something. I guess.
	//@Override
	public Hit hit(final Jay jay) {
		double t;
		
		final Vector3D normalizedAxis = axis.normal();
		Point3D hitPoint;
		Vector3D normal;
		
		Vector3D d = jay.getDirection().cross(normalizedAxis);
		d = d.normal();
		
		Vector3D temp = jay.getOrigin().subtract(this.floorCenter);
		
		double distance = Math.abs(temp.dot(d));
		
		if(distance <= this.radius){
			double diff = Math.sqrt(this.radius*this.radius - distance*distance);
			t = Math.abs(((temp.cross(normalizedAxis)).dot(d) / d.module()) - diff);
			
			hitPoint = jay.getOrigin().add(jay.getDirection().multiply(t));
			if(hitPoint.getY() > this.ceilingCenter.getY())
				return new NotHit();
			else {
				Vector3D v1 = hitPoint.subtract(this.floorCenter);
				Vector3D v2 = this.axis.multiply(v1.dot(this.axis));
				normal = v2.multiply(-1).add(v1);
				normal = normal.normal();
				return new Hit(t, normal);
			}
		} else return new NotHit();

	}
	
	
	Mistakes were made */

	@Override
	public List<Section> sections(Jay arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}