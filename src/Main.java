import java.io.IOException;

import javax.swing.JFrame;

import org.jtrace.Material;
import org.jtrace.Materials;
import org.jtrace.Scene;
import org.jtrace.ViewPlane;
import org.jtrace.cameras.Camera;
import org.jtrace.cameras.PinHoleCamera;
import org.jtrace.geometry.Plane;
import org.jtrace.geometry.Sphere;
import org.jtrace.interceptor.ShadowInterceptor;
import org.jtrace.lights.PointLight;
import org.jtrace.listeners.ImageListener;
import org.jtrace.primitives.ColorRGB;
import org.jtrace.primitives.Point3D;
import org.jtrace.primitives.Vector3D;
import org.jtrace.shader.Shaders;

public class Main
{
	
	public static JFrame jf;
	public static MyFrame mf;
	public static int resolutionX;
	public static int resolutionY;
	
	public static void main(String[] args) throws IOException
	{
		
		mf = new MyFrame(); 
		long speed = 1;
		
		resolutionX = 1024;
		resolutionY = 768;
		
		mf.init("L is for the lollipop");
		
		//while(true){
			//for(double i = 1; i < 100; i*= 1.01){
				mf.setVisible(false);
				criaImagem(3);
				MyFrame.atualizaImagem();
				
				try {
					Thread.sleep(speed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			//}
		//}
			
		
	}

	private static void criaImagem(double zoomFactor){
		
		Scene cena = new Scene();
		Camera camera = new PinHoleCamera(new Point3D(0, 0, -20), Point3D.ORIGIN, Vector3D.UNIT_Y);
		cena.setCamera(camera);
		camera.setZoomFactor(zoomFactor);
		
		ViewPlane vp = new ViewPlane(resolutionX, resolutionY);
		MyTracer tracer = new MyTracer();
		
		Sphere lolli = new Sphere(new Point3D(0, 13, -11),4, Materials.metallic(ColorRGB.RED));
		cena.add(lolli);
		cena.add(new PointLight( new Point3D(20, 20, -30)));
		
		int skew_left = 0, move_forward = 0;
		Cylinder pop = new Cylinder(new Point3D(0+skew_left, 0, 0+move_forward), new Point3D(0+skew_left, -5, 0+move_forward), new Point3D(0+skew_left, 5, 0+move_forward), 2, Materials.matte(ColorRGB.WHITE));
		cena.add(pop);
		
		Plane plano = new Plane(new Point3D(0, -10, 0), Vector3D.UNIT_Y, Materials.matte(ColorRGB.WHITE));
		cena.add(plano);
		
		CSGObject csg = new CSGObject(new Point3D(-10, 0, 5),10, Materials.metallic(ColorRGB.YELLOW), new Point3D(-5, 3, 5),10, Materials.metallic(ColorRGB.WHITE));
		cena.add(csg);
		
		tracer.addShaders(Shaders.ambientShader());
		tracer.addShaders(Shaders.diffuseShader());
		tracer.addShaders(Shaders.specularShader(64));
		
		tracer.addInterceptors(new ShadowInterceptor());
		
		tracer.addListeners(new ImageListener("src/image/imagem.png","png"));
		int n1 = 7, n2 = 19, inc = 1;
		while( n1 < vp.getVres() || n2 < vp.getHres() ) {
			tracer.render(cena, vp, n1, n2);
			MyFrame.atualizaImagem();
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			n1 += inc++; n2 += inc++;
//			System.out.println(n1+" "+n2);
		}
		
	}
	
}
