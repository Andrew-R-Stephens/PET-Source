
package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.data.ExampleSceneLoaderAlt;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.data.ModelSurfaceViewAlt;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.mapsmenu.mapdisplay.data.SceneLoaderAlt;
import com.TritiumGaming.phasmophobiaevidencepicker.rendering.util.Utils;
import com.TritiumGaming.phasmophobiaevidencepicker.rendering.util.content.ContentUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This activity represents the container for our 3D viewer.
 *
 * @author andresoviedo
 */
public class ModelFragment extends Fragment {

    private static final int REQUEST_CODE_OPEN_FILE = 1000;

    private String paramAssetDir;
    private String paramAssetFilename;
    /**
     * The file to load. Passed as input parameter
     */
    private String paramFilename;
    /**
     * Enter into Android Immersive mode so the renderer is full screen or not
     */
    private boolean immersiveMode = true;
    /**
     * Background GL clear color. Default is light gray
     */
    private float[] backgroundColor = new float[]{0.2f, 0.2f, 0.2f, 1.0f};

    private ModelSurfaceViewAlt gLView;

    private SceneLoaderAlt scene;

    private Handler handler;

    public ModelFragment() {
        super(R.layout.fragment_mapview3d);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Try to get input parameters
        Bundle b = getActivity().getIntent().getExtras();
        if (b != null) {
            this.paramAssetDir = b.getString("assetDir");
            this.paramAssetFilename = b.getString("assetFilename");
            this.paramFilename = b.getString("uri");
            this.immersiveMode = "true".equalsIgnoreCase(b.getString("immersiveMode"));
            try{
                String[] backgroundColors = b.getString("backgroundColor").split(" ");
                backgroundColor[0] = Float.parseFloat(backgroundColors[0]);
                backgroundColor[1] = Float.parseFloat(backgroundColors[1]);
                backgroundColor[2] = Float.parseFloat(backgroundColors[2]);
                backgroundColor[3] = Float.parseFloat(backgroundColors[3]);
            }catch(Exception ex){
                // Assuming default background color
            }
        }
        Log.i("Renderer", "Params: assetDir '" + paramAssetDir + "', assetFilename '" + paramAssetFilename + "', uri '"
                + paramFilename + "'");

        handler = new Handler(getActivity().getMainLooper());

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        gLView = view.findViewById(R.id.interactiveMap3DDisplay); //new ModelSurfaceViewAlt
        // (this);
        //getActivity().setContentView(gLView);

        // Create our 3D sceneario
        if (paramFilename == null && paramAssetFilename == null) {
            scene = new ExampleSceneLoaderAlt(this);
        } else {
            scene = new SceneLoaderAlt(this);
        }
        try {
            scene.init();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        // TODO: Alert user when there is no multitouch support (2 fingers). He won't be able to rotate or zoom for
        // example
        Utils.printTouchCapabilities(getActivity().getPackageManager());

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.model_toggle_wireframe:
                scene.toggleWireframe();
                break;
            case R.id.model_toggle_boundingbox:
                scene.toggleBoundingBox();
                break;
            case R.id.model_toggle_textures:
                scene.toggleTextures();
                break;
            case R.id.model_toggle_lights:
                scene.toggleLighting();
                break;
            case R.id.model_load_texture:
                Intent target = Utils.createGetContentIntent();
                Intent intent = Intent.createChooser(target, "Select a file");
                try {
                    startActivityForResult(intent, REQUEST_CODE_OPEN_FILE);
                } catch (ActivityNotFoundException e) {
                    // The reason for the existence of aFileChooser
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public File getParamFile() {
        return getParamFilename() != null ? new File(getParamFilename()) : null;
    }

    public String getParamAssetDir() {
        return paramAssetDir;
    }

    public String getParamAssetFilename() {
        return paramAssetFilename;
    }

    public String getParamFilename() {
        return paramFilename;
    }

    public float[] getBackgroundColor(){
        return backgroundColor;
    }

    public SceneLoaderAlt getScene() {
        return scene;
    }

    public ModelSurfaceViewAlt getgLView() {
        return gLView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_OPEN_FILE:
                if (resultCode == getActivity().RESULT_OK) {
                    // The URI of the selected file
                    final Uri uri = data.getData();
                    Log.i("Menu", "Loading '" + uri.toString() + "'");
                    if (uri != null) {
                        final String path = ContentUtils.getPath(getActivity().getApplicationContext(), uri);
                        if (path != null) {
                            try {
                                scene.loadTexture(null, new URL("file://"+path));
                            } catch (MalformedURLException e) {
                                Toast.makeText(getActivity().getApplicationContext(), "Problem loading texture '" + uri.toString() + "'",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Problem loading texture '" + uri.toString() + "'",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Result when loading texture was '" + resultCode + "'",
                            Toast.LENGTH_SHORT).show();
                }
        }
    }
}
