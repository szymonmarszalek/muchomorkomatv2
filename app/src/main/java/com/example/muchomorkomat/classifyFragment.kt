package com.example.muchomorkomat

import android.Manifest
import android.app.Activity
import android.app.AlertDialog

import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.muchomorkomat.database.AppDatabase
import com.example.muchomorkomat.database.MushroomDao
import com.example.muchomorkomat.database.MushroomEntity

import com.example.muchomorkomat.databinding.FragmentClassifyBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import recognizingViewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class classifyFragment : Fragment() {

    private var _binding: FragmentClassifyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: recognizingViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClassifyBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            confirm()
            //pickImageGallery()
        }


    }

    private fun storagePermissionsGranted() = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).all{
        ContextCompat.checkSelfPermission(
            requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun pickImageGallery() {
        if(storagePermissionsGranted()){
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            getImage.launch(intent)
        }
        else{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            getImage.launch(intent)
        }
    }
    @OptIn(DelicateCoroutinesApi::class)
    private val getImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val database = AppDatabase.create(requireContext())
        if (it.resultCode == Activity.RESULT_OK) {
            val selectedImage = it.data?.data
            val result=viewModel.classifyMushroom(selectedImage!!,requireContext())
            setPhotoAndClassify(selectedImage,result[0].label.toString())
            GlobalScope.launch {
                database.mushroomDao().insert(MushroomEntity(0,result[0].label))
                println(database.mushroomDao().getAll())
            }

        }
    }
    private fun setPhotoAndClassify(photoUri: Uri?, mushroom: String?){
        binding.results.setPhoto(photoUri)
        binding.results.setClassifiedResult(mushroom)
    }

    private fun confirm() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Tak"){ _, _ ->
            pickImageGallery()
        }
        builder.setNegativeButton("Nie"){ _, _ ->}
        builder.setMessage("Wynik aplikacji nie daje pełnej gwarancji, jest tylko sugestią. Aplikacja nie ponosi odpowiedzialności za dalsze podjęte przez użytkownika decyzje. Czy potwierdzasz, że chcesz przejść do klasyfikacji zdjęcia?")

        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS=101
    }


}