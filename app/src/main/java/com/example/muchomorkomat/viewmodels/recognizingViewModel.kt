

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.*

import com.example.muchomorkomat.ml.Model1

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.label.Category
import java.lang.IllegalArgumentException

class recognizingViewModel(application: Application) : AndroidViewModel(application){



    fun classifyMushroom(uri: Uri, context: Context): List<Category> {
        if(Build.VERSION.SDK_INT >= 29) {
            val source: ImageDecoder.Source = ImageDecoder.createSource(
                context.contentResolver,
                uri
            )
            var bitmap: Bitmap = ImageDecoder.decodeBitmap(source)
            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
            val model = Model1.newInstance(context)
            val outputs = model.process(TensorImage.fromBitmap(bitmap))
            return getClassification(outputs.probabilityAsCategoryList)
        }
        else {
            @Suppress("DEPRECATION")
            var bitmap=MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
            val model = Model1.newInstance(context)
            val outputs = model.process(TensorImage.fromBitmap(bitmap))
            return getClassification(outputs.probabilityAsCategoryList)
        }

    }

    private fun getClassification(result:MutableList<Category>): List<Category> {
        val sortedResults=result.sortedByDescending { it.score }
        return sortedResults.take(1)
    }


}

class recognizingViewModelFactory(private val application: Application)
    : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(recognizingViewModel::class.java)) {
            return recognizingViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}