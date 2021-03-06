package br.com.luvia.loader;

import java.io.File;
import java.io.IOException;

import br.com.etyllica.loader.LoaderImpl;
import br.com.luvia.gis.GISInfo;
import br.com.luvia.loader.gis.TFWLoader;

public class GISInfoLoader extends LoaderImpl {

	private static GISInfoLoader instance = null;

	private GISInfoLoader() {
		super();

		folder = "assets/images/";
	}

	public static GISInfoLoader getInstance() {

		if(instance==null) {
			instance = new GISInfoLoader();
		}

		return instance;
	}

	public GISInfo loadInfo(String fileName) {
		return loadInfo(getPath()+folder, fileName);
	}
	
	public GISInfo loadInfo(String fullPath, String fileName) {

			File file = new File(fullPath+fileName);

			try {
				return new TFWLoader().loadGISInfo(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return null;
	}

}
