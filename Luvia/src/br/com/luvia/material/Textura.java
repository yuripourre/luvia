package br.com.luvia.material;

import java.awt.Color;
import java.awt.image.BufferedImage;

import br.com.etyllica.layer.BufferedLayer;

/**
 * 
 * @author yuripourre
 * @license LGPLv3
 *
 */

public class Textura extends BufferedLayer{

	public Textura(BufferedImage imagem) {
		super(imagem);
		
		espelharHorizontal();
	}
		
	public byte[][][] getAlphaBytes(){
		byte imagem2D[][][] = new byte [w][h][4];

		Color c;

		for(int i=0;i<h;i++){
			for(int j=0;j<w;j++){

				c = new Color(getImagemBuffer().getRGB(j,i));

				imagem2D[j][i][0] = (byte)c.getRed();
				imagem2D[j][i][1] = (byte)c.getGreen();
				imagem2D[j][i][2] = (byte)c.getBlue();
				imagem2D[j][i][3] = (byte)c.getAlpha();
			}
		}

		return imagem2D;		
	}

}