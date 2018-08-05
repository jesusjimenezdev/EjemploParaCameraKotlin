package jesus.net.ejemploparacamerakotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mindorks.paracamera.Camera
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import android.graphics.Bitmap
import android.content.Intent
import android.widget.ImageView


class MainActivity : AppCompatActivity() {

    var camera: Camera? = null
    var imageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)

        camera = Camera.Builder()
                .resetToCorrectOrientation(true)
                .setTakePhotoRequestCode(1)
                .setDirectory("Imagenes")
                .setName("imagen_" + System.currentTimeMillis())
                .setImageFormat(Camera.IMAGE_JPEG)
                .setCompression(75)
                .setImageHeight(1000)// it will try to achieve this height as close as possible maintaining the aspect ratio;
                .build(this);

        btnFoto.setOnClickListener {
            try {
                camera!!.takePicture()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Get the bitmap and image path onActivityResult of an activity or fragment
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Camera.REQUEST_TAKE_PHOTO) {
            val bitmap = camera?.cameraBitmap
            if (bitmap != null) {
                imageView?.setImageBitmap(bitmap)
            } else {
                Toast.makeText(this.applicationContext, "Error al tomar la fotografia!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        camera?.deleteImage()
    }
}
