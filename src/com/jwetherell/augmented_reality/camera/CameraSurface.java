package com.jwetherell.augmented_reality.camera;

import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * Represents the camera's surface and all the initialization involved with it.
 * 
 * This file was adapted from Mixare <http://www.mixare.org/>
 * 
 * @author Daniele Gobbetti <info@mixare.org>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class CameraSurface extends SurfaceView implements SurfaceHolder.Callback {
    private static SurfaceHolder holder = null;
    private static Camera camera = null;

    public CameraSurface(Context context) {
        super(context);

        try {
            holder = getHolder();
            holder.addCallback(this);
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (camera != null) {
                try {
                    camera.stopPreview();
                } catch (Exception ex) {
                	ex.printStackTrace();
                }
                try {
                    camera.release();
                } catch (Exception ex) {
                	ex.printStackTrace();
                }
                camera = null;
            }

            camera = Camera.open();
            camera.setPreviewDisplay(holder);
        } catch (Exception ex) {
            try {
                if (camera != null) {
                    try {
                        camera.stopPreview();
                    } catch (Exception ex1) {
                    	ex.printStackTrace();
                    }
                    try {
                        camera.release();
                    } catch (Exception ex2) {
                    	ex.printStackTrace();
                    }
                    camera = null;
                }
            } catch (Exception ex3) {
            	ex.printStackTrace();
            }
        }
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            if (camera != null) {
                try {
                    camera.stopPreview();
                } catch (Exception ex) {
                	ex.printStackTrace();
                }
                try {
                    camera.release();
                } catch (Exception ex) {
                	ex.printStackTrace();
                }
                camera = null;
            }
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        try {
            Camera.Parameters parameters = camera.getParameters();
            try {
                List<Camera.Size> supportedSizes = null;
                //On older devices (<1.6) the following will fail
                //the camera will work nevertheless
                supportedSizes = CameraCompatibility.getSupportedPreviewSizes(parameters);

                //preview form factor
                float ff = (float)w/h;

                //holder for the best form factor and size
                float bff = 0;
                int bestw = 0;
                int besth = 0;
                Iterator<Camera.Size> itr = supportedSizes.iterator();
                
                //we look for the best preview size, it has to be the closest to the
                //screen form factor, and be less wide than the screen itself
                //the latter requirement is because the HTC Hero with update 2.1 will
                //report camera preview sizes larger than the screen, and it will fail
                //to initialize the camera
                //other devices could work with previews larger than the screen though
                while(itr.hasNext()) {
                    Camera.Size element = itr.next();
                    //current form factor
                    float cff = (float)element.width/element.height;
                    //check if the current element is a candidate to replace the best match so far
                    //current form factor should be closer to the bff
                    //preview width should be less than screen width
                    //preview width should be more than current bestw
                    //this combination will ensure that the highest resolution will win
                    if ((ff-cff <= ff-bff) && (element.width <= w) && (element.width >= bestw)) {
                        bff=cff;
                        bestw = element.width;
                        besth = element.height;
                    }
                } 
                //Some Samsung phones will end up with bestw and besth = 0 because their minimum preview size is bigger then the screen size.
                //In this case, we use the default values: 480x320
                if ((bestw == 0) || (besth == 0)){
                    bestw = 480;
                    besth = 320;
                }
                parameters.setPreviewSize(bestw, besth);
            } catch (Exception ex) {
                parameters.setPreviewSize(480 , 320);
            }

            camera.setParameters(parameters);
            camera.startPreview();
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
    }
}
