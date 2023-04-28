package com.example.memesmania

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {
    lateinit var urll:String
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        getMemeData()
        val share=findViewById<Button>(R.id.share)
        share.setOnClickListener {
            share()
        }
    }

    private fun getMemeData()
    {
        val image=findViewById<ImageView>(R.id.image)
        val pb=findViewById<CardView>(R.id.pb)
        pb.visibility=View.VISIBLE
        RetrofitInstance.apiInterface.getData()
            .enqueue(object :retrofit2.Callback<responseModel> {
                override fun onResponse(
                    call: Call<responseModel>,
                    response: Response<responseModel>)
                {

                    Glide.with(this@MainActivity)
                        .load(response.body()?.url)
                        .into(image)
                    urll= response.body()?.url.toString()
                    pb.visibility=View.GONE

                }
                override fun onFailure(call: Call<responseModel>, t: Throwable) {
                    pb.visibility=View.GONE
                    Toast.makeText(this@MainActivity,"${t.localizedMessage}",Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun next(view: View) {
        getMemeData()
    }

    fun share() {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey checkout this cool meme ${urll}")
        val chooser=Intent.createChooser(intent,"Share this meme using....")
        startActivity(chooser)

    }
}