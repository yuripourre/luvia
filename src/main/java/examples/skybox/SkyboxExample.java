package examples.skybox;


import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import br.com.abby.linear.AimPoint;
import br.com.etyllica.commons.event.KeyEvent;
import br.com.etyllica.commons.event.MouseEvent;
import br.com.etyllica.commons.event.PointerEvent;
import br.com.luvia.core.context.ApplicationGL;
import br.com.luvia.core.graphics.Graphics3D;
import br.com.luvia.graphics.SkyBox;

public class SkyboxExample extends ApplicationGL {

	private SkyBox skyBox;

	protected float mx = 0;
	protected float my = 0;
	
	protected boolean click = false;
	
	protected double turnSpeed = 1;
	
	private AimPoint aim;
	
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	
	protected double farPlane = 5000;
	
	public SkyboxExample(int w, int h) {
		super(w, h);
	}

	@Override
	public void init(Graphics3D drawable) {
		aim = new AimPoint(0, 5, 0);
		aim.setAngleY(180);
				
		//skyBox = new SkyBox("skybox.jpg");
		skyBox = new SkyBox("skybox.jpg", 1500);
		//skyBox.setZ(90);
	}
	
	@Override
	public void load() {
		loading = 100;
	}
	
	@Override
	public void reshape(Graphics3D drawable, int x, int y, int width, int height) {

		GL2 gl = drawable.getGL2();
		GLU glu = drawable.getGLU();

		gl.glViewport((int)x, (int)y, (int)w, (int)h);

		gl.glMatrixMode(GL2.GL_PROJECTION);

		gl.glLoadIdentity();

		glu.gluPerspective(60.0, (double) w / (double) h, 0.1, farPlane);

		gl.glMatrixMode(GL2.GL_MODELVIEW);

		gl.glLoadIdentity();

	}

	@Override
	public void updateKeyboard(KeyEvent event) {

		if(event.isKeyDown(KeyEvent.VK_RIGHT_ARROW)) {
			rightPressed = true;
		} else if(event.isKeyUp(KeyEvent.VK_RIGHT_ARROW)) {
			rightPressed = false;
		}
		
		if(event.isKeyDown(KeyEvent.VK_LEFT_ARROW)) {
			leftPressed = true;			
		} else if(event.isKeyUp(KeyEvent.VK_LEFT_ARROW)) {
			leftPressed = false;
		}
	}
	
	public void updateMouse(PointerEvent event) {

		mx = event.getX();
		my = event.getY();

		if(event.isButtonDown(MouseEvent.MOUSE_BUTTON_LEFT)) {

			click = true;
		}

		if(event.isButtonUp(MouseEvent.MOUSE_BUTTON_LEFT)) {
			click = false;
		}
	}

	@Override
	public void display(Graphics3D g) {

		updateControls(0);
		
		GL2 gl = g.getGL().getGL2();

		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glClearColor(1f, 1f, 1f, 1);
				
		//Transform by Aim
		g.aimCamera(aim);

		skyBox.draw(g);
		
		gl.glFlush();
	}
	
	public void updateControls(long now) {
				
		if(leftPressed) {
			aim.offsetAngleY(+turnSpeed);			
		}
		
		if(rightPressed) {
			aim.offsetAngleY(-turnSpeed);			
		}
		
	}
	
}