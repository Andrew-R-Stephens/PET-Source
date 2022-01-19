package com.TritiumGaming.phasmophobiaevidencepicker.rendering.model3D.services.stl;

import android.app.Activity;
import android.opengl.GLES20;
import android.util.Log;

import com.TritiumGaming.phasmophobiaevidencepicker.rendering.model3D.controller.LoaderTask;
import com.TritiumGaming.phasmophobiaevidencepicker.rendering.model3D.model.Object3DBuilder;
import com.TritiumGaming.phasmophobiaevidencepicker.rendering.model3D.model.Object3DData;
import com.TritiumGaming.phasmophobiaevidencepicker.rendering.model3D.services.WavefrontLoader;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Collections;
import java.util.List;

/**
 + STL loader supported by the org.j3d STL parser
 *
 * @author andresoviedo
 */
public final class STLLoader {

	private static ByteBuffer createNativeByteBuffer(int length) {
		// initialize vertex byte buffer for shape coordinates
		ByteBuffer bb = ByteBuffer.allocateDirect(length);
		// use the device hardware's native byte order
		bb.order(ByteOrder.nativeOrder());
		return bb;
	}

	public static void loadSTLAsync(final Activity parent, URL url, final Object3DBuilder.Callback callback) {
		new STLLoaderTask(parent,url,callback).execute();
	}

	private static class STLLoaderTask extends LoaderTask {

		STLFileReader stlFileReader;

		STLLoaderTask(Activity parent, URL url, Object3DBuilder.Callback callback){
			super(parent,url,null,null,null, callback);
		}

		@Override
		protected List<Object3DData> build() throws IOException {
			// Parse STL
			this.stlFileReader = new STLFileReader(url);
			int totalFaces = stlFileReader.getNumOfFacets()[0];
			Log.i("STLLoader", "Num of objects: " + stlFileReader.getNumOfObjects());
			Log.i("STLLoader", "Found '" + totalFaces + "' facets");
			Log.i("STLLoader", "Parsing messages: " + stlFileReader.getParsingMessages());

			// Allocate data
			FloatBuffer normalsBuffer = createNativeByteBuffer(totalFaces * 3 * 3 * 4).asFloatBuffer();
			FloatBuffer vertexBuffer = createNativeByteBuffer(totalFaces * 3 * 3 * 4).asFloatBuffer();

			// Initialize model dimensions (needed by the Object3DData#scaleCenter()
			WavefrontLoader.ModelDimensions modelDimensions = new WavefrontLoader.ModelDimensions();

			// notify succeded!
			Object3DData data3D = new Object3DData(vertexBuffer).setVertexNormalsArrayBuffer(normalsBuffer);
			data3D.setDimensions(modelDimensions);
			data3D.setDrawUsingArrays(true);
			data3D.setDrawMode(GLES20.GL_TRIANGLES);
			if (totalFaces > 0){
				data3D.setFaces(new WavefrontLoader.Faces(totalFaces));
			}
			return Collections.singletonList(data3D);
		}

		@Override
		protected void build(List<Object3DData> datas) throws Exception
		{
			int counter = 0;
			try {

				Object3DData data = datas.get(0);

				// Parse all facets...
				double[] normal = new double[3];
				double[][] vertices = new double[3][3];
				int normalCounter=0, vertexCounter=0;

				FloatBuffer normalsBuffer = data.getVertexNormalsArrayBuffer();
				FloatBuffer vertexBuffer = data.getVertexArrayBuffer();
				WavefrontLoader.ModelDimensions modelDimensions = data.getDimensions();

				int totalFaces = stlFileReader.getNumOfFacets()[0];
				boolean first = true;
				while (stlFileReader.getNextFacet(normal, vertices) && counter < totalFaces) {
					Log.d("STLLoader", "Loading facet " + counter++ + "");
					normalsBuffer.put(normalCounter++,(float) normal[0]);
					normalsBuffer.put(normalCounter++,(float) normal[1]);
					normalsBuffer.put(normalCounter++,(float) normal[2]);
					normalsBuffer.put(normalCounter++,(float) normal[0]);
					normalsBuffer.put(normalCounter++,(float) normal[1]);
					normalsBuffer.put(normalCounter++,(float) normal[2]);
					normalsBuffer.put(normalCounter++,(float) normal[0]);
					normalsBuffer.put(normalCounter++,(float) normal[1]);
					normalsBuffer.put(normalCounter++,(float) normal[2]);

					vertexBuffer.put(vertexCounter++,(float) vertices[0][0]);
					vertexBuffer.put(vertexCounter++,(float) vertices[0][1]);
					vertexBuffer.put(vertexCounter++,(float) vertices[0][2]);
					vertexBuffer.put(vertexCounter++,(float) vertices[1][0]);
					vertexBuffer.put(vertexCounter++,(float) vertices[1][1]);
					vertexBuffer.put(vertexCounter++,(float) vertices[1][2]);
					vertexBuffer.put(vertexCounter++,(float) vertices[2][0]);
					vertexBuffer.put(vertexCounter++,(float) vertices[2][1]);
					vertexBuffer.put(vertexCounter++,(float) vertices[2][2]);

					// update model dimensions
					if (first){
						modelDimensions.set((float) vertices[0][0],(float) vertices[0][1],(float) vertices[0][2]);
						first = false;
					}
					modelDimensions.update((float) vertices[0][0],(float) vertices[0][1],(float) vertices[0][2]);
					modelDimensions.update((float) vertices[1][0],(float) vertices[1][1],(float) vertices[1][2]);
					modelDimensions.update((float) vertices[2][0],(float) vertices[2][1],(float) vertices[2][2]);
				}

				Log.i("STLLoader", "Building 3D object...");
				data.centerAndScale(5, new float[]{0,0,0});

			} catch (Exception e) {
				Log.e("STLLoader", "Face '"+counter+"'"+e.getMessage(), e);
				throw e;
			}finally {
				try {
					stlFileReader.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
	}
}