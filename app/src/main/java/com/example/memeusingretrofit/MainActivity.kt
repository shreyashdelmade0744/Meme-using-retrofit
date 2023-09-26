package com.example.memeusingretrofit

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.memeusingretrofit.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
// https://meme-api.com/gimme
    lateinit var binding :ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    getData()
        binding.changebtn.setOnClickListener {
            getData()
        }

    }

    private fun getData() {

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("please wait while data is fetching")
        progressDialog.show()




        RetrofitInstance.apiInterface.getData().enqueue(object : Callback<responseDataClass?> {
            override fun onResponse(
                call: Call<responseDataClass?>,
                response: Response<responseDataClass?>
            ) {
                //data mile toh kya karna hai
                binding.title.text = response.body()?.title
                binding.author.text = response.body()?.author
                Glide.with(this@MainActivity).load(response.body()?.url).into(binding.memeimg)

                progressDialog.dismiss()


            }

            override fun onFailure(call: Call<responseDataClass?>, t: Throwable) {
                //data nahi mila toh kya karna hai
                Toast.makeText(this@MainActivity, "${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        })
    }
}