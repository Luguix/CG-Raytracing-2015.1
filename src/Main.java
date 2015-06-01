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
	private static int resolutionX;
	private static int resolutionY;
	private static long changeSpeed;
	private static double zoom;
	
	public static void main(String[] args) throws IOException
	{
		
		MyFrame mf = new MyFrame(); 
		changeSpeed = 1000;
		
		resolutionX = 600;
		resolutionY = 400;
		zoom = 8;
		
		mf.init("L is for Lollipop");

		criaImagem(zoom);
		
		MyFrame.atualizaImagem();
		
	}
		
	
	private static void criaImagem(double zoomFactor){
		
		Camera camera = new PinHoleCamera(new Point3D(0, 0, -20), Point3D.ORIGIN, Vector3D.UNIT_Y);
		camera.setZoomFactor(zoomFactor);
		
		Scene cena = new Scene();
		cena.setCamera(camera);
		cena.withBackground(new ColorRGB(0.68, 0.74, 0.93));
		cena.add(new PointLight(new Point3D(5, 10, -30)));
		
		ViewPlane vp = new ViewPlane(resolutionX, resolutionY);
		
		Point3D sphereCenter = new Point3D(0, 6, -10);
		double sphereRadius = 3.0;
		Material sphereMaterial = Materials.metallic(ColorRGB.RED);
		
		Sphere s = new Sphere(sphereCenter, sphereRadius, sphereMaterial);
		cena.add(s);
		
		Plane grass = new Plane(new Point3D(0, -10, 0), Vector3D.UNIT_Y, Materials.matte(new ColorRGB(0.11, 0.43, 0.02)));
		cena.add(grass);
		
		CSGObject banana = new CSGObject(new Point3D(-10, 0, 0),10, Materials.metallic(ColorRGB.YELLOW), new Point3D(-5, 3, 0),10, Materials.metallic(ColorRGB.WHITE));
		cena.add(banana);
		
		int lollipopZ = -7;		
		Lollipop p = new Lollipop(new Point3D(0,0,lollipopZ), new Point3D(0, -5, lollipopZ), new Point3D(0, 5, lollipopZ), 1, Materials.matte(ColorRGB.WHITE), sphereCenter, sphereRadius, sphereMaterial);
		cena.add(p);
		
		MyTracer tracer = new MyTracer();
		
		tracer.addInterceptors(new ShadowInterceptor());
		
		tracer.addShaders(Shaders.ambientShader());
		tracer.addShaders(Shaders.diffuseShader());
		tracer.addShaders(Shaders.specularShader(64));
		
		tracer.addListeners(new ImageListener("src/image/imagem.png","png"));
		
		
		int n1 = 1, n2 = 1, inc = 1;
		while( n1 < vp.getVres() || n2 < vp.getHres() ) {
			tracer.render(cena, vp, n1, n2);
			MyFrame.atualizaImagem();
			
			try {
				Thread.sleep(changeSpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			n1 += inc++; n2 += inc++;
//			System.out.println(n1+" "+n2);
		}
		tracer.render(cena, vp, vp.getVres(), vp.getHres());
		MyFrame.atualizaImagem();
		
	}
	
}