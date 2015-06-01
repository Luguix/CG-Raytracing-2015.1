import java.util.List;

import org.jtrace.Constants;
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


	@Override
	public List<Section> sections(Jay arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
