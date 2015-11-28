package examples.mesh;

import java.awt.Color;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

import br.com.abby.core.loader.MeshLoader;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.storage.octree.Octree;
import br.com.etyllica.storage.octree.OctreeNode;
import br.com.luvia.core.context.ApplicationGL;
import br.com.luvia.core.video.Graphics3D;
import br.com.luvia.linear.Mesh;

public class MeshExample extends ApplicationGL {
			
	private Mesh stone;
	private Mesh tree;
	
	private double angleY = 0;
	private boolean rotate = true;
			
	public MeshExample(int width, int height) {
		super(width, height);
	}
		
	@Override
	public void init(Graphics3D g) {
		GL2 gl = g.getGL2(); // get the OpenGL graphics context

		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color
		gl.glClearDepth(1.0f);      // set clear depth value to farthest
		gl.glEnable(GL.GL_DEPTH_TEST); // enables depth testing
		gl.glDepthFunc(GL.GL_LEQUAL);  // the type of depth test to do
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST); // best perspective correction
		gl.glShadeModel(GL2.GL_SMOOTH); // blends colors nicely, and smoothes out lighting
		
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_DECAL);
		
		//Load models
		stone = new Mesh(MeshLoader.getInstance().loadModel("stone/stone.obj"));
		stone.setColor(Color.WHITE);
		stone.offsetX(-2);
		stone.offsetY(0.5);
		stone.setScale(0.5f);
		
		tree = new Mesh(MeshLoader.getInstance().loadModel("bamboo/bamboo.obj"));
		tree.setColor(Color.WHITE);
		tree.setScale(1.5f);
		
		loading = 100;
	}

	@Override
	public void load() {
		loading = 50;
	}
	
	@Override
	public void display(Graphics3D g) {
		GL2 gl = g.getGL2();  // get the OpenGL 2 graphics context
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT); // clear color and depth buffers
		gl.glLoadIdentity();  // reset the model-view matrix
		gl.glClearColor(1f,1f,1f,1f);
		
		g.setColor(Color.WHITE);
		gl.glTranslatef(0.0f, 0.0f, -15.0f); // translate into the screen
		//gl.glScaled(150, 150, 150);
		gl.glRotated(angleY, 0, 1, 0);
		
		//Draw Models
		//Start batch
		gl.glEnable(GL.GL_DEPTH_TEST);

		gl.glPushMatrix();
		stone.texturedRender(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glDisable(GL.GL_CULL_FACE);
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		
		tree.texturedRender(gl);
		gl.glPopMatrix();
		
		//End batch
		gl.glDisable(GL.GL_DEPTH_TEST);
		
		//Rotate Models
		if(rotate) {
			angleY += 1;
		}
	}
	
	public void renderOctree(Graphics3D g, Octree<?> tree) {
		
		OctreeNode<?> root = tree.getRoot();
		
		renderOctreeNode(g, root);
	}
	
	public void renderOctreeNode(Graphics3D g, OctreeNode<?> node) {
		g.drawBoundingBox(node.getBox());
		
		for(OctreeNode<?> child: node.getChildrenNodes()) {
			renderOctreeNode(g, child);
		}
	}

	@Override
	public void reshape(Graphics3D g, int x, int y, int width, int height) {
		GL2 gl = g.getGL2(); // get the OpenGL graphics context
		GLU glu = g.getGLU();

		if (height == 0) height = 1;   // prevent divide by zero
		float aspect = (float)width / height;

		// Set the view port (display area) to cover the entire window
		gl.glViewport(0, 0, width, height);

		// Setup perspective projection, with aspect ratio matches viewport
		gl.glMatrixMode(GL2.GL_PROJECTION);  // choose projection matrix
		gl.glLoadIdentity();             // reset projection matrix

		glu.gluPerspective(45.0, aspect, 0.1, 100.0); // fovy, aspect, zNear, zFar

		// Enable the model-view transform
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity(); // reset
	}

	public void updateKeyboard(KeyEvent event) {
		if(event.isKeyDown(KeyEvent.VK_SPACE)) {
			rotate = !rotate;
		} else if(event.isKeyUp(KeyEvent.VK_SPACE)) {
			rotate = !rotate;
		}
	}
	
	@Override
	public void draw(Graphic g) {
		
	}
	
}