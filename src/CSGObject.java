import java.util.List;

import org.jtrace.Hit;
import org.jtrace.Jay;
import org.jtrace.Material;
import org.jtrace.NotHit;
import org.jtrace.Section;
import org.jtrace.geometry.GeometricObject;
import org.jtrace.geometry.Sphere;
import org.jtrace.primitives.Point3D;


public class CSGObject extends GeometricObject{

	private Sphere s1;
	private Sphere s2;
	
	public CSGObject(final Point3D center1, final double radius1, final Material material1, final Point3D center2, final double radius2, final Material material2) {
		super(material1);
		s1 = new Sphere(center1,radius1,material1);		
		s2 = new Sphere(center2,radius2,material2);		
		
	}

	@Override
	public Hit hit(final Jay jay) {
		Hit hit1 = s1.hit(jay);
		Hit hit2 = s2.hit(jay);
		
		//UNION
		/*
		if(hit1.isHit() && hit2.isHit()){
			if(hit1.getT() > hit2.getT())
				return hit2;
			else return hit1;
		}
		
		if(hit1.isHit())
			return hit1;
		else if(hit2.isHit())
			return hit2;
		else return new NotHit();*/
		
		//S1 - S2
		
		if(hit1.isHit() && !hit2.isHit())
			return hit1;
		return new NotHit();
		
		
		//S2 - S1
		/*
		if(hit2.isHit() && !hit1.isHit())
			return hit2;
		return new NotHit();*/
		
		//INTERSECTION
		/*
		if(hit1.isHit() || hit2.isHit())
			return hit1;
		
		return new NotHit();*/
	}

	@Override
	public List<Section> sections(Jay arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
