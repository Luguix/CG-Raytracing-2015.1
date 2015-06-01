import java.util.List;

import org.jtrace.Hit;
import org.jtrace.Jay;
import org.jtrace.Material;
import org.jtrace.NotHit;
import org.jtrace.Section;
import org.jtrace.geometry.GeometricObject;
import org.jtrace.geometry.Sphere;
import org.jtrace.primitives.Point3D;

public class Lollipop extends GeometricObject{

	private Cylinder c;
	private Sphere s;
	
	public Lollipop(final Point3D center, final Point3D floorCenter, final Point3D ceilingCenter, final double radius, Material material, final Point3D center2, final double radius2, final Material material2) {
		super(material);
		c = new Cylinder(center, floorCenter, ceilingCenter, radius, material);
		s = new Sphere(center2, radius2, material2);
	}

	@Override
	public Hit hit(final Jay jay) {
		Hit hit1 = c.hit(jay);
		Hit hit2 = s.hit(jay);
		
		//UNION
		
		if(hit1.isHit() && hit2.isHit()){
			return hit2;
		}
		
		if(hit1.isHit())
			return hit1;
		else if(hit2.isHit())
			return hit2;
		else return new NotHit();
		
	}

	@Override
	public List<Section> sections(Jay arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
