package examples.geom;

import java.awt.Color;

import com.jogamp.opengl.GL2;

import br.com.etyllica.commons.event.KeyEvent;
import br.com.luvia.core.context.ApplicationGL;
import br.com.luvia.core.graphics.Graphics3D;

public class PyramidExample extends ApplicationGL {

	public PyramidExample(int width, int height) {
		super(width, height);
	}

	private Color color = Color.WHITE;
	private boolean wireFrame = false;
	private double angleY = 0;

	@Override
	public void init(Graphics3D g) {

		GL2 gl = g.getGL2();

		gl.glClearColor(0, 0, 0, 0);
		gl.glShadeModel(GL2.GL_FLAT);
	}

	@Override
	public void load() {
		loading = 100;
	}

	@Override
	public void display(Graphics3D g) {
		GL2 gl = g.getGL2();
		
		if(wireFrame) {
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
		} else {
			gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
		}
		
		angleY += 0.4;
		
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		//gl.glColor3f(1.0f, 1.0f, 0f);
		g.setColor(color);
		gl.glLoadIdentity();             /* clear the matrix */
		/* viewing transformation  */
		g.getGLU().gluLookAt(0.0, 0.0, 5.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
		gl.glRotated(angleY, 0,1,0);
		gl.glScalef(1.0f, 2.0f, 1.0f);      /* modeling transformation */ 
		g.drawPyramid(1.0f);
		
		gl.glFlush();
	}

	@Override
	public void reshape(Graphics3D g, int x, int y, int width, int height) {
		GL2 gl = g.getGL2(); // get the OpenGL graphics context
		
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION);  // choose projection matrix
		gl.glLoadIdentity();             // reset projection matrix
		gl.glFrustum(-1.0, 1.0, -1.0, 1.0, 1.5, 20.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
	}
	
	@Override
	public void updateKeyboard(KeyEvent event) {
		if(event.isKeyDown(KeyEvent.VK_SPACE)) {
			wireFrame = !wireFrame;
		}
	}
}
