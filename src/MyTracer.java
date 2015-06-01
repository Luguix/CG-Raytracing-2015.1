import org.jtrace.Jay;
import org.jtrace.Scene;
import org.jtrace.Tracer;
import org.jtrace.ViewPlane;
import org.jtrace.cameras.Camera;
import org.jtrace.primitives.ColorRGB;

public class MyTracer extends Tracer {
	// @Override
	public void render(Scene scene, ViewPlane viewPlane, int n1, int n2) {
        final int hres = viewPlane.getHres();
        final int vres = viewPlane.getVres();
        final Camera camera = scene.getCamera();

        fireStart(viewPlane);
        initInterceptors(scene);
        
        int r = 0, c = 0;
	        while (r < vres) {
	        	c = 0;
	            while (c < hres) {
	                final Jay jay = camera.createJay(r+1+(vres/(2*n1)), c+1+(hres/(2*n2)), vres, hres);
	                
	                final ColorRGB color = trace(scene, jay);
	
	                for(int i = r; i < r+(vres/n1) && i < vres; i++) {
	                	for(int j = c; j < c+(hres/n2) && j < hres; j++) {
	                		fireAfterTrace(color, j, i);
	                	}
	                }
	                
	                c+=hres/n2;
	                if(c > hres) c = hres;
	            }
	            r+=vres/n1;
	            if(r > vres) r = vres;
	        }
	        // System.out.println(c+" "+r);
	        fireFinish();
    }
}
