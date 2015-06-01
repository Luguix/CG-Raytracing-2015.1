import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jtrace.Scene;
import org.jtrace.Tracer;
import org.jtrace.ViewPlane;
import org.jtrace.cameras.Camera;
import org.jtrace.cameras.PinHoleCamera;
import org.jtrace.listeners.ImageListener;
import org.jtrace.primitives.Point3D;
import org.jtrace.primitives.Vector3D;

public class MyFrame extends JFrame {
	
	private static ImageIcon imageIcon;
	private static JFrame jf;
	private static String imagePath = "src/image/imagem.png";
	
	public MyFrame() {    
	        setUndecorated(true); 	          
	}

	public void init(String title) {

		jf = new JFrame();
        jf.setTitle(title);
        
        this.clearImage();
        
        imageIcon = new ImageIcon(imagePath);
        
        jf.getContentPane().add(new JLabel(imageIcon)); 
        imageIcon.getImage().flush();
        
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        jf.setLocationRelativeTo(null);  
        jf.pack();
        jf.setVisible(true);
		
	}
	
	
	public static void atualizaImagem(){
		
		jf.getContentPane().removeAll();
		imageIcon.getImage().flush();

		
		jf.getContentPane().add(new JLabel(new ImageIcon(imagePath)));
		jf.setVisible(true);
		
	}
	
	
	private static void clearImage(){
		Scene cena = new Scene();
		Camera camera = new PinHoleCamera(new Point3D(0, 0, -20), Point3D.ORIGIN, Vector3D.UNIT_Y);
		cena.setCamera(camera);
		camera.setZoomFactor(0);
		
		ViewPlane vp = new ViewPlane(600, 400);
		Tracer tracer = new Tracer();
				
		tracer.addListeners(new ImageListener("src/image/imagem.png","png"));

		tracer.render(cena, vp);
	}
	
}
