package examples;

import static javax.media.opengl.GL.GL_LINEAR;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_MIN_FILTER;

import java.awt.Color;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.input.mouse.MouseButton;
import br.com.etyllica.core.video.Graphic;
import br.com.luvia.core.ApplicationGL;
import br.com.luvia.loader.TextureLoader;
import br.com.luvia.util.Camera;

import com.jogamp.opengl.util.texture.Texture;

public class Perspective extends ApplicationGL {

	private Texture floor;

	private Camera camera;
	
	protected int mx = 0;
	protected int my = 0;

	public Perspective(int w, int h) {
		super(w, h);
	}


	@Override
	public void init(GLAutoDrawable arg0) {
		floor = TextureLoader.getInstance().loadTexture("/mark.png");
	}
	
	@Override
	public void load() {
		
		camera = new Camera(0,15,-10);
		
		loading = 100;
	}
	
	protected void lookCamera(GL2 gl){
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();

		double targetx = 0;
		double targety = 0;
		double targetz = 0;
		
		glu.gluLookAt( camera.getX(), camera.getY(), camera.getZ(), targetx, targety, targetz, 0, 1, 0 );

	}
	
	protected void drawFloor(GL2 gl){

		gl.glColor3d(1,1,1);

		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

		drawGrid(gl,200,120);

	}

	private void drawGrid(GL2 gl, double x, double y){

		double tileSize = 5;

		floor.enable(gl);
		floor.bind(gl);

		for(int j=0;j<y;j++){

			for(int i=0;i<x;i++){
				drawTile(gl, i, j, tileSize);
			}
			
		}

		floor.disable(gl);
	}

	private void drawTile(GL2 gl, double x, double y, double tileSize){

		gl.glBegin(GL2.GL_QUADS);

		//(0,0)
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(x*tileSize, 0, y*tileSize);

		//(1,0)
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(x*tileSize+tileSize, 0, y*tileSize);

		//(1,1)
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(x*tileSize+tileSize, 0, y*tileSize+tileSize);

		//(0,1)
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(x*tileSize, 0, y*tileSize+tileSize);

		gl.glEnd();
	}

	private void drawAxis(GL2 gl){

		double axisSize = 100;

		//Draw Axis
		gl.glLineWidth(2.5f);

		//Draw X Axis
		gl.glColor3d(1.0, 0.0, 0.0);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3d(0.0, 0.0, 0.0);
		gl.glVertex3d(axisSize, 0, 0);
		gl.glEnd();

		//Draw Y Axis
		gl.glColor3d(0.0, 1.0, 0.0);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3d(0.0, 0.0, 0.0);
		gl.glVertex3d(0, axisSize, 0);
		gl.glEnd();

		//Draw Z Axis
		gl.glColor3d(0.0, 0.0, 1.0);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3d(0.0, 0.0, 0.0);
		gl.glVertex3d(0, 0, axisSize);
		gl.glEnd();

	}

	@Override
	public void timeUpdate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

		GL2 gl = drawable.getGL().getGL2();

		gl.glViewport (x, y, w, h);

		gl.glMatrixMode(GL2.GL_PROJECTION);

		gl.glLoadIdentity();

		glu.gluPerspective(60.0, (double) w / (double) h, 0.1, 500.0);

		gl.glMatrixMode(GL2.GL_MODELVIEW);

		gl.glLoadIdentity();

	}	

	protected boolean click = false;

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {

		return GUIEvent.NONE;
	}
	
	public GUIEvent updateMouse(PointerEvent event) {

		mx = event.getX();
		my = event.getY();

		if(event.onButtonDown(MouseButton.MOUSE_BUTTON_LEFT)){

			click = true;
		}

		if(event.onButtonUp(MouseButton.MOUSE_BUTTON_LEFT)){
			click = false;
		}

		return GUIEvent.NONE;
	}

	@Override
	public void display(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();

		//TODO TEST
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glClearColor(1f, 1f, 1f, 1);
				
		//Transform by Camera
		lookCamera(drawable.getGL().getGL2());		

		//Draw Scene
		drawAxis(gl);

		drawFloor(gl);
		

		gl.glFlush();

	}
	

	@Override
	public void draw(Graphic g) {

		int size = 100;

		g.setColor(Color.RED);
		g.fillRect(w/2,50,20,20);

		g.setColor(Color.BLUE);
		g.drawRect(w/2-size/2, h/2-size/2, size, size);

		//Draw Gui
		g.setColor(Color.WHITE);
		g.drawShadow(20,20, "Scene",Color.BLACK);
				
	}
	
}